package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.entity.MarketPlaceProducts;
import com.efo.entity.MarketPlaceVendors;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.service.MarketPlaceVendorsService;
import com.efo.service.RoleService;
import com.efo.service.UserService;

@Controller
public class MarketPlaceController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MarketPlaceVendorsService marketPlaceVendorsService;
	
	@Autowired
	private RoleService roleService;
		
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}

	@RequestMapping("/user/marketplaceregister")
	public String MarketPlaceRegister(Model model, Principal principal) {
		MarketPlaceVendors marketPlaceVendors = null;
		MarketPlaceProducts marketPlaceProducts = null;
		if (principal != null) {
			User user = userService.retrieve(principal.getName());
			marketPlaceVendors = new MarketPlaceVendors();
			marketPlaceVendors.setUser_id(user.getUser_id());
			marketPlaceVendors.setTotal_sales(0.0);
			marketPlaceVendors.setCommission_paid(0.0);
			assignName(marketPlaceVendors, user);
			marketPlaceVendorsService.create(marketPlaceVendors);
			
			marketPlaceProducts = new MarketPlaceProducts();
			marketPlaceProducts.setReference(marketPlaceVendors.getReference());
			marketPlaceProducts.setIntroduced_on(new Date());
		}
		
		model.addAttribute("marketPlaceProduct", marketPlaceProducts );
		
		return "marketplaceregister";
	}
	
	@RequestMapping("/user/addregistry")
	public String addRegistry(@ModelAttribute("marketPlaceProduct") MarketPlaceProducts marketPlaceProduct) {
		
		MarketPlaceVendors marketPlaceVendors = marketPlaceVendorsService.retrieve(marketPlaceProduct.getReference());
		marketPlaceVendors.getMarketPlaceProducts().add(marketPlaceProduct);
		marketPlaceProduct.setMarketPlaceVendors(marketPlaceVendors);
		User user = userService.retrieve(marketPlaceVendors.getUser_id());
		Role role = roleService.retrieve("MARKETPLACE");
		
		user.getRoles().add(role);
		userService.merge(user);
		
		marketPlaceVendorsService.merge(marketPlaceVendors);
		
		return "redirect:/index/introduction-a";
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
