package com.lawencon.jobportal.admin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dto.report.ReportGetResDto;

@Repository
public class ReportDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<ReportGetResDto> getReport(LocalDate startDate, LocalDate endDate){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tcp.full_name, tj.job_title, tet.employment_name, ");
		sql.append("ABS(DATE_PART('day', th.created_at - ta.created_at)) as date_diff ");
		sql.append("FROM t_application ta ");
		sql.append("INNER JOIN t_hired th ON ta.candidate_id = th.candidate_id  ");
		sql.append("INNER JOIN t_candidate tc ON ta.candidate_id = tc.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tc.profile_id = tcp.id ");
		sql.append("INNER JOIN t_job tj ON ta.job_id = tj.id  ");
		sql.append("INNER JOIN t_employment_type tet ON tj.employment_type_id = tet.id ");
		sql.append("WHERE 1 = 1 ");
		
		if(startDate != null && endDate != null) {
			sql.append("AND ta.created_at >= :startDate AND th.created_at <= :endDate ");
		}
		
		final Query reportQuery = this.em().createNativeQuery(sql.toString());
		
		if (startDate != null && endDate != null) {
			reportQuery.setParameter("startDate", startDate);
			reportQuery.setParameter("endDate", endDate);
		}
		
		final List<?> reportObjs =reportQuery.getResultList();
		
		final List<ReportGetResDto> listReport = new ArrayList<>();
		
		if (reportObjs.size() > 0) {
			for (Object reportObj : reportObjs) {
				final Object[] reportArr = (Object[]) reportObj;

				final ReportGetResDto report = new ReportGetResDto();
				report.setCandidateName(reportArr[0].toString());
				report.setJobName(reportArr[1].toString());
				report.setEmploymentTypeName(reportArr[2].toString());
				report.setDateDiff(Double.valueOf(reportArr[3].toString()));
				listReport.add(report);
			}
		}
		
		return listReport;
	}
	
	
	
}
