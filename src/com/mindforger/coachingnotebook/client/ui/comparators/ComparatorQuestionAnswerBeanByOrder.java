package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ComparatorQuestionAnswerBeanByOrder implements Comparator<QuestionAnswerBean> {
	
	private int descending=-1;
	
	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		int result=0;
		if(o1!=null && o2!=null) {
			if(o1.getOrder()>o2.getOrder()) {
				result=-1*descending;
			} else {
				if(o1.getOrder()<o2.getOrder()) {
					result=1*descending;
				} else {
					result=0;
				}
			}
		}
		return result;
	}
}
