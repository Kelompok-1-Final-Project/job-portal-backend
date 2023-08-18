package com.lawencon.jobportal.candidate.dto.profile;

public class UpdateSummaryReqDto {
	private String candidateId;
	private String summary;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
