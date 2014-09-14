package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ComparatorQABeanByGoalName implements Comparator<QuestionAnswerBean> {

	private int descending;
	private RiaContext ria; 
	
	public ComparatorQABeanByGoalName(boolean descending, RiaContext ria) {
		this.ria=ria;
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		if(o1!=null && o2!=null) {
			String n1=ria.getRia().getGrowNameForId(o1.getGrowKey());
			String n2=ria.getRia().getGrowNameForId(o2.getGrowKey());
			if(n1!=null && n2!=null) {
				return n1.compareTo(n2)*descending;				
			}
		}
		return 0;
	}
}
