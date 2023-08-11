package com.lawencon.jobportal.admin.dto.job;

public class JobInsertReqDto {

	private String jobTitle;
	private Integer salaryStart;
	private Integer salaryEnd;
	private String description;
	private String endDate;
	private String companyCode;
	private String jobPositionCode;
	private String jobStatusCode;
	private String employmentCode;
	private String hrId;
	private String interviewerId;

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getSalaryStart() {
		return salaryStart;
	}

	public void setSalaryStart(Integer salaryStart) {
		this.salaryStart = salaryStart;
	}

	public Integer getSalaryEnd() {
		return salaryEnd;
	}

	public void setSalaryEnd(Integer salaryEnd) {
		this.salaryEnd = salaryEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getJobPositionCode() {
		return jobPositionCode;
	}

	public void setJobPositionCode(String jobPositionCode) {
		this.jobPositionCode = jobPositionCode;
	}

	public String getJobStatusCode() {
		return jobStatusCode;
	}

	public void setJobStatusCode(String jobStatusCode) {
		this.jobStatusCode = jobStatusCode;
	}

	public String getEmploymentCode() {
		return employmentCode;
	}

	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}

	public String getHrId() {
		return hrId;
	}

	public void setHrId(String hrId) {
		this.hrId = hrId;
	}

	public String getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(String interviewerId) {
		this.interviewerId = interviewerId;
	}

}
