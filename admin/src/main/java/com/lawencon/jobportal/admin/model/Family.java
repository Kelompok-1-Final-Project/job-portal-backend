package com.lawencon.jobportal.admin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_family")
public class Family extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@Column(name = "family_name", length = 30, nullable = false)
	private String familyName;

	@OneToOne
	@JoinColumn(name = "relationship_id")
	private Relationship relationship;

	@OneToOne
	@JoinColumn(name = "degree_id")
	private Degree degree;

	@Column(name = "birthdate", nullable = false)
	private LocalDate birthdate;

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}

}
