package com.efo.payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentObject {
	private JSONObject pmntObject;
	private JSONArray itemsArray;

	public JSONObject totalAmount(JSONObject totalAmount) throws JSONException {
		pmntObject.put("totalAmount", totalAmount);
		return totalAmount;
	}
	
	public JSONObject currency(String currency) throws JSONException {
		return pmntObject.put("currency", currency);
	}
	
	public JSONObject value(double value) throws JSONException {
		return pmntObject.put("value", value);
	}
	
	public JSONObject details(JSONObject details) throws JSONException {
		pmntObject.put("details", details);
		return details;
	}
	
	public JSONObject discount(double discount) throws JSONException {
		return pmntObject.put("discount", discount);
	}
	
	public JSONObject serviceCharge(double serviceCharge ) throws JSONException {
		return pmntObject.put("serviceCharge", serviceCharge);
	}
	
	public JSONObject shippingFee(double shippingFee) throws JSONException {
		return pmntObject.put("shippingFee", shippingFee);
	}
	
	public JSONObject tax(double tax) throws JSONException {
		return pmntObject.put("tax", tax);
	}
	
	public JSONObject subtotal(double subtotal) throws JSONException {
		return pmntObject.put("subtotal", subtotal);
	}
	
	public JSONObject buyer(JSONObject buyer) throws JSONException {
		pmntObject.put("buyer", buyer);
		
		return buyer;
	}
	
	public JSONObject firstName(String firstName) throws JSONException {
		return pmntObject.put("firstName", firstName);
	}
	
	public JSONObject middleName(String middleName) throws JSONException {
		return pmntObject.put("middleName", middleName);
	}

	public JSONObject lastName(String lastName) throws JSONException {
		return pmntObject.put("lastName", lastName);
	}
	
	public JSONObject contact(JSONObject contact) throws JSONException {
		pmntObject.put("contact", contact);
		return contact;
	}
	
	public JSONObject phone(String phone) throws JSONException {
		return pmntObject.put("phone", phone);
	}
	
	public JSONObject email(String email) throws JSONException {
		return pmntObject.put("email", email);
	}
	
	public JSONObject shippingAddress(JSONObject shippingAddress) throws JSONException {
		pmntObject.put("shippingAddress", shippingAddress);
		
		return shippingAddress;
	}
	
	public JSONObject line1(String line1) throws JSONException {
		return pmntObject.put("line1", line1);
	}
	
	public JSONObject line2(String line2) throws JSONException {
		return pmntObject.put("line2", line2);
	}
	
	public JSONObject city(String city) throws JSONException {
		return pmntObject.put("city", city);
	}
	
	public JSONObject state(String state) throws JSONException {
		return pmntObject.put("state", state);
	}
	
	public JSONObject zipCode(String zipCode) throws JSONException {
		return pmntObject.put("zipCode", zipCode);
	}
	
	public JSONObject countryCode(String countryCode) throws JSONException {
		return pmntObject.put("countryCode", countryCode);
	}
	
	public JSONObject billingAddress(JSONObject billingAddress) throws JSONException {
		pmntObject.put("billingAddress", billingAddress);
		
		return billingAddress;
	}
	
	public JSONObject redirectUrl(JSONObject redirectUrl) throws JSONException {
		pmntObject.put("redirectUrl", redirectUrl);
		
		return redirectUrl;
	}
	
	public JSONObject success(String success) throws JSONException {
		return pmntObject.put("success", success);
	}
	
	public JSONObject failure(String failure) throws JSONException {
		return pmntObject.put("failure", failure);
	}
	
	public JSONObject cancel(String cancel) throws JSONException {
		return pmntObject.put("cancel", cancel);
	}
	
	public JSONObject requestReferenceNumber(String requestReferenceNumber) throws JSONException {
		return pmntObject.putOpt("requestReferenceNumber", requestReferenceNumber);
	}
	
	public JSONObject metadata(JSONObject metadata) throws JSONException {
		return pmntObject.putOpt("metadata", metadata);
	}
	
	public JSONObject getPmntObject() {
		return pmntObject;
	}

	public JSONObject setPmntObject(JSONObject pmntObject) {
		this.pmntObject = pmntObject;
		
		return pmntObject;
	}

	public JSONArray getItems() {
		return itemsArray;
	}

	public void setItems(JSONArray itemsArray) {
		this.itemsArray = itemsArray;
	}

}
