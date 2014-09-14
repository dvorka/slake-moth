package com.mindforger.coachingnotebook.shared.skins;

import com.mindforger.coachingnotebook.shared.Ego;

public class Skin {
	
	static {
		EgoSkin skin;
		
		if(Ego.is(Ego.COACHING_NOTEBOOK)) {
			skin=new CoachingNotebookSkin();
		} else {
			skin=new MindForgerSkin();				
		}
		
		name=skin.name();
	}
	
	public static String name;

}
