package com.mindforger.coachingnotebook.shared.skins;

public class CoachingNotebookSkin implements EgoSkin {

	String name;
	
	public CoachingNotebookSkin() {
		name="CoachingNotebook";
	}
	
	@Override
	public String name() {
		return name;
	}

}
