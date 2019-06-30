package com.efo.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.efo.component.ProfileUtils;
import com.efo.component.SendEmail;
import com.efo.dao.InvoiceNumDao;
import com.efo.entity.Products;
import com.efo.entity.Profiles;
import com.efo.entity.ShoppingCart;
import com.efo.entity.ShoppingCartItems;
import com.efo.entity.Transactions;
import com.efo.entity.User;
import com.efo.forms.PrintSalesReceiptPDF;
import com.efo.payment.BraintreeGatewayFactory;
import com.efo.payment.Checkout;
import com.efo.service.FetalTransactionService;
import com.efo.service.ProductsService;
import com.efo.service.ProfilesService;
import com.efo.service.ShoppingCartItemsService;
import com.efo.service.ShoppingCartService;
import com.efo.service.TransactionsService;
import com.efo.service.UserService;

@Controller
public class ShoppingCartController {
	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ShoppingCartItemsService cartItemsService;

	@Autowired
	private InvoiceNumDao invoiceNumService;

	@Autowired
	private ProductsService productsService;

	@Autowired
	private Checkout checkout;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private PrintSalesReceiptPDF salesReceipt;

	@Autowired
	private TransactionsService transactionsService;

	@Autowired
	private FetalTransactionService fetalTransactionService;

	@Autowired
	private ProfilesService profilesService;

	@Value("${efo.federal.taxRate}")
	private String taxRate;

	@Value("${efo.upload.repository}")
	private String uploadrepository;

	@Value("${efo.download.repository}")
	private String downloadrepository;

	@Value("${efo.payment.gateway}")
	private String gateway;

	@Value("${efo.config.url}")
	private String configUrl;

	@Value("${efo.documentsPath}")
	private String documentsPath;

	@Value("${efo.resourcesPath}")
	private String resourcesPath;

