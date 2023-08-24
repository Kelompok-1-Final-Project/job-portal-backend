package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.QuestionDao;
import com.lawencon.jobportal.candidate.dao.QuestionOptionDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionGetResDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionInsertReqDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionOptionReqDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionOptionResDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionOptionUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Question;
import com.lawencon.jobportal.candidate.model.QuestionOption;

@Service
public class QuestionService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionOptionDao questionOptionDao;
	
	public InsertResDto insertQuestion(List<QuestionInsertReqDto> data) {
		em().getTransaction().begin();
		final InsertResDto result = new InsertResDto();
		try {
			Question questionResult  = null;
			for(QuestionInsertReqDto req:data) {
				final Question question = new Question();
				question.setQuestion(req.getQuestion());
				question.setQuestionCode(req.getQuestionCode());
				questionResult = questionDao.save(question);
				final List<QuestionOptionReqDto> listQuestionOption = req.getListQuestionOption();
				for(QuestionOptionReqDto q:listQuestionOption) {
					final QuestionOption questionOption = new QuestionOption();
					questionOption.setQuestion(questionResult);
					questionOption.setLabels(q.getLabels());
					questionOption.setIsAnswer(q.getIsAnswer());
					questionOption.setOptionCode(q.getOptionCode());
					questionOptionDao.save(questionOption);
				}
			}
			em().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		
		result.setMessage("Question Successfully Inserted.");
		
		return result;
	}
	
	public UpdateResDto updateQuestion(QuestionUpdateReqDto data) {
		em().getTransaction().begin();
		
		final Question questionGet = questionDao.getByCode(data.getQuestionCode());
		final Question question = questionDao.getById(Question.class, questionGet.getId());
		question.setQuestion(data.getQuestion());
		final Question questionResult = questionDao.saveAndFlush(question);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(questionResult.getVersion());
		result.setMessage("Question Successfully Updated.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public UpdateResDto updateQuestionOption(QuestionOptionUpdateReqDto data) {
		em().getTransaction().begin(); 
		
		final QuestionOption optionId = questionOptionDao.getByCode(data.getOptionCode());
		final QuestionOption option = questionOptionDao.getById(QuestionOption.class, optionId.getId());
		option.setLabels(data.getLabels());
		option.setIsAnswer(data.getIsAnswer());
		final QuestionOption optionResult = questionOptionDao.save(option);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(optionResult.getVersion());
		result.setMessage("Option Successfully Updated.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public Boolean deleteQuestion(String questionId) {
		em().getTransaction().begin();
		
		final List<QuestionOption> listQuestionOption = questionOptionDao.getByQuestion(questionId);
		for(QuestionOption q:listQuestionOption) {
			questionOptionDao.deleteById(QuestionOption.class, q.getId());
		}
		final Boolean result = questionDao.deleteById(Question.class, questionId);
		
		em().getTransaction().commit();
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
