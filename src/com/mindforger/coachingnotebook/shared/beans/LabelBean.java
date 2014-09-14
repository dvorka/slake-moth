package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class LabelBean implements Serializable {

	private String id;
	private String name;
    
    public LabelBean() {
    }
    
	@Override
	public String toString() {
		return "LabelBean [id=" + id + ", name=" + name + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = -4464568444252380192L;
}
