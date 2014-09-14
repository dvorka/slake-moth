package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;


public class BackupLifeVisionBean implements BackupBean {

	private String key;
	private String ownerId;
	private String lifeVision;
	
	public BackupLifeVisionBean() {
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

	public String getLifeVision() {
		return lifeVision;
	}

	public void setLifeVision(String lifeVision) {
		this.lifeVision = lifeVision;
	}
}
