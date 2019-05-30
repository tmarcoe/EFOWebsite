package com.efo.emailForms;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;

import org.springframework.stereotype.Component;

@Component
public class PasswordResetEmailForm {
	
	public String forgotPWEmailForm(String password) {
		String result = html(
				
				head(
					style()	
					),
				body(
					h3("Your temporary password is " + password),
					h3("After you log in with your temporary password,"),
					h3("you will be forced to change it.")
				)).renderFormatted();
		
		return result;
	}
}
