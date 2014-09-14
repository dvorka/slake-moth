package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;


public class BackupGrowBean implements BackupBean {

	private String key;
	private String ownerId;
	private String ownerKey;
	private String name;
	private String description;
	private int importance;
	private int urgency;
	private long modified;
	private String conclusion;
	private String[] tags;
	private String gDescription;
	private String rDescription;
	private String oDescription;
	private String wDescription;
	private String iDescription;
	private int progress;
	private String magicTriangle;
	private String sharedTo;
	
	public BackupGrowBean() {
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

    public String getgDescription() {
		return gDescription;
	}

	public void setgDescription(String gDescription) {
		this.gDescription = gDescription;
	}

	public String getrDescription() {
		return rDescription;
	}

	public void setrDescription(String rDescription) {
		this.rDescription = rDescription;
	}

	public String getoDescription() {
		return oDescription;
	}

	public void setoDescription(String oDescription) {
		this.oDescription = oDescription;
	}

	public String getwDescription() {
		return wDescription;
	}

	public void setwDescription(String wDescription) {
		this.wDescription = wDescription;
	}

	public String getiDescription() {
		return iDescription;
	}

	public void setiDescription(String iDescription) {
		this.iDescription = iDescription;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getMagicTriangle() {
		return magicTriangle;
	}

	public void setMagicTriangle(String magicTriangle) {
		this.magicTriangle = magicTriangle;
	}

	public String getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}

	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(String ownerKey) {
		this.ownerKey = ownerKey;
	}
}
