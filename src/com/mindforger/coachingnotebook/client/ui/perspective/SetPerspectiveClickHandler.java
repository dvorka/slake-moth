package com.mindforger.coachingnotebook.client.ui.perspective;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mindforger.coachingnotebook.client.RiaContext;

public class SetPerspectiveClickHandler implements ClickHandler {
	
	private String value;
	private RiaContext ria;

	public SetPerspectiveClickHandler(String value, final RiaContext ria) {
		this.value=value;
		this.ria=ria;
	}

	public void onClick(ClickEvent event) {
		ria.getRia().handleSetPerspective(value);
	}
}
