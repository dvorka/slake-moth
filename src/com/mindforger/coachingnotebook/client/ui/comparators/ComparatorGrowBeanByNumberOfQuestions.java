package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByNumberOfQuestions implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByNumberOfQuestions(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}
	
	public int compare(GrowBean o1, GrowBean o2) {
		if(o1!=null && o2!=null) {
			int o1q = countQuestions(o1);
			int o2q = countQuestions(o2);
			if(o1q>o2q) {
				return -1*descending;
			} else {
				return 1*descending;
			}
		}
		return 0;
	}

	public static int countQuestions(GrowBean bean) {
		int result=
			bean.getG().getQuestions().size()+
			bean.getR().getQuestions().size()+
			bean.getO().getQuestions().size()+
			bean.getW().getQuestions().size()+
			bean.getI().getQuestions().size();
		return result;
	}
}

