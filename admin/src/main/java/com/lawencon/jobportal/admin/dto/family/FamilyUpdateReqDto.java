package com.lawencon.jobportal.admin.dto.family;

public class FamilyUpdateReqDto {

	private String familyId;
	private String candidateId;
	private String familyName;
	private String relationshipCode;
	private String degreeCode;
	private String birthdate;

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getRelationshipCode() {
		return relationshipCode;
	}

	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}

	public String getDegreeCode() {
		return degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

}
