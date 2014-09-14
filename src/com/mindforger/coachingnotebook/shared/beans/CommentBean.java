package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;
import java.util.Date;

public class CommentBean implements Serializable {
	
	private String commentId;
	private UserBean author;
	private String questionId;
	private String comment;	
	private Date created;
	
	public CommentBean() {
	}

	public CommentBean(String commentId, UserBean author, String questionId, String comment, Date created) {
		super();
		this.commentId = commentId;
		this.author = author;
		this.questionId = questionId;
		this.comment = comment;
		this.created = created;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public UserBean getAuthor() {
		return author;
	}

	public void setAuthor(UserBean author) {
		this.author = author;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	private static final long serialVersionUID = -4084590479722716282L;
}
