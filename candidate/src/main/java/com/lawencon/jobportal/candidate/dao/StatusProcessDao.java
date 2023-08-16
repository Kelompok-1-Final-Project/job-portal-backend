package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.StatusProcess;

@Repository
public class StatusProcessDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public StatusProcess getByCode(String code) {
		final String sql = "SELECT "
				+ "id, process_name, process_code, ver " 
				+ "FROM " 
				+ "t_status_process " 
				+ "WHERE "
				+ "process_code = :code";

		final Object statusObj = this.em().createNativeQuery(sql)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] statusArr = (Object[]) statusObj;

		StatusProcess statusProcess = null;

		if (statusArr.length > 0) {
			statusProcess = new StatusProcess();
			statusProcess.setId(statusArr[0].toString());
			statusProcess.setProcessCode(statusArr[1].toString());
			statusProcess.setProcessName(statusArr[2].toString());
			statusProcess.setVersion(Integer.valueOf(statusArr[3].toString()));
		}

		return statusProcess;
	}
}
