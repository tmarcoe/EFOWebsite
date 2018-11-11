package com.efo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class SalesItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	private Long item_id;
	
	private Long reference;
	private String sku;
	private String product_name;
	private double qty;
	private double sold_for;
	private double regular_price;
	private String options;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REFERENCE", nullable = false, insertable=false, updatable=false )
	private RetailSales retailSales;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SKU", nullable = false, insertable=false, updatable=false )
	private Product product;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "salesItem", cascade = CascadeType.ALL)
	private UsedCoupons usedCoupons;

	
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public UsedCoupons getUsedCoupons() {
		return usedCoupons;
	}
	public void setUsedCoupons(UsedCoupons usedCoupons) {
		this.usedCoupons = usedCoupons;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getSold_for() {
		return sold_for;
	}
	public void setSold_for(double sold_for) {
		this.sold_for = sold_for;
	}
	public double getRegular_price() {
		return regular_price;
	}
	public void setRegular_price(double regular_price) {
		this.regular_price = regular_price;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public RetailSales getRetailSales() {
		return retailSales;
	}
	public void setRetailSales(RetailSales retailSales) {
		this.retailSales = retailSales;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}	
	
}
