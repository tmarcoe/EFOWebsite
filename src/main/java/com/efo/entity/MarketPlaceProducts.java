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
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class MarketPlaceProducts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long product_reference;
	private Long reference;
	@NotBlank
	private String product_name;
	@NotBlank
	private String product_description;
	private String keywords;
	private Double product_price;
	private Double product_tax;
	private String file_name;
	@NotBlank
	private String version;
	private Date introduced_on;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "marketPlaceProducts", cascade = CascadeType.ALL)
	private Set<MarketPlaceSales> marketPlaceSales = new HashSet<MarketPlaceSales>(0);

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE", referencedColumnName ="REFERENCE", nullable = false, insertable=false, updatable=false )
	private MarketPlaceVendors marketPlaceVendors;
	
	@Transient
	private boolean firstTime;
	
	@Transient
	private String company_name;
	
	@Transient
	private MultipartFile company_logo;
	
	@Transient
	private MultipartFile file;

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

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
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

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public MultipartFile getCompany_logo() {
		return company_logo;
	}

	public void setCompany_logo(MultipartFile company_logo) {
		this.company_logo = company_logo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
