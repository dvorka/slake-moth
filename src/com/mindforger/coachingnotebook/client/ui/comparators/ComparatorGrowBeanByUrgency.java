package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByUrgency implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByUrgency(boolean descending) {
		if(descending) {
			this.descending=1;						
		} else {
			this.descending=-1;			
		}
	}

	public int compare(GrowBean o1, GrowBean o2) {
		return compare(o1, o2, true);
	}

	public int compare(GrowBean o1, GrowBean o2, boolean recursive) {
		int result=0;
		if(o1!=null && o2!=null) {
			if(o1.getUrgency()>o2.getUrgency()) {
				result= -1*descending;
			} else {
				if(o1.getUrgency()<o2.getUrgency()) {
					result= 1*descending; 
				} else {
					result=0;
				}
			}
		}
		
		if(result==0 && recursive) {
			ComparatorGrowBeanByImportance importanceComparator
				=new ComparatorGrowBeanByImportance(descending==1);
			return importanceComparator.compare(o1, o2, false);
		}
		return result;
	}
}
