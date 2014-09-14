package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;

public class GrowTabsHorizontal extends DecoratedTabPanel implements GrowTabs {

	private static String[] tabTitles = new String[]{"G","R","O","W","!"};
	
	final GrowTab gTab;
	final GrowTab rTab;
	final GrowTab oTab;
	final GrowTab wTab;
	final GrowTab iTab;

	RiaContext ria;
	
	public GrowTabsHorizontal(RiaContext ria) {
		setStyleName("mf-growTabs");
		
		this.ria=ria;
		
		QuestionSetsBean questions=ria.getState().getQuestionSetsBean();
		
		// Goal tab
		gTab = new GrowTab(QuestionAnswerBean.G_PART, questions, this, ria);
		add(gTab, tabTitles[0]);

		// Reality tab
		rTab = new GrowTab(QuestionAnswerBean.R_PART, questions, this, ria);
		add(rTab, tabTitles[1]);

		// Options tab
		oTab = new GrowTab(QuestionAnswerBean.O_PART, questions, this, ria);
		add(oTab, tabTitles[2]);

		// Way Forward tab
		wTab = new GrowTab(QuestionAnswerBean.W_PART, questions, this, ria);
		add(wTab, tabTitles[3]);

		// ! tab
		iTab = new GrowTab(QuestionAnswerBean.I_PART, questions, this, ria);
		add(iTab, tabTitles[4]);

		setAnimationEnabled(true);
		ensureDebugId("cwTabPanel");
		selectTab(0);
	}

	public GrowTab getGTab() {
		return gTab;
	}

	public GrowTab getRTab() {
		return rTab;
	}

	public GrowTab getOTab() {
		return oTab;
	}

	public GrowTab getWTab() {
		return wTab;
	}

	public GrowTab getITab() {
		return iTab;
	}
	
	public GrowBean fromRia(GrowBean result) {
		if(result==null) {
			result=new GrowBean();			
		} 
		result.setG(gTab.fromRia());
		result.setR(rTab.fromRia());
		result.setO(oTab.fromRia());
		result.setW(wTab.fromRia());
		result.setI(iTab.fromRia());
		return result;
	}

	public void toRia(GrowBean bean) {
		gTab.toRia(bean);
		rTab.toRia(bean);
		oTab.toRia(bean);
		wTab.toRia(bean);
		iTab.toRia(bean);
	}

	public void save(boolean reloadAfterSave) {
		ria.getGrowPanel().save(reloadAfterSave);
	}

	public Widget getTabsWidget() {
		return this;
	}

	public int getSelectedTab() {
		return getTabBar().getSelectedTab();
	}
	
}
