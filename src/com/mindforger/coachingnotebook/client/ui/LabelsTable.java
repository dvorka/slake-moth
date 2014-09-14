package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.LabelBean;

public class LabelsTable extends FlexTable {

	private MindForgerServiceAsync service;
	private RiaMessages i18n;

	public LabelsTable(RiaContext ctx) {
		i18n = ctx.getI18n();
		
		addStyleName("mf-labelsTable");
		setCellSpacing(5);
		setCellPadding(3);

		setHTML(0, 0, "<b>"+i18n.label()+"</b>");
		setHTML(0, 1, "<b>"+i18n.actions()+"</b>");
	}

	public LabelsTable(LabelBean[] result, RiaContext ctx) {
		this(ctx);
		
		this.service=ctx.getService();
		
		if(result!=null) {
			for (int i = 0; i < result.length; i++) {
				addRow(result[i].getId(), result[i].getName());
			}			
		}
	}

	public void addRow(String id, String labelName) {
		int numRows = getRowCount();

		setWidget(numRows, 0, new LabelsTableToLabelButton(labelName, id, service));
		setWidget(numRows, 1, new HTML(i18n.deleteLowerCase()+"/5"));
	}

	public void removeRow() {
		int numRows = getRowCount();
		if (numRows > 1) {
			removeRow(numRows - 1);
			getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
		}
	}	
	
}
