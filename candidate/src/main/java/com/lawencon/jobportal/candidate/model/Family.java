package com.lawencon.jobportal.candidate.model;

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
	private User candidate;

	@Column(name = "family_name", length = 30, nullable = false)
	private String familyName;

	@OneToOne
	@JoinColumn(name = "relationship_id")
	private Relationship relationship;

	@Column(name = "birthdate", length = 36, nullable = false)
	private LocalDate familyBirthdate;
	
	@OneToOne
	@JoinColumn(name = "degree_id")
	private Degree familyDegree;

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}


	public Degree getFamilyDegree() {
		return familyDegree;
	}

	public void setFamilyDegree(Degree familyDegree) {
		this.familyDegree = familyDegree;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public LocalDate getFamilyBirthdate() {
		return familyBirthdate;
	}

	public void setFamilyBirthdate(LocalDate familyBirthdate) {
		this.familyBirthdate = familyBirthdate;
	}


}
