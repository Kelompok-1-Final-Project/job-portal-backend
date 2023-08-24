package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Skill;

@Repository
public class SkillDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Skill getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, skill_name, skill_code, ver ");
		sql.append("FROM t_skill ");
		sql.append("WHERE skill_code = :code ");

		final Object skillObj = em().createNativeQuery(sql.toString())
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
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, skill_name, skill_code, ver ");
		sql.append("FROM t_skill ");
		sql.append("WHERE skill_name = :skillName ");

		final Object skillObj = em().createNativeQuery(sql.toString())
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
