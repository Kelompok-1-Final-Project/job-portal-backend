package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_industry")
public class Industry extends BaseEntity{
	
	@Column(name = "industry_code", length = 5, nullable = false, unique = true)
	private String industryCode;
	
	@Column(name = "industry_name", length = 30, nullable = false)
	private String industryName;

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
}
