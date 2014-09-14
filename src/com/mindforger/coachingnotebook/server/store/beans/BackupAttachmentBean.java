package com.mindforger.coachingnotebook.server.store.beans;

import com.mindforger.coachingnotebook.server.store.BackupBean;
import com.mindforger.coachingnotebook.shared.beans.AttachmentBean;

public class BackupAttachmentBean implements BackupBean {

	private String key;
	private String growKey;
	private String noteKey;
	private String ownerId;
	private int type;
	private String urlLabel;
	private String url;
	private String targetGrowKey;

	public BackupAttachmentBean() {
		type=AttachmentBean.URL;
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
}
