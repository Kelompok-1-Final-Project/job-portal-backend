package com.lawencon.jobportal.candidate.dto.job;

public class JobBenefitUpdateReqDto {
	private String jobCode;
	private String benefitId;

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}

}
