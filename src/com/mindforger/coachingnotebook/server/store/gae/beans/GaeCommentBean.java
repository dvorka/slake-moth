package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCommentBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeCommentBean implements Serializable, GaeBackupTranscoder<BackupCommentBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String authorUserKey;
	@Persistent
	private String questionKey;
	@Persistent
	private String growKey;
	@Persistent
	private Date created;
	@Persistent
	private String comment;
	
	public GaeCommentBean() {
	}
	
	public GaeCommentBean(Key key, String authorUserKey, String questionKey, String growKey, Date created, String comment) {
		super();
		this.key = key;
		this.authorUserKey= authorUserKey;
		this.questionKey = questionKey;
		this.growKey = growKey;
		this.created = created;
		this.comment = comment;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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

	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growKey) {
		this.growKey = growKey;
	}

	public BackupCommentBean toBackup() {
		return new BackupCommentBean(
				Utils.keyToString(key),
				authorUserKey,
				questionKey,
				growKey,
				created,
				comment);
	}

	public void fromBackup(BackupCommentBean t) {
        key=Utils.stringToKey(t.getKey());    		
        authorUserKey=t.getAuthorUserKey();
        questionKey=t.getQuestionKey();
        growKey=t.getGrowKey();
        created=t.getCreated();
        comment=t.getComment();
	}
	
	private static final long serialVersionUID = 1L;
}
