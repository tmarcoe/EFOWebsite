package com.efo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index/")
public class WebsiteDownloadController {
	
	@RequestMapping("documentation")
	public String download() {
		
		return "documentation";
	}

}
