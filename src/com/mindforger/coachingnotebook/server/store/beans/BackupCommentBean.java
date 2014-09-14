package com.mindforger.coachingnotebook.server.store.beans;

import java.util.Date;

import com.mindforger.coachingnotebook.server.store.BackupBean;

public class BackupCommentBean implements BackupBean {

	private String key;
	private String authorUserKey;
	private String questionKey;
	private Date created;
	private String comment;
	private String growKey;

	public BackupCommentBean(
			String key, 
			String authorUserKey, 
			String questionKey,
			String growKey,
			Date created, 
			String comment) {
		super();
	
		this.key=key;
		this.authorUserKey=authorUserKey;
		this.questionKey=questionKey;
		this.growKey=growKey;
		this.created=created;
		this.comment=comment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAuthorUserKey() {
		return authorUserKey;
	}

	public void setAuthorUserKey(String authorUserKey) {
		this.authorUserKey = authorUserKey;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}
	
	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growKey) {
		this.growKey = growKey;
	}
}
