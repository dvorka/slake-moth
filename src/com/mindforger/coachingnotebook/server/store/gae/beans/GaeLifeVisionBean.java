package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupLifeVisionBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.LifeVisionBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeLifeVisionBean implements Serializable, GaeBackupTranscoder<BackupLifeVisionBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String ownerId;
	@Persistent
	private Text lifeVision;
	
	public GaeLifeVisionBean() {
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

	public Text getLifeVision() {
		return lifeVision;
	}

	public void setLifeVision(Text lifeVision) {
		this.lifeVision = lifeVision;
	}

	public LifeVisionBean toPojo() {
		LifeVisionBean pojo=new LifeVisionBean();
    	pojo.setLifeVision((lifeVision==null?null:lifeVision.getValue()));
		return pojo;
	}

	public void fromPojo(LifeVisionBean pojo) {
    	lifeVision=(pojo.getLifeVision()==null?null:new Text(pojo.getLifeVision()));
	}

	public BackupLifeVisionBean toBackup() {
		BackupLifeVisionBean backup=new BackupLifeVisionBean();
		backup.setKey(Utils.keyToString(key));
		backup.setOwnerId(ownerId);
    	backup.setLifeVision((lifeVision==null?null:lifeVision.getValue()));

		return backup;
	}

	public void fromBackup(BackupLifeVisionBean backup) {
        key=Utils.stringToKey(backup.getKey());    		
    	ownerId=backup.getOwnerId();
    	lifeVision=(backup.getLifeVision()==null?null:new Text(backup.getLifeVision()));
	}
	
	private static final long serialVersionUID = 1L;
}
