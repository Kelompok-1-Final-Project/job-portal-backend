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
import com.lawencon.jobportal.candidate.dao.OrganizationDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.organization.OrganizationGetResDto;
import com.lawencon.jobportal.candidate.dto.organization.OrganizationInsertReqDto;
import com.lawencon.jobportal.candidate.dto.organization.OrganizationUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Organization;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.util.DateConvert;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RestTemplate restTemplate;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

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

	public InsertResDto insertOrganization(OrganizationInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Organization organization = new Organization();
			final User candidate = userDao.getById(User.class, data.getCandidateId());
			organization.setCandidate(candidate);
			organization.setOrganizationName(data.getOrganizationName());
			organization.setPositionName(data.getPositionName());
			organization.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
			organization.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
			organization.setDescription(data.getDescription());
			final Organization organizationResult = organizationDao.save(organization);
			
			final String organizationInsertAdminAPI = "http://localhost:8080/organizations";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<OrganizationInsertReqDto> organizationInsert = RequestEntity.post(organizationInsertAdminAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(organizationInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(organizationResult.getId());
				result.setMessage("Organization Successfully added.");
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

	public boolean deleteOrganization(String organizationId) {
		em().getTransaction().begin();

		final Boolean result = organizationDao.deleteById(Organization.class, organizationId);

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateOrganization(OrganizationUpdateReqDto data) {
		em().getTransaction().begin();

		final Organization organization = organizationDao.getById(Organization.class, data.getOrganizationId());
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		organization.setCandidate(candidate);
		organization.setOrganizationName(data.getOrganizationName());
		organization.setPositionName(data.getPositionName());
		organization.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		organization.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		organization.setDescription(data.getDescription());
		final Organization organizationResult = organizationDao.saveAndFlush(organization);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(organizationResult.getVersion());
		result.setMessage("Organization Successfully Updated.");

		em().getTransaction().commit();
		return result;
	}
}
