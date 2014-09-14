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
import com.mindforger.coachingnotebook.server.store.beans.BackupFriendBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeFriendBean implements Serializable, GaeBackupTranscoder<BackupFriendBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String ownerId;
	@Persistent
	private String ownerKey;
	// role (friend/coach/client) that friend set
	@Persistent
	private String ownerRole;
	
	@Persistent
	private String friendId;
	@Persistent
	private String friendKey;	
	// role (friend/coach/client) that owner set
	@Persistent
	private String friendRole;
	
	@Persistent
	private boolean confirmed;

	public GaeFriendBean() {
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

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public boolean getConfirmed() {
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
	
	public BackupFriendBean toBackup() {
		BackupFriendBean backup=new BackupFriendBean();
		
		backup.setKey(Utils.keyToString(key));
		backup.setOwnerId(ownerId);
    	backup.setOwnerKey(ownerKey);
    	backup.setOwnerRole(ownerRole);

		backup.setFriendId(friendId);
    	backup.setFriendKey(friendKey);
    	backup.setFriendRole(friendRole);
    	
    	backup.setConfirmed(confirmed);

    	return backup;
	}

	public void fromBackup(BackupFriendBean backup) {
		key=Utils.stringToKey(backup.getId());
		
    	ownerId=backup.getOwnerId();
    	ownerKey=backup.getOwnerKey();
    	ownerRole=backup.getOwnerRole();
    	
    	friendId=backup.getFriendId();
    	friendKey=backup.getFriendKey();
    	friendRole=backup.getFriendRole();

    	confirmed=backup.isConfirmed();
	}
	
	private static final long serialVersionUID = -6682133217095230945L;
}
