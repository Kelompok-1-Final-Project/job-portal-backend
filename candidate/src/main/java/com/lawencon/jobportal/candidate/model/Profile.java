package com.lawencon.jobportal.candidate.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_profile")
public class Profile {
	
	@Column(name = "id_number",length =16,unique=true)
	private String idNumber;
	@Column(name = "full_name",length =30,nullable = false)
	private String fullName;
	@Column(name = "summary")
	private String summary;
	@Column(name = "birthdate")
	private LocalDate birthdate;
	@Column(name = "mobile_number",length =15)
	private String mobileNumber;
	@Column(name="expected_salary")
	private Integer expectedSalary;
	@OneToOne
	@JoinColumn(name = "photo_id")
	private File photo;
	@OneToOne
	@JoinColumn(name = "cv_id")
	private File cv;
	@OneToOne
	@JoinColumn(name = "gender_id")
	private Gender gender;
	@OneToOne
	@JoinColumn(name = "marital_status_id")
	private MaritalStatus maritalStatus;
	@OneToOne
	@JoinColumn(name = "person_type_id")
	private PersonType personType;
	
	
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public File getCv() {
		return cv;
	}
	public void setCv(File cv) {
		this.cv = cv;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public PersonType getPersonType() {
		return personType;
	}
	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}
}
