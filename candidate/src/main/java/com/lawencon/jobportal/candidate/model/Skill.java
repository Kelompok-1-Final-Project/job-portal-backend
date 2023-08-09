package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "t_skill")
public class Skill {
	@Column(name = "skill_code",length =5,unique=true,nullable = false)
	private String skillCode;
	@Column(name = "skill_name",length =30,nullable = false)
	private String skillName;
	
	
	public String getSkillCode() {
		return skillCode;
	}
	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
}
