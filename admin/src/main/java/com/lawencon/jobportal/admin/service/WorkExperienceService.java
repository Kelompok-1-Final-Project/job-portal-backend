package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.WorkExperienceDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.workexperience.WorkExperienceGetResDto;
import com.lawencon.jobportal.admin.dto.workexperience.WorkExperienceInsertReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.WorkExperience;
import com.lawencon.jobportal.admin.util.DateConvert;

@Service
public class WorkExperienceService {
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private WorkExperienceDao workExperienceDao;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<WorkExperienceGetResDto> getByCandidate(String candidateId) {
		final List<WorkExperienceGetResDto> experienceGetResDtos = new ArrayList<>();

		workExperienceDao.getByCandidate(candidateId).forEach(we -> {
			final WorkExperienceGetResDto experienceGetResDto = new WorkExperienceGetResDto();
			experienceGetResDto.setId(we.getId());
			experienceGetResDto.setPositionName(we.getPositionName());
			experienceGetResDto.setCompanyName(we.getCompanyName());
			experienceGetResDto.setStartDate(we.getStartDate().toString());
			experienceGetResDto.setEndDate(we.getEndDate().toString());

			experienceGetResDtos.add(experienceGetResDto);
		});

		return experienceGetResDtos;
	}
	
	public InsertResDto insertWorkExperience(WorkExperienceInsertReqDto data) {
		em().getTransaction().begin();

		final WorkExperience experience = new WorkExperience();
		
		final Candidate candidate = candidateDao.getByEmail(data.getCandidateEmail());
		experience.setCandidate(candidate);
		experience.setPositionName(data.getPositionName());
		experience.setCompanyName(data.getCompanyName());
		experience.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		experience.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		
		final WorkExperience experienceResult = workExperienceDao.save(experience);
		
		final InsertResDto result = new InsertResDto();
		result.setId(experienceResult.getId());
		result.setMessage("Experience Successfully added.");

		em().getTransaction().commit();
		return result;
	}
}
