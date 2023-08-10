package com.lawencon.jobportal.admin.dto.candidateprogress;

public class CandidateProgressUpdateReqDto {

	private String candidateProgressId;
	private String StatusProcessCode;

	public String getCandidateProgressId() {
		return candidateProgressId;
	}

	public void setCandidateProgressId(String candidateProgressId) {
		this.candidateProgressId = candidateProgressId;
	}

	public String getStatusProcessCode() {
		return StatusProcessCode;
	}

	public void setStatusProcessCode(String statusProcessCode) {
		StatusProcessCode = statusProcessCode;
	}

}
