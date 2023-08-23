package com.lawencon.jobportal.admin.dto.question;

public class QuestionOptionUpdateReqDto {
	private String quesstionCode;
	private String optionId;
	private String labels;
	private Boolean isAnswer;

	public String getQuesstionCode() {
		return quesstionCode;
	}

	public void setQuesstionCode(String quesstionCode) {
		this.quesstionCode = quesstionCode;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
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
