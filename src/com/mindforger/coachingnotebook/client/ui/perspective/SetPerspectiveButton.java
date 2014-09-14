package com.mindforger.coachingnotebook.client.ui.perspective;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class SetPerspectiveButton extends Button {

	public SetPerspectiveButton(final String label, final String value, final RiaContext ctx) {
		if(value.equals(ctx.getState().getUserSettings().getPerspective())) {
			setStyleName("mf-perspectiveSelectionButton mf-perspectiveSelectionButtonSelected");						
		} else {
			setStyleName("mf-perspectiveSelectionButton");			
		}
		setText(label);
		setTitle(ctx.getI18n().setPerspectiveTo()+" "+label);
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getRia().handleSetPerspective(value);
			}
		});		
	}
}
