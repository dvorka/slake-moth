package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class CheckListAnswerBean implements Serializable {

	public static final String RATHER_YES="ry";
	public static final String RATHER_NO="rn";
	public static final String YES="y";
	public static final String NO="n";

	public static final String FAKE_GROW_ID_WHEEL_OF_LIFE="WoL";
	
	private Integer id;
	private String answer;
	
	public CheckListAnswerBean() {
	}
	
	public CheckListAnswerBean(Integer id, String answer) {
		this.id = id;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}	
	
	public float getPercent() {
		if(answer!=null) {
			if(YES.equals(answer)) {
				return 1.0f;
			} else {
				if(RATHER_YES.equals(answer)) {
					return 1.0f/3.0f*2.0f;
				} else {
					if(RATHER_NO.equals(answer)) {
						return 1.0f/3.0f;
					}
				}				
			}
		}
		return 0.0f;
	}
	
	private static final long serialVersionUID = -3110488739160409430L;
}
