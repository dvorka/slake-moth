package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByOwner implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByOwner(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(GrowBean o1, GrowBean o2) {
		if(o1!=null && o2!=null && o1.getOwner()!=null && o2.getOwner()!=null) {
			return o1.getOwner().getNickname().compareTo(o2.getOwner().getNickname())*descending;
		}
		return 0;
	}
}
