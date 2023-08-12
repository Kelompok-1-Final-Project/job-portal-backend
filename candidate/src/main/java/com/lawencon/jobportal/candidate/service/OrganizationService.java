package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.candidate.dao.OrganizationDao;
import com.lawencon.jobportal.candidate.dto.organization.OrganizationGetResDto;

public class OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;
	
	public List<OrganizationGetResDto> getByCandidate(String candidateId) {
		final List<OrganizationGetResDto> organizationGetResDtos = new ArrayList<>();

		organizationDao.getByCandidate(candidateId).forEach(o -> {
			final OrganizationGetResDto organizationGetResDto = new OrganizationGetResDto();
			organizationGetResDto.setId(o.getId());
			organizationGetResDto.setOrganizationName(o.getOrganizationName());
			organizationGetResDto.setPositionName(o.getPositionName());
			organizationGetResDto.setDescription(o.getDescription());
			organizationGetResDto.setStartDate(o.getStartDate().toString());
			organizationGetResDto.setEndDate(o.getEndDate().toString());
			organizationGetResDtos.add(organizationGetResDto);
		});

		return organizationGetResDtos;
	}
}
