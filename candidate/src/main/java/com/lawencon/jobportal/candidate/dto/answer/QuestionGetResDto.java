package com.lawencon.jobportal.candidate.dto.answer;

import java.util.List;

public class QuestionGetResDto {
	private String question;
	private String questionCode;
	private List<QuestionOptionGetResDto> optionGetResDtos;

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

	public List<QuestionOptionGetResDto> getOptionGetResDtos() {
		return optionGetResDtos;
	}

	public void setOptionGetResDtos(List<QuestionOptionGetResDto> optionGetResDtos) {
		this.optionGetResDtos = optionGetResDtos;
	}

}
