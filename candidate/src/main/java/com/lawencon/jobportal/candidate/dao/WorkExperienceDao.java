package com.lawencon.jobportal.candidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.WorkExperience;

@Repository
public class WorkExperienceDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<WorkExperience> getByCandidate(String candidateId){
		final List<WorkExperience> workExperiences = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "id, position_name, company_name, start_date, end_date, ver "
				+ "FROM "
				+ "t_work_experience "
				+ "WHERE "
				+ "candidate_id = :candidateId ";
		
		final List<?> workExperienceObjs = em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		if (workExperienceObjs.size() > 0) {
			for (Object workExperienceObj : workExperienceObjs) {
				final Object[] workExperienceArr = (Object[]) workExperienceObj;

				final WorkExperience workExperience = new WorkExperience();
				workExperience.setId(workExperienceArr[0].toString());
				workExperience.setPositionName(workExperienceArr[1].toString());
				workExperience.setCompanyName(workExperienceArr[2].toString());
				workExperience.setStartDate(LocalDate.parse(workExperienceArr[3].toString()));
				workExperience.setEndDate(LocalDate.parse(workExperienceArr[4].toString()));
				workExperience.setVersion(Integer.valueOf(workExperienceArr[5].toString()));
				
				workExperiences.add(workExperience);
			}
		}

		return workExperiences;

	}
}
