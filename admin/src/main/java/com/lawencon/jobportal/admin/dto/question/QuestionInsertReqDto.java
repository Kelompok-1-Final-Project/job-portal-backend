package com.lawencon.jobportal.admin.dto.question;

import java.util.List;

public class QuestionInsertReqDto {

	private String question;
	private List<QuestionOptionReqDto> listQuestionOpion;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<QuestionOptionReqDto> getListQuestionOpion() {
		return listQuestionOpion;
	}

	public void setListQuestionOpion(List<QuestionOptionReqDto> listQuestionOpion) {
		this.listQuestionOpion = listQuestionOpion;
	}



}
