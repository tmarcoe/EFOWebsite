package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.dao.InvoiceNumDao;
import com.efo.entity.Products;
import com.efo.entity.ShoppingCart;
import com.efo.entity.User;
import com.efo.service.ProductsService;
import com.efo.service.ShoppingCartService;
import com.efo.service.UserService;

@Controller
public class ProductController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private InvoiceNumDao invoiceNumService;
	
	@Value("${efo.upload.repository}")
	private String uploadrepository;
	
	@Value("${efo.download.repository}")
	private String downloadrepository;
	
	@Value("${efo.payment.gateway}")
	private String gateway;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}

	@RequestMapping("/index/efo")
	public String efo() {
		
		return "efo";
	}
	
	@RequestMapping("/index/ftl")
	public String ftl() {
		
		return "ftl";
	}

	@RequestMapping("/index/ftlide")
	public String ftlIde() {
		
		return "ftlide";
	}
	
	@RequestMapping("/user/displayefoprd")
	public String displayEfoProduct(@ModelAttribute("prdId") String prdId, Model model, Principal principal) {
		
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
		
		Products products = productsService.retrieve(prdId);
		
		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("product", products);

		
		return "displayefoprd";
	}
}
