package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_company")
public class Company extends BaseEntity{
	
	@Column(name = "company_code",length =5,unique=true,nullable = false)
	private String companyCode;
	
	@Column(name = "company_name",length =30,nullable = false)
	private String companyName;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;
	
	@OneToOne
	@JoinColumn(name = "industry_id")
	private Industry industry;
	
	@OneToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCompanyCode() {
		return companyCode;
	}
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public Industry getIndustry() {
		return industry;
	}
	
	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
