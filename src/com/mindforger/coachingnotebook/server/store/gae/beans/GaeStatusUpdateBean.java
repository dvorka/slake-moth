package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.store.GaeBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeStatusUpdateBean implements Serializable, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String authorKey;
	@Persistent
    private String statusUpdate;
	@Persistent
	private Date timestamp;
	
	public GaeStatusUpdateBean() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getAuthorUser() {
		return authorKey;
	}

	public void setAuthorUser(String authorKey) {
		this.authorKey = authorKey;
	}

	public String getStatusUpdate() {
		return statusUpdate;
	}

	public void setStatusUpdate(String statusUpdate) {
		this.statusUpdate = statusUpdate;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	private static final long serialVersionUID = -4853611860259442120L;
}
