package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String reference;
	private Long user_id;		// User that owns the shopping cart
	private Date time_ordered;
	private Date time_processed;
	private Date time_delivered;
	private String payment_gateway;
	private String result;
	private String trans_result;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "shoppingCart", cascade = CascadeType.ALL)
	private Set<ShoppingCartItems> shoppingCartItems = new HashSet<ShoppingCartItems>(0);

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getTime_ordered() {
		return time_ordered;
	}

	public void setTime_ordered(Date time_ordered) {
		this.time_ordered = time_ordered;
	}

	public Date getTime_processed() {
		return time_processed;
	}

	public void setTime_processed(Date time_processed) {
		this.time_processed = time_processed;
	}

	public Date getTime_delivered() {
		return time_delivered;
	}

	public void setTime_delivered(Date time_delivered) {
		this.time_delivered = time_delivered;
	}

	public String getPayment_gateway() {
		return payment_gateway;
	}

	public void setPayment_gateway(String payment_gateway) {
		this.payment_gateway = payment_gateway;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTrans_result() {
		return trans_result;
	}

	public void setTrans_result(String trans_result) {
		this.trans_result = trans_result;
	}

	public Set<ShoppingCartItems> getShoppingCartItems() {
		return shoppingCartItems;
	}

	public void setShoppingCartItems(Set<ShoppingCartItems> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}
}
