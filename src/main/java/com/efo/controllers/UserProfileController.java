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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String addUserProfile(@Valid @ModelAttribute("user") User user, BindingResult result) throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException {
		
		if (result.hasErrors() || 
				userService.exists(user.getUsername()) ||
				"".compareTo(user.getCustomer().getSalutation()) == 0 ||
				"".compareTo(user.getCustomer().getFirstname()) == 0 ||
				"".compareTo(user.getCustomer().getLastname()) == 0 || 
				"".compareTo(user.getCustomer().getMaleFemale()) == 0 ||
				"".compareTo(user.getCommon().getAddress1()) == 0 ||
				"".compareTo(user.getCommon().getCity()) == 0 ||
				"".compareTo(user.getCommon().getCountry()) == 0) {
			
			if (userService.exists(user.getUsername())) result.rejectValue("username", "DuplicateKey.user.username");
			if ("".compareTo(user.getCustomer().getSalutation()) == 0 ) result.rejectValue("customer.salutation", "NotBlank.customer.salutation");
			if ("".compareTo(user.getCustomer().getFirstname()) == 0 ) result.rejectValue("customer.firstname", "NotBlank.customer.firstname");
			if ("".compareTo(user.getCustomer().getLastname()) == 0 ) result.rejectValue("customer.lastname", "NotBlank.customer.lastname");
			if ("".compareTo(user.getCustomer().getMaleFemale()) == 0 ) result.rejectValue("customer.maleFemale", "NotBlank.customer.maleFemale");
			if ("".compareTo(user.getCommon().getAddress1()) == 0 ) result.rejectValue("common.address1", "NotBlank.common.address1");
			if ("".compareTo(user.getCommon().getCity()) == 0 ) result.rejectValue("common.city", "NotBlank.common.city");
			if ("".compareTo(user.getCommon().getCountry()) == 0 ) result.rejectValue("common.country", "NotBlank.common.country");
			
			return "createuserprofile";
		}

		user.setRoles(new HashSet<Role>());
		user.getRoles().add(roleService.retrieve("USER"));
		user.getCustomer().setUser(user);
		user.getCustomer().setSince(new Date());
		user.setCommon(new CommonFields());
		user.getCommon().setUser(user);

		userService.create(user);

		Verify verify = new Verify();
		String stId = userEncryption.emailAddressEncryption(user.getCustomer().getFirstname() + user.getCustomer().getLastname());
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

		return "edituserprofile";
	}

	@RequestMapping("/user/updateuserprofile")
	public String updateUserProfile(@Valid @ModelAttribute("user") User user, BindingResult result) {
		
		if (result.hasErrors() || 
				"".compareTo(user.getCustomer().getSalutation()) == 0 ||
				"".compareTo(user.getCustomer().getFirstname()) == 0 ||
				"".compareTo(user.getCustomer().getLastname()) == 0 || 
				"".compareTo(user.getCustomer().getMaleFemale()) == 0 ||
				"".compareTo(user.getCommon().getAddress1()) == 0 ||
				"".compareTo(user.getCommon().getCity()) == 0 ||
				"".compareTo(user.getCommon().getCountry()) == 0) {
			
			if ("".compareTo(user.getCustomer().getSalutation()) == 0 ) result.rejectValue("customer.salutation", "NotBlank.customer.salutation");
			if ("".compareTo(user.getCustomer().getFirstname()) == 0 ) result.rejectValue("customer.firstname", "NotBlank.customer.firstname");
			if ("".compareTo(user.getCustomer().getLastname()) == 0 ) result.rejectValue("customer.lastname", "NotBlank.customer.lastname");
			if ("".compareTo(user.getCustomer().getMaleFemale()) == 0 ) result.rejectValue("customer.maleFemale", "NotBlank.customer.maleFemale");
			if ("".compareTo(user.getCommon().getAddress1()) == 0 ) result.rejectValue("common.address1", "NotBlank.common.address1");
			if ("".compareTo(user.getCommon().getCity()) == 0 ) result.rejectValue("common.city", "NotBlank.common.city");
			if ("".compareTo(user.getCommon().getCountry()) == 0 ) result.rejectValue("common.country", "NotBlank.common.country");
			
			return "edituserprofile";
		}

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
