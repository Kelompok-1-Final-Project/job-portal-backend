package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.DegreeDao;
import com.lawencon.jobportal.admin.dao.FamilyDao;
import com.lawencon.jobportal.admin.dao.RelationshipDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.family.FamilyGetResDto;
import com.lawencon.jobportal.admin.dto.family.FamilyInsertReqDto;
import com.lawencon.jobportal.admin.dto.relationship.RelationshipGetResDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Degree;
import com.lawencon.jobportal.admin.model.Family;
import com.lawencon.jobportal.admin.model.Relationship;

@Service
public class FamilyService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private RelationshipDao relationshipDao;
	
	@Autowired
	private FamilyDao familyDao;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private DegreeDao degreeDao;
	
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
	
	public List<FamilyGetResDto> getAllFamilyCandidate(String candidateId){
		final List<Family> listFamily = familyDao.getAllFamilyCandidate(candidateId);
		final List<FamilyGetResDto> listResult = new ArrayList<>();
		for(Family f:listFamily) {
			final FamilyGetResDto result = new FamilyGetResDto();
			result.setFamilyId(f.getId());
			result.setFamilyName(f.getFamilyName());
			result.setDegreeName(f.getDegree().getDegreeName());
			result.setRealtionshipName(f.getRelationship().getRelationshipName());
			result.setBirthdate(f.getBirthdate().toString());
			listResult.add(result);
		}
		return listResult;
	}
	
	public InsertResDto insertFamily(FamilyInsertReqDto data) {
		em().getTransaction().begin();
		
		final Family family = new Family();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		family.setCandidate(candidate);
		family.setFamilyName(data.getFamilyName());
		
		final Relationship relationship = relationshipDao.getByCode(data.getRelationshipCode());
		final Relationship relationshipResult = relationshipDao.getById(Relationship.class, relationship.getId());
		family.setRelationship(relationshipResult);
		
		final Degree degree = degreeDao.getByCode(data.getDegreeCode());
		final Degree degreeResult = degreeDao.getById(Degree.class, degree.getId());
		family.setDegree(degreeResult);
		
		final Family familyResult = familyDao.save(family);
		
		final InsertResDto result = new InsertResDto();
		result.setId(familyResult.getId());
		result.setMessage("Family added successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public Boolean deleteFamily(String familyId) {
		em().getTransaction().begin();
		
		final Boolean result = familyDao.deleteById(Family.class, familyId);
		
		em().getTransaction().commit();
		return result;
	}
}
