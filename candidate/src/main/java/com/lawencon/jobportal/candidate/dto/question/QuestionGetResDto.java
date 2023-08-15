package com.lawencon.jobportal.candidate.dto.question;

import java.util.List;

public class QuestionGetResDto {

	private String questionId;
	private String question;
	private List<QuestionOptionResDto> listQuestionOption;

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
