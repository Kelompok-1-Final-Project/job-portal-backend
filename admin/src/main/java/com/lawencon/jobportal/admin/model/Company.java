package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_company")
public class Company extends BaseEntity {

	@Column(name = "company_code", length = 5, nullable = false, unique = true)
	private String companyCode;

	@Column(name = "company_name", length = 30, nullable = false)
	private String companyName;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;
	
	@OneToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;

	@OneToOne
	@JoinColumn(name = "industry_id", nullable = false)
	private Industry industry;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

}
