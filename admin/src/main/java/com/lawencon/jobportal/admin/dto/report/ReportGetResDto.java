package com.lawencon.jobportal.admin.dto.report;

public class ReportGetResDto {
	private String candidateName;
	private String jobName;
	private String employmentTypeName;
	private Double dateDiff;

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}

	public Double getDateDiff() {
		return dateDiff;
	}

	public void setDateDiff(Double dateDiff) {
		this.dateDiff = dateDiff;
	}

}
