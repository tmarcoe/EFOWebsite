package com.efo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index/")
public class ProductController {
	
	@RequestMapping("efo")
	public String efo() {
		
		return "efo";
	}
	
	@RequestMapping("ftl")
	public String ftl() {
		
		return "ftl";
	}

	@RequestMapping("ftlide")
	public String ftlIde() {
		
		return "ftlide";
	}
}
