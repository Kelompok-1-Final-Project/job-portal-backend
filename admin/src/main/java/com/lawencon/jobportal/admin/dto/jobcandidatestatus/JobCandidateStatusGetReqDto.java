package com.lawencon.jobportal.admin.dto.jobcandidatestatus;

public class JobCandidateStatusGetReqDto {
	private String email;
	private Integer startIndex;
	private Integer endIndex;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

}
