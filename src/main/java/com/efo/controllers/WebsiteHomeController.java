package com.efo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.entity.User;
import com.efo.service.UserService;

@Controller
@RequestMapping("/index/")
public class WebsiteHomeController {
	
	@Autowired
	UserService userService;

	@RequestMapping("")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("introduction-a")
	public String introductionA(Principal principal, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.retrieve(principal.getName());
			if (user == null) {
				user = new User();
				user.setUser_id((long) 0);
			}
		} else {
			user = new User();
			user.setUser_id((long) 0);
		}
		
		model.addAttribute("user", user);
		
		return "introduction-a";
	}
	
	@RequestMapping("introduction-b")
	public String introductionB() {
		
		return "introduction-b";
	}
	
	@RequestMapping("introduction-c")
	public String introductionC() {
		
		return "introduction-c";
	}
	@RequestMapping("introduction-d")
	public String introductionD() {
		
		return "introduction-d";
	}

	@RequestMapping("contactus")
	public String contactUs() {
		
		return "contactus";
	}
	
	@RequestMapping("aboutus")
	public String aboutUs() {
		
		return "aboutus";
	}

}
