package com.lawencon.jobportal.admin.dto.hired;

public class HiredGetResDto {

	private String hiredId;
	private String candidateId;
	private String candidateName;
	private String jobId;
	private String jobTitle;

	public String getHiredId() {
		return hiredId;
	}

	public void setHiredId(String hiredId) {
		this.hiredId = hiredId;
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

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}
