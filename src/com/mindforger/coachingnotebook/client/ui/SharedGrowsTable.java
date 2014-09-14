package com.mindforger.coachingnotebook.client.ui;

import java.util.Arrays;
import java.util.Comparator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByImportance;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByName;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByOwner;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByProgress;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByTimestamp;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByUrgency;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;

public class SharedGrowsTable extends FlexTable implements SortableTable {

	private TableSortCriteria sortCriteria;
	private boolean sortIsAscending;
	
	private RiaContext ctx;
	private RiaMessages i18n;
	private StatusLine statusLine;
	
	public SharedGrowsTable(RiaContext ctx) {
		this.ctx=ctx;	
		i18n = ctx.getI18n();
		statusLine = ctx.getStatusLine();
	}
	
	public void init() {
		addStyleName("mf-sharedGrowsTable");
		sortCriteria=TableSortCriteria.BY_TIMESTAMP;
		sortIsAscending=true;
	}

	public void refreshWithNewSortingCriteria() {
		ctx.getService().getSharedGrows(new AsyncCallback<GrowBean[]>() {
			public void onFailure(Throwable caught) {
				ctx.getRia().handleServiceError(caught);
			}
			public void onSuccess(GrowBean[] result) {
				if(result==null || result.length==0) {
					statusLine.showProgress(i18n.thereAreNoGoalsOthersSharedWithYou());
				} else {
					refresh(result);					
				}
			}
		});
	}
	
	private void refresh(GrowBean[] result) {
		if(result==null || result.length==0) {
			setVisible(false);
			return;
		} else {
			setVisible(true);
		}
				
		Comparator<GrowBean> comparator;
		switch(sortCriteria) {
		case BY_IMPORTANCE:
			comparator=new ComparatorGrowBeanByImportance(sortIsAscending);
			break;
		case BY_URGENCY:
			comparator=new ComparatorGrowBeanByUrgency(sortIsAscending);
			break;
		case BY_PROGRESS:
			comparator=new ComparatorGrowBeanByProgress(sortIsAscending);
			break;
		case BY_NAME:
			comparator=new ComparatorGrowBeanByName(sortIsAscending);
			break;
		case BY_OWNER:
			comparator=new ComparatorGrowBeanByOwner(sortIsAscending);
			break;
		case BY_TIMESTAMP:
		default:
			comparator=new ComparatorGrowBeanByTimestamp(sortIsAscending);
			break;
		}
		
		Arrays.sort(result, comparator);
		
		removeAllRows();
		addRows(result);
	}
	
	private void addRows(GrowBean[] result) {
		addTableTitle();
		if(result!=null) {
			for (int i = 0; i < result.length; i++) {
				addRow(
						result[i].getKey(), 
						result[i].getName(), 
						result[i].getDescription(), 
						result[i].getImportance(), 
						result[i].getUrgency(), 
						result[i].getProgress(), 
						result[i].getModifiedPretty(),
						result[i].getOwner());
			}			
		}
	}

	private void addTableTitle() {
		setWidget(0, 0, new TableSetSortingButton(i18n.goal(),TableSortCriteria.BY_NAME, this, ctx));
		setWidget(0, 1, new TableSetSortingButton(i18n.owner(),TableSortCriteria.BY_OWNER, this, ctx));
		setWidget(0, 2, new TableSetSortingButton(i18n.importance(),TableSortCriteria.BY_IMPORTANCE, this, ctx));
		setWidget(0, 3, new TableSetSortingButton(i18n.urgency(),TableSortCriteria.BY_URGENCY, this, ctx));
		setWidget(0, 4, new TableSetSortingButton(i18n.progress(),TableSortCriteria.BY_PROGRESS, this, ctx));
		setWidget(0, 5, new TableSetSortingButton(i18n.modified(),TableSortCriteria.BY_TIMESTAMP, this, ctx));
	}
		
	public void addRow(String id, String goalName, String description, int importance, int urgency, int progress, String modified, final UserBean owner) {
		int numRows = getRowCount();

		HorizontalPanel urgencyPanel=new HorizontalPanel();
		urgencyPanel.setStyleName("mf-showUrgencyPanel");		
		for (int i = 0; i < 5; i++) {
			if(i <= (urgency-1)) {
				urgencyPanel.add(new HTML("<span class='mf-showUrgencyOn' title='"+i18n.urgency()+" "+urgency+"'>!</span>"));
			} else {
				urgencyPanel.add(new HTML("<span class='mf-showUrgencyOff' title='"+i18n.urgency()+" "+urgency+"'>!</span>"));
			}
		}		
		HorizontalPanel importancePanel=new HorizontalPanel();
		importancePanel.setStyleName("mf-showImportancePanel");		
		for (int i = 0; i < 5; i++) {
			if(i <= (importance-1)) {
				importancePanel.add(new HTML("<span class='mf-showImportanceOn' title='"+i18n.importance()+" "+importance+"'><img src='./images/star-black-white.png'/></span>")); // i18n
			} else {
				importancePanel.add(new HTML("<span class='mf-showImportanceOff' title='"+i18n.importance()+" "+importance+"'><img src='./images/star-gray-white.png'/></span>")); // i18n
			}
		}
		
		final HTML progressHtml = new HTML(progress+"%&nbsp;&nbsp;");
		progressHtml.setStyleName("mf-progressHtml");
		
		GrowsTableToGrowButton growButton = new GrowsTableToGrowButton(
				goalName, 
				description, 
				id, 
				"mf-growsTableGoalButton",
				GrowsTableToGrowButton.FIRST_TAB, 
				ctx);
		if(progress==100) {
			growButton.addStyleName("mf-growsTableGoalFinishedButton");
		}
		
		String htmlString="<img src='"+RiaUtilities.getGravatatarUrl(owner)+"?s=25&d=identicon' title='"+owner.getNickname()+"'>";
		HTML photoHtml = new HTML(htmlString);
		photoHtml.setStyleName("mf-sharedGrowsOwnerProfilePhoto");
		photoHtml.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getUserProfilePanel().refreshProfile(owner.getUserId());
				ctx.getRia().showUserProfile();
			}
		});			
		
		setWidget(numRows, 0, growButton);
		setWidget(numRows, 1, photoHtml);
		setWidget(numRows, 2, importancePanel);
		setWidget(numRows, 3, urgencyPanel);
		setWidget(numRows, 4, progressHtml);
		setWidget(numRows, 5, new HTML(""+modified));
	}

	public void removeRow() {
		int numRows = getRowCount();
		if (numRows > 1) {
			removeRow(numRows - 1);
			getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
		}
	}

	public void setSortingCriteria(TableSortCriteria criteria, boolean sortIsAscending) {
		this.sortCriteria=criteria;
		this.sortIsAscending=sortIsAscending;
	}

	public TableSortCriteria getSortingCriteria() {
		return sortCriteria;
	}

	public boolean isSortAscending() {
		return sortIsAscending;
	}
}