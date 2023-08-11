package com.lawencon.jobportal.admin.dto.question;

import java.util.List;

public class QuestionInsertReqDto {

	private String question;
	private List<QuestionOptionReqDto> listQuestionOption;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<QuestionOptionReqDto> getListQuestionOption() {
		return listQuestionOption;
	}

	public void setListQuestionOption(List<QuestionOptionReqDto> listQuestionOption) {
		this.listQuestionOption = listQuestionOption;
	}

}
