package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class FriendBean implements Serializable {
	
	public static final String ROLE_FRIEND="Friend";
	public static final String ROLE_COACH="Coach";
	public static final String ROLE_CLIENT="Client";
	
	// role of the owner set by friend
	private UserBean owner;
	// role of the friend set by owner
	private UserBean friend;
	
	private boolean confirmed;
	// flag requestedByOwner is set by the server when getting list of my friends: if true, then owner is the requestor, otherwise friend is the requestor
	private boolean requestedByOwner;
	
	public FriendBean() {
	}
	
	public UserBean getOwner() {
		return owner;
	}

	public void setOwner(UserBean owner) {
		this.owner = owner;
	}

	public UserBean getFriend() {
		return friend;
	}

	public void setFriend(UserBean friend) {
		this.friend = friend;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isRequestedByOwner() {
		return requestedByOwner;
	}

	public void setRequestedByOwner(boolean requestedByOwner) {
		this.requestedByOwner = requestedByOwner;
	}

	private static final long serialVersionUID = 983885073219227706L;
}
