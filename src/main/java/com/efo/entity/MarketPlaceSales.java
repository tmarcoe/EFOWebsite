package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MarketPlaceSales implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long buyer;				//User ID of the buyer
	private Long product_reference;
	private String product_name;
	private Double sold_for;
	private Double tax;
	private Double discount;
	private Date date_sold;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PRODUCT_REFERENCE", referencedColumnName ="PRODUCT_REFERENCE", nullable = false, insertable=false, updatable=false )
	private MarketPlaceProducts marketPlaceProducts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuyer() {
		return buyer;
	}

	public void setBuyer(Long buyer) {
		this.buyer = buyer;
	}

	public Long getProduct_reference() {
		return product_reference;
	}

	public void setProduct_reference(Long product_reference) {
		this.product_reference = product_reference;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Double getSold_for() {
		return sold_for;
	}

	public void setSold_for(Double sold_for) {
		this.sold_for = sold_for;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Date getDate_sold() {
		return date_sold;
	}

	public void setDate_sold(Date date_sold) {
		this.date_sold = date_sold;
	}

	public MarketPlaceProducts getMarketPlaceProducts() {
		return marketPlaceProducts;
	}

	public void setMarketPlaceProducts(MarketPlaceProducts marketPlaceProducts) {
		this.marketPlaceProducts = marketPlaceProducts;
	}

}
