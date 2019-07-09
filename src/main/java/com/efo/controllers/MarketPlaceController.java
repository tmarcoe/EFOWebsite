package com.efo.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braintreegateway.BraintreeGateway;
import com.efo.dao.InvoiceNumDao;
import com.efo.entity.MarketPlaceProducts;
import com.efo.entity.MarketPlaceVendors;
import com.efo.entity.ShoppingCart;
import com.efo.entity.User;
import com.efo.forms.PrintMarketPlaceSalesPDF;
import com.efo.payment.BraintreeGatewayFactory;
import com.efo.service.MarketPlaceProductsService;
import com.efo.service.MarketPlaceVendorsService;
import com.efo.service.ShoppingCartService;
import com.efo.service.UserService;

@Controller
public class MarketPlaceController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MarketPlaceVendorsService marketPlaceVendorsService;
	
	@Autowired
	private MarketPlaceProductsService marketPlaceProductsService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private InvoiceNumDao invoiceNumService;
	
	@Autowired
	PrintMarketPlaceSalesPDF mpSalesReport;
	
	@Value("${efo.upload.logo}")
	private String uploadLogo;
	
	@Value("${efo.download.logo}")
	private String downloadLogo;
	
	@Value("${efo.upload.repository}")
	private String uploadrepository;
	
	@Value("${efo.download.repository}")
	private String downloadrepository;
	
	@Value("${efo.payment.gateway}")
	private String gateway;
	
	@Value("${efo.config.url}")
	private String configUrl;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}
	
	@RequestMapping("/admin/managempvendors")
	public String manageMarketPlaceVendors(Model model) {
		
		model.addAttribute("vendorList", marketPlaceVendorsService.retrieveRawList());
		
		return "managempvendors";
	}
	
	@RequestMapping("/admin/individualreport")
	public String individualReport(@ModelAttribute("id") Long id, Model model) throws IOException {
		
		mpSalesReport.print(marketPlaceVendorsService.retrieve(id));
		
		return "redirect:/admin/managempvendors";
	}

	@RequestMapping("/user/marketplaceregister")
	public String MarketPlaceRegister(Model model, Principal principal) {
		MarketPlaceVendors marketPlaceVendors = null;
		MarketPlaceProducts marketPlaceProducts = null;
		
		if (principal != null) {
			User user = userService.retrieve(principal.getName());
			marketPlaceVendors = marketPlaceVendorsService.retrieveByUserId(user.getUser_id());
			
			marketPlaceProducts = new MarketPlaceProducts();
			marketPlaceProducts.setFirstTime(false);
			
			if (marketPlaceVendors == null ) {
				marketPlaceVendors = new MarketPlaceVendors();
				marketPlaceVendors.setUser_id(user.getUser_id());
				marketPlaceVendors.setSales_total(0.0);
				marketPlaceVendors.setCommission_total(0.0);
				assignName(marketPlaceVendors, user);
				marketPlaceVendorsService.create(marketPlaceVendors);
				marketPlaceProducts.setFirstTime(true);
			}else if ( marketPlaceVendors.getMarketPlaceProducts().size() == 0){
				marketPlaceProducts.setFirstTime(true);
			}
			
			
			marketPlaceProducts.setReference(marketPlaceVendors.getReference());
			marketPlaceProducts.setIntroduced_on(new Date());
		}
		
		model.addAttribute("marketPlaceProduct", marketPlaceProducts );
		
		return "marketplaceregister";
	}
	@RequestMapping("/user/cancelregistry")
	public String cancelRegistry(@ModelAttribute("reference") Long reference) {
		MarketPlaceVendors mpv = marketPlaceVendorsService.retrieve(reference);
		
		if (mpv.getMarketPlaceProducts().size() == 0) {
			marketPlaceVendorsService.delete(reference);
		}
		
		return "redirect:/index/introduction-a";
	}
	@RequestMapping("/user/addregistry")
	public String addRegistry(@Valid @ModelAttribute("marketPlaceProduct") MarketPlaceProducts marketPlaceProduct, BindingResult result) throws IOException {
		File file = null;
		
		if (result.hasErrors() || marketPlaceProduct.getFile().isEmpty()) {
			if (marketPlaceProduct.getFile().isEmpty()) {
				result.rejectValue("file", "NotBlank.marketPlaceProduct.file");
			}
			return "marketplaceregister";
		}
		
		MarketPlaceVendors marketPlaceVendors = marketPlaceVendorsService.retrieve(marketPlaceProduct.getReference());
		if (marketPlaceProduct.isFirstTime()) {
			marketPlaceVendors.setCompany_name(marketPlaceProduct.getCompany_name());
			if (marketPlaceProduct.getCompany_logo().isEmpty() == false) {
				InputStream is = marketPlaceProduct.getCompany_logo().getInputStream();

				File f1 = new File(uploadLogo);

				file = File.createTempFile("img", ".png", f1);
				marketPlaceVendors.setLogo(file.getName());
				FileOutputStream fos = new FileOutputStream(file);

				int data = 0;
				while ((data = is.read()) != -1) {
					fos.write(data);
				}

				fos.close();
				is.close();
			}
		}
		
		String type = marketPlaceProduct.getFile().getOriginalFilename();
		type = type.substring(type.lastIndexOf('.'));

		InputStream is = marketPlaceProduct.getFile().getInputStream();

		File f1 = new File(uploadrepository);

		file = File.createTempFile("upl", type, f1);
		marketPlaceProduct.setFile_name(file.getName());
		FileOutputStream fos = new FileOutputStream(file);

		int data = 0;
		while ((data = is.read()) != -1) {
			fos.write(data);
		}

		fos.close();
		is.close();
		
		marketPlaceVendors.getMarketPlaceProducts().add(marketPlaceProduct);
		marketPlaceProduct.setMarketPlaceVendors(marketPlaceVendors);
		
		marketPlaceVendorsService.merge(marketPlaceVendors);
		
		return "redirect:/index/introduction-a";
	}
	
	@RequestMapping("/index/viewmarketplace")
	public String viewMarketPlace(Model model, Principal principal) {
		Long uid = 0L;
		
		List<MarketPlaceProducts> mpList = marketPlaceProductsService.retrieveRawList();
		if (principal != null) {
			User user = userService.retrieve(principal.getName());
			uid = user.getUser_id();
		}
		
		model.addAttribute("userId", uid);
		model.addAttribute("logoPath", downloadLogo);
		model.addAttribute("mpList", mpList);
		
		return "viewmarketplace";
	}
	
	@RequestMapping("/index/marketplacesearch")
	public String marketPlaceSearch(@ModelAttribute("search") String search, Model model, Principal principal) {
		Long uid = 0L;
		
		if (principal != null) {
			User user = userService.retrieve(principal.getName());
			uid = user.getUser_id();
		}
		
		List<MarketPlaceProducts> mpList = marketPlaceProductsService.keywordSearch(search);
		
		model.addAttribute("userId", uid);
		model.addAttribute("logoPath", downloadLogo);
		model.addAttribute("mpList", mpList);
		
		return "viewmarketplace";
	}
	
	@RequestMapping("/user/editmpproduct")
	public String editMarketPlaceProduct(@ModelAttribute("product_reference") Long product_reference, Model model, Principal principal) {
		MarketPlaceProducts marketPlaceProduct = marketPlaceProductsService.retrieve(product_reference);

		model.addAttribute("marketPlaceProduct", marketPlaceProduct);
		
		return "editmpproduct";
	}
	
	@RequestMapping("/user/updatempproduct")
	public String updateMarketPlaceProduct(@Valid @ModelAttribute("marketPlaceProduct") MarketPlaceProducts marketPlaceProduct, BindingResult result) {
		
		if (result.hasErrors()) {
			
			return "editmpproduct";
		}
		
		marketPlaceProductsService.merge(marketPlaceProduct);
		
		return "redirect:/index/viewmarketplace";
	}
	
	@RequestMapping("/user/displayprd/{prdNumber}")
	public String displayPrd(@PathVariable("prdNumber") String prdNumber, Model model, Principal principal) throws SecurityException, IllegalArgumentException, MalformedURLException, IOException {
		
		User user = userService.retrieve(principal.getName());
		ShoppingCart shoppingCart = shoppingCartService.retrieveByUserId(user.getUser_id());
		
		if (shoppingCart.getTime_ordered() == null) {
			// This is a new Shopping Cart
			shoppingCart.setReference(invoiceNumService.getNextKey());
			shoppingCart.setUser_id(user.getUser_id());
			shoppingCart.setTime_ordered(new Date());
			shoppingCart.setPayment_gateway(gateway);
			shoppingCartService.create(shoppingCart);
		}
		
		MarketPlaceProducts marketPlaceProduct = marketPlaceProductsService.retrieve(Long.valueOf(prdNumber));
		BraintreeGateway gateway = BraintreeGatewayFactory.fromConfigFile(new URL(configUrl));
		
		model.addAttribute("clientToken", gateway.clientToken().generate());
		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("product", marketPlaceProduct);
		model.addAttribute("logoPath", downloadLogo);
		
		return "displayprd";
	}
	
	@RequestMapping("/user/deletempproduct")
	public String deleteMarketPlaceProduct(@ModelAttribute("product_reference") Long product_reference) {
		
		MarketPlaceProducts product = marketPlaceProductsService.retrieve(product_reference);
		
		File file = new File(uploadrepository + product.getFile_name());
		
		file.delete();
		
		marketPlaceProductsService.delete(product_reference);
		
		return "redirect:/index/viewmarketplace";
	}
	
	private void assignName(MarketPlaceVendors maketPlaceVendor, User user)  {
		if (user.getCustomer() != null){
			maketPlaceVendor.setName(user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname());
		}else if (user.getEmployee() != null) {
			maketPlaceVendor.setName(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
		}else if (user.getVendor() != null) {
			maketPlaceVendor.setName(user.getVendor().getFirstname() + " " + user.getVendor().getLastname());
		}else if (user.getInvestor() != null) {
			maketPlaceVendor.setName(user.getInvestor().getFirstname() + " " + user.getInvestor().getLastname());
		}else{
			maketPlaceVendor.setName("");
		}		
	}
	

}
