package com.lawencon.jobportal.admin.dto.question;

public class QuestionOptionResDto {

	private String questionOptionId;
	private String labels;
	private Boolean isAnswer;

	public String getQuestionOptionId() {
		return questionOptionId;
	}

	public void setQuestionOptionId(String questionOptionId) {
		this.questionOptionId = questionOptionId;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public Boolean getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(Boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

}
