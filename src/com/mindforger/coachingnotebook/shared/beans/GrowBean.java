package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;
import java.util.Date;

public class GrowBean implements Serializable {

	public static final String STRATEGY_FAST_CHEAP="fast-cheap";
	public static final String STRATEGY_FAST_GOOD="fast-good";
	public static final String STRATEGY_GOOD_CHEAP="good-cheap";
	
	public static final String[] SHARING_OPTIONS_LABELS={
		"Nobody",
		"Everybody",
		"All Connections",
		"Selected Connections"
	};
	public static final String SHARING_OPTION_VALUE_SELECTED_CONNECTIONS="SelectedConnections";
	public static final String SHARING_OPTION_VALUE_NOBODY="Nobody";
	public static final String SHARING_OPTION_VALUE_EVERYBODY="Everybody";
	public static final String SHARING_OPTION_VALUE_ALL_CONNECTIONS="AllConnections";
	public static final String[] SHARING_OPTIONS_VALUES={
		SHARING_OPTION_VALUE_NOBODY,
		SHARING_OPTION_VALUE_EVERYBODY,
		SHARING_OPTION_VALUE_ALL_CONNECTIONS,
		SHARING_OPTION_VALUE_SELECTED_CONNECTIONS
	};
	
	private String key;
	
	private String ownerId;
	// owner is set only for beans owned by other users
	private UserBean owner;
	
	private String name;
	private String description;
		
	private GrowPartBean g;
	private GrowPartBean r;
	private GrowPartBean o;
	private GrowPartBean w;
	private GrowPartBean i;
	
	private int importance;
	private int urgency;
	private Date modified;
	private String modifiedPretty;
	private String magicTriangle;
	private int progress;
	private String sharedTo;
		
	private String conclusion;
	
	private String[] tags;
    
    public GrowBean() {
    	g=new GrowPartBean();
    	r=new GrowPartBean();
    	o=new GrowPartBean();
    	w=new GrowPartBean();
    	i=new GrowPartBean();
    	
    	name=description="";
    	
    	tags=new String[0];
    	modifiedPretty=(modified==null?"":modified.toString());
    	sharedTo=SHARING_OPTION_VALUE_NOBODY;
    }
    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GrowPartBean getG() {
		return g;
	}

	public void setG(GrowPartBean g) {
		this.g = g;
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

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public GrowPartBean getR() {
		return r;
	}

	public void setR(GrowPartBean r) {
		this.r = r;
	}

	public GrowPartBean getO() {
		return o;
	}

	public void setO(GrowPartBean o) {
		this.o = o;
	}

	public GrowPartBean getW() {
		return w;
	}

	public void setW(GrowPartBean w) {
		this.w = w;
	}

	public GrowPartBean getI() {
		return i;
	}

	public void setI(GrowPartBean i) {
		this.i = i;
	}

	public String getModifiedPretty() {
		return modifiedPretty;
	}

	public void setModifiedPretty(String modifiedPretty) {
		this.modifiedPretty = modifiedPretty;
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
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public UserBean getOwner() {
		return owner;
	}

	public void setOwner(UserBean owner) {
		this.owner = owner;
	}

	public int getNumberOfQuestions() {
		return
			g.getQuestions().size()+
			r.getQuestions().size()+
			o.getQuestions().size()+
			w.getQuestions().size()+
			i.getQuestions().size();
	}
	
	private static final long serialVersionUID = -6452383097209247054L;
}
