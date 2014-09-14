package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaMessages;

public class TagsPanel extends HorizontalPanel {

	public TagsPanel(final MindForgerServiceAsync mfService, final StatusLine errorLabel, final RiaMessages i18n) {
		
		final Button addTagButton = new Button("+");
		addTagButton.setStyleName("mf-button");
		final TextBox newTagName = new TextBox();
		newTagName.setStyleName("mf-newTagName");
		newTagName.setText(i18n.labelName());
		add(newTagName);
		add(addTagButton);
				
	}
}
