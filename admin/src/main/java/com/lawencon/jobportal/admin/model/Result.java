package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_result")
public class Result {

	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "skill_test_id")
	private SkillTest skillTest;

	@Column(name = "grade", nullable = false)
	private Double grade;

	@Column(name = "notes")
	private String notes;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public SkillTest getSkillTest() {
		return skillTest;
	}

	public void setSkillTest(SkillTest skillTest) {
		this.skillTest = skillTest;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
