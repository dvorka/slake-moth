package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;



public class BackupFriendBean implements BackupBean {
	
	private String key;
	private String ownerId;
	private String friendId;
	private boolean confirmed;	
	private String ownerRole;
	private String friendRole;
	private String ownerKey;
	private String friendKey;	

	public BackupFriendBean() {
	}

	public String getKey() {
		return key;
	}

	public String getId() {
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

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getFriendRole() {
		return friendRole;
	}

	public void setFriendRole(String friendRole) {
		this.friendRole = friendRole;
	}

	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(String ownerKey) {
		this.ownerKey = ownerKey;
	}

	public String getFriendKey() {
		return friendKey;
	}

	public void setFriendKey(String friendKey) {
		this.friendKey = friendKey;
	}
}
