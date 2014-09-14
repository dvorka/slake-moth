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
import com.mindforger.coachingnotebook.server.store.beans.BackupPermissionBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.PermissionBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaePermissionBean implements Serializable, GaeBackupTranscoder<BackupPermissionBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
		
	@Persistent
	private String subjectKey;
	
	@Persistent
	private String subjectOwnerId;
	
	@Persistent
	private String userId;
	@Persistent
	private String userKey;

	@Persistent
	private int permission;

	
	public GaePermissionBean() {
	}
	
	public GaePermissionBean(String subjectKey, String ownerId, String userId, int permission) {
		this.subjectKey=subjectKey;
		this.subjectOwnerId=ownerId;
		this.userId=userId;
		this.permission=permission;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getSubjectKey() {
		return subjectKey;
	}

	public void setSubjectId(String subjectKey) {
		this.subjectKey = subjectKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	public String getSubjectOwnerId() {
		return subjectOwnerId;
	}

	public void setSubjectOwnerId(String subjectOwnerId) {
		this.subjectOwnerId = subjectOwnerId;
	}

	public void fromPojo(PermissionBean permissionBean, String userKey) {
    	subjectKey=permissionBean.getSubjectKey();
    	subjectOwnerId=permissionBean.getSubjectOwnerId();
    	userId=permissionBean.getUser().getUserId();
    	permission=permissionBean.getPermission();
    	this.userKey=userKey;
	}
	
	public BackupPermissionBean toBackup() {
		BackupPermissionBean backup=new BackupPermissionBean();
		
		backup.setKey(Utils.keyToString(key));
		backup.setSubjectKey(subjectKey);
		backup.setSubjectOwnerId(subjectOwnerId);
		backup.setUserId(userId);
		backup.setPermission(permission);
		backup.setUserKey(userKey);
		
		return backup;
	}

	public void fromBackup(BackupPermissionBean backup) {
		key=Utils.stringToKey(backup.getKey());    		
    	subjectKey=backup.getSubjectKey();
    	subjectOwnerId=backup.getSubjectOwnerId();
    	userId=backup.getUserId();
    	permission=backup.getPermission();
    	userKey=backup.getUserKey();
	}
	
	private static final long serialVersionUID = 4848573610199482701L;
}
