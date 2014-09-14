package com.mindforger.coachingnotebook.client.ui;

import java.util.Arrays;
import java.util.Comparator;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByGoalName;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQABeanByName;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class LessonsLearnedTable extends FlexTable implements SortableTable {
	
	private RiaContext ctx;
	private TableSortCriteria sortCriteria;
	private boolean sortIsAscending;
	
    private QuestionAnswerBean[] actionsCache;
	private RiaMessages i18n;
	
	public LessonsLearnedTable(RiaContext ctx) {
		setStyleName("mf-lessonsLearnedTable");
		this.ctx=ctx;
		this.i18n=ctx.getI18n();
		sortCriteria=TableSortCriteria.BY_NAME;
		sortIsAscending=true;
	}

	public void refresh(QuestionAnswerBean[] result) {
		actionsCache=result;

		if(result==null || result.length==0) {
			setStyleName("mf-helpEmptyLessonsTable");
			removeAllRows();
			HTML html = new HTML("<img src='./images/help-empty-lessons.png' class='mf-helpEmptyLessonsImg'>");
			html.setStyleName("mf-helpEmptyLessons");
			setWidget(0,0,html);
			ctx.getStatusLine().showHelp(i18n.lessonsYouHaveLearned1()+" <span style='color: #000;'>!</span> "+i18n.lessonsYouHaveLearned2()+" ");
			return;
		} else {
			setStyleName("mf-lessonsLearnedTable");
			setVisible(true);
			if(result.length==1) {
				ctx.getStatusLine().showInfo(result.length+" "+i18n.lessonLearnedLowerCase());				
			} else {
				if(result.length>1) {
					ctx.getStatusLine().showInfo(result.length+" "+i18n.lessonLearnedLowerCase());									
				}
			}
		}
		
		Comparator<QuestionAnswerBean> comparator;
		switch(sortCriteria) {
		case BY_GOAL:
			comparator=new ComparatorQABeanByGoalName(sortIsAscending, ctx);
			break;
		case BY_NAME:
		default:
			comparator=new ComparatorQABeanByName(sortIsAscending);
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
				addRow(result[i].getGrowKey(), result[i].getQuestion());
			}			
		}
	}

	private void addTableTitle() {
		setWidget(0, 0, new TableSetSortingButton(i18n.lessonLearned(),TableSortCriteria.BY_NAME, this, ctx));
		setWidget(0, 1, new TableSetSortingButton(i18n.goal(),TableSortCriteria.BY_GOAL, this, ctx));
	}
		
	public void addRow(String id, String actionName) {
		int numRows = getRowCount();
		setWidget(numRows, 0, new GrowsTableToGrowButton(actionName, i18n.openGoalAssociatedWithThisLesson(), id, "mf-growsTableGoalButton", 4, ctx));
		setWidget(numRows, 1, new HTML(ctx.getRia().getGrowNameForId(id)));
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
