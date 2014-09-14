package com.mindforger.coachingnotebook.client.ui;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListItemStatusEnum;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class ActionPlanForGrowPanel extends VerticalPanel {
	
	private HorizontalPanel viewPanel;
	
	private FlexTable actionItemsTable;
	private HTML actionPlanSectionLabel;
	private HTML checklistDocumentation, html;
		
    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private RiaMessages i18n;
	
	public ActionPlanForGrowPanel(final String mode, final RiaContext ctx) {
		i18n = ctx.getI18n();
		
		if(!QuestionAnswerBean.W_PART.equals(mode)) {
			this.setVisible(false);
			return;
		}

		setStyleName("mf-actionPlanForGrowPanel");
		
		/*
		 * view mode
		 */
		
		viewPanel = new HorizontalPanel();
		viewPanel.setStyleName("mf-actionPlanHead");
		
		actionPlanSectionLabel = new HTML();
		actionPlanSectionLabel.setHTML(i18n.action()+"<br/>"+i18n.plan());
		actionPlanSectionLabel.setTitle(i18n.actionPlanTowardsAchievingThisGoal());
	    actionPlanSectionLabel.setStyleName("mf-actionPlanLeftTitle");
		viewPanel.add(actionPlanSectionLabel);
		
		VerticalPanel rightPanel=new VerticalPanel();
		rightPanel.setStyleName("mf-actionPlanRightPanel");

		checklistDocumentation=new HTML(i18n.actionPlanChecklistDocumentation());
	    checklistDocumentation.setStyleName("mf-checklistDocumentation");
		rightPanel.add(checklistDocumentation);
								
		actionItemsTable = new FlexTable();
		actionItemsTable.setStyleName("mf-viewActionPlan");
						
		final DisclosurePanel disclosurePanel=new DisclosurePanel(i18n.actionPlanDetails());
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(actionItemsTable);
		rightPanel.add(disclosurePanel);

		actionPlanSectionLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				disclosurePanel.setOpen(!disclosurePanel.isOpen());
			}
		});
		
		viewPanel.add(rightPanel);
		add(viewPanel);				
	}	
		
	public void toRia(GrowBean grow) {
		if(isVisible()) {
			if(grow!=null) {
				refresh(grow.getW().getQuestions());
			}			
		}
	}

	public void refresh(List<QuestionAnswerBean> wQuestions) {
		actionItemsTable.removeAllRows();
		if(wQuestions!=null && wQuestions.size()>0) {
			for (int i = 0; i < wQuestions.size(); i++) {
				// TODO i18n vs. ID
				if("Action".equals(wQuestions.get(i).getQuestionLabel()) || "Task".equals(wQuestions.get(i).getQuestionLabel())) {
					int progress=0;
					if(wQuestions.get(i).getProgress()==0) {
						html = new HTML(CheckListItemStatusEnum.UNKNOWN.label());					
						html.setStyleName(CheckListItemStatusEnum.UNKNOWN.cssClass());
					} else {
						progress = wQuestions.get(i).getProgress();
						html = new HTML(progress+"%");
						if(progress==100) {
							html.setStyleName(CheckListItemStatusEnum.OK.cssClass());							
						} else {
							html.setStyleName(CheckListItemStatusEnum.NOK.cssClass());							
						}
					}
					actionItemsTable.setWidget(i, 0, html);
					html = new HTML(wQuestions.get(i).getQuestion());
					html.setStyleName("mf-checklistText");
					actionItemsTable.setWidget(i, 1, html);
					Date deadline=wQuestions.get(i).getDeadline();
					if(deadline!=null) {
						html = new HTML(dateFormat.format(wQuestions.get(i).getDeadline()));
						html.setStyleName("mf-actionPlanDeadline");							
						if(progress!=100) {
							html.addStyleName(wQuestions.get(i).getDeadlineCssClass());							
						}
						actionItemsTable.setWidget(i, 2, html);						
					}
				}
			}
		}
	}	
}
