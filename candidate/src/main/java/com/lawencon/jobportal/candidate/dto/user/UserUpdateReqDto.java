package com.lawencon.jobportal.candidate.dto.user;

public class UserUpdateReqDto {
	private String candidateId;
	private String idNumber;
	private String fullName;
	private String email;
	private String birthdate;
	private String mobileNumber;
	private String photoExt;
	private String photoFiles;
	private Integer expectedSalary;
	private String maritalStatusCode;
	private String genderCode;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhotoExt() {
		return photoExt;
	}

	public void setPhotoExt(String photoExt) {
		this.photoExt = photoExt;
	}

	public String getPhotoFiles() {
		return photoFiles;
	}

	public void setPhotoFiles(String photoFiles) {
		this.photoFiles = photoFiles;
	}

	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

}
