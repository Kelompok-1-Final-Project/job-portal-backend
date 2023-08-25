package com.lawencon.jobportal.admin.dto.skilltest;

import java.util.List;

public class QuestionGetResDto {
	private String question;
	private String questionId;
	private String questionCode;
	private List<QuestionOptionGetResDto> optionGetResDtos;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public List<QuestionOptionGetResDto> getOptionGetResDtos() {
		return optionGetResDtos;
	}

	public void setOptionGetResDtos(List<QuestionOptionGetResDto> optionGetResDtos) {
		this.optionGetResDtos = optionGetResDtos;
	}

}
