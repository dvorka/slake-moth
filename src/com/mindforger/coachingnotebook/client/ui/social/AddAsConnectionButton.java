package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class AddAsConnectionButton extends Button {
	
	public AddAsConnectionButton(final String friendId, final String role, final RiaContext ctx) {
		setText(role); // TODO translate based on the ID here
		setStyleName("mf-button");
		addStyleName("mf-addAsFriendButton");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getStatusLine().showProgress(ctx.getI18n().requestingFriendship());
				ctx.getService().requestFriendship(friendId, role, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						ctx.getRia().handleServiceError(caught);
					}
					public void onSuccess(Void result) {
						ctx.getStatusLine().hideStatus();
						ctx.getLeftMenubar().showConnections();
						ctx.getConnectionsPanel().hideConnectionActionPanel();
					}
				});
			}
		});
	}
}
