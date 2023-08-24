package com.lawencon.jobportal.candidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Question;
import com.lawencon.jobportal.candidate.model.SkillTestQuestion;

@Repository
public class SkillTestQuestionDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<SkillTestQuestion> getBySkillTest(String skillTestId) {
		final List<SkillTestQuestion> skillTestQuestions = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "tstq.id as stq_id, tq.id, tq.question, tq.question_code, tstq.ver "
				+ "FROM "
				+ "t_skill_test_question tstq "
				+ "INNER JOIN "
				+ "t_question tq ON tstq.question_id = tq.id "
				+ "WHERE "
				+ "tstq.skill_test_id = :skillTestId ";
		
		final List<?> skillTestQuestionObjs = this.em().createNativeQuery(sql)
				.setParameter("skillTestId", skillTestId)
				.getResultList();

		if (skillTestQuestionObjs.size() > 0) {
			for (Object skillTestQuestionObj : skillTestQuestionObjs) {
				final Object[] skillTestQuestionArr = (Object[]) skillTestQuestionObj;

				final SkillTestQuestion skillTestQuestion = new SkillTestQuestion();
				skillTestQuestion.setId(skillTestQuestionArr[0].toString());
				
				final Question question = new Question();
				question.setId(skillTestQuestionArr[1].toString());
				question.setQuestion(skillTestQuestionArr[2].toString());
				question.setQuestionCode(skillTestQuestionArr[3].toString());
				skillTestQuestion.setQuestion(question);
				
				skillTestQuestion.setVersion(Integer.valueOf(skillTestQuestionArr[4].toString()));
				skillTestQuestions.add(skillTestQuestion);
			}
		}

		return skillTestQuestions;
	}
	
	public SkillTestQuestion getBySkillTestAndQuestion(String skillTestId, String questionId){
		final String sql = "SELECT "
				+ "tstq.id as stq_id, tq.id, tq.question, tq.question_code, tstq.ver "
				+ "FROM "
				+ "t_skill_test_question tstq "
				+ "INNER JOIN "
				+ "t_question tq ON tstq.question_id = tq.id "
				+ "WHERE "
				+ "tstq.skill_test_id = :skillTestId AND tstq.question_id = :questionId";
		
		try {
			final Object skillTestQuestionObjs = em().createNativeQuery(sql)
					.setParameter("skillTestId", skillTestId)
					.setParameter("questionId", questionId)
					.getSingleResult();
			
			final Object[] skillTestQuestionArr = (Object[]) skillTestQuestionObjs;
			
			SkillTestQuestion skillTestQuestion = null;

			if(skillTestQuestionArr.length > 0) {
				skillTestQuestion = new SkillTestQuestion();
				skillTestQuestion.setId(skillTestQuestionArr[0].toString());
				
				final Question question = new Question();
				question.setId(skillTestQuestionArr[1].toString());
				question.setQuestion(skillTestQuestionArr[2].toString());
				question.setQuestionCode(skillTestQuestionArr[3].toString());
				skillTestQuestion.setQuestion(question);
				
				skillTestQuestion.setVersion(Integer.valueOf(skillTestQuestionArr[4].toString()));
			}
			return skillTestQuestion;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
