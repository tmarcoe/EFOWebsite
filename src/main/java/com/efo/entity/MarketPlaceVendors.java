package com.efo.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MarketPlaceVendors implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Long user_id;		// User ID from Customer table
	private String company_name;
	private String logo;
	private String name;
	private Double sales_total;
	private Double commission_total;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "marketPlaceVendors", cascade = CascadeType.ALL)
	private Set<MarketPlaceProducts> marketPlaceProducts = new HashSet<MarketPlaceProducts>(0);
	
	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSales_total() {
		return sales_total;
	}

	public void setSales_total(Double sales_total) {
		this.sales_total = sales_total;
	}

	public Double getCommission_total() {
		return commission_total;
	}

	public void setCommission_total(Double commission_total) {
		this.commission_total = commission_total;
	}

	public Set<MarketPlaceProducts> getMarketPlaceProducts() {
		return marketPlaceProducts;
	}

	public void setMarketPlaceProducts(Set<MarketPlaceProducts> marketPlaceProducts) {
		this.marketPlaceProducts = marketPlaceProducts;
	}

}
