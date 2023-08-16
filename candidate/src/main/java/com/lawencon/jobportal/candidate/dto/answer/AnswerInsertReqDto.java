package com.lawencon.jobportal.candidate.dto.answer;

public class AnswerInsertReqDto {
	private String questionId;
	private String optionId;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

}
