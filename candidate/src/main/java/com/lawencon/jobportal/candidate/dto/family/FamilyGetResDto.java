package com.lawencon.jobportal.candidate.dto.family;

public class FamilyGetResDto {
	private String id;
	private String familyName;
	private String relationshipName;
	private String familyDegree;
	private String familyBirthDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public String getFamilyDegree() {
		return familyDegree;
	}

	public void setFamilyDegree(String familyDegree) {
		this.familyDegree = familyDegree;
	}

	public String getFamilyBirthDate() {
		return familyBirthDate;
	}

	public void setFamilyBirthDate(String familyBirthDate) {
		this.familyBirthDate = familyBirthDate;
	}
}
