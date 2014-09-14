package com.mindforger.coachingnotebook.client.ui.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowBeanByImportance implements Comparator<GrowBean> {

	private int descending; 
	
	public ComparatorGrowBeanByImportance(boolean descending) {
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
			if(o1.getImportance()>o2.getImportance()) {
				result=-1*descending;
			} else {
				if(o1.getImportance()<o2.getImportance()) {
					result=1*descending;
				} else {
					result=0;
				}
			}
		}
		
		if(result==0 && recursive) {
			ComparatorGrowBeanByUrgency urgencyComparator
				=new ComparatorGrowBeanByUrgency(descending==1);
			return urgencyComparator.compare(o1, o2, false);
		}
		return result;
	}
}
