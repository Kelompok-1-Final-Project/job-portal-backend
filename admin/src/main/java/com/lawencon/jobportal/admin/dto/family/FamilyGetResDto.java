package com.lawencon.jobportal.admin.dto.family;

public class FamilyGetResDto {

	private String familyId;
	private String familyName;
	private String realtionshipName;
	private String degreeName;
	private String birthdate;

	public String getRealtionshipName() {
		return realtionshipName;
	}

	public void setRealtionshipName(String realtionshipName) {
		this.realtionshipName = realtionshipName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

}
