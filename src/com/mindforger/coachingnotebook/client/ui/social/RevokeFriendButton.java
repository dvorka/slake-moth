package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.ui.LeftMenubar;
import com.mindforger.coachingnotebook.client.ui.StatusLine;

public class RevokeFriendButton extends Button {
	private RiaContext ctx;
	
	private String text; 
	private String title; 
	private String friendId;

	private StatusLine statusLine;
	private MindForgerServiceAsync service;
	private Ria ria;
	private LeftMenubar leftMenubar;
	
	public RevokeFriendButton(RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public void init() {
		this.statusLine=ctx.getStatusLine();
		this.service=ctx.getService();
		this.ria=ctx.getRia();
		this.leftMenubar=ctx.getLeftMenubar();
		
		super.setText(text);
		super.setTitle(title);
		super.setStyleName("mf-button");
		super.addStyleName("mf-addAsFriendButton");
		
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				statusLine.showProgress(ctx.getI18n().revokingFriendship());
				service.revokeFriendship(friendId, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						ria.handleServiceError(caught);
					}
					public void onSuccess(Void result) {
						statusLine.hideStatus();
						leftMenubar.showConnections();
					}
				});
			}
		});
	}
}
