package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class AttachmentBean implements Serializable {

	public static final int GROW = 1;
	public static final int URL = 2;

	private String key;
	private String growId;
	private String noteId;
	private int type;
	private String urlLabel;
	private String url;
	private String targetGrowId;
	
	public AttachmentBean() {
		type=URL;
		urlLabel=url="";
	}

	public AttachmentBean(String urlLabel, String url) {
		this.type=URL;
		this.urlLabel=urlLabel;
		this.url=url;
	}

	public AttachmentBean(int type, String id) {
		if(type==GROW) {
			this.targetGrowId=id;
		} else {
			throw new RuntimeException("Unknow attachment type!");
		}
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTargetGrowId() {
		return targetGrowId;
	}

	public void setTargetGrowId(String targetGrowId) {
		this.targetGrowId = targetGrowId;
	}

	public String getGrowId() {
		return growId;
	}

	public void setGrowId(String growId) {
		this.growId = growId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
	private static final long serialVersionUID = -2874675536292863028L;
}
