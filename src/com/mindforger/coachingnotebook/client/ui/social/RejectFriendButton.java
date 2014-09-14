package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class RejectFriendButton extends Button {

	public RejectFriendButton(final RiaContext ctx, final String friendId) {
		setText(ctx.getI18n().reject());
		setStyleName("mf-button");
		addStyleName("mf-addAsFriendButton");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getStatusLine().showProgress(ctx.getI18n().rejectingFriendship());
				ctx.getService().rejectFriendship(friendId, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						ctx.getRia().handleServiceError(caught);
					}
					public void onSuccess(Void result) {
						ctx.getStatusLine().hideStatus();
						ctx.getLeftMenubar().showConnections();
					}
				});
			}
		});
	}
}
