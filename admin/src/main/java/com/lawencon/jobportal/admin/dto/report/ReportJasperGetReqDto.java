package com.lawencon.jobportal.admin.dto.report;

public class ReportJasperGetReqDto {
	private String fullName;
	private String jobTitle;
	private String employmentName;
	private Double dateDiff;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmploymentName() {
		return employmentName;
	}

	public void setEmploymentName(String employmentName) {
		this.employmentName = employmentName;
	}

	public Double getDateDiff() {
		return dateDiff;
	}

	public void setDateDiff(Double dateDiff) {
		this.dateDiff = dateDiff;
	}

}
