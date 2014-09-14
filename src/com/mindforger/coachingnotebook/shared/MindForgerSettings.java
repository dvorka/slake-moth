package com.mindforger.coachingnotebook.shared;

public interface MindForgerSettings {
	
	Ego ego= Ego.COACHING_NOTEBOOK;

	// features
	boolean PERSPECTIVES_ENABLED=true;
	boolean SWOT_ENABLED=false;
	boolean BLUE_LIFE_ENABLED=false;

	boolean EXTENSIVE_HELP=false;
	
	// system 
	boolean OPENID_ENABLED = false;
	boolean FTS_ENABLED = true;
	boolean BILLING_ENABLED=false;
	boolean IMAGE_PROXY_ENABLED=false;

	// admin
	boolean IMPORT_FROM_ANYWHERE = false;		
}
