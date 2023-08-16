package com.lawencon.jobportal.candidate.dto.user;

public class UserRegisterByAdminReqDto {
	private String fullName;
	private String password;
	private String email;
	private String idNumber;
	private String summary;
	private String birthdate;
	private String mobileNumber;
	private String photoExt;
	private String photoFiles;
	private String cvExt;
	private String cvFiles;
	private String expectedSalary;
	private String genderCode;
	private String maritalStatusCode;
	private String personTypeCode;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getCvExt() {
		return cvExt;
	}

	public void setCvExt(String cvExt) {
		this.cvExt = cvExt;
	}

	public String getCvFiles() {
		return cvFiles;
	}

	public void setCvFiles(String cvFiles) {
		this.cvFiles = cvFiles;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public String getPersonTypeCode() {
		return personTypeCode;
	}

	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}

}
