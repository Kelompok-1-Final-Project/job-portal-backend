package com.lawencon.jobportal.admin.dto.candidateprogress;

public class CandidateProgressGetResDto {

	private String candidateProgressId;
	private String candidateId;
	private String candidateName;
	private String jobId;
	private String jobName;
	private String statusCode;
	private String statusName;
	
	public String getCandidateProgressId() {
		return candidateProgressId;
	}

	public void setCandidateProgressId(String candidateProgressId) {
		this.candidateProgressId = candidateProgressId;
	}
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
