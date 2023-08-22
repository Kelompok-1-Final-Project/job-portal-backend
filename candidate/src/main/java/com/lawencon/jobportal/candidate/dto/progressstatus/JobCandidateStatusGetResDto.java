package com.lawencon.jobportal.candidate.dto.progressstatus;

public class JobCandidateStatusGetResDto {
	private String jobCandidateStatusId;
	private String jobId;
	private String candidateName;
	private String statusName;
	private String statusCode;

	public String getJobCandidateStatusId() {
		return jobCandidateStatusId;
	}

	public void setJobCandidateStatusId(String jobCandidateStatusId) {
		this.jobCandidateStatusId = jobCandidateStatusId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
