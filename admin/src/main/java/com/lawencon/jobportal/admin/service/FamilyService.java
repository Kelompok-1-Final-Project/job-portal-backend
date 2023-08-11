package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.RelationshipDao;
import com.lawencon.jobportal.admin.dto.relationship.RelationshipGetResDto;
import com.lawencon.jobportal.admin.model.Relationship;

@Service
public class FamilyService {
	
	@Autowired
	private RelationshipDao relationshipDao;
	
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
}
