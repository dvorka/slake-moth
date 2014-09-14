package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;

public class BackupUserSettingsBean implements BackupBean {

	private String key;	
	private String ownerId;
	private String perspective;
	private boolean horizontalGrowTabsRendering;
	private boolean sendFriendRequestEmails;
	private boolean sendCommentOnYourGrowEmails;
	private boolean sendActionItemsDeadlineEmails;
	private boolean sendGrowSharedEmails;
	
	public BackupUserSettingsBean() {
	}

	public BackupUserSettingsBean(
			String key, 
			String ownerId,
			String perspective,
			boolean horizontalGrowTabsRendering,
			boolean sendFriendRequestEmails,
			boolean sendCommentOnYourGrowEmails,
			boolean sendActionItemsDeadlineEmails, 
			boolean sendGrowSharedEmails) {
		super();
		this.key = key;
		this.ownerId = ownerId;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
