package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class UserProfileBean implements Serializable {
	
	private UserBean user;
	private UserBean[] friends;
	private DescriptorBean[] growDescriptors;
	
	public UserProfileBean() {
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public UserBean[] getFriends() {
		return friends;
	}

	public void setFriends(UserBean[] friends) {
		this.friends = friends;
	}

	public DescriptorBean[] getGrowDescriptors() {
		return growDescriptors;
	}

	public void setGrowDescriptors(DescriptorBean[] growDescriptors) {
		this.growDescriptors = growDescriptors;
	}
	
	private static final long serialVersionUID = 829754283903993044L;
}
