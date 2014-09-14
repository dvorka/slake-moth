package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByName implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByName(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(GrowBean o1, GrowBean o2) {
		if(o1!=null && o2!=null) {
			return o1.getName().compareTo(o2.getName())*descending;
		}
		return 0;
	}
}
