package com.lawencon.jobportal.candidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Level;
import com.lawencon.jobportal.candidate.model.Skill;
import com.lawencon.jobportal.candidate.model.UserSkill;

@Repository
public class UserSkillDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<UserSkill> getByCandidate(String candidateId){
		final List<UserSkill> userSkills = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "tus.id, ts.skill_name, tl.level_name, tus.ver, ts.skill_code, tl.level_code "
				+ "FROM "
				+ "t_user_skill tus "
				+ "INNER JOIN "
				+ "t_skill ts "
				+ "ON "
				+ "ts.id = tus.skill_id "
				+ "INNER JOIN "
				+ "t_level tl "
				+ "ON "
				+ "tus.level_id = tl.id "
				+ "WHERE "
				+ "candidate_id = :candidateId ";
		
		final List<?> userSkillObjs = em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		if (userSkillObjs.size() > 0) {
			for (Object userSkillObj : userSkillObjs) {
				final Object[] userSkillArr = (Object[]) userSkillObj;

				final UserSkill userSkill = new UserSkill();
				userSkill.setId(userSkillArr[0].toString());
				
				final Skill skill = new Skill();
				skill.setSkillName(userSkillArr[1].toString());
				skill.setSkillCode(userSkillArr[4].toString());
				userSkill.setSkill(skill);
				
				final Level level = new Level();
				level.setLevelName(userSkillArr[2].toString());
				level.setLevelCode(userSkillArr[5].toString());
				userSkill.setLevel(level);
				
				userSkill.setVersion(Integer.valueOf(userSkillArr[3].toString()));
				
				userSkills.add(userSkill);
			}
		}

		return userSkills;

	}
}
