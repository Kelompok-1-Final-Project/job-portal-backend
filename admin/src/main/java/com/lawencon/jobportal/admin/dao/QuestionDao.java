package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Question;

@Repository
public class QuestionDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Question> getBySkillTest(String skillTestId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tq.id, tq.question ");
		sql.append("FROM t_question tq ");
		sql.append("INNER JOIN t_skill_test_question tstq ON tstq.question_id = tq.id ");
		sql.append("WHERE tstq.skill_test_id = :skillTestId ");
		
		final List<?> questionObj = this.em().createNativeQuery(sql.toString())
				.setParameter("skillTestId", skillTestId)
				.getResultList();
		
		final List<Question> listQuestion = new ArrayList<>();
		if(questionObj.size() > 0) {
			for(Object obj:questionObj) {
				final Object[] questionArr = (Object[]) obj;
				final Question question = new Question();
				question.setId(questionArr[0].toString());
				question.setQuestion(questionArr[1].toString());
				listQuestion.add(question);
			}
		}
		
		return listQuestion;
	}
	
	public Question getByCode(String questionCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT q FROM Question q WHERE q.questionCode = :questionCode ");
		
		final Question question = em().createQuery(sql.toString(), Question.class)
				.setParameter("questionCode", questionCode)
				.getSingleResult();
		
		return question;
	}
}
