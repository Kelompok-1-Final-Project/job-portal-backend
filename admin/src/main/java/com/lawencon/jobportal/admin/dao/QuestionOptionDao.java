package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.QuestionOption;

@Repository
public class QuestionOptionDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<QuestionOption> getByQuestion(String questionId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, labels, is_answer ");
		sql.append("FROM t_question_option tqo  ");
		sql.append("WHERE question_id = :questionId ");
		
		final List<?> optionObj = this.em().createNativeQuery(sql.toString())
				.setParameter("questionId", questionId)
				.getResultList();
		
		final List<QuestionOption> listQuestionOption = new ArrayList<>();
		if(optionObj.size() > 0) {
			for(Object obj:optionObj) {
				final Object[] optionArr = (Object[]) obj;
				final QuestionOption questionOption = new QuestionOption();
				questionOption.setId(optionArr[0].toString());
				questionOption.setLabels(optionArr[1].toString());
				questionOption.setIsAnswer(Boolean.parseBoolean(optionArr[2].toString()));
				listQuestionOption.add(questionOption);
			}
		}
		return listQuestionOption;
	}
}
