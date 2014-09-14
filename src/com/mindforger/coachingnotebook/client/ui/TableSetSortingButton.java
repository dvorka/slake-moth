package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;

public class TableSetSortingButton extends Button {
	
	public TableSetSortingButton(String text, final TableSortCriteria criteria, final SortableTable table, RiaContext ctx) {
		setTitle(ctx.getI18n().clickToSort());
		setText(text);
		setStyleName("mf-growsTableHeadColumnButton");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(table.getSortingCriteria().equals(criteria)) {
					table.setSortingCriteria(criteria, !table.isSortAscending());
				} else {
					table.setSortingCriteria(criteria, true);					
				}
				table.refreshWithNewSortingCriteria();
			}
		});
	}
}
