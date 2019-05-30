package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.entity.Forum;
import com.efo.entity.ForumReplies;
import com.efo.entity.User;
import com.efo.service.ForumRepliesService;
import com.efo.service.ForumService;
import com.efo.service.UserService;

@Controller
public class ForumController {
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ForumRepliesService forumRepliesService;
	
	@Autowired
	UserService userService;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/user/createpost")
	public String createPost(Model model, Principal principal) {
		
		Forum forum = new Forum(new Date());
		User user = userService.retrieve(principal.getName());
		forum.setAuthor(user.getUser_id());
		assignName(forum, user);
		model.addAttribute("forum", forum);
		
		return "createpost";
	}
	
	@RequestMapping("/user/addPost")
	public String addPost(@ModelAttribute("forum") Forum forum) {
		
		forumService.create(forum);
		
		return "redirect:/index/viewPosts";
	}
	
	@RequestMapping("/index/viewPosts")
	public String viewPosts(Model model, Principal principal) {
		Long whoAmI = 0L;
		
		if (principal != null ) {
			User user = userService.retrieve(principal.getName());
			whoAmI = user.getUser_id();
		}
		
		model.addAttribute("whoAmI", whoAmI);
		model.addAttribute("forumList", forumService.retrieveRawList());
		
		return "viewPosts";
	}
	
	@RequestMapping("/user/replytopost")
	public String replyToPost(@ModelAttribute("reference") Long reference, 
							  @ModelAttribute("parent") Long parent,
							  @ModelAttribute("level") Long level, Model model, Principal principal) {
		
		User user = userService.retrieve(principal.getName());
		
		ForumReplies forumReply = new ForumReplies();
		forumReply.setReference(reference);
		forumReply.setLevel(level + 1);
		forumReply.setParent(parent);
		forumReply.setPost_created(new Date());
		forumReply.setAuthor(user.getUser_id());
		
		assignName(forumReply, user);
		
		model.addAttribute("forumReply", forumReply);
		
		return "replytopost";
	}
	
	@RequestMapping("/user/editpost")
	public String editPost(@ModelAttribute("reference") Long reference, Model model) {
		
		Forum forum = forumService.retrieve(reference);
		
		model.addAttribute("forum", forum);
		
		return "editpost";
	}
	
	@RequestMapping("/user/updatepost")
	public String updatePost(@ModelAttribute("forum") Forum forum) {
		
		forumService.merge(forum);
		
		return "redirect:/index/viewPosts";
	}
	
	@RequestMapping("/user/deletepost")
	public String deletePost(@ModelAttribute("reference") Long reference) {
		
		forumService.deletePost(reference);
		
		return "redirect:/index/viewPosts";
	}
	
	@RequestMapping("/user/deletereply")
	public String deleteReply(@ModelAttribute("id") Long id) {
		
		forumRepliesService.delete(id);
		
		return "redirect:/index/viewPosts";
	}
	
	@RequestMapping("/user/editreply")
	public String editReply(@ModelAttribute("id") Long id, Model model) {
		
		ForumReplies forumReply = forumRepliesService.retrieve(id);
		
		model.addAttribute("forumReply", forumReply);
		
		return "editreply";
	}
	
	@RequestMapping("/user/updatereply")
	public String editReply(@ModelAttribute("forumReply") ForumReplies forumReply) {
		forumRepliesService.update(forumReply);
		
		return "redirect:/index/viewPosts";
	}
	
	@RequestMapping("/user/addreply")
	public String addReply(@ModelAttribute("forumReply") ForumReplies forumReply) {
		
		Forum forum = forumService.retrieve(forumReply.getReference());
		forum.getForumReplies().add(forumReply);
		forumReply.setForum(forum);
		forumService.merge(forum);
		
		return "redirect:/index/viewPosts";
	}
	private void assignName(Forum forum, User user)  {
		if (user.getCustomer() != null){
			forum.setName(user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname());
		}else if (user.getEmployee() != null) {
			forum.setName(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
		}else if (user.getVendor() != null) {
			forum.setName(user.getVendor().getFirstname() + " " + user.getVendor().getLastname());
		}else if (user.getInvestor() != null) {
			forum.setName(user.getInvestor().getFirstname() + " " + user.getInvestor().getLastname());
		}else{
			forum.setName("");
		}		
	}
	
	private void assignName(ForumReplies forumReply, User user)  {
		if (user.getCustomer() != null){
			forumReply.setName(user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname());
		}else if (user.getEmployee() != null) {
			forumReply.setName(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
		}else if (user.getVendor() != null) {
			forumReply.setName(user.getVendor().getFirstname() + " " + user.getVendor().getLastname());
		}else if (user.getInvestor() != null) {
			forumReply.setName(user.getInvestor().getFirstname() + " " + user.getInvestor().getLastname());
		}else{
			forumReply.setName("");
		}		
	}

}
