package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_family")
public class Family {
	
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;
	
	@Column(name = "family_name",length =30,nullable = false)
	private String familyname;
	
	@OneToOne
	@JoinColumn(name = "relationship_id")
	private Relationship relationship;
	
	@Column(name = "family_degree",length =36,nullable = false)
	private String familyDegree;
	@Column(name = "family_birthdate",length =36,nullable = false)
	private String familyBirtdate;
	
	
	
	public User getCandidate() {
		return candidate;
	}
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
	public String getFamilyname() {
		return familyname;
	}
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}
	public Relationship getRelationship() {
		return relationship;
	}
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
	public String getFamilyDegree() {
		return familyDegree;
	}
	public void setFamilyDegree(String familyDegree) {
		this.familyDegree = familyDegree;
	}
	public String getFamilyBirtdate() {
		return familyBirtdate;
	}
	public void setFamilyBirtdate(String familyBirtdate) {
		this.familyBirtdate = familyBirtdate;
	}
}
