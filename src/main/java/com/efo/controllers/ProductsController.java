package com.efo.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Products;
import com.efo.service.ProductsService;

@Controller
@RequestMapping("/admin/")
public class ProductsController {
	private final String pageLink = "/admin/prpaging";

	PagedListHolder<Products> prList;

	@Value("${efo.upload.repository}")
	private String uploadrepository;

	@Value("${efo.download.repository}")
	private String downloadrepository;

	@Autowired
	ProductsService productsService;

	@RequestMapping("newproduct")
	public String newProduct(Model model) {

		model.addAttribute("product", new Products());

		return "newproduct";
	}

	@RequestMapping("addproduct")
	public String addProduct(@Valid @ModelAttribute("product") Products product, BindingResult result) throws IOException {
		File file = null;

		if (result.hasErrors() || product.getProduct_file().isEmpty()) {

			if (product.getProduct_file().isEmpty()) {
				result.rejectValue("product_file", "NotBlank.product.product_file");
			}

			return "newproduct";
		}

		if (product.getProduct_id().toUpperCase().startsWith("EFO") == false) {
			product.setProduct_id("EFO" + product.getProduct_id());
		}

		String type = product.getProduct_file().getOriginalFilename();
		type = type.substring(type.lastIndexOf('.'));

		InputStream is = product.getProduct_file().getInputStream();

		File f1 = new File(uploadrepository);

		file = File.createTempFile("upl", type, f1);
		product.setFile_name(file.getName());
		FileOutputStream fos = new FileOutputStream(file);

		int data = 0;
		while ((data = is.read()) != -1) {
			fos.write(data);
		}

		fos.close();
		is.close();

		productsService.create(product);

		return "redirect:/admin/manageproducts";
	}

	@RequestMapping("scproductedit")
	public String scProductEdit(@ModelAttribute("id") String id, Model model) {

		model.addAttribute("product", productsService.retrieve(id));

		return "scproductedit";
	}

	@RequestMapping("updatescproduct")
	public String updateSCProduct(@Valid @ModelAttribute("product") Products product, BindingResult result) {
		if (result.hasErrors()) {
			return "scproductedit";
		}

		productsService.update(product);

		return "redirect:/admin/manageproducts";
	}

	@RequestMapping("scproductdelete")
	public String scProductDelete(@ModelAttribute("id") String id) {

		productsService.delete(id);

		return "redirect:/admin/manageproducts";
	}

	@RequestMapping("manageproducts")
	public String manageProduct(Model model) {

		prList = productsService.retrieveList();

		prList.setPage(0);
		prList.setPageSize(30);

		model.addAttribute("objectList", prList);
		model.addAttribute("pagelink", pageLink);

		return "manageproducts";
	}

	@RequestMapping(value = "prpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			prList.nextPage();
		} else if ("prev".equals(page)) {
			prList.previousPage();
		} else if (pgNum != -1) {
			prList.setPage(pgNum);
		}
		model.addAttribute("objectList", prList);
		model.addAttribute("pagelink", pageLink);

		return "manageproducts";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}

}
