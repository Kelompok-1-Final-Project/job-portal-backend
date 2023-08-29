package com.lawencon.jobportal.admin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_education")
public class Education extends BaseEntity{
	
	@Column(name = "education_name",length =30,nullable = false)
	private String educationName;
	
	@Column(name = "start_date",nullable = false)
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
//	@Column(name = "description")
//	private String description;
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	public String getEducationName() {
		return educationName;
	}
	
	public void setEducationName(String educationName) {
		this.educationName = educationName;
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
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}


