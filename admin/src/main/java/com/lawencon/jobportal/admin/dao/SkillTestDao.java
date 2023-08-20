package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.SkillTest;

@Repository
public class SkillTestDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public SkillTest getByJob(String jobId) {
		final String sql = "SELECT "
				+ "id, test_name, test_code, job_id, ver "
				+ "FROM "
				+ "t_skill_test "
				+ "WHERE "
				+ "job_id = :jobId ";
		
		final Object skillTestObj = em().createNativeQuery(sql)
				.setParameter("jobId", jobId)
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
	
	public SkillTest getByCandidateAndJob(String candidateId, String jobId) {
		final String sql = "SELECT "
				+ "id, test_name, job_id, ver "
				+ "FROM "
				+ "t_skill_test "
				+ "WHERE "
				+ "job_id = :jobId "
				+ "AND "
				+ "candidate_id = : candidateId";
		
		final Object skillTestObj = em().createNamedQuery(sql)
				.setParameter("jobId", jobId)
				.setParameter("candidateId", candidateId)
				.getSingleResult();
		
		final Object[] skillTestArr = (Object[]) skillTestObj;
		
		SkillTest skillTest = null;
		
		if(skillTestArr.length > 0) {
			skillTest = new SkillTest();
			skillTest.setId(skillTestArr[0].toString());
			skillTest.setTestName(skillTestArr[1].toString());
			
			final Job job = new Job();
			job.setId(skillTestArr[2].toString());
			skillTest.setJob(job);
			
			skillTest.setVersion(Integer.valueOf(skillTestArr[3].toString()));
			
		}
		
		return skillTest;
	}
	
	public SkillTest getByCode(String testCode) {
		final String sql = "SELECT "
				+ "st "
				+ "FROM "
				+ "SkillTest st "
				+ "WHERE "
				+ "st.testCode = :testCode";
		
		final SkillTest skillTest = em().createQuery(sql, SkillTest.class)
				.setParameter("testCode", testCode)
				.getSingleResult();
		
		return skillTest;
	}
}
