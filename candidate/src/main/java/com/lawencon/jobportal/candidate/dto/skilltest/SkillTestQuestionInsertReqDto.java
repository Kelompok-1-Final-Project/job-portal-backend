package com.lawencon.jobportal.candidate.dto.skilltest;

public class SkillTestQuestionInsertReqDto {
	private String questionId;
	private String skillTestId;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getSkillTestId() {
		return skillTestId;
	}

	public void setSkillTestId(String skillTestId) {
		this.skillTestId = skillTestId;
	}

}
