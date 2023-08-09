package com.lawencon.jobportal.candidate.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_organization")
public class Organization extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;
	
	@Column(name = "organization_name",length =30,nullable = false)
	private String organizationName;
	
	@Column(name = "position_name",length =30,nullable = false)
	private String positionName;
	
	@Column(name = "start_date",nullable = false)
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "description")
	private String description;
	
	public User getCandidate() {
		return candidate;
	}
	
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}
	
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getPositionName() {
		return positionName;
	}
	
	public void setPositionName(String positionName) {
		this.positionName = positionName;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
