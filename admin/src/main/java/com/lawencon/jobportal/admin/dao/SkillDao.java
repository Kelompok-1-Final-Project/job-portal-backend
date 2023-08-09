package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Skill;

@Repository
public class SkillDao extends AbstractJpaDao {
	private final EntityManager em = ConnHandler.getManager();

	public Skill getByCode(String code) {
		final String sql = "SELECT "
				+ "id, skill_name, skill_code, ver " 
				+ "FROM " 
				+ "t_skill " 
				+ "WHERE "
				+ "skill_code = :code";

		final Object skillObj = this.em.createNativeQuery(sql, Skill.class)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] skillArr = (Object[]) skillObj;

		Skill skill = null;

		if (skillArr.length > 0) {
			skill = new Skill();
			skill.setId(skillArr[0].toString());
			skill.setSkillName(skillArr[1].toString());
			skill.setSkillCode(skillArr[2].toString());
			skill.setVersion(Integer.valueOf(skillArr[3].toString()));
		}

		return skill;
	}
	
	public Skill getByName(String skillName) {
		final String sql = "SELECT "
				+ "id, skill_name, skill_code, ver " 
				+ "FROM " 
				+ "t_skill " 
				+ "WHERE "
				+ "skill_name = :skillName";

		final Object skillObj = this.em.createNativeQuery(sql, Skill.class)
				.setParameter("skillName", skillName)
				.getSingleResult();

		final Object[] skillArr = (Object[]) skillObj;

		Skill skill = null;

		if (skillArr.length > 0) {
			skill = new Skill();
			skill.setId(skillArr[0].toString());
			skill.setSkillName(skillArr[1].toString());
			skill.setSkillCode(skillArr[2].toString());
			skill.setVersion(Integer.valueOf(skillArr[3].toString()));
		}

		return skill;
	}
}
