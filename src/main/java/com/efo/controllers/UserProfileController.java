package com.efo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.component.PasswordGenerator;
import com.efo.component.RoleUtilities;
import com.efo.component.SendEmail;
import com.efo.component.UserEncryption;
import com.efo.emailForms.PasswordResetEmailForm;
import com.efo.emailForms.VerifyEmailForm;
import com.efo.entity.CommonFields;
import com.efo.entity.Customer;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.entity.Verify;
import com.efo.service.RoleService;
import com.efo.service.UserService;
import com.efo.service.VerifyService;

@Controller
public class UserProfileController {

	@Value("${efo.upload.profileimages}")
	private String uploadProfileImage;

	@Value("${efo.download.profileimages}")
	private String downloadProfileImage;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleUtilities roleUtilities;

	@Autowired
	private VerifyEmailForm verifyEmailForm;
	
	@Autowired
	PasswordResetEmailForm passwordResetEmailForm;

	@Autowired
	private SendEmail sendEmail;
	
	@Autowired
	private UserEncryption userEncryption;

	@Autowired
	private VerifyService verifyService;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}

	@RequestMapping("/index/createuserprofile")
	public String createUserProfile(Model model) {

		User user = new User();
		user.setCustomer(new Customer());

		model.addAttribute("user", user);

		return "createuserprofile";
	}

	@RequestMapping("/index/adduserprofile")
	public String addUserProfile(@ModelAttribute("user") User user) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, MessagingException {

		user.setRoles(new HashSet<Role>());
		user.getRoles().add(roleService.retrieve("USER"));
		user.getCustomer().setUser(user);
		user.getCustomer().setSince(new Date());
		user.setCommon(new CommonFields());
		user.getCommon().setUser(user);
		
		userService.create(user);

		Verify verify = new Verify();
		String stId = userEncryption.emailAddressEncryption(user.getCustomer().getFirstname()+user.getCustomer().getLastname());
		verify.setId(stId);
		verify.setUser_id(user.getUser_id());
		verifyService.Create(verify);

		String email = verifyEmailForm.createVerifyEmail(user, stId, user.getPassword());
		sendEmail.sendHtmlMail("tmtmarcoe80@gmail.com", user.getUsername(), user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname(),
				"Verify your email", email);

		return "notify";
	}

	@RequestMapping("/user/edituserprofile")
	public String editUserProfile(Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());

		user.setRoleString(roleUtilities.roleToString(user.getRoles()));
		model.addAttribute("user", user);
		model.addAttribute("imageDir", downloadProfileImage);

		return "edituserprofile";
	}

	@RequestMapping("/user/updateuserprofile")
	public String updateUserProfile(@ModelAttribute("user") User user) {

		user.setRoles(roleUtilities.stringToRole(user.getRoleString()));
		user.getCustomer().setUser(user);
		user.getCommon().setUser(user);

		userService.merge(user);


		return "redirect:/index/introduction-a";
	}

	@RequestMapping("/index/verifyemail")
	public String verifyEmail(@ModelAttribute("id") String id, @ModelAttribute("uid") Long uid) {
		Verify verify = verifyService.retrieve(id);

		if (verify != null) {
			if (id.compareTo(verify.getId()) == 0) {
				User user = userService.retrieve(uid);
				user.setEnabled(true);
				userService.merge(user);
				verifyService.delete(verify);
			}
		}

		return "verifyemail";
	}
	
	@RequestMapping("/index/forgotmypassword")
	public String forgotMyPassword(@ModelAttribute("username") String username, Model model) throws UnsupportedEncodingException, MessagingException {
		User user = userService.retrieve(username);
		String password = PasswordGenerator.generate(8);
		user.setPassword(encoder.encode(password));
		userService.updatePassword(user);
		String content = passwordResetEmailForm.forgotPWEmailForm(password);
		
		sendEmail.sendHtmlMail("tmtmarcoe80@gmail.com", user.getUsername(), user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname(),
				"Important information about your account", content);
		
		return "forgotmypassword";
	}
	
	@RequestMapping("/user/changepassword")
	public String changePassword(Principal principal, Model model) {
		User user = userService.retrieve(principal.getName());
		
		model.addAttribute("user", user);
		
		return "changepassword";
	}
}
