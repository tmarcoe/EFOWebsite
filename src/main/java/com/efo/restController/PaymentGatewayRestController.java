package com.efo.restController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayRestController {
	
	@Value("${efo.payment.url}")
	private String paymentUrl;
	
	@Value("${efo.success.url}")
	private String successUrl;
	
	@Value("${efo.failure.url}")
	private String failureUrl;
	
	@Value("${efo.cancel.url}")
	private String cancelUrl;
	
	public String paymayaGatway() throws JSONException, IOException {
		
		JSONObject json = new JSONObject();
		JSONObject totalAmount  = new JSONObject();
		JSONObject details = new JSONObject();
		JSONObject buyer = new JSONObject();
		JSONObject contact = new JSONObject();
		JSONObject shippingAddress = new JSONObject();
		JSONObject billingAddress = new JSONObject();
		JSONObject redirectUrl = new JSONObject();

		PaymentObject paymentObject = new PaymentObject();
		
		paymentObject.setPmntObject(json);
		paymentObject.totalAmount(totalAmount);
		paymentObject.setPmntObject(totalAmount);
		paymentObject.currency("USD");
		paymentObject.value(100.00);
		paymentObject.details(details);
		paymentObject.setPmntObject(details);
		paymentObject.discount(0.0);
		paymentObject.serviceCharge(0.0);
		paymentObject.shippingFee(0.0);
		paymentObject.tax(0.0);
		paymentObject.subtotal(100.0);
		paymentObject.setPmntObject(json);
		paymentObject.buyer(buyer);
		paymentObject.setPmntObject(buyer);
		paymentObject.firstName("John");
		paymentObject.middleName("Quincy");
		paymentObject.lastName("Doe");
		paymentObject.contact(contact);
		paymentObject.setPmntObject(contact);
		paymentObject.phone("+639173952227");
		paymentObject.email("john@doe.com");
		paymentObject.setPmntObject(json);
		paymentObject.shippingAddress(shippingAddress);
		paymentObject.setPmntObject(shippingAddress);
		paymentObject.line1("1111 MyStreet");
		paymentObject.line2("Brgy. Sasa");
		paymentObject.city("Davao City");
		paymentObject.state("Davao Del Sur");
		paymentObject.zipCode("8000");
		paymentObject.setPmntObject(json);
		paymentObject.billingAddress(billingAddress);
		paymentObject.setPmntObject(billingAddress);
		paymentObject.line1("1111 MyStreet");
		paymentObject.line2("Brgy. Sasa");
		paymentObject.city("Davao City");
		paymentObject.state("Davao Del Sur");
		paymentObject.zipCode("8000");
		paymentObject.setPmntObject(json);
		paymentObject.redirectUrl(redirectUrl);
		paymentObject.setPmntObject(redirectUrl);
		paymentObject.success(successUrl);
		paymentObject.failure(failureUrl);
		paymentObject.cancel(cancelUrl);
		
		URL url = new URL(paymentUrl);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream(); 
		byte[] input = json.toString().getBytes("utf-8");
		os.write(input, 0, input.length);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	    StringBuilder response = new StringBuilder();
	    String responseLine = null;
	    while ((responseLine = br.readLine()) != null) {
	        response.append(responseLine.trim());
	    }
	    System.out.println(response.toString());
	    
		return "";
	}

}
