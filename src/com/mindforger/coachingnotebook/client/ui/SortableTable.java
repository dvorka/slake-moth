package com.mindforger.coachingnotebook.client.ui;

public interface SortableTable {

	TableSortCriteria getSortingCriteria();
	void setSortingCriteria(TableSortCriteria criteria, boolean sortIsAscending);
	boolean isSortAscending();
	void refreshWithNewSortingCriteria();
	
}
