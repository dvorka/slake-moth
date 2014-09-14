package com.mindforger.coachingnotebook.client.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByGoalName;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByName;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByProgress;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByTimestamp;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ActionsTable extends FlexTable implements SortableTable {
	
	private RiaContext ctx;
	private TableSortCriteria sortCriteria;
	private boolean sortIsAscending;
    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
	
    private QuestionAnswerBean[] actionsCache;
	private RiaMessages i18n;
    
	public ActionsTable(RiaContext ctx) {
		this.ctx=ctx;
		i18n = ctx.getI18n();
		
		setStyleName("mf-actionsTable");
		sortCriteria=TableSortCriteria.BY_TIMESTAMP;
		sortIsAscending=true;
	}

	public void refresh(QuestionAnswerBean[] result) {
		actionsCache=result;
		
		if(result==null || result.length==0) {
			setStyleName("mf-helpEmptyActionsTable");
			removeAllRows();
			HTML html = new HTML("<img src='./images/help-empty-actions.png' class='mf-helpEmptyActionsImg'>");
			html.setStyleName("mf-helpEmptyActions");
			setWidget(0,0,html);
			ctx.getStatusLine().showHelp(i18n.youHaveNoActions());
			return;
		} else {
			setStyleName("mf-actionsTable");
			setVisible(true);
			if(result.length==1) {
				ctx.getStatusLine().showInfo(result.length+" "+i18n.actionLowerCase());				
			} else {
				if(result.length>1) {
					ctx.getStatusLine().showInfo(result.length+" "+i18n.actionsLowerCase());									
				}
			}
		}

		Comparator<QuestionAnswerBean> comparator;
		switch(sortCriteria) {
		case BY_PROGRESS:
			comparator=new ComparatorQABeanByProgress(sortIsAscending);
			break;
		case BY_NAME:
			comparator=new ComparatorQABeanByName(sortIsAscending);
			break;
		case BY_GOAL:
			comparator=new ComparatorQABeanByGoalName(sortIsAscending, ctx);
			break;
		case BY_DEADLINE:
		default:
			comparator=new ComparatorQABeanByTimestamp(sortIsAscending);
			break;
		}
		
		Arrays.sort(result, comparator);
		
		removeAllRows();
		addRows(result);
	}
	
	private void addRows(QuestionAnswerBean[] result) {
		addTableTitle();
		if(result!=null) {
			for (int i = 0; i < result.length; i++) {
				Date deadline = result[i].getDeadline();
				
				String deadlineString="";
				if(deadline!=null) {
					deadlineString=dateFormat.format(deadline);
				}
				addRow(result[i].getGrowKey(), result[i].getQuestion(), result[i].getProgress(), deadlineString, result[i].getDeadlineCssClass());
			}			
		}
	}

	private void addTableTitle() {
		setWidget(0, 0, new TableSetSortingButton(i18n.progress(),TableSortCriteria.BY_PROGRESS, this, ctx));
		setWidget(0, 1, new TableSetSortingButton(i18n.action(),TableSortCriteria.BY_NAME, this, ctx));
		setWidget(0, 2, new TableSetSortingButton(i18n.goal(),TableSortCriteria.BY_GOAL, this, ctx));
		setWidget(0, 3, new TableSetSortingButton(i18n.deadline(),TableSortCriteria.BY_DEADLINE, this, ctx));
	}
		
	public void addRow(String id, String actionName, int progress, String deadline, String deadlineCssClass) {
		int numRows = getRowCount();
		
		HTML html = new HTML(progress+"%&nbsp;&nbsp;");
		html.setStyleName("mf-progressHtml");
		setWidget(numRows, 0, html);
		
		setWidget(numRows, 1, new GrowsTableToGrowButton(actionName, i18n.openGoalAssociatedWithThisAction(), id, "mf-growsTableGoalButton", 3, ctx));
		
		setWidget(numRows, 2, new HTML(ctx.getRia().getGrowNameForId(id)));
		
		html = new HTML(""+deadline);
		html.setStyleName(deadlineCssClass);
		setWidget(numRows, 3, html);
	}

	public void removeRow() {
		int numRows = getRowCount();
		if (numRows > 1) {
			removeRow(numRows - 1);
			getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
		}
	}

	public TableSortCriteria getSortingCriteria() {
		return sortCriteria;
	}

	public void setSortingCriteria(TableSortCriteria criteria, boolean sortIsAscending) {
		this.sortCriteria=criteria;
		this.sortIsAscending=sortIsAscending;		
	}

	public boolean isSortAscending() {
		return sortIsAscending;
	}

	public void refreshWithNewSortingCriteria() {
		refresh(actionsCache);
	}	
}

class DeadlineComparator implements Comparator<QuestionAnswerBean>{
	public int compare(QuestionAnswerBean o1, QuestionAnswerBean o2) {
		Date d1=(o1==null||o1.equals("")?null:o1.getDeadline());
		Date d2=(o2==null||o2.equals("")?null:o2.getDeadline());
		
		if(d1==null) {
			if(d2==null) {
				return 0;
			} else {
				return 1;
			}
		} else {
			if(d2==null) {
				return -1;
			} else {
				if(d1.after(d2)) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}
}
