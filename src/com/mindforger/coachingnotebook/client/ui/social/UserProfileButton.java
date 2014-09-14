package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class UserProfileButton extends Button {
	
	public UserProfileButton(
			final String nickname,
			final String userId,
			final String cssClass,
			final RiaContext ctx) {
		
		setText(nickname);
		setStyleName(cssClass);
		
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getUserProfilePanel().refreshProfile(userId);
				ctx.getRia().showUserProfile();
			}
		});
	}
}
