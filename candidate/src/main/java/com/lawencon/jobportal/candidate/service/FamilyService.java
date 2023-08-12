package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.candidate.dao.FamilyDao;
import com.lawencon.jobportal.candidate.dto.family.FamilyGetResDto;

public class FamilyService {
	@Autowired
	private FamilyDao familyDao;
	
	public List<FamilyGetResDto> getByCandidate(String candidateId) {
		final List<FamilyGetResDto> familyGetResDtos = new ArrayList<>();

		familyDao.getByCandidate(candidateId).forEach(f -> {
			final FamilyGetResDto familyGetResDto = new FamilyGetResDto();
			familyGetResDto.setId(f.getId());
			familyGetResDto.setFamilyName(f.getFamilyname());
			familyGetResDto.setFamilyDegree(f.getFamilyDegree());
			familyGetResDto.setRelationshipName(f.getRelationship().getRelationshipName());
			familyGetResDto.setFamilyBirthDate(f.getFamilyBirtdate());
			familyGetResDtos.add(familyGetResDto);
		});

		return familyGetResDtos;
	}

}
