package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;

public class BackupQuestionBean implements BackupBean {
	
	private String key;
	private String question;
	private String growType;
	private String ownerId;

	public BackupQuestionBean() {
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getGrowType() {
		return growType;
	}

	public void setGrowType(String growType) {
		this.growType = growType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
