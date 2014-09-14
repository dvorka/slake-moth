package com.mindforger.coachingnotebook.client.ui.checklist;

public enum CheckListItemStatusEnum {
	UNKNOWN("?","mf-checklistUnknown"),
	OK("V","mf-checklistOk"),
	NOK("X","mf-checklistNotOk");
	
	String label;
	String cssClass;
	
	CheckListItemStatusEnum(String label, String cssClass) {
		this.label=label;
		this.cssClass=cssClass;
	}

	public String label() {
		return label;
	}

	public String cssClass() {
		return cssClass;
	}
}

