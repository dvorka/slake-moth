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
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeQuestionBean implements Serializable, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String ownerId;
	
	@Persistent
	private String question;
	// G, R, O, W, I
	@Persistent
	private String growType;
	
	public GaeQuestionBean() {
	}
	
	public GaeQuestionBean(String question, String growType, String ownerId) {
		this.question=question;
		this.growType=growType;
		this.ownerId=ownerId;
	}

	public Key getKey() {
		return key;
	}

	public void setkey(Key key) {
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

	public void setOwnerId(String owner) {
		this.ownerId = owner;
	}
	
	public void fromBackup(BackupQuestionBean backup) {
		key=Utils.stringToKey(backup.getKey());
		ownerId=backup.getOwnerId();
		growType=backup.getGrowType();
		question=backup.getQuestion();
	}
	
	public BackupQuestionBean toBackup() {
		BackupQuestionBean backup=new BackupQuestionBean();
		backup.setKey(Utils.keyToString(key));			
		backup.setOwnerId(ownerId);
		backup.setGrowType(growType);
		backup.setQuestion(question);
		return backup;
	}
	
	private static final long serialVersionUID = -2328335422759222417L;
}
