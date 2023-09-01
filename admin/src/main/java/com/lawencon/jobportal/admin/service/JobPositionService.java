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
import com.lawencon.jobportal.admin.dao.JobPositionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.jobposition.JobPositionGetResDto;
import com.lawencon.jobportal.admin.dto.jobposition.JobPositionInsertReqDto;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.util.GeneratorId;

@Service
public class JobPositionService {
	
	@Autowired
	private JobPositionDao jobPositionDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
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
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final String jobPositionCode = GeneratorId.generateCode();
			final JobPosition jobPosition = new JobPosition();
			jobPosition.setPositionName(data.getPositionName());
			jobPosition.setPositionCode(jobPositionCode);
			data.setPositionCode(jobPositionCode);
			
			final JobPosition jobPositionResult = jobPositionDao.save(jobPosition);
			
			final String positionInsertCandidateAPI = "http://localhost:8081/job-position";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<JobPositionInsertReqDto> positionInsert = RequestEntity.post(positionInsertCandidateAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(positionInsert,
					InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				insertResDto.setId(jobPositionResult.getId());
				insertResDto.setMessage("Job Position Insert Successfully");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
			
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return insertResDto;
	}

}
