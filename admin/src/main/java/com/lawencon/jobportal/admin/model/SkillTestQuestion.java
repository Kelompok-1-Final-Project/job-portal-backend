package com.lawencon.jobportal.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_skill_test_question")
public class SkillTestQuestion extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	@OneToOne
	@JoinColumn(name = "skill_test_id")
	private SkillTest skillTest;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public SkillTest getSkillTest() {
		return skillTest;
	}

	public void setSkillTest(SkillTest skillTest) {
		this.skillTest = skillTest;
	}
}
