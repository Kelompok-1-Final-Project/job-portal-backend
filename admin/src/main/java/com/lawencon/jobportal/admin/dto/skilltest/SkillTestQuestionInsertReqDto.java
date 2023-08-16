package com.lawencon.jobportal.admin.dto.skilltest;

import java.util.List;

public class SkillTestQuestionInsertReqDto {
	private List<String> questionCode;
	private String skillTestCode;

	public List<String> getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(List<String> questionCode) {
		this.questionCode = questionCode;
	}

	public String getSkillTestCode() {
		return skillTestCode;
	}

	public void setSkillTestCode(String skillTestCode) {
		this.skillTestCode = skillTestCode;
	}

}
