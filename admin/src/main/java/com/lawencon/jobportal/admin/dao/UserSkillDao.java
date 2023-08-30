package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Level;
import com.lawencon.jobportal.admin.model.Skill;
import com.lawencon.jobportal.admin.model.UserSkill;

@Repository
public class UserSkillDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<UserSkill> getByCandidate(String candidateId){
		final List<UserSkill> userSkills = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tus.id, ts.skill_name, tl.level_name, tus.ver, ts.skill_code, tl.level_code ");
		sql.append("FROM t_user_skill tus ");
		sql.append("INNER JOIN t_skill ts ON ts.id = tus.skill_id ");
		sql.append("INNER JOIN t_level tl ON tus.level_id = tl.id ");
		sql.append("WHERE candidate_id = :candidateId ");
		
		final List<?> userSkillObjs = em().createNativeQuery(sql.toString())
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
