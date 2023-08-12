package com.lawencon.jobportal.admin.dto.question;

import java.util.List;

public class QuestionUpdateReqDto {

	private String questionId;
	private String question;
	private List<QuestionOptionResDto> listQuestionOption;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<QuestionOptionResDto> getListQuestionOption() {
		return listQuestionOption;
	}

	public void setListQuestionOption(List<QuestionOptionResDto> listQuestionOption) {
		this.listQuestionOption = listQuestionOption;
	}


}
