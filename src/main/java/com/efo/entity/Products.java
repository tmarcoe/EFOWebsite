package com.efo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Products implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String product_id;
	private String product_name;
	private String product_description;
	private Double product_price;
	private Double product_tax;
	private Double product_discount;
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public Double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double product_price) {
		this.product_price = product_price;
	}
	public Double getProduct_tax() {
		return product_tax;
	}
	public void setProduct_tax(Double product_tax) {
		this.product_tax = product_tax;
	}
	public Double getProduct_discount() {
		return product_discount;
	}
	public void setProduct_discount(Double product_discount) {
		this.product_discount = product_discount;
	}
	
}
