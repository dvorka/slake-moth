package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserSettingsBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.beans.UserSettingsBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeUserSettingsBean implements Serializable, GaeBackupTranscoder<BackupUserSettingsBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
		
	@Persistent
	private String ownerId;

	@Persistent
	private String perspective;
	
	@Persistent
	private boolean horizontalGrowTabsRendering;

	@Persistent
	private boolean sendFriendRequestEmails;
	@Persistent
	private boolean sendCommentOnYourGrowEmails;
	@Persistent
	private boolean sendActionItemsDeadlineEmails;
	@Persistent
	private boolean sendGrowSharedEmails;
	
	public GaeUserSettingsBean() {
		horizontalGrowTabsRendering=false;

		sendFriendRequestEmails=true;
		sendCommentOnYourGrowEmails=true;
		sendActionItemsDeadlineEmails=true;
		sendGrowSharedEmails=true;
		
		perspective=MindForgerConstants.PERSPECTIVE_LIFE_DESIGNER;
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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void fromPojo(UserSettingsBean t) {
		perspective=t.getPerspective();
		horizontalGrowTabsRendering=t.isHorizontalGrowTabsRendering();
		sendFriendRequestEmails=t.isSendFriendRequestEmails();
		sendCommentOnYourGrowEmails=t.isSendCommentOnYourGrowEmails();
		sendActionItemsDeadlineEmails=t.isSendActionItemsDeadlineEmails();
		sendGrowSharedEmails=t.isSendGrowSharedEmails();
	}
	
	public UserSettingsBean toPojo() {
		UserSettingsBean bean=new UserSettingsBean(
				perspective,
				horizontalGrowTabsRendering, 
				sendFriendRequestEmails, 
				sendCommentOnYourGrowEmails, 
				sendActionItemsDeadlineEmails, 
				sendGrowSharedEmails);
		return bean;
	}
	
	public BackupUserSettingsBean toBackup() {
		BackupUserSettingsBean bean=new BackupUserSettingsBean(
				Utils.keyToString(key),
				ownerId,
				perspective,
				horizontalGrowTabsRendering, 
				sendFriendRequestEmails, 
				sendCommentOnYourGrowEmails, 
				sendActionItemsDeadlineEmails, 
				sendGrowSharedEmails);
		return bean;
	}

	public void fromBackup(BackupUserSettingsBean t) {
		key=Utils.stringToKey(t.getKey());
		ownerId=t.getOwnerId();
		perspective=t.getPerspective();
		horizontalGrowTabsRendering=t.isHorizontalGrowTabsRendering();
		sendFriendRequestEmails=t.isSendFriendRequestEmails();
		sendCommentOnYourGrowEmails=t.isSendCommentOnYourGrowEmails();
		sendActionItemsDeadlineEmails=t.isSendActionItemsDeadlineEmails();
		sendGrowSharedEmails=t.isSendGrowSharedEmails();
	}
		
	private static final long serialVersionUID = 1L;
}

