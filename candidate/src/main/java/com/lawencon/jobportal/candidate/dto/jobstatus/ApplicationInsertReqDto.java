package com.lawencon.jobportal.candidate.dto.jobstatus;

public class ApplicationInsertReqDto {

	private String candidateEmail;
	private String jobCode;

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

}
