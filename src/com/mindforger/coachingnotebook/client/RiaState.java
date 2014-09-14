package com.mindforger.coachingnotebook.client;

import java.util.HashMap;
import java.util.Map;

import com.mindforger.coachingnotebook.shared.MindForgerSettings;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;
import com.mindforger.coachingnotebook.shared.beans.RiaBootImageBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;
import com.mindforger.coachingnotebook.shared.beans.UserSettingsBean;

public class RiaState {

	private UserBean currentUser;
	private UserLimitsBean accountLimits;
	private UserSettingsBean userSettings;
	private GrowBean[] growBeans;
	private QuestionSetsBean questionSetsBean;
	
	private Map<String,GrowBean> growBeanByKey;
	
	public RiaState() {
		growBeanByKey=new HashMap<String, GrowBean>();
	}
	
	public void init(RiaBootImageBean bean) {
		currentUser=bean.getUser();
		accountLimits=bean.getAccountLimits();
		userSettings=bean.getUserSettings();
		questionSetsBean=bean.getQuestionSetsBean();
		
		setGrowBeans(bean.getGrowBeans());
	}
	
	public boolean inPerspective(String[] perspectiveIds) {
		if(MindForgerSettings.PERSPECTIVES_ENABLED) {
			if(perspectiveIds!=null && perspectiveIds.length>0) {
				for (String perspectiveId: perspectiveIds) {
					if(userSettings.getPerspective().equals(perspectiveId)) {
						return true;
					}
				}
			}
			return false;			
		} else {
			return true;
		}
	}

	public GrowBean getGrowBean(String growId) {
		if(growId!=null) {
			return growBeanByKey.get(growId);
		}
		return null;
	}
	
	public UserBean getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserBean currentUser) {
		this.currentUser = currentUser;
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
		growBeanByKey.clear();
		if(growBeans!=null) {
			for (GrowBean growBean : growBeans) {
				growBeanByKey.put(growBean.getKey(), growBean);
			}
		}
	}

	public QuestionSetsBean getQuestionSetsBean() {
		return questionSetsBean;
	}	
}
