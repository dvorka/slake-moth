package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GrowPartBean implements Serializable {
	
	private String description;	
	private List<QuestionAnswerBean> questions;
	
	public GrowPartBean() {
		questions=new ArrayList<QuestionAnswerBean>();
	}
	 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionAnswerBean> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionAnswerBean> questions) {
		this.questions = questions;
	}

	private static final long serialVersionUID = -5249983533931013003L;
}
