package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class RiaBootImageBean implements Serializable {
	
	private UserBean user;
	private UserLimitsBean accountLimits;
	private UserSettingsBean userSettings;
	private GrowBean[] growBeans;
	private QuestionSetsBean questionSetsBean;
	
	public RiaBootImageBean() {
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public UserLimitsBean getAccountLimits() {
		return accountLimits;
	}

	public void setAccountLimits(UserLimitsBean accountLimits) {
		this.accountLimits = accountLimits;
	}

	public UserSettingsBean getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(UserSettingsBean userSettings) {
		this.userSettings = userSettings;
	}

	public GrowBean[] getGrowBeans() {
		return growBeans;
	}

	public void setGrowBeans(GrowBean[] growBeans) {
		this.growBeans = growBeans;
	}
	
	public QuestionSetsBean getQuestionSetsBean() {
		return questionSetsBean;
	}

	public void setQuestionSetsBean(QuestionSetsBean questionSetsBean) {
		this.questionSetsBean = questionSetsBean;
	}

	private static final long serialVersionUID = 5219928239332050881L;
}
