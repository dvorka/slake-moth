package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ComparatorQABeanByName implements Comparator<QuestionAnswerBean> {

	private int descending; 
	
	public ComparatorQABeanByName(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		if(o1!=null && o2!=null) {
			return o1.getQuestion().compareTo(o2.getQuestion())*descending;
		}
		return 0;
	}
}
