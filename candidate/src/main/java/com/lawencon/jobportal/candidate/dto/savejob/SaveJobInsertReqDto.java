package com.lawencon.jobportal.candidate.dto.savejob;

public class SaveJobInsertReqDto {

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
