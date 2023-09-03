package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.SkillTest;

@Repository
public class SkillTestDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public SkillTest getByJob(String jobCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ts.id, ts.test_name, ts.test_code, ts.job_id, ts.ver ");
		sql.append("FROM t_skill_test ts ");
		sql.append("INNER JOIN t_job tj ON tj.id = ts.job_id ");
		sql.append("WHERE tj.job_code = :jobCode ");
		
		final Object skillTestObj = em().createNativeQuery(sql.toString())
				.setParameter("jobCode", jobCode)
				.getSingleResult();
		
		final Object[] skillTestArr = (Object[]) skillTestObj;
		
		SkillTest skillTest = null;
		
		if(skillTestArr.length > 0) {
			skillTest = new SkillTest();
			skillTest.setId(skillTestArr[0].toString());
			skillTest.setTestName(skillTestArr[1].toString());
			skillTest.setTestCode(skillTestArr[2].toString());
			
			final Job job = new Job();
			job.setId(skillTestArr[3].toString());
			skillTest.setJob(job);
			
			skillTest.setVersion(Integer.valueOf(skillTestArr[4].toString()));
			
		}
		
		return skillTest;
	}
	
	public SkillTest getByCode(String testCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT st FROM SkillTest st WHERE st.testCode = :testCode");
		
		final SkillTest skillTest = em().createQuery(sql.toString(), SkillTest.class)
				.setParameter("testCode", testCode)
				.getSingleResult();
		
		return skillTest;
	}
}
