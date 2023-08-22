package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.Result;

@Repository
public class ResultDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Result> getByJob(String jobId){
		final List<Result> results = new ArrayList<>();
		final String sql = "SELECT  "
				+ "tr.id, tcp.full_name, tr.grade, tr.notes, tr.ver "
				+ "FROM  "
				+ "t_result tr "
				+ "INNER JOIN "
				+ "t_candidate tc ON tc.id = tr.candidate_id  "
				+ "INNER JOIN  "
				+ "t_candidate_profile tcp ON tc.profile_id = tcp.id "
				+ "INNER JOIN "
				+ "t_skill_test tst ON tst.id = tr.skill_test_id  "
				+ "WHERE "
				+ "tst.job_id = :jobId ";
		
		final List<?> resultObjs = this.em().createNativeQuery(sql)
				.setParameter("jobId", jobId)
				.getResultList();

		if (resultObjs.size() > 0) {
			for (Object resultObj : resultObjs) {
				final Object[] resultArr = (Object[]) resultObj;

				final Result result = new Result();
				result.setId(resultArr[0].toString());
				
				final Candidate candidate = new Candidate();
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(resultArr[1].toString());
				candidate.setCandidateProfile(candidateProfile);
				result.setCandidate(candidate);
				
				result.setGrade(Double.valueOf(resultArr[2].toString()));
				result.setNotes(resultArr[3].toString());
				result.setVersion(Integer.valueOf(resultArr[4].toString()));
				results.add(result);
			}
		}
		return results;
	}
}
