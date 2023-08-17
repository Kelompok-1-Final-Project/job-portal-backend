package com.lawencon.jobportal.candidate.service;

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
import com.lawencon.jobportal.candidate.dao.QuestionDao;
import com.lawencon.jobportal.candidate.dao.QuestionOptionDao;
import com.lawencon.jobportal.candidate.dao.SkillTestDao;
import com.lawencon.jobportal.candidate.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.answer.AnswerCandidateReqDto;
import com.lawencon.jobportal.candidate.dto.answer.AnswerInsertReqDto;
import com.lawencon.jobportal.candidate.dto.answer.QuestionGetResDto;
import com.lawencon.jobportal.candidate.dto.answer.QuestionOptionGetResDto;
import com.lawencon.jobportal.candidate.dto.answer.ScoreInsertReqDto;
import com.lawencon.jobportal.candidate.dto.answer.TestGetResDto;
import com.lawencon.jobportal.candidate.model.QuestionOption;
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
	
	@Autowired
	private RestTemplate restTemplate;
	
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
	
	
	public InsertResDto answerInsert(AnswerInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			int totalCorrectAnswer = 0;
			final List<AnswerCandidateReqDto> answerCandidateReqDto = data.getAnswerCandidateReqDtos();
			final int totalMultiChoice = answerCandidateReqDto.size();
			for(AnswerCandidateReqDto answer : answerCandidateReqDto) {
				final QuestionOption questionOption = questionOptionDao.getById(QuestionOption.class, answer.getOptionId());
				if (questionOption.getIsAnswer()) {
					totalCorrectAnswer++;
				}
			}
			if(totalMultiChoice != 0) {
				final double score = (totalCorrectAnswer/totalMultiChoice) * 100.0;
				final String notes = ("You answered " +totalCorrectAnswer+" out of "+ totalMultiChoice +" questions correctly. The final score is" + score);
				
				final ScoreInsertReqDto dataSend = new ScoreInsertReqDto();
				dataSend.setCandidateId(data.getCandidateId());
				dataSend.setSkillTestId(data.getSkillTestId());
				dataSend.setScore(score);
				dataSend.setNotes(notes);	
				
				final String resultCandidateAPI = "http://localhost:8081/results";

				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(JwtConfig.get());

				final RequestEntity<ScoreInsertReqDto> progressUpdate = RequestEntity
						.post(resultCandidateAPI).headers(headers).body(dataSend);

				final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(progressUpdate,
						InsertResDto.class);

				if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
					insertResDto.setMessage("Insert Data Successfully.");
					em().getTransaction().commit();
				} else {
					em().getTransaction().rollback();
					throw new RuntimeException("Insert Failed");
				}
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		return insertResDto;
	}
}
