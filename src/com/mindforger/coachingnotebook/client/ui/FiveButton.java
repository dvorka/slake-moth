package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class FiveButton extends Button {
	
	int value;
	
	public FiveButton(final RiaContext ria, String label, String title, final int value, final FiveButtonsPanel importancePanel) {
		super(label);
		setTitle(title);
		
		this.value=value;
		
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ria.getGrowPanel().isRdWr()) {
					importancePanel.setMeasuredValue(value);
					importancePanel.save();						
				}
			}
		});
	}
}
