package com.lawencon.jobportal.admin.dto.jobbenefit;

public class JobBenefitReqDto {
	private String jobId;
	private String benefitId;
	private String jobCode;
	private String benefitCode;

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}

}
