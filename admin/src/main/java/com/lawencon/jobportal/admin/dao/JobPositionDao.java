package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.JobPosition;

@Repository
public class JobPositionDao extends AbstractJpaDao{

	private final EntityManager em = ConnHandler.getManager();
	
	public List<JobPosition> getByPositionName(String positionName){
		final List<JobPosition> jobPositions = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "id, position_code, position_name, ver "
				+ "FROM "
				+ "t_job_position "
				+ "WHERE "
				+ "position_name ILIKE ':positionName%'";
		
		final List<?> positionObjs = this.em.createNativeQuery(sql)
				.setParameter("positionName", positionName)
				.getResultList();
		
		if(positionObjs.size() > 0) {
			for (Object positionObj : positionObjs) {
				final Object[] positionArr = (Object[]) positionObj;
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setId(positionArr[0].toString());
				jobPosition.setPositionCode(positionArr[1].toString());
				jobPosition.setPositionName(positionArr[2].toString());
				jobPosition.setVersion(Integer.valueOf(positionArr[3].toString()));
				jobPositions.add(jobPosition);
			}
		}
		
		return jobPositions;
	}
	
	public JobPosition getByCode(String positionCode) {
		final String sql = "SELECT "
				+ "id, position_code, position_name, ver "
				+ "FROM "
				+ "t_job_position "
				+ "WHERE "
				+ "position_code = :positionCode";
		
		final Object jobPositionObj = this.em.createNativeQuery(sql, JobPosition.class)
				.setParameter("positionCode", positionCode)
				.getSingleResult();
		
		final Object[] positionArr = (Object[]) jobPositionObj;
		
		JobPosition jobPosition = null;
		
		if (positionArr.length > 0) {
			jobPosition = new JobPosition();
			jobPosition.setId(positionArr[0].toString());
			jobPosition.setPositionCode(positionArr[1].toString());
			jobPosition.setPositionName(positionArr[2].toString());
			jobPosition.setVersion(Integer.valueOf(positionArr[3].toString()));
		}
		
		return jobPosition;
	}
	
	
}
