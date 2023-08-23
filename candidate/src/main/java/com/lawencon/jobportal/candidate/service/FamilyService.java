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
import com.lawencon.jobportal.candidate.dao.DegreeDao;
import com.lawencon.jobportal.candidate.dao.FamilyDao;
import com.lawencon.jobportal.candidate.dao.RelationshipDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.degree.DegreeGetResDto;
import com.lawencon.jobportal.candidate.dto.family.FamilyGetResDto;
import com.lawencon.jobportal.candidate.dto.family.FamilyInsertReqDto;
import com.lawencon.jobportal.candidate.dto.family.FamilyUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.relationship.RelationshipGetResDto;
import com.lawencon.jobportal.candidate.model.Degree;
import com.lawencon.jobportal.candidate.model.Family;
import com.lawencon.jobportal.candidate.model.Relationship;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.util.DateConvert;

@Service
public class FamilyService {
	
	@Autowired
	private FamilyDao familyDao;
	
	@Autowired
	private RelationshipDao relationshipDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DegreeDao degreeDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	
	public List<FamilyGetResDto> getByCandidate(String candidateId) {
		final List<FamilyGetResDto> familyGetResDtos = new ArrayList<>();

		familyDao.getByCandidate(candidateId).forEach(f -> {
			final FamilyGetResDto familyGetResDto = new FamilyGetResDto();
			familyGetResDto.setId(f.getId());
			familyGetResDto.setFamilyName(f.getFamilyName());
			familyGetResDto.setFamilyDegree(f.getFamilyDegree().getDegreeName());
			familyGetResDto.setDegreeCode(f.getFamilyDegree().getDegreeCode());
			familyGetResDto.setRelationshipName(f.getRelationship().getRelationshipName());
			familyGetResDto.setRelationshipCode(f.getRelationship().getRelationshipCode());
			familyGetResDto.setFamilyBirthDate(f.getFamilyBirthdate().toString());
			familyGetResDtos.add(familyGetResDto);
		});

		return familyGetResDtos;
	}
	
	public List<RelationshipGetResDto> getAllRelationship() {
		final List<Relationship> listRelationship = relationshipDao.getAll(Relationship.class);
		final List<RelationshipGetResDto> listResult = new ArrayList<>();
		for(Relationship r: listRelationship) {
			final RelationshipGetResDto result = new RelationshipGetResDto();
			result.setRelationshipId(r.getId());
			result.setRelationshipCode(r.getRelationshipCode());
			result.setRelationshipName(r.getRelationshipName());
			listResult.add(result);
		}
		return listResult;
	}
	
	public List<DegreeGetResDto> getAllDegree() {
		final List<Degree> listDegree = degreeDao.getAll(Degree.class);
		final List<DegreeGetResDto> listResult = new ArrayList<>();
		for(Degree d: listDegree) {
			final DegreeGetResDto result = new DegreeGetResDto();
			result.setId(d.getId());
			result.setDegreeName(d.getDegreeName());
			result.setDegreeCode(d.getDegreeCode());
			listResult.add(result);
		}
		return listResult;
	}

	public InsertResDto insertFamily(FamilyInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final Family family = new Family();
			final User candidate = userDao.getById(User.class, data.getUserId());
			data.setUserEmail(candidate.getEmail());
			family.setCandidate(candidate);
			family.setFamilyName(data.getFamilyName());
			
			final Relationship relationship = relationshipDao.getByCode(data.getRelationshipCode());
			final Relationship relationshipResult = relationshipDao.getById(Relationship.class, relationship.getId());
			family.setRelationship(relationshipResult);
			
			final Degree degree = degreeDao.getByCode(data.getDegreeCode());
			final Degree degreeResult = degreeDao.getById(Degree.class, degree.getId());
			family.setFamilyDegree(degreeResult);
			
			family.setFamilyBirthdate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
			
			final Family familyResult = familyDao.save(family);
		
			final String familyInsertAdminAPI = "http://localhost:8080/families";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<FamilyInsertReqDto> familyInsert = RequestEntity.post(familyInsertAdminAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(familyInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(familyResult.getId());
				result.setMessage("Family added successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean deleteFamily(String familyId) {
		em().getTransaction().begin();
		
		final Boolean result = familyDao.deleteById(Family.class, familyId);
		
		em().getTransaction().commit();
		return result;
	}
	
	public UpdateResDto updateFamily(FamilyUpdateReqDto data) {
		em().getTransaction().begin();
		
		final Family family = familyDao.getById(Family.class, data.getFamilyId());
		family.setFamilyName(data.getFamilyName());
		family.setFamilyBirthdate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
		
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		family.setCandidate(candidate);
		
		final Degree degreeDb = degreeDao.getByCode(data.getDegreeCode());
		final Degree degree = degreeDao.getById(Degree.class, degreeDb.getId());
		family.setFamilyDegree(degree);
		
		final Relationship relationshipDb = relationshipDao.getByCode(data.getRelationshipCode());
		final Relationship relationship = relationshipDao.getById(Relationship.class, relationshipDb.getId());
		family.setRelationship(relationship);
		final Family familyResult = familyDao.saveAndFlush(family);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(familyResult.getVersion());
		result.setMessage("Family Updated Successfully.");
		
		em().getTransaction().commit();
		return result;
		
	}
}
