package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_benefit")
public class Benefit extends BaseEntity{

	@Column(name = "benefit_code", length=5, unique=true, nullable=false)
	private String benefitCode;
	
	@Column(name = "benefit_name", length=30, nullable=false)
	private String benefitName;

	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getBenefitName() {
		return benefitName;
	}

	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	
	
}
