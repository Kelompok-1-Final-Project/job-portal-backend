package com.lawencon.jobportal.candidate.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_job_candidate_status")
public class JobCandidateStatus extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;
	
	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@OneToOne
	@JoinColumn(name = "status_id")
	private StatusProcess statusProcess;

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

	public StatusProcess getStatus() {
		return statusProcess;
	}

	public void setStatus(StatusProcess status) {
		this.statusProcess = status;
	}
}
