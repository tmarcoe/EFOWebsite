package com.efo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
	

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


}
