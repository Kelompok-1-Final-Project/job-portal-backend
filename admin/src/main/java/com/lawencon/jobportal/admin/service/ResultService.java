package com.lawencon.jobportal.admin.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.ResultDao;
import com.lawencon.jobportal.admin.dao.SkillTestDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.result.ResultInsertReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Result;
import com.lawencon.jobportal.admin.model.SkillTest;

@Service
public class ResultService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private SkillTestDao skillTestDao;
	
	@Autowired
	private ResultDao resultDao;
	
	public InsertResDto insertResult(ResultInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final SkillTest skillTest = skillTestDao.getById(SkillTest.class, data.getSkillTestId());
		
		final Result result = new Result();
		result.setCandidate(candidate);
		result.setSkillTest(skillTest);
		result.setGrade(data.getScore());
		result.setNotes(data.getNotes());
		final Result resultDb = resultDao.save(result);
		
		final InsertResDto response = new InsertResDto();
		response.setId(resultDb.getId());
		response.setMessage("Answer Successfully Inserted.");
		
		em().getTransaction().commit();
		
		return response;
	}
	
	public Boolean deleteResult(String resultId) {
		em().getTransaction().begin();
		final Boolean result = resultDao.deleteById(Result.class, resultId);
		em().getTransaction().commit();
		return result;
	}
}
