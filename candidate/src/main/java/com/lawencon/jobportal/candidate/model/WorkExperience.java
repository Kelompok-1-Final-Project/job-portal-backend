package com.lawencon.jobportal.candidate.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_work_experience")
public class WorkExperience {
	
	@Column(name = "position_name",length =30,nullable = false)
	private String positionName;
	@Column(name = "company_name",length =30,nullable = false)
	private String companyName;
	@Column(name = "start_date",nullable = false)
	private LocalDate startDate;
	@Column(name = "end_date",nullable = false)
	private LocalDate endDate;
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public User getCandidate() {
		return candidate;
	}
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
}
