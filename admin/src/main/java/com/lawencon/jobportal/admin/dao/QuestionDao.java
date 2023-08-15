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
		final String sql = "SELECT  "
				+ "	tq.id, tq.question "
				+ "FROM  "
				+ "	t_question tq  "
				+ "INNER JOIN  "
				+ "	t_skill_test_question tstq ON tstq.question_id = tq.id "
				+ "WHERE  "
				+ "	tstq.skill_test_id = :skillTestId ";
		
		final List<?> questionObj = this.em().createNativeQuery(sql)
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
		final String sql = "SELECT "
				+ "q "
				+ "FROM "
				+ "Question q "
				+ "WHERE "
				+ "q.questionCode = :questionCode";
		
		final Question question = em().createQuery(sql, Question.class)
				.setParameter("questionCode", questionCode)
				.getSingleResult();
		
		return question;
	}
}
