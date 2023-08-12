package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Level;

@Repository
public class LevelDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Level getByCode(String code) {
		final String sql = "SELECT "
				+ "id, level_code, level_name, ver "
				+ "FROM "
				+ "t_level "
				+ "WHERE "
				+ "level_code = :code";

		final Object levelObj = em().createNativeQuery(sql, Level.class)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] levelArr = (Object[]) levelObj;

		Level level = null;

		if (levelArr.length > 0) {
			level = new Level();
			level.setId(levelArr[0].toString());
			level.setLevelCode(levelArr[1].toString());
			level.setLevelName(levelArr[2].toString());
			level.setVersion(Integer.valueOf(levelArr[3].toString()));
		}

		return level;
	}
}
