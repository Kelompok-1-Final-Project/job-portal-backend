package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_relationship")
public class Relationship {
	
	@Column(name = "relationship_code",length =5,unique=true,nullable = false)
	private String relationshipCode;
	@Column(name = "relationship_name",length =30,nullable = false)
	private String relationshipName;
	
	
	public String getRelationshipCode() {
		return relationshipCode;
	}
	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}
	public String getRelationshipName() {
		return relationshipName;
	}
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}
}
