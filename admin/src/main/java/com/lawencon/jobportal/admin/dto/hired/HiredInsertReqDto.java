package com.lawencon.jobportal.admin.dto.hired;

public class HiredInsertReqDto {

	private String candidateId;
	private String jobId;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

}
