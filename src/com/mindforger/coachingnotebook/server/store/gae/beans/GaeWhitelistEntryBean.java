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
import com.mindforger.coachingnotebook.server.store.beans.BackupWhitelistEntryBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeWhitelistEntryBean implements Serializable, GaeBackupTranscoder<BackupWhitelistEntryBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
		
	@Persistent
	private String emailAsString;

	public GaeWhitelistEntryBean() {
	}
	
	public String getEmailAsString() {
		return emailAsString;
	}

	public void setEmailAsString(String emailAsString) {
		this.emailAsString = emailAsString;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	public BackupWhitelistEntryBean toBackup() {
		BackupWhitelistEntryBean backup=new BackupWhitelistEntryBean();
		
		backup.setKey(Utils.keyToString(key));
		backup.setEmailAsString(emailAsString);
		
		return backup;
	}

	public void fromBackup(BackupWhitelistEntryBean backup) {
        key=Utils.stringToKey(backup.getKey());    		
    	emailAsString=backup.getEmailAsString();
	}
	
	private static final long serialVersionUID = 4848573610199482701L;
}
