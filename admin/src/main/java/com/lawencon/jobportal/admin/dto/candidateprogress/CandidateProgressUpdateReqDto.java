package com.lawencon.jobportal.admin.dto.candidateprogress;

public class CandidateProgressUpdateReqDto {

	private String candidateProgressId;
	private String statusProcessCode;

	public String getCandidateProgressId() {
		return candidateProgressId;
	}

	public void setCandidateProgressId(String candidateProgressId) {
		this.candidateProgressId = candidateProgressId;
	}

	public String getStatusProcessCode() {
		return statusProcessCode;
	}

	public void setStatusProcessCode(String statusProcessCode) {
		this.statusProcessCode = statusProcessCode;
	}

}
