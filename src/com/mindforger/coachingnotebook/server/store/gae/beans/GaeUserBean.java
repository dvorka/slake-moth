package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeUserBean implements Serializable, GaeBackupTranscoder<BackupUserBean>, GaeBean {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
		
	@Persistent
	private String userId;
	@Persistent
	private String nickname;
	@Persistent
	private String web;
	@Persistent
	private String description;
	@Persistent
	private String gravatarMd5;
	
	@Persistent
	private Email email;
	
	@Persistent
	private Date registered;
	@Persistent
	private Date lastLogin;
	@Persistent
	private Date previousLogin;
	@Persistent
	private int loginCounter;
	
	// limits
	@Persistent
	private int limitGrows;
	@Persistent
	private int limitQuestionsPerGrow;
		
	public GaeUserBean() {
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNickname() {
		return (nickname==null?email.toString():nickname);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
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

	public Date getPreviousLogin() {
		return previousLogin;
	}

	public void setPreviousLogin(Date previousLogin) {
		this.previousLogin = previousLogin;
	}

	public UserBean toPojo() {
		UserBean userBean=new UserBean(
				userId,
				nickname,
				web,
				description,
				"",
				gravatarMd5);
		return userBean;
	}
	
	public BackupUserBean toBackup() {
		BackupUserBean backup= new BackupUserBean();
		backup.setKey(Utils.keyToString(key));
		backup.setRegistered((registered==null?0:registered.getTime()));
		backup.setLastLogin((lastLogin==null?0:lastLogin.getTime()));
		backup.setPreviousLogin((previousLogin==null?0:previousLogin.getTime()));
		backup.setLoginCounter(loginCounter);
		backup.setUserId(userId);
		backup.setNickname(nickname);
		backup.setDescription(description);
		backup.setWeb(web);
		backup.setEmail((email==null?null:email.getEmail()));
		backup.setLimitGrows(limitGrows);
		backup.setLimitQuestionsPerGrow(limitQuestionsPerGrow);
		String lowercaseEmail=(email==null?"":email.getEmail().toLowerCase().trim());
		backup.setGravatarMd5((gravatarMd5==null?Utils.md5Hex(lowercaseEmail):gravatarMd5));
		
		return backup;
	}
	
	public void fromBackup(BackupUserBean backup) {
		key=Utils.stringToKey(backup.getKey());
		registered=new Date(backup.getRegistered());
		lastLogin=new Date(backup.getLastLogin());
		previousLogin=new Date(backup.getPreviousLogin());
		loginCounter=backup.getLoginCounter();
		userId=backup.getUserId();
		nickname=backup.getNickname();
		description=backup.getDescription();
		web=backup.getWeb();
		email=(backup.getEmail()==null?null:new Email(backup.getEmail()));
		limitGrows=backup.getLimitGrows();
	    limitQuestionsPerGrow=backup.getLimitQuestionsPerGrow();
	    gravatarMd5=backup.getGravatarMd5();
	}
	
	public UserLimitsBean toUserLimitBean() {
		UserLimitsBean result=new UserLimitsBean();
		result.setLimitGrows(limitGrows);
		result.setLimitQuestionsPerGrow(limitQuestionsPerGrow);
		return result;
	}
	
	private static final long serialVersionUID = 4022196636571836706L;
}
