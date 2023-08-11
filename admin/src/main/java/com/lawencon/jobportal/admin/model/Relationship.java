package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_relationship")
public class Relationship extends BaseEntity {


	@Column(name = "relationship_code", length = 5, nullable = false)
	private String relationshipCode;

	@Column(name = "relationship_name", length = 30, nullable = false)
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
