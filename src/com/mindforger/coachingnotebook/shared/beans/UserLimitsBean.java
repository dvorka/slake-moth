package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class UserLimitsBean implements Serializable {

	public static final String LIMIT_EXCEEDED="LIMIT-EXCEEDED:";

	private int limitGrows;
	private int limitQuestionsPerGrow;
	
	public UserLimitsBean() {
	}

	public int getLimitGrows() {
		return limitGrows;
	}

	public void setLimitGrows(int limitGrows) {
		this.limitGrows = limitGrows;
	}

	public int getLimitQuestionsPerGrow() {
		return limitQuestionsPerGrow;
	}

	public void setLimitQuestionsPerGrow(int limitQuestionsPerGrow) {
		this.limitQuestionsPerGrow = limitQuestionsPerGrow;
	}	

	private static final long serialVersionUID = 4742236127937556219L;	
}
