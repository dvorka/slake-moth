package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class GrowsTableToGrowButton extends Button {
	
	public static final int FIRST_TAB=0;
	
	public GrowsTableToGrowButton(
			final String growLabel, 
			final String description, 
			final String growId,
			final String cssClass,
			final int tabToSelect,
			final RiaContext ctx) {
		setText(growLabel);
		setTitle(description);
		setStyleName(cssClass);

		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
	    		ctx.getStatusLine().showProgress(ctx.getI18n().loadingGrow());
	      		ctx.getRia().loadGrow(growId, tabToSelect);			    			    		
			}
		});
	}
}
