package com.lawencon.jobportal.admin.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_interview")
public class Interview extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@OneToOne
	@JoinColumn(name = "interviewer_id")
	private User interviewer;
	
	@Column(name = "schedule", nullable=false)
	private LocalDateTime schedule;
	
	@Column(name = "notes", nullable=false)
	private String notes;

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

	public User getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(User interviewer) {
		this.interviewer = interviewer;
	}

	public LocalDateTime getSchedule() {
		return schedule;
	}

	public void setSchedule(LocalDateTime schedule) {
		this.schedule = schedule;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
