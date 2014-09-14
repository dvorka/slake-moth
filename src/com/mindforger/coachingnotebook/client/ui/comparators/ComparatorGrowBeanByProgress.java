package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByProgress implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByProgress(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(GrowBean o1, GrowBean o2) {
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
