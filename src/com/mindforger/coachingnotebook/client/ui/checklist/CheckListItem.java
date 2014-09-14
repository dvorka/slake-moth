package com.mindforger.coachingnotebook.client.ui.checklist;

public class CheckListItem {

	private int id;
	private CheckListItemStatusEnum status;
	private String label;
	private int percent;
	
	public CheckListItem() {
	}
	
	public CheckListItem(int id, CheckListItemStatusEnum status, String label, int percent) {
		this.id=id;
		this.status=status;
		this.label=label;
		this.percent=percent;
	}

	public CheckListItem(int id, String label) {
		this(id,CheckListItemStatusEnum.UNKNOWN,label, 0);
	}

	public CheckListItemStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CheckListItemStatusEnum status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
		if(percent>90) {
			status=CheckListItemStatusEnum.OK;
		} else {
			status=CheckListItemStatusEnum.NOK;			
		}
	}
}
