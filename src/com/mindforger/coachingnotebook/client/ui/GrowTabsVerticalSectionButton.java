package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class GrowTabsVerticalSectionButton extends Button {

	public GrowTabsVerticalSectionButton(String text, final GrowTab section, RiaContext ctx) {
		setText(text);
		setTitle(ctx.getI18n().clickToFoldUnfoldSection());
		setStyleName("mf-growTabsVerticalSectionButton");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				section.setVisible(!section.isVisible());
			}
		});		
	}
}
