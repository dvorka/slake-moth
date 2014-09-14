package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;

public class SearchResultsPanel extends FlexTable {

	private RiaContext ctx;
	private RiaMessages i18n;
	
	public SearchResultsPanel(RiaContext ctx) {
		this.ctx=ctx;
		this.i18n = ctx.getI18n();

		addStyleName("mf-growsTable");
	}

	public void refresh(DescriptorBean[] result) {
		if(result==null || result.length==0) {
			setVisible(false);
			return;
		} else {
			setVisible(true);
		}
				
		// TODO write the number of results & that there are no results
		
		removeAllRows();
		addRows(result);
	}
	
	private void addRows(DescriptorBean[] result) {
		addTableTitle();
		if(result!=null) {
			for (int i = 0; i < result.length; i++) {
				String id;
				switch (result[i].getType()) {
				case QUESTION:
					id=result[i].getParentId();
					break;
				default:
					id=result[i].getId();
					break;
				}
				
				addRow(id, result[i].getName(), result[i].getDescription(), result[i].getType());
			}			
		}
	}

	private void addTableTitle() {
		setWidget(0, 0, new HTML(""));
		setWidget(0, 1, new HTML(i18n.type()));
	}
		
	public void addRow(String id, String goalName, String description, MindForgerResourceType type) {
		int numRows = getRowCount();

		Button button;
		
		switch (type) {
		case QUESTION:
			button = new GrowsTableToGrowButton(goalName, description, id, "mf-growsTableGoalButton", 3, ctx);
			break;
		default:
			// GROW
			button = new GrowsTableToGrowButton(goalName, description, id, "mf-growsTableGoalButton", 3, ctx);
			break;
		}		

		setWidget(numRows, 0, button);
		setWidget(numRows, 1, new HTML(""+type));
	}

	public void removeRow() {
		int numRows = getRowCount();
		if (numRows > 1) {
			removeRow(numRows - 1);
			getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
		}
	}
}