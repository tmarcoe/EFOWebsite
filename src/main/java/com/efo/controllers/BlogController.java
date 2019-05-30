package com.efo.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Blogs;
import com.efo.service.BlogsService;

@Controller
public class BlogController {
	@Value("${efo.download.blogimages}")
	private String downloadPath;
	
	@Value("${efo.upload.blogimages}")
	private String uploadPath;
	
	private final String pageLink = "/admin/blogpaging";
	private PagedListHolder<Blogs> blogList;
	
	@Autowired
	private BlogsService blogsService;
	

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/index/viewblog")
	public String viewBlog(Model model) {
		
		
		model.addAttribute("blogList", blogsService.retreiveRawList());
		model.addAttribute("fileLoc", downloadPath);
		
		return "viewblog";
	}
	
	@RequestMapping("/admin/createblog")
	public String createBlog(Model model ) {
		Blogs blog = new Blogs();
		blog.setTimestamp(new Date());
		
		model.addAttribute("blog", blog);
		
		return "createblog";
	}
	
	@RequestMapping("/admin/addblog")
	public String addBlog(@ModelAttribute("blog") Blogs blog) throws IOException {
		File file = null;
		
		// String contentType = blog.getFile().getContentType();
		
		InputStream is = blog.getFile().getInputStream();

		File f1 = new File(uploadPath);

		file = File.createTempFile("img", ".png", f1);
		blog.setImage_file(file.getName());

		FileOutputStream fos = new FileOutputStream(file);

		int data = 0;
		while ((data = is.read()) != -1) {
			fos.write(data);
		}

		fos.close();
		is.close();
		
		blogsService.create(blog);
		
		return "redirect:/index/viewblog";
	}
	
	@RequestMapping("/admin/manageblogs")
	public String manageBlogs(Model model) {
		
		blogList = blogsService.retreiveList();
	
		model.addAttribute("objectList", blogList);
		model.addAttribute("pagelink", pageLink);

		return "manageblogs";
	}
	
	@RequestMapping("/admin/editblog")
	public String editBlog(@ModelAttribute("id") Long id, Model model) {
		
		Blogs blog = blogsService.retreive(id);
		model.addAttribute("blog", blog);
		
		return "editblog";
	}
	
	@RequestMapping("/admin/updateblog")
	public String updateBlog(@ModelAttribute("blog") Blogs blog) {
		
		blogsService.update(blog);
		
		return "redirect:/admin/manageblogs";
	}
	
	@RequestMapping("/admin/deleteblog")
	public String deleteBlog(@ModelAttribute("id") Long id) {
		
		Blogs blog = blogsService.retreive(id);
		
		File file = new File(uploadPath + blog.getImage_file());
		file.delete();
		
		blogsService.delete(blog.getId());
		
		return "redirect:/admin/manageblogs";
	}
	

	@RequestMapping(value = "/admin/blogpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			blogList.nextPage();
		} else if ("prev".equals(page)) {
			blogList.previousPage();
		} else if (pgNum != -1) {
			blogList.setPage(pgNum);
		}
		model.addAttribute("objectList", blogList);
		model.addAttribute("pagelink", pageLink);

		return "manageblogs";
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
