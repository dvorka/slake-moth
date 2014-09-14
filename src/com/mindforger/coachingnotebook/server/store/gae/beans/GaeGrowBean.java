package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupGrowBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeGrowBean implements Serializable, GaeBackupTranscoder<BackupGrowBean>, GaeBean {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String ownerId;
	@Persistent
	private String ownerKey;
	
	@Persistent
	private String name;
	@Persistent
	private Text description;

	@Persistent
	private int importance;
	@Persistent
	private int urgency;
	@Persistent
	private Date modified;
		
	@Persistent
	private String magicTriangle;
	@Persistent
	private int progress;
	
	@Persistent
	private Text conclusion;
	
	@Persistent
	private String sharedTo;
	
	@NotPersistent
	private List<String> tags;

	@NotPersistent
	private Text gDescription;
	@NotPersistent
	private Text rDescription;
	@NotPersistent
	private Text oDescription;
	@NotPersistent
	private Text wDescription;
	@NotPersistent
	private Text iDescription;
		    
    public GaeGrowBean() {
    	tags=new ArrayList<String>();
    }

    public GaeGrowBean(String ownerKey) {
    	this();
    	this.ownerKey=ownerKey;
    }
    
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Text getDescription() {
		return description;
	}

	public void setDescription(Text description) {
		this.description = description;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Text getConclusion() {
		return conclusion;
	}

	public void setConclusion(Text conclusion) {
		this.conclusion = conclusion;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

    public Text getgDescription() {
		return gDescription;
	}

	public void setgDescription(Text gDescription) {
		this.gDescription = gDescription;
	}

	public Text getrDescription() {
		return rDescription;
	}

	public void setrDescription(Text rDescription) {
		this.rDescription = rDescription;
	}

	public Text getoDescription() {
		return oDescription;
	}

	public void setoDescription(Text oDescription) {
		this.oDescription = oDescription;
	}

	public Text getwDescription() {
		return wDescription;
	}

	public void setwDescription(Text wDescription) {
		this.wDescription = wDescription;
	}

	public Text getiDescription() {
		return iDescription;
	}

	public void setiDescription(Text iDescription) {
		this.iDescription = iDescription;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}	
	
	public String getMagicTriangle() {
		return magicTriangle;
	}

	public void setMagicTriangle(String magicTriangle) {
		this.magicTriangle = magicTriangle;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}
	
	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(String ownerKey) {
		this.ownerKey = ownerKey;
	}

	public void fromPojo(GrowBean pojo, String ownerKey) {
        key=Utils.stringToKey(pojo.getKey());    		
    	this.ownerKey=ownerKey;
    	name=pojo.getName();
    	description=(pojo.getDescription()==null?null:new Text(pojo.getDescription()));
    	importance=pojo.getImportance();
    	urgency=pojo.getUrgency();
    	modified=pojo.getModified();
    	conclusion=(pojo.getConclusion()==null?null:new Text(pojo.getConclusion()));
    	progress=pojo.getProgress();
    	magicTriangle=pojo.getMagicTriangle();
    	sharedTo=(pojo.getSharedTo()==null?GrowBean.SHARING_OPTION_VALUE_NOBODY:pojo.getSharedTo());
        	
    	tags=Arrays.asList(pojo.getTags());
    }
    
	public GrowBean toPojo() {
		GrowBean pojo=new GrowBean();
		
		pojo.setKey(Utils.keyToString(key));
		pojo.setOwnerId(ownerId);
    	pojo.setName(name);
    	pojo.setDescription((description==null?null:description.getValue()));
    	pojo.setImportance(importance);
    	pojo.setUrgency(urgency);
    	pojo.setModified(modified);
    	pojo.setModifiedPretty(Utils.getPrettyTimestampHtml(modified.getTime()));
    	pojo.setConclusion((conclusion==null?null:conclusion.getValue()));
    	pojo.setProgress(progress);
    	pojo.setMagicTriangle(magicTriangle);
    	pojo.setSharedTo((sharedTo==null?GrowBean.SHARING_OPTION_VALUE_NOBODY:sharedTo));
    	
    	pojo.setTags(tags.toArray(new String[tags.size()]));

		return pojo;
	}

	public void fromBackup(BackupGrowBean backup) {
        key=Utils.stringToKey(backup.getKey());    		
    	ownerId=backup.getOwnerId();
        ownerKey=backup.getOwnerKey();    		
    	name=backup.getName();
    	description=(backup.getDescription()==null?null:new Text(backup.getDescription()));
    	importance=backup.getImportance();
    	urgency=backup.getUrgency();
    	modified=new Date(backup.getModified());
    	conclusion=(backup.getConclusion()==null?null:new Text(backup.getConclusion()));
    	progress=backup.getProgress();
    	magicTriangle=backup.getMagicTriangle();
    	sharedTo=backup.getSharedTo();
        
    	if(backup!=null && backup.getTags()!=null) {
        	tags=Arrays.asList(backup.getTags());    		
    	}
    }
    
	public BackupGrowBean toBackup() {
		BackupGrowBean backup=new BackupGrowBean();
		
		backup.setKey(Utils.keyToString(key));
		backup.setOwnerId(ownerId);
		backup.setOwnerKey(ownerKey);
    	backup.setName(name);
    	backup.setDescription((description==null?null:description.getValue()));
    	backup.setImportance(importance);
    	backup.setUrgency(urgency);
    	backup.setModified((modified==null?0:modified.getTime()));
    	backup.setConclusion((conclusion==null?null:conclusion.getValue()));
    	backup.setProgress(progress);
    	backup.setMagicTriangle(magicTriangle);
    	backup.setSharedTo(sharedTo);
    	
    	backup.setTags(tags.toArray(new String[tags.size()]));

		return backup;
	}
	
	private static final long serialVersionUID = -5573402746042600182L;
}