	@Value("${efo.admin.email}")
	private String csrEmail;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
		binder.registerCustomEditor(ArrayList.class, new CustomCollectionEditor(ArrayList.class, true));
	}

	@RequestMapping("/user/displayefoprd")
	public String displayEfoProduct(@ModelAttribute("prdId") String prdId, Model model, Principal principal)
			throws SecurityException, IllegalArgumentException, MalformedURLException, IOException {

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
		BraintreeGateway gateway = BraintreeGatewayFactory.fromConfigFile(new URL(configUrl));

		model.addAttribute("clientToken", gateway.clientToken().generate());
		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("product", products);

		return "displayefoprd";
	}

	@RequestMapping("/index/thankyou")
	public String thankyou(@ModelAttribute("shoppingCart") ShoppingCart shoppingCart, Model model) throws Exception {
		final String msg = "Thank you for your recent purchase with EFO. You will find your sales receipt attached to this email.";
		if (shoppingCart.getUser_id() == null){
			return "redirect:/index/introduction-a";
		}
		Object[] variables = null;

		User user = userService.retrieve(shoppingCart.getUser_id());
		List<ShoppingCartItems> items = cartItemsService.retrieveRawList(shoppingCart.getReference());
		shoppingCart.setShoppingCartItems(new HashSet<ShoppingCartItems>(items));

		Double price = totalPrice(shoppingCart);
		if (price > 0.0) {
			Transactions transaction = new Transactions();

			transaction.setTimestamp(new Date());
			transaction.setAmount(price);
			transaction.setTax(totalTax(shoppingCart));
			transaction.setPayment_name(getFirstName(user) + " " + getLastName(user));
			transaction.setUser_id(user.getUser_id());
			transaction.setStart(transaction.getTimestamp());
			transaction.setName("Retail Sales (Cash)");
			transaction.setDescr("Internet purchase of software");

			String profileName = transaction.getName();
			Profiles profile = profilesService.retrieve(profileName);

			if ("".compareTo(profile.getVariables()) != 0) {
				variables = ProfileUtils.getObject(ProfileUtils.prepareVariableString("%tax%", taxRate, profile.getVariables()));
			}

			fetalTransactionService.execTransaction(profile, transaction, variables);

			transactionsService.create(transaction);
		}
		shoppingCartService.closeCart(shoppingCart.getReference());

		String pdfFile = salesReceipt.print(shoppingCart);
		sendEmail.sendHtmlMailWithAttachment(csrEmail, user.getUsername(), getFirstName(user) + " " + getLastName(user), "Sales Receipt", msg,
				resourcesPath + pdfFile);

		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("repository", downloadrepository);

		return "thankyou";
	}

	@RequestMapping("/index/crediterror")
	public String creditError(@ModelAttribute("message") String message, Model model) {

		model.addAttribute("message", message);

		return "crediterror";
	}

	@RequestMapping("/user/processorder")
	public String processOrder(@ModelAttribute("shoppingCart") ShoppingCart shoppingCart, @ModelAttribute("payment_method_nonce") String nonce, Model model,
			RedirectAttributes rm) throws SecurityException, IllegalArgumentException, MalformedURLException, IOException {

		User user = userService.retrieve(shoppingCart.getUser_id());

		BraintreeGateway gateway = BraintreeGatewayFactory.fromConfigFile(new URL(configUrl));
		BigDecimal amount = new BigDecimal(totalShoppingCart(shoppingCart));
		if (amount.doubleValue() > 0.0) {
			Result<Transaction> result = checkout.braintreePayment(user, shoppingCart, gateway, amount, nonce);

			if (result.isSuccess()) {

				rm.addFlashAttribute("shoppingCart", shoppingCart);

				return "redirect:/index/thankyou";
			} else {

				rm.addAttribute("message", result.getMessage());

				return "redirect:/index/crediterror";
			}
		} else {

			rm.addFlashAttribute("shoppingCart", shoppingCart);

			return "redirect:/index/thankyou";
		}

	}

	@RequestMapping("/user/shoppingcart")
	public String shoppingCart(Model model, Principal principal) throws SecurityException, IllegalArgumentException, MalformedURLException, IOException {
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

		BraintreeGateway gateway = BraintreeGatewayFactory.fromConfigFile(new URL(configUrl));

		model.addAttribute("clientToken", gateway.clientToken().generate());
		model.addAttribute("shoppingCart", shoppingCart);

		return "shoppingcart";
	}

	private Double totalShoppingCart(ShoppingCart cart) {
		Double total = 0.0;
		List<ShoppingCartItems> items = cartItemsService.retrieveRawList(cart.getReference());

		for (ShoppingCartItems item : items) {
			Double price = ((item.getProduct_price() * item.getQty()) - item.getProduct_discount());
			total += price;
		}

		return total;
	}

	private String getFirstName(User user) {
		String firstName = "";
		if (user != null) {
			if (user.getCustomer() != null) {
				firstName = user.getCustomer().getFirstname();
			} else if (user.getEmployee() != null) {
				firstName = user.getEmployee().getFirstname();
			} else if (user.getInvestor() != null) {
				firstName = user.getInvestor().getFirstname();
			} else if (user.getVendor() != null) {
				firstName = user.getVendor().getFirstname();
			}
		}
		return firstName;
	}

	private String getLastName(User user) {
		String lastName = "";
		if (user != null) {
			if (user.getCustomer() != null) {
				lastName = user.getCustomer().getLastname();
			} else if (user.getEmployee() != null) {
				lastName = user.getEmployee().getLastname();
			} else if (user.getInvestor() != null) {
				lastName = user.getInvestor().getLastname();
			} else if (user.getVendor() != null) {
				lastName = user.getVendor().getLastname();
			}
		}
		return lastName;
	}

	private Double totalPrice(ShoppingCart shoppingCart) {
		Double total = 0.0;

		Set<ShoppingCartItems> items = shoppingCart.getShoppingCartItems();

		for (ShoppingCartItems item : items) {
			total += (item.getProduct_price() * item.getQty()) - item.getProduct_discount();
		}

		return total;
	}
	private Double totalTax(ShoppingCart shoppingCart) {
		Double total = 0.0;

		Set<ShoppingCartItems> items = shoppingCart.getShoppingCartItems();

		for (ShoppingCartItems item : items) {
			total += item.getProduct_tax();
		}

		return total;
	}

}
