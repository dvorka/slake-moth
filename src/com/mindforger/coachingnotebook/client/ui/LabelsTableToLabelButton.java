package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class LabelsTableToLabelButton extends Button {
	
	public LabelsTableToLabelButton(String growLabel, String growId, final MindForgerServiceAsync mfService) {
		setText(growLabel);
		
		final String id=growId;
		
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
	      		RootPanel.get("goalsTableContainer").setVisible(false);	        	  
	    		RootPanel.get("growTabsContainer").setVisible(true);
	    		
	      		mfService.getGrow(id, new AsyncCallback<GrowBean>() {
	    			public void onFailure(Throwable caught) {
	    				GWT.log("Error: "+caught.getLocalizedMessage(),caught);
	    			}
	    			public void onSuccess(GrowBean bean) {
	    				GWT.log("RIA - grow loaded successfuly!");
	    				// bean 2 RIA
	    				// TODO growPanel.goalNameField.setText(bean.name);
	    				// TODO fill other fields and create a static method that will be able to handle this - RiaTranscoder.toRia(); .toBean();
	    			}
	    		});			    			    		
			}
		});
	}
}
