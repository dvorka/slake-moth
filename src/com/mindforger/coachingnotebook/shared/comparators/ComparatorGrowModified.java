package com.mindforger.coachingnotebook.shared.comparators;

import java.util.Comparator;

import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ComparatorGrowModified implements Comparator<GrowBean>{
	public int compare(GrowBean o1, GrowBean o2) {
		if(o1!=null && o2!=null && o1.getModified()!=null && o2.getModified()!=null) {
			if(o1.getModified().getTime()>o2.getModified().getTime()) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}
}

