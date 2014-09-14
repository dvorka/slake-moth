package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class PermissionBean implements Serializable {

	public static final int PERMISSION_NO=0;
	public static final int PERMISSION_READ=100;
	public static final int PERMISSION_CAN_COMMENT=200;
	public static final int PERMISSION_WRITE=300;
	
	private String subjectkey;
	private String subjectOwnerId;
	private UserBean user;
	private int permission;
	
	public PermissionBean() {
	}

	public PermissionBean(String subjectKey, String subjectOwnerId, UserBean user, int permission) {
		this();
		this.subjectkey=subjectKey;
		this.subjectOwnerId=subjectOwnerId;
		this.user=user;
		this.permission=permission;
	}

	public String getSubjectKey() {
		return subjectkey;
	}

	public void setSubjectKey(String subjectKey) {
		this.subjectkey = subjectKey;
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}	

	public String getSubjectOwnerId() {
		return subjectOwnerId;
	}

	public void setSubjectOwnerId(String subjectOwnerId) {
		this.subjectOwnerId = subjectOwnerId;
	}

	private static final long serialVersionUID = -15054787949851262L;
}
