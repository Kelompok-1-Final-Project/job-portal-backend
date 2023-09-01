package com.lawencon.jobportal.candidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Organization;

@Repository
public class OrganizationDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Organization> getByCandidate(String candidateId){
		final List<Organization> organizations = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, organization_name, position_name, start_date, end_date, description, ver ");
		sql.append("FROM t_organization ");
		sql.append("WHERE candidate_id = :candidateId ");
		
		final List<?> organizationObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		if (organizationObjs.size() > 0) {
			for (Object organizationObj : organizationObjs) {
				final Object[] organizationArr = (Object[]) organizationObj;

				final Organization organization = new Organization();
				
				organization.setId(organizationArr[0].toString());
				organization.setOrganizationName(organizationArr[1].toString());
				organization.setPositionName(organizationArr[2].toString());
				organization.setStartDate(LocalDate.parse(organizationArr[3].toString()));
				organization.setEndDate(LocalDate.parse(organizationArr[4].toString()));
				organization.setDescription(organizationArr[5].toString());
				organization.setVersion(Integer.valueOf(organizationArr[6].toString()));
			
				organizations.add(organization);
			}
		}

		return organizations;

	}
}
