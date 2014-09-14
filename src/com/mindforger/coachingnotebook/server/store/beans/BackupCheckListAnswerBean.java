package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;



public class BackupCheckListAnswerBean implements BackupBean {

	private String key;
	private String ownerId;
	private String growKey;
	private Integer questionId;
	private String answer;
	private String mode;

	public BackupCheckListAnswerBean() {
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

	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growId) {
		this.growKey = growId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
}
