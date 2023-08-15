package com.lawencon.jobportal.candidate.dto.question;

import java.util.List;

public class QuestionInsertReqDto {

	private String question;
	private String questionCode;
	private List<QuestionOptionReqDto> listQuestionOption;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public List<QuestionOptionReqDto> getListQuestionOption() {
		return listQuestionOption;
	}

	public void setListQuestionOption(List<QuestionOptionReqDto> listQuestionOption) {
		this.listQuestionOption = listQuestionOption;
	}

}
