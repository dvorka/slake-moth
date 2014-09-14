package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;

public class GrowTabsVertical extends VerticalPanel implements GrowTabs {

	private final GrowTab gTab;
	private final GrowTab rTab;
	private final GrowTab oTab;
	private final GrowTab wTab;
	private final GrowTab iTab;

	private final GrowTab[] tabs;
	
	private RiaContext ria;
	
	private GrowTabsVerticalSectionButton button;
	private HorizontalPanel horizontalPanel;
	private RiaMessages i18n;
	
	public GrowTabsVertical(RiaContext ctx) {
		setStyleName("mf-growTabsVertical");

		this.ria=ctx;
		this.i18n=ctx.getI18n();
		
		QuestionSetsBean questions=ctx.getState().getQuestionSetsBean();
		
		// Goal tab
		gTab = new GrowTab(QuestionAnswerBean.G_PART, questions, this, ctx);
		gTab.addStyleName("mf-goalTabVertical");
		gTab.setVisible(false);
		button = new GrowTabsVerticalSectionButton("G", gTab, ctx);
		horizontalPanel = getVerticalSectionHead(button, i18n.goal());
		add(horizontalPanel);
		add(gTab);

		// Reality tab
		rTab = new GrowTab(QuestionAnswerBean.R_PART, questions, this, ctx);
		rTab.addStyleName("mf-goalTabVertical");
		rTab.setVisible(false);
		button = new GrowTabsVerticalSectionButton("R", rTab, ctx);
		horizontalPanel = getVerticalSectionHead(button, i18n.reality());
		add(horizontalPanel);
		add(rTab);

		// Options tab
		oTab = new GrowTab(QuestionAnswerBean.O_PART, questions, this, ctx);
		oTab.addStyleName("mf-goalTabVertical");
		oTab.setVisible(false);
		button = new GrowTabsVerticalSectionButton("O", oTab, ctx);
		horizontalPanel = getVerticalSectionHead(button, i18n.options());
		add(horizontalPanel);
		add(oTab);

		// Way Forward tab
		wTab = new GrowTab(QuestionAnswerBean.W_PART, questions, this, ctx);
		wTab.addStyleName("mf-goalTabVertical");
		wTab.setVisible(false);
		button = new GrowTabsVerticalSectionButton("W", wTab, ctx);
		horizontalPanel = getVerticalSectionHead(button, i18n.will());
		add(horizontalPanel);
		add(wTab);

		// ! tab
		iTab = new GrowTab(QuestionAnswerBean.I_PART, questions, this, ctx);
		iTab.addStyleName("mf-goalTabVertical");
		iTab.setVisible(false);
		button = new GrowTabsVerticalSectionButton("!", iTab, ctx);
		horizontalPanel = getVerticalSectionHead(button, i18n.results());
		add(horizontalPanel);
		add(iTab);
		
		tabs=new GrowTab[] {
				gTab,
				rTab,
				oTab,
				wTab,
				iTab
		};
	}

	private HorizontalPanel getVerticalSectionHead(GrowTabsVerticalSectionButton button, String text) {
		HorizontalPanel horizontalPanel=new HorizontalPanel();
		horizontalPanel.setStyleName("mf-growsTabVerticalSectionHead");
		horizontalPanel.add(button);
		horizontalPanel.add(new HTML("<div class='mf-growsTabVerticalSectionHr'/>"));
		HTML html = new HTML(text);
		html.setStyleName("mf-growsTabVerticalSectionSubTitle");
		horizontalPanel.add(html);
		return horizontalPanel;
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
		for (int i = 0; i < tabs.length; i++) {
			tabs[i].toRia(bean);
		}		
	}

	public void save(boolean reloadAfterSave) {
		ria.getGrowPanel().save(reloadAfterSave);
	}

	public Widget getTabsWidget() {
		return this;
	}

	public void foldAll() {
		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setVisible(false);
		}		
	}
	
	public void selectTab(int tabToSelect) {
		if(tabToSelect>=0 && tabToSelect<tabs.length) {
			foldAll();
			tabs[tabToSelect].setVisible(true);			
		}
	}

	public int getSelectedTab() {
		for (int i = 0; i < tabs.length; i++) {
			if(tabs[i].isVisible()) {
				return i;
			}
		}
		return 0;
	}
}
