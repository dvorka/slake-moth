package com.mindforger.coachingnotebook.client.ui.recent;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.ui.GrowsTableToGrowButton;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;

public class RecentPanel extends FlexTable {
	
	private static final int MAX_LABEL_LENGTH = 13;
	private static final int MAX_RECENT_ENTRIES = 10;
	
	private Map<String, Widget> idToRow=new HashMap<String, Widget>();
	private RiaContext ctx;
	
	public RecentPanel(RiaContext ctx) {
		this.ctx=ctx;

		addStyleName("mf-growsTableDISABLED");
	}

	public void addRow(String idOfEntityToLoad, String noteOrQuestionId, String name, String parentLabel, MindForgerResourceType type) {
		Button button;
		
		String shortName=name;
		if(shortName!=null && shortName.length()>MAX_LABEL_LENGTH) {
			shortName=shortName.substring(0,MAX_LABEL_LENGTH)+"...";
		}
		
		switch (type) {
		case QUESTION:
			button = new GrowsTableToGrowButton(shortName, "Question: "+name, idOfEntityToLoad, "mf-menuButtonOff", 3, ctx);
			break;
		default:
			// GROW
			button = new GrowsTableToGrowButton(shortName, "Goal: "+name, idOfEntityToLoad, "mf-menuButtonOff", 3, ctx);
			break;
		}		

		String mapKey = idOfEntityToLoad+noteOrQuestionId;
		if(idToRow.containsKey(mapKey)) {
			remove(idToRow.get(mapKey));
		}
		
		insertRow(0);
		setWidget(0, 0, button);
		idToRow.put(mapKey, button);
		
		while(getRowCount()>MAX_RECENT_ENTRIES) {
			removeRow(MAX_RECENT_ENTRIES);
		}
	}	
}
