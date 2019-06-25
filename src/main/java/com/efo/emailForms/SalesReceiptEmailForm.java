package com.efo.emailForms;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.link;
import static j2html.TagCreator.div;
import static j2html.TagCreator.attrs;
import org.springframework.stereotype.Component;

@Component
public class SalesReceiptEmailForm {
	
	public String salesReceiptForm(String invoiceNum) {
		String result = html(
				
				head(
						style()	,
						link().withRel("stylesheet").withHref("/css/distrubute.css")
					),
				body(
						div(attrs(".centerTitle"),
							h1("Invoice: " + invoiceNum),
							h2("Thank you for your recent EFO purchase.")
						),
						h3("Attached, you will find your sales receipt."),
						h3("If you have any questions, please feel free to contact us.")
				)).renderFormatted();
		
		return result;
	}
}
