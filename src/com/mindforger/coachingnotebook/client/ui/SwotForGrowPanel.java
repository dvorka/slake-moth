package com.mindforger.coachingnotebook.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class SwotForGrowPanel extends VerticalPanel {
	
	private HorizontalPanel viewPanel;
	
	private HTML swotSectionLabel;
	private HTML checklistDocumentation;
	
	private SwotChart swotChart;

	private RiaMessages i18n;
	
	public SwotForGrowPanel(final String mode, final RiaContext ctx) {
		i18n = ctx.getI18n();
		
		if(!QuestionAnswerBean.O_PART.equals(mode)) {
			this.setVisible(false);
			return;
		}

		setStyleName("mf-swotForGrowPanel");
		
		ctx.setSwotForGrowPanel(this);
		
		/*
		 * view mode
		 */
		
		viewPanel = new HorizontalPanel();
		viewPanel.setStyleName("mf-swotHead");
		
		swotSectionLabel = new HTML();
		swotSectionLabel.setText(i18n.swot());
		swotSectionLabel.setTitle(i18n.strenghtsToThreats());
	    swotSectionLabel.setStyleName("mf-swotLeftTitle");
		viewPanel.add(swotSectionLabel);
		
		VerticalPanel rightPanel=new VerticalPanel();
		rightPanel.setStyleName("mf-swotRightPanel");

		checklistDocumentation=new HTML(i18n.swotChecklistDocumentation());
	    checklistDocumentation.setStyleName("mf-checklistDocumentation");
		rightPanel.add(checklistDocumentation);

		swotChart = new SwotChart(ctx);
		
		final DisclosurePanel disclosurePanel=new DisclosurePanel(i18n.analysisDetails());
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(swotChart);		
		rightPanel.add(disclosurePanel);

	    swotSectionLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getGrowPanel().isRdWr()) {
					disclosurePanel.setOpen(!disclosurePanel.isOpen());					
				}
			}
		});
		
		viewPanel.add(rightPanel);
		add(viewPanel);				
	}	
		
	public void toRia(GrowBean grow) {
		List<QuestionAnswerBean> swotItems=new ArrayList<QuestionAnswerBean>();
		swotItems.addAll(grow.getR().getQuestions());
		swotItems.addAll(grow.getO().getQuestions());
		swotChart.refresh(swotItems.toArray(new QuestionAnswerBean[swotItems.size()]));			
	}	
}
