package com.lawencon.jobportal.admin.dto.job;

public class SendDataJobReqDto {
	private String jobCode;
	private String candidateEmail;

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

}
