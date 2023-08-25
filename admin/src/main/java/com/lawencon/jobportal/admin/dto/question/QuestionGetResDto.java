package com.lawencon.jobportal.admin.dto.question;

import java.util.List;

public class QuestionGetResDto {

	private String questionId;
	private String question;
	private String questionCode;
	private List<QuestionOptionResDto> listQuestionOption;

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public List<QuestionOptionResDto> getListQuestionOption() {
		return listQuestionOption;
	}

	public void setListQuestionOption(List<QuestionOptionResDto> listQuestionOption) {
		this.listQuestionOption = listQuestionOption;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
