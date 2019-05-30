package com.efo.emailForms;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import org.springframework.stereotype.Component;

import static j2html.TagCreator.a;
import com.efo.entity.User;

@Component
public class VerifyEmailForm {
	
	public String createVerifyEmail(User user, String id, String pw) {
		String result = html(
				
				head(
					style()	
					),
				body(
					h3("Welcome " + user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname()),
					h3("Please follow the link to verify your email address"),
					a("Verify Email").withHref("http://localhost:8080/index/verifyemail?id=" + id + "&uid=" + user.getUser_id())
				)).renderFormatted();
		
		return result;
	}
	
	

}
