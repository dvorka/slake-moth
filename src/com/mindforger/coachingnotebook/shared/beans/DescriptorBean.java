package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;
import java.util.Date;

import com.mindforger.coachingnotebook.shared.MindForgerResourceType;

public class DescriptorBean implements Serializable {

	private MindForgerResourceType type;
	
	private String id;
	private String parentId;
	private String name;
	private String description;
	private Date modified;
	
	public DescriptorBean() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public MindForgerResourceType getType() {
		return type;
	}

	public void setType(MindForgerResourceType type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "DescriptorBean [type=" + type + ", id=" + id + ", parentId="
				+ parentId + ", name=" + name + ", description=" + description
				+ ", modified=" + modified + "]";
	}

	private static final long serialVersionUID = -4732095654251873489L;
}
