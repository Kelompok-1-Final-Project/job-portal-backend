package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_skill_test")
public class SkillTest extends BaseEntity {

	@Column(name = "test_name", length = 30, nullable = false)
	private String testName;

	@Column(name = "test_code", length = 5, nullable = false)
	private String testCode;

	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}
