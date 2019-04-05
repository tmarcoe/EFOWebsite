package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Transactions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Date timestamp;
	@DecimalMin(".01")
	private Double amount;
	@NotBlank
	@Column(length = 128)
	private String name;
	private Double down;
	private Double interest;
	private Long num_payments;
	private Double each_payment;
	private Date start;
	private String schedule;
	@NotBlank
	@Column(length = 1024)
	private String descr;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="NAME", referencedColumnName ="NAME", nullable = false, insertable=false, updatable=false )
	private Profiles profiles;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDown() {
		return down;
	}

	public void setDown(Double down) {
		this.down = down;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Long getNum_payments() {
		return num_payments;
	}

	public void setNum_payments(Long num_payments) {
		this.num_payments = num_payments;
	}

	public Double getEach_payment() {
		return each_payment;
	}

	public void setEach_payment(Double each_payment) {
		this.each_payment = each_payment;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Profiles getProfiles() {
		return profiles;
	}

	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}
	
}
