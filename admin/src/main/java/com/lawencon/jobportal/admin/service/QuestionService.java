package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.admin.dao.QuestionDao;
import com.lawencon.jobportal.admin.dao.QuestionOptionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.question.QuestionGetResDto;
import com.lawencon.jobportal.admin.dto.question.QuestionInsertReqDto;
import com.lawencon.jobportal.admin.dto.question.QuestionOptionReqDto;
import com.lawencon.jobportal.admin.dto.question.QuestionOptionResDto;
import com.lawencon.jobportal.admin.dto.question.QuestionOptionUpdateReqDto;
import com.lawencon.jobportal.admin.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.QuestionOption;
import com.lawencon.jobportal.admin.util.GeneratorId;

@Service
public class QuestionService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionOptionDao questionOptionDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public InsertResDto insertQuestion(List<QuestionInsertReqDto> data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			Question questionResult  = null;
			for(int i = 0; i < data.size(); i++) {
				final Question question = new Question();
				question.setQuestion(data.get(i).getQuestion());
				final String questionCode = GeneratorId.generateCode();
				question.setQuestionCode(questionCode);
				data.get(i).setQuestionCode(questionCode);
				
				questionResult = questionDao.save(question);
				final List<QuestionOptionReqDto> listQuestionOption = data.get(i).getListQuestionOption();
				for(QuestionOptionReqDto q:listQuestionOption) {
					final QuestionOption questionOption = new QuestionOption();
					questionOption.setQuestion(questionResult);
					questionOption.setLabels(q.getLabels());
					questionOption.setIsAnswer(q.getIsAnswer());
					questionOptionDao.save(questionOption);
				}
			}
			final String questionCandidateAPI = "http://localhost:8081/questions";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<List<QuestionInsertReqDto>> questionInsert = RequestEntity.post(questionCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(questionInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setMessage("Question Successfully Inserted.");
				
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public UpdateResDto updateQuestion(QuestionUpdateReqDto data) {
		em().getTransaction().begin();
		
		final Question question = questionDao.getById(Question.class, data.getQuestionId());
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
		
		final QuestionOption option = questionOptionDao.getById(QuestionOption.class, data.getOptionId());
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
	
	public QuestionGetResDto getDetailByQuestion(String questionId){
		final Question question = questionDao.getById(Question.class, questionId);
		final QuestionGetResDto questionGetResDto = new QuestionGetResDto();
		questionGetResDto.setQuestionId(question.getId());
		questionGetResDto.setQuestion(question.getQuestion());
		
		final List<QuestionOption> listQuestionOption = questionOptionDao.getByQuestion(question.getId());
		final List<QuestionOptionResDto> listQuestionOptionResult = new ArrayList<>();
		for(QuestionOption qo:listQuestionOption) {
			final QuestionOptionResDto resultOption = new QuestionOptionResDto();
			resultOption.setQuestionOptionId(qo.getId());
			resultOption.setLabels(qo.getLabels());
			resultOption.setIsAnswer(qo.getIsAnswer());
			listQuestionOptionResult.add(resultOption);
		}
		questionGetResDto.setListQuestionOption(listQuestionOptionResult);

		return questionGetResDto;
	}
	
}
