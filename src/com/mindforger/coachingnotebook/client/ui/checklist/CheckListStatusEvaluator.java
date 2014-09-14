package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.Map;

import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;

public class CheckListStatusEvaluator {

	private Map<Integer, CheckListAnswerBean> answersMap;
	
	public CheckListStatusEvaluator(Map<Integer, CheckListAnswerBean> answersMap) {
		this.answersMap=answersMap;
	}
	
	/**
	 * Get checklist item value in percents safely.
	 */
	public float p(Integer answerId) {
		CheckListAnswerBean result = answersMap.get(answerId);
		if(result!=null) {
			return result.getPercent();
		} else {
			return 0f;
		}
	}
}