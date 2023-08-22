package com.lawencon.jobportal.admin.service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.WorkExperience;

@Service
public class WorkExperienceService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
//	public InsertResDto insertWorkExperience(WorkExperienceInsertReqDto data) {
//		em().getTransaction().begin();
//
//		final WorkExperience experience = new WorkExperience();
//		final Candidate candidate = userDao.getById(Candidate.class, data.getCandidateId());
//		experience.setCandidate(candidate);
//		experience.setPositionName(data.getPositionName());
//		experience.setCompanyName(data.getCompanyName());
//		experience.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
//		experience.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
//		final WorkExperience experienceResult = workExperienceDao.save(experience);
//		final InsertResDto result = new InsertResDto();
//		result.setId(experienceResult.getId());
//		result.setMessage("Experience Successfully added.");
//
//		em().getTransaction().commit();
//		return result;
//	}
}
