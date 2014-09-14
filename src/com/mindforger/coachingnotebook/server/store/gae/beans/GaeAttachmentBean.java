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
import com.mindforger.coachingnotebook.server.store.beans.BackupAttachmentBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.AttachmentBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeAttachmentBean implements Serializable, GaeBackupTranscoder<BackupAttachmentBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;

	@Persistent
	private String ownerId;

	@Persistent
	private String growKey;

	@Persistent
	private String noteKey;
	
	@Persistent
	private int type;
	@Persistent
	private String urlLabel;
	@Persistent
	private String url;
	@Persistent
	private String targetGrowKey;
	
	public GaeAttachmentBean() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrlLabel() {
		return urlLabel;
	}

	public void setUrlLabel(String urlLabel) {
		this.urlLabel = urlLabel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growKey) {
		this.growKey = growKey;
	}
	
	public String getTargetGrowKey() {
		return targetGrowKey;
	}

	public void setTargetGrowKey(String targetGrowKey) {
		this.targetGrowKey = targetGrowKey;
	}
	
	public String getNoteKey() {
		return noteKey;
	}

	public void setNoteKey(String noteKey) {
		this.noteKey = noteKey;
	}

	public void fromPojo(AttachmentBean pojo) {
		key=Utils.stringToKey(pojo.getKey());
		targetGrowKey=pojo.getTargetGrowId();
		noteKey=pojo.getNoteId();
		type=pojo.getType();
		url=pojo.getUrl();
		urlLabel=pojo.getUrlLabel();
		growKey=pojo.getGrowId();
	}
		
	public AttachmentBean toPojo() {
		AttachmentBean pojo=new AttachmentBean();
		pojo.setKey(Utils.keyToString(key));			

		pojo.setNoteId(noteKey);
		pojo.setType(type);
		pojo.setUrl(url);
		pojo.setUrlLabel(urlLabel);
		pojo.setTargetGrowId(targetGrowKey);
		return pojo;
	}
	
	public BackupAttachmentBean toBackup() {
		BackupAttachmentBean pojo=new BackupAttachmentBean();
		pojo.setKey(Utils.keyToString(key));
		pojo.setOwnerId(ownerId);
		pojo.setGrowKey(growKey);
		pojo.setNoteKey(noteKey);
		pojo.setType(type);
		pojo.setUrl(url);
		pojo.setUrlLabel(urlLabel);
		pojo.setTargetGrowKey(targetGrowKey);
		return pojo;
	}

	public void fromBackup(BackupAttachmentBean pojo) {
		key=Utils.stringToKey(pojo.getKey());
		ownerId=pojo.getOwnerId();
		growKey=pojo.getGrowKey();
		noteKey=pojo.getNoteKey();
		type=pojo.getType();
		url=pojo.getUrl();
		urlLabel=pojo.getUrlLabel();
		targetGrowKey=pojo.getTargetGrowKey();
	}
	
	private static final long serialVersionUID = 8527817281362910025L;
}
