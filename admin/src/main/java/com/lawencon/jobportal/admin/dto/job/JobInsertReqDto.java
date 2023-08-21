package com.lawencon.jobportal.admin.dto.job;

import java.util.List;

public class JobInsertReqDto {

	private String jobTitle;
	private String jobCode;
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
	private List<String> benefitCode;
	private String testName;
	private String testCode;
	private List<String> questionCode;
	private List<String> questionId;

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
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

	public List<String> getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(List<String> benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public List<String> getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(List<String> questionCode) {
		this.questionCode = questionCode;
	}

	public List<String> getQuestionId() {
		return questionId;
	}

	public void setQuestionId(List<String> questionId) {
		this.questionId = questionId;
	}

}
