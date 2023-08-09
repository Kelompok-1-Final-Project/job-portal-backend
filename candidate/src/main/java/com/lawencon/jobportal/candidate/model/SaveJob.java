package com.lawencon.jobportal.candidate.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_save_job")
public class SaveJob extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;
	
	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	public User getCandidate() {
		return candidate;
	}
	
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
	
	public Job getJob() {
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
}
