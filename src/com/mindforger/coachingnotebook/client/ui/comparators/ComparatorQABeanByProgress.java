package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ComparatorQABeanByProgress implements Comparator<QuestionAnswerBean> {

	private int descending; 
	
	public ComparatorQABeanByProgress(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		if(o1!=null && o2!=null) {
			if(o1.getProgress()>o2.getProgress()) {
				return -1*descending;
			} else {
				return 1*descending;
			}
		}
		return 0;
	}
}
