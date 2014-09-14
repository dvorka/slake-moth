package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class UserSettingsBean implements Serializable {
		
	private String perspective;
	
	private boolean horizontalGrowTabsRendering;

	private boolean sendFriendRequestEmails;
	private boolean sendCommentOnYourGrowEmails;
	private boolean sendActionItemsDeadlineEmails;
	private boolean sendGrowSharedEmails;
	
	public UserSettingsBean() {
	}

	public UserSettingsBean(String perspective,
			boolean horizontalGrowTabsRendering,
			boolean sendFriendRequestEmails,
			boolean sendCommentOnYourGrowEmails,
			boolean sendActionItemsDeadlineEmails, boolean sendGrowSharedEmails) {
		super();
		this.perspective = perspective;
		this.horizontalGrowTabsRendering = horizontalGrowTabsRendering;
		this.sendFriendRequestEmails = sendFriendRequestEmails;
		this.sendCommentOnYourGrowEmails = sendCommentOnYourGrowEmails;
		this.sendActionItemsDeadlineEmails = sendActionItemsDeadlineEmails;
		this.sendGrowSharedEmails = sendGrowSharedEmails;
	}

	public boolean isHorizontalGrowTabsRendering() {
		return horizontalGrowTabsRendering;
	}

	public void setHorizontalGrowTabsRendering(boolean horizontalGrowTabsRendering) {
		this.horizontalGrowTabsRendering = horizontalGrowTabsRendering;
	}

	public boolean isSendFriendRequestEmails() {
		return sendFriendRequestEmails;
	}

	public void setSendFriendRequestEmails(boolean sendFriendRequestEmails) {
		this.sendFriendRequestEmails = sendFriendRequestEmails;
	}

	public boolean isSendCommentOnYourGrowEmails() {
		return sendCommentOnYourGrowEmails;
	}

	public void setSendCommentOnYourGrowEmails(boolean sendCommentOnYourGrowEmails) {
		this.sendCommentOnYourGrowEmails = sendCommentOnYourGrowEmails;
	}

	public boolean isSendActionItemsDeadlineEmails() {
		return sendActionItemsDeadlineEmails;
	}

	public void setSendActionItemsDeadlineEmails(
			boolean sendActionItemsDeadlineEmails) {
		this.sendActionItemsDeadlineEmails = sendActionItemsDeadlineEmails;
	}

	public boolean isSendGrowSharedEmails() {
		return sendGrowSharedEmails;
	}

	public void setSendGrowSharedEmails(boolean sendGrowSharedEmails) {
		this.sendGrowSharedEmails = sendGrowSharedEmails;
	}

	public String getPerspective() {
		return perspective;
	}

	public void setPerspective(String perspective) {
		this.perspective = perspective;
	}

	private static final long serialVersionUID = -9116380662640943067L;
}
