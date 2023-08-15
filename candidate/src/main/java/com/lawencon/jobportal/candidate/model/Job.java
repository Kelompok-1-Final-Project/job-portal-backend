package com.lawencon.jobportal.candidate.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_job")
public class Job extends BaseEntity {

	@Column(name = "job_code", length = 5, nullable = false)
	private String jobCode;

	@Column(name = "job_title", length = 30, nullable = false)
	private String jobTitle;

	@Column(name = "salary_start", nullable = false)
	private Integer salaryStart;

	@Column(name = "salary_end", nullable = false)
	private Integer salaryEnd;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToOne
	@JoinColumn(name = "job_position_id")
	private JobPosition jobPosition;

	@OneToOne
	@JoinColumn(name = "job_status_id")
	private JobStatus jobStatus;

	@OneToOne
	@JoinColumn(name = "employment_type_id")
	private EmploymentType employementType;

//	@OneToOne
//	@JoinColumn(name = "hr_id")
//	private User hr;
//
//	@OneToOne
//	@JoinColumn(name = "interviewer_id")
//	private User interviewer;

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

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

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public JobPosition getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(JobPosition jobPosition) {
		this.jobPosition = jobPosition;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public EmploymentType getEmployementType() {
		return employementType;
	}

	public void setEmployementType(EmploymentType employementType) {
		this.employementType = employementType;
	}

//	public User getHr() {
//		return hr;
//	}
//
//	public void setHr(User hr) {
//		this.hr = hr;
//	}
//
//	public User getInterviewer() {
//		return interviewer;
//	}
//
//	public void setInterviewer(User interviewer) {
//		this.interviewer = interviewer;
//	}
}
