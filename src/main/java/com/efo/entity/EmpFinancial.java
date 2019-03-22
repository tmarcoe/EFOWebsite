package com.efo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class EmpFinancial implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "employee"))
	@Id
	@GeneratedValue(generator = "generator")
	private Long user_id;
	@Column(length = 20)
	private String ssn;
	@Column(length = 2)
	private String status;
	@Column(length = 1)
	private String pay_method;
	@Column(length = 24)
	private String account_num;
	@Column(length = 24)
	private String routing_num;
	@Column(length = 20)
	private String ein;
	
	private Double rate;
	private Double ytd_salary;
	private Double ytd_fed;
	private Double ytd_unemp;
	private Double ytd_med;
	private Double ytd_ret;
	private Double ytd_garnish;
	@Column(length = 1)
	private String employee_type;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Employee employee;
	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getRouting_num() {
		return routing_num;
	}

	public void setRouting_num(String routing_num) {
		this.routing_num = routing_num;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getYtd_salary() {
		return ytd_salary;
	}

	public void setYtd_salary(Double ytd_salary) {
		this.ytd_salary = ytd_salary;
	}

	public Double getYtd_fed() {
		return ytd_fed;
	}

	public void setYtd_fed(Double ytd_fed) {
		this.ytd_fed = ytd_fed;
	}

	public Double getYtd_unemp() {
		return ytd_unemp;
	}

	public void setYtd_unemp(Double ytd_unemp) {
		this.ytd_unemp = ytd_unemp;
	}

	public Double getYtd_med() {
		return ytd_med;
	}

	public void setYtd_med(Double ytd_med) {
		this.ytd_med = ytd_med;
	}

	public Double getYtd_ret() {
		return ytd_ret;
	}

	public void setYtd_ret(Double ytd_ret) {
		this.ytd_ret = ytd_ret;
	}

	public Double getYtd_garnish() {
		return ytd_garnish;
	}

	public void setYtd_garnish(Double ytd_garnish) {
		this.ytd_garnish = ytd_garnish;
	}

	public String getEmployee_type() {
		return employee_type;
	}

	public void setEmployee_type(String employee_type) {
		this.employee_type = employee_type;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
				
}
