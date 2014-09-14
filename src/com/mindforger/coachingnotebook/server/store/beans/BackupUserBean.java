package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;

public class BackupUserBean implements BackupBean {
	
	private String key;
	private long registered;
	private long lastLogin;
	private long previousLogin;
	private int loginCounter;
	private String userId;
	private String nickname;
	private String web;
	private String description;
	private String email;
	private int limitGrows;
	private int limitQuestionsPerGrow;
	private String gravatarMd5;
	
	public BackupUserBean() {
	}

	public void setKey(String key) {
		this.key=key;
	}

	public void setRegistered(long registered) {
		this.registered=registered;
	}
	
	public long getPreviousLogin() {
		return previousLogin;
	}

	public void setPreviousLogin(long previousLogin) {
		this.previousLogin = previousLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin=lastLogin;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter=loginCounter;
	}

	public void setUserId(String userId) {
		this.userId=userId;
	}

	public void setNickname(String nickname) {
		this.nickname=nickname;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public String getKey() {
		return key;
	}

	public long getRegistered() {
		return registered;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public int getLoginCounter() {
		return loginCounter;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public int getLimitGrows() {
		return limitGrows;
	}

	public void setLimitGrows(int limitGrows) {
		this.limitGrows = limitGrows;
	}

	public int getLimitQuestionsPerGrow() {
		return limitQuestionsPerGrow;
	}

	public void setLimitQuestionsPerGrow(int limitQuestionsPerGrow) {
		this.limitQuestionsPerGrow = limitQuestionsPerGrow;
	}

	public String getGravatarMd5() {
		return gravatarMd5;
	}

	public void setGravatarMd5(String gravatarMd5) {
		this.gravatarMd5 = gravatarMd5;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
}

