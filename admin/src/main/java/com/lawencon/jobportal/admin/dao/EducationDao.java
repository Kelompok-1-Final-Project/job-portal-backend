package com.lawencon.jobportal.admin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Education;

@Repository
public class EducationDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Education> getByCandidate(String candidateId){
		final List<Education> educations = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, education_name, start_date, end_date, ver ");
		sql.append("FROM t_education ");
		sql.append("WHERE candidate_id = :candidateId ");
		
		final List<?> educationObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		if (educationObjs.size() > 0) {
			for (Object educationObj : educationObjs) {
				final Object[] educationArr = (Object[]) educationObj;

				final Education education = new Education();
				education.setId(educationArr[0].toString());
				education.setEducationName(educationArr[1].toString());
				education.setStartDate(LocalDate.parse(educationArr[2].toString()));
				education.setEndDate(LocalDate.parse(educationArr[3].toString()));
				education.setVersion(Integer.valueOf(educationArr[4].toString()));
				educations.add(education);
			}
		}

		return educations;

	}
}
