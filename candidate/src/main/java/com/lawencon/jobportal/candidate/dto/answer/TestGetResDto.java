package com.lawencon.jobportal.candidate.dto.answer;

import java.util.List;

public class TestGetResDto {
	private String testId;
	private String testName;
	private String testCode;
	private List<QuestionGetResDto> questionGetResDtos;

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public List<QuestionGetResDto> getQuestionGetResDtos() {
		return questionGetResDtos;
	}

	public void setQuestionGetResDtos(List<QuestionGetResDto> questionGetResDtos) {
		this.questionGetResDtos = questionGetResDtos;
	}

}
