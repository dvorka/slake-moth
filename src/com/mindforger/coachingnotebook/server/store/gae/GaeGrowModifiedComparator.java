package com.mindforger.coachingnotebook.server.store.gae;

import java.util.Comparator;

import com.mindforger.coachingnotebook.server.store.gae.beans.GaeGrowBean;

public class GaeGrowModifiedComparator implements Comparator<GaeGrowBean>{
	public int compare(GaeGrowBean o1, GaeGrowBean o2) {
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
