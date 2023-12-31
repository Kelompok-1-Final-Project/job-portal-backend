package com.lawencon.jobportal.admin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_profile")
public class CandidateProfile extends BaseEntity {

	@Column(name = "id_number", length = 15, unique = true)
	private String idNumber;

	@Column(name = "full_name", length = 30, nullable = false)
	private String fullName;

	@Column(name = "summary")
	private String summary;

	@Column(name = "birthdate")
	private LocalDate birthDate;

	@Column(name = "mobile_number", length = 15)
	private String mobileNumber;

	@OneToOne
	@JoinColumn(name = "photo_id")
	private File photo;

	@OneToOne
	@JoinColumn(name = "cv_id")
	private File cv;

	@Column(name = "expected_salary")
	private Integer expectedSalary;

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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
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
