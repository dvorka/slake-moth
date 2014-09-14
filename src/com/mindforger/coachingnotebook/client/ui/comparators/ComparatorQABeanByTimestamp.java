package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;
import java.util.Date;

import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ComparatorQABeanByTimestamp implements Comparator<QuestionAnswerBean> {

	private int descending; 
	
	public ComparatorQABeanByTimestamp(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		Date d1=(o1==null||o1.equals("")?null:o1.getDeadline());
		Date d2=(o2==null||o2.equals("")?null:o2.getDeadline());
		
		if(d1==null) {
			if(d2==null) {
				return 0*descending;
			} else {
				return 1*descending;
			}
		} else {
			if(d2==null) {
				return -1*descending;
			} else {
				if(d1.after(d2)) {
					return 1*descending;
				} else {
					return -1*descending;
				}
			}
		}
	}	
}
