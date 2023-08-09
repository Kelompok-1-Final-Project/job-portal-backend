package com.lawencon.jobportal.candidate.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_user_skill")
public class UserSkill extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "candidate_id")
	
	private User candidate;
	@OneToOne
	@JoinColumn(name = "skill_id")
	
	private Skill skill;
	public User getCandidate() {
		return candidate;
	}
	
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	
}
