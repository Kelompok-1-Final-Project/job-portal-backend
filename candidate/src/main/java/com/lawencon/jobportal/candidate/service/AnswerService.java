package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.QuestionDao;
import com.lawencon.jobportal.candidate.dao.QuestionOptionDao;
import com.lawencon.jobportal.candidate.dao.SkillTestDao;
import com.lawencon.jobportal.candidate.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.answer.AnswerInsertReqDto;
import com.lawencon.jobportal.candidate.dto.answer.QuestionGetResDto;
import com.lawencon.jobportal.candidate.dto.answer.QuestionOptionGetResDto;
import com.lawencon.jobportal.candidate.dto.answer.TestGetResDto;
import com.lawencon.jobportal.candidate.model.SkillTest;

@Service
public class AnswerService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private SkillTestDao skillTestDao;
	
	@Autowired
	private SkillTestQuestionDao skillTestQuestionDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionOptionDao questionOptionDao;
	
	public TestGetResDto getAllQuestion(String jobId){
		final TestGetResDto testGetResDto = new TestGetResDto();
		
		final SkillTest skillTest = skillTestDao.getByJob(jobId);
		testGetResDto.setTestId(skillTest.getId());
		testGetResDto.setTestName(skillTest.getTestName());
		testGetResDto.setTestCode(skillTest.getTestCode());
		final List<QuestionGetResDto> questionGetResDtos = new ArrayList<>();
		
		skillTestQuestionDao.getBySkillTest(skillTest.getId()).forEach(stq -> {
			final QuestionGetResDto questionGetResDto = new QuestionGetResDto();
			final List<QuestionOptionGetResDto> questionOptionGetResDtos = new ArrayList<>();
			
			questionOptionDao.getByQuestion(stq.getQuestion().getId()).forEach(qo -> {
				final QuestionOptionGetResDto questionOptionGetResDto = new QuestionOptionGetResDto();
				questionOptionGetResDto.setOptionId(qo.getId());
				questionOptionGetResDto.setLabel(qo.getLabels());
				questionOptionGetResDto.setIsAnswer(qo.getIsAnswer());
				questionOptionGetResDtos.add(questionOptionGetResDto);
			});
			
			questionGetResDto.setQuestion(stq.getQuestion().getQuestion());
			questionGetResDto.setQuestionCode(stq.getQuestion().getQuestionCode());
			questionGetResDto.setOptionGetResDtos(questionOptionGetResDtos);
			questionGetResDtos.add(questionGetResDto);
		});
		
		testGetResDto.setQuestionGetResDtos(questionGetResDtos);
		
		return testGetResDto;
	}
	
	
	public InsertResDto answerInsert(List<AnswerInsertReqDto> data) {
		
	}
}
