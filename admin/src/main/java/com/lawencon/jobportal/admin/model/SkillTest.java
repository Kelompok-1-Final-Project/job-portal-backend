package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_skill_test")
public class SkillTest extends BaseEntity{
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	@Column(name = "test_name", length=30, nullable=false)
	private String testName;
	
	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@Column(name = "grade", nullable=false)
	private Double grade;
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
}
