package com.lawencon.jobportal.admin.dto;

public class JobBenefitResDto {
	private String jobBenefitId;
	private String benefitId;
	private String benefitName;
	private String benefitCode;

	public String getJobBenefitId() {
		return jobBenefitId;
	}

	public void setJobBenefitId(String jobBenefitId) {
		this.jobBenefitId = jobBenefitId;
	}

	public String getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
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
