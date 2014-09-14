package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class LifeVisionBean implements Serializable {

	private String lifeVision;
	
	public LifeVisionBean() {
	}

	public String getLifeVision() {
		return lifeVision;
	}

	public void setLifeVision(String lifeVision) {
		this.lifeVision = lifeVision;
	}

	private static final long serialVersionUID = -1751001034523066059L;
}
