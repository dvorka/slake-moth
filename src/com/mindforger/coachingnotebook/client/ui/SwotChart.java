package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class SwotChart extends FlexTable {

	private static final String PANEL_WIDTH="300px";
	private static final String PANEL_HEIGHT="250px";
	
	VerticalPanel opportunities, threats, strengths, weaknesses;
	RiaContext ctx;
	private RiaMessages i18n;
	
	public SwotChart(RiaContext ctx) {
		this.ctx=ctx;
		i18n = ctx.getI18n();
		
		addStyleName("mf-iuChart");
		
		// row, column, widget
		setHTML(0, 0, "");
		setHTML(1, 0, "");
		setHTML(2, 0, "");
		setHTML(2, 1, "<div class='mf-swot4growInternal'>"+i18n.internalItems()+"</div>"); // TODO capabilities, resources, processes
		setHTML(2, 2, "<div class='mf-swot4growExternal'>"+i18n.externalInfluences()+"</div>");
		
		VerticalPanel opportunitiesPanel=new VerticalPanel();
		HTML html = new HTML(i18n.opportunities()+"<span class='mf-swot4growMitigation'>&nbsp;&gt;&nbsp;"+i18n.invest()+"</span>");
		html.setStyleName("mf-iuChartSometime");
		opportunitiesPanel.add(html);
		opportunitiesPanel.setStyleName("mf-chartNotImportantNotUrgent");
		opportunitiesPanel.setWidth(PANEL_WIDTH);
		opportunitiesPanel.setHeight(PANEL_HEIGHT);
		setWidget(0, 2, opportunitiesPanel);
		opportunitiesPanel = opportunities = encapsulateInnerPanel(opportunitiesPanel);		
		VerticalPanel threatsPanel=new VerticalPanel();
		html = new HTML(i18n.threats()+"<span class='mf-swot4growMitigation'>&nbsp;&gt;&nbsp;"+i18n.identify()+"</span>");
		html.setStyleName("mf-iuChartSoon");
		threatsPanel.add(html);
		threatsPanel.setStyleName("mf-chartImportantUrgent");
		threatsPanel.setWidth(PANEL_WIDTH);
		threatsPanel.setHeight(PANEL_HEIGHT);
		setWidget(1, 2, threatsPanel);		
		threatsPanel = threats = encapsulateInnerPanel(threatsPanel);		
		VerticalPanel strengthsPanel=new VerticalPanel();
		html = new HTML(i18n.strenghts()+"<span class='mf-swot4growMitigation'>&nbsp;&gt;&nbsp;"+i18n.capitalize()+"</span>");
		html.setStyleName("mf-iuChartSometime");
		strengthsPanel.add(html);		
		strengthsPanel.setStyleName("mf-chartNotImportantNotUrgent");
		strengthsPanel.setWidth(PANEL_WIDTH);
		strengthsPanel.setHeight(PANEL_HEIGHT);
		setWidget(0, 1, strengthsPanel);
		strengthsPanel = strengths = encapsulateInnerPanel(strengthsPanel);		
		VerticalPanel weaknessesPanel=new VerticalPanel();
		html = new HTML(i18n.weaknesses()+"<span class='mf-swot4growMitigation'>&nbsp;&gt;&nbsp;"+i18n.shoreUp()+"</span>");
		html.setStyleName("mf-iuChartFirst");
		weaknessesPanel.add(html);
		weaknessesPanel.setStyleName("mf-chartImportantNotUrgent");		
		weaknessesPanel.setWidth(PANEL_WIDTH);
		weaknessesPanel.setHeight(PANEL_HEIGHT);
		setWidget(1, 1, weaknessesPanel);	
		weaknessesPanel = weaknesses = encapsulateInnerPanel(weaknessesPanel);		
	}
	
	private void toRia(QuestionAnswerBean[] result) {
		Button html;
		String styleName="mf-iuChartGoalButton";		
		for (int i = 0; i < result.length; i++) {
			String trimmedName=RiaUtilities.trimName(result[i].getQuestion(), 20);
			final String label = result[i].getQuestionLabel();
			html = new Button(trimmedName);
			html.setStyleName(styleName);
			// TODO i18n vs. label
			if("Strength".equals(label)) {
				strengths.add(html);
				continue;
			}
			// TODO i18n vs. label
			if("Weakness".equals(label)) {
				weaknesses.add(html);
				continue;
			}
			// TODO i18n vs. label
			if("Opportunity".equals(label)) {
				opportunities.add(html);
				continue;
			}
			// TODO i18n vs. label
			if("Threat".equals(label)) {
				threats.add(html);
				continue;
			}
		}
	}

	private VerticalPanel encapsulateInnerPanel(VerticalPanel importantNotUrgent) {
		VerticalPanel inner=new VerticalPanel();
		inner.setWidth("100%");
		importantNotUrgent.add(inner);
		return inner;
	}
	
	public void refresh(QuestionAnswerBean[] result) {
		opportunities.clear();
		strengths.clear();
		threats.clear();
		weaknesses.clear();

		toRia(result);		
	}	
}
