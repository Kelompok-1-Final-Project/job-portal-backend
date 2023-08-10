package com.lawencon.jobportal.admin.dto.benefit;

public class BenefitGetResDto {
	private String id;
	private String benefitName;
	private String benefitCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getBenefitCode() {
		return benefitCode;
	}
	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}
}
