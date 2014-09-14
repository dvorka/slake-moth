package com.mindforger.coachingnotebook.shared;

public enum Ego {
	COACHING_NOTEBOOK;
	
	public static boolean is(Ego ego) {
		return MindForgerSettings.ego.equals(ego);
	}
}
