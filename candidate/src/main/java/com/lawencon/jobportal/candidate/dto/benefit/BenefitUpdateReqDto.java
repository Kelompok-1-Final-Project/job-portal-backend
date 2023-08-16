package com.lawencon.jobportal.candidate.dto.benefit;

public class BenefitUpdateReqDto {
	
	private String benefitCode;
	private String benefitName;

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
