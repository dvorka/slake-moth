package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.admin.MindForgerKernel;
import com.mindforger.coachingnotebook.server.store.GaeBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeMindForgerKernelBean implements Serializable, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private boolean whitelistProtectedLogin;
		
	public GaeMindForgerKernelBean() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean isWhitelistProtectedLogin() {
		return whitelistProtectedLogin;
	}

	public void setWhitelistProtectedLogin(boolean whitelistProtectedLogin) {
		this.whitelistProtectedLogin = whitelistProtectedLogin;
	}

	public void toPojo() {
		MindForgerKernel.whitelistProtectedLogin=whitelistProtectedLogin;
	}

	public void fromPojo() {
		whitelistProtectedLogin=MindForgerKernel.whitelistProtectedLogin;
	}

	private static final long serialVersionUID = 1L;
}
