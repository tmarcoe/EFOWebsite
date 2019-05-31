package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MarketPlaceProducts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long product_reference;
	private Long reference;
	private String product_name;
	private Double product_price;
	private Double product_tax;
	private String version;
	private Date introduced_on;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "marketPlaceProducts", cascade = CascadeType.ALL)
	private Set<MarketPlaceSales> marketPlaceSales = new HashSet<MarketPlaceSales>(0);

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE", referencedColumnName ="REFERENCE", nullable = false, insertable=false, updatable=false )
	private MarketPlaceVendors marketPlaceVendors;

	public Long getProduct_reference() {
		return product_reference;
	}

	public void setProduct_reference(Long product_reference) {
		this.product_reference = product_reference;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getIntroduced_on() {
		return introduced_on;
	}

	public void setIntroduced_on(Date introduced_on) {
		this.introduced_on = introduced_on;
	}

	public Set<MarketPlaceSales> getMarketPlaceSales() {
		return marketPlaceSales;
	}

	public void setMarketPlaceSales(Set<MarketPlaceSales> marketPlaceSales) {
		this.marketPlaceSales = marketPlaceSales;
	}

	public MarketPlaceVendors getMarketPlaceVendors() {
		return marketPlaceVendors;
	}

	public void setMarketPlaceVendors(MarketPlaceVendors marketPlaceVendors) {
		this.marketPlaceVendors = marketPlaceVendors;
	}
	
	
}
