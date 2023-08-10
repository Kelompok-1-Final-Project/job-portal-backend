package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.SkillTest;

@Repository
public class SkillTestDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public SkillTest getByCandidateAndJob(String candidateId, String jobId) {
		final String sql = "SELECT "
				+ "id, test_name, job_id, grade, notes, candidate_id, ver "
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
			
			skillTest.setGrade(Double.valueOf(skillTestArr[3].toString()));
			skillTest.setNotes(skillTestArr[4].toString());
			
			final Candidate candidate = new Candidate();
			candidate.setId(skillTestArr[5].toString());
			skillTest.setCandidate(candidate);
			
			skillTest.setVersion(Integer.valueOf(skillTestArr[6].toString()));
			
		}
		
		return skillTest;
	}
}
