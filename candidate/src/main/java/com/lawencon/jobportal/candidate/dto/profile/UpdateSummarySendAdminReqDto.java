package com.lawencon.jobportal.candidate.dto.profile;

public class UpdateSummarySendAdminReqDto {
	private String candidateEmail;
	private String summary;

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
