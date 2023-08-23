package com.lawencon.jobportal.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_employee")
public class Employee extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "candidate_id", nullable = false)
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
