package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public interface GrowTabs {

	GrowTab getGTab();
	GrowTab getRTab();
	GrowTab getOTab();
	GrowTab getWTab();
	GrowTab getITab();

	void save(boolean reloadAfterSave);

	GrowBean fromRia(GrowBean result);	
	void toRia(GrowBean bean);
	
	Widget getTabsWidget();	
	void selectTab(int tabToSelect);
	int getSelectedTab();
}
