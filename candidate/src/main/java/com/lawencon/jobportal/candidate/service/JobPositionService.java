package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.JobPositionDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.jobposition.JobPositionGetResDto;
import com.lawencon.jobportal.candidate.dto.jobposition.JobPositionInsertReqDto;
import com.lawencon.jobportal.candidate.model.JobPosition;

@Service
public class JobPositionService {
	
	@Autowired
	private JobPositionDao jobPositionDao;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<JobPositionGetResDto> getAll(){
		final List<JobPositionGetResDto> jobPositionGetResDtos = new ArrayList<>();
		jobPositionDao.getAll(JobPosition.class).forEach(jp -> {
			final JobPositionGetResDto jobPositionGetResDto = new JobPositionGetResDto();
			jobPositionGetResDto.setId(jp.getId());
			jobPositionGetResDto.setPositionName(jp.getPositionName());
			jobPositionGetResDto.setPositionCode(jp.getPositionCode());
			jobPositionGetResDtos.add(jobPositionGetResDto);
		});
		
		return jobPositionGetResDtos;
	}
	
	public InsertResDto insertJobPosition(JobPositionInsertReqDto data) {
		em().getTransaction().begin();
		final InsertResDto insertResDto = new InsertResDto();
		
		final JobPosition jobPosition = new JobPosition();
		jobPosition.setPositionName(data.getPositionName());
		jobPosition.setPositionCode(data.getPositionCode());
		
		final JobPosition jobPositionResult = jobPositionDao.save(jobPosition);
		
		insertResDto.setId(jobPositionResult.getId());
		insertResDto.setMessage("Job Position Insert Successfully");
		
		em().getTransaction().commit();
		return insertResDto;
	}

}
