package com.lawencon.jobportal.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_medical_checkup")
public class MedicalCheckup extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
