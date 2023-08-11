package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.QuestionDao;
import com.lawencon.jobportal.admin.dao.QuestionOptionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.question.QuestionGetResDto;
import com.lawencon.jobportal.admin.dto.question.QuestionInsertReqDto;
import com.lawencon.jobportal.admin.dto.question.QuestionOptionReqDto;
import com.lawencon.jobportal.admin.dto.question.QuestionOptionResDto;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.QuestionOption;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionOptionDao questionOptionDao;
	
	public InsertResDto insertQuestion(List<QuestionInsertReqDto> data) {
		Question questionResult  = null;
		for(QuestionInsertReqDto req:data) {
			final Question question = new Question();
			question.setQuestion(req.getQuestion());
			questionResult = questionDao.save(question);
			final List<QuestionOptionReqDto> listQuestionOption = req.getListQuestionOpion();
			for(QuestionOptionReqDto q:listQuestionOption) {
				final QuestionOption questionOption = new QuestionOption();
				questionOption.setQuestion(questionResult);
				questionOption.setLabels(q.getLabels());
				questionOption.setIsAnswer(q.getIsAnswer());
				questionOptionDao.save(questionOption);
			}
		}
		final InsertResDto result = new InsertResDto();
		result.setId(questionResult.getId());
		result.setMessage("Insert Question Successfully.");
		return result;
	}
	
	public Boolean deleteQuestion(String questionId) {
		final List<QuestionOption> listQuestionOption = questionOptionDao.getByQuestion(questionId);
		for(QuestionOption q:listQuestionOption) {
			questionOptionDao.deleteById(QuestionOption.class, q.getId());
		}
		final Boolean result = questionDao.deleteById(Question.class, questionId);
		return result;
	}
	
	public List<QuestionGetResDto> getAllQuestion(){
		final List<Question> listQuestion = questionDao.getAll(Question.class);
		final List<QuestionGetResDto> listResult = new ArrayList<>();
		for(Question q:listQuestion) {
			final QuestionGetResDto result = new QuestionGetResDto();
			result.setQuestion(q.getId());
			result.setQuestion(q.getQuestion());
			listResult.add(result);
		}
		return listResult;
	}
	
	public List<QuestionGetResDto> getAllQuestionBySkillTest(String skillTestId){
		final List<Question> listQuestion = questionDao.getBySkillTest(skillTestId);
		final List<QuestionGetResDto> listResult = new ArrayList<>();
		for(Question q:listQuestion) {
			final QuestionGetResDto result = new QuestionGetResDto();
			result.setQuestionId(q.getId());
			result.setQuestion(q.getQuestion());
			final List<QuestionOption> listQuestionOption = questionOptionDao.getByQuestion(q.getId());
			final List<QuestionOptionResDto> listQuestionOptionResult = new ArrayList<>();
			for(QuestionOption qo:listQuestionOption) {
				final QuestionOptionResDto resultOption = new QuestionOptionResDto();
				resultOption.setQuestionOptionId(qo.getId());
				resultOption.setLabels(qo.getLabels());
				resultOption.setIsAnswer(qo.getIsAnswer());
				listQuestionOptionResult.add(resultOption);
			}
			result.setListQuestionOption(listQuestionOptionResult);
			listResult.add(result);
		}
		return listResult;
	}
}
