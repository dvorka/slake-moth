package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;

public class BackupWhitelistEntryBean implements BackupBean {

	private String key;
	private String emailAsString;

	public BackupWhitelistEntryBean() {
	}
	
	public String getEmailAsString() {
		return emailAsString;
	}

	public void setEmailAsString(String emailAsString) {
		this.emailAsString = emailAsString;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
