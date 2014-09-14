package com.mindforger.coachingnotebook.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListConstants;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListPanel;
import com.mindforger.coachingnotebook.client.ui.checklist.WizardFinishListener;
import com.mindforger.coachingnotebook.client.ui.checklist.WizardPanel;

public class ImproveWoLButton extends Button implements WizardFinishListener {
	
	private WizardPanel wizardPanel;
	private RiaContext ctx;
	private RiaMessages i18n;

	public ImproveWoLButton(final String areaOfLife, final RiaContext ctx, final CheckListPanel checkListPanel) {
		this.ctx=ctx;
		i18n = ctx.getI18n();
		
		setText(i18n.improve());
		setStyleName("mf-button");
		addStyleName("mf-improveWoLButton");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String firstStepDocumentation=i18n.wolFirstStepDocumentation();
				String lastStepDocumentation=i18n.wolLastStepDocumentation();					
				Map<String, String> contextualCustomization=new HashMap<String, String>();
				contextualCustomization.put(CheckListConstants.CQ_IMPROVE_WHEEL_VAR_AREA, i18n.your()+" "+areaOfLife);
				String mode = WizardPanel.MODE_WHEEL_OF_LIFE+areaOfLife;
				wizardPanel = new WizardPanel(
						firstStepDocumentation,
						lastStepDocumentation,
						contextualCustomization,
						ctx, 
						mode,
						ImproveWoLButton.this);
				wizardPanel.setGrowId(mode);
				wizardPanel.addStyleName("mf-improveWoLButtonWizard");
				int wizardRow = ctx.getLifeDesignerPanel().getRowCount();
				ctx.getLifeDesignerPanel().setWidget(wizardRow, 0, wizardPanel);
				wizardPanel.renderFirstWizardStep();
				
				ctx.getLifeDesignerPanel().hideAllRows(new Integer[]{ctx.getLifeDesignerPanel().getJourneyDocRow(), wizardRow});
			}
		});
	}

	public void wizardFinishedWithOk() {
		finishWizard();
	}

	public void wizardFinishedWithCancel() {
		finishWizard();
	}

	private void finishWizard() {
		ctx.getLifeDesignerPanel().showAllRows();
		ctx.getLifeDesignerPanel().remove(wizardPanel);
	}
}
