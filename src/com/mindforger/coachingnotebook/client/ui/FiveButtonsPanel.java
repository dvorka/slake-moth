package com.mindforger.coachingnotebook.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;

public class FiveButtonsPanel extends HorizontalPanel {
	
	private int measuredValue;
	private ArrayList<FiveButton> buttons;
	private String offStyleName;
	private String onStyleName;
	private RiaContext ria;
	
	public FiveButtonsPanel(String buttonLabel, String buttonTitle, String offStyleName, String onStyleName, RiaContext ria) {
		measuredValue=0;
		this.offStyleName=offStyleName;
		this.onStyleName=onStyleName;
		buttons=new ArrayList<FiveButton>();
		this.ria=ria;
		
		for (int i = 0; i < 5; i++) {
			// http://www.fileformat.info/info/unicode/char/2605/index.htm
			FiveButton button = new FiveButton(ria, buttonLabel, buttonTitle+" "+(i+1), i+1,this);
			button.setStyleName(offStyleName);
			buttons.add(button);
			add(button);
		}		
	}

	public void setMeasuredValue(int measuredValue) {
		this.measuredValue=measuredValue;
		refreshImportanceStyles();
	}
	
	private void refreshImportanceStyles() {
		for (int i = 0; i < buttons.size(); i++) {
			if(i <= (measuredValue-1)) {
				buttons.get(i).setStyleName(onStyleName);
			} else {
				buttons.get(i).setStyleName(offStyleName);
			}
		}
	}

	public int getMeasuredValue() {
		return measuredValue;
	}
	
	public void save() {
		ria.getGrowPanel().save(true);
	}
}
