package com.lawencon.jobportal.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user_skill")
public class UserSkill extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "skill_id")
	private Skill skill;

	@OneToOne
	@JoinColumn(name = "level_id")
	private Level level;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}
