package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class WizardPanel extends FlexTable {

	public static final String MODE_G=QuestionAnswerBean.G_PART;
	public static final String MODE_R=QuestionAnswerBean.R_PART;
	public static final String MODE_O=QuestionAnswerBean.O_PART;
	public static final String MODE_W=QuestionAnswerBean.W_PART;
	public static final String MODE_I=QuestionAnswerBean.I_PART;
	public static final String MODE_WHEEL_OF_LIFE=CheckListAnswerBean.FAKE_GROW_ID_WHEEL_OF_LIFE;
	
	private String growId;
	private String mode;
	private RiaContext ria;
	
	private WizardState wizardState;
	private String wizardFirstStepDocumentation;
	private String wizardLastStepDocumentation;
	
	private WizardFinishListener wizardListener;
	
	private HTML html;
	private Button button;
	private RiaMessages i18n;

	public WizardPanel(
			String wizardFirstStepDocumentation, 
			String wizardLastStepDocumentation,
			Map<String, String> contextualCustomization,
			RiaContext ria,
			String mode,
			WizardFinishListener listener) {
		this.ria=ria;
		this.mode=mode;
		this.wizardFirstStepDocumentation=wizardFirstStepDocumentation;
		this.wizardLastStepDocumentation=wizardLastStepDocumentation;
		this.wizardListener=listener;
		
		i18n=ria.getI18n();
		
		setStyleName("mf-checkListWizardPanel");
		
		wizardState=new WizardState(mode, contextualCustomization, ria.getI18n());
	}

	public void resetWizardState() {
		wizardState.reset();
	}
	
	public void renderFirstWizardStep() {
		setVisible(true);
		removeAllRows();
		
		html = new HTML("<span class='mf-hint'>&gt;&gt;</span> " + wizardFirstStepDocumentation);
		html.setStyleName("mf-checkListWizardQuestion");
		setWidget(0, 0, html);
		HorizontalPanel wizardButtonsPanel=new HorizontalPanel();
		wizardButtonsPanel.setStyleName("mf-checklistWizardButtons");
		button = new Button(i18n.next(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				wizardState.reset();
				renderWizardStep();
			}
		});
		button.setStyleName("mf-button");
		wizardButtonsPanel.add(button);
		button = new Button(i18n.cancel(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				wizardListener.wizardFinishedWithCancel();
				setVisible(false);
			}
		});
		button.setStyleName("mf-buttonLooser");
		wizardButtonsPanel.add(button);
		setWidget(1, 0, wizardButtonsPanel);
	}

	private void renderWizardStep() {
		WizardQ q=wizardState.getQuestion();

		removeAllRows();
		
		html = new HTML("<span class='mf-hint' title='"+i18n.question()+"'>"+i18n.q()+":</span>  "+q.getQuestion());
		html.setStyleName("mf-checkListWizardQuestion");
		setWidget(0, 0, html);
		
		HorizontalPanel wizardButtonsPanel=new HorizontalPanel();
		wizardButtonsPanel.setStyleName("mf-checklistWizardButtons");
		if(wizardState.getQuestion().getQuestionType()==WizardQ.QUESTION_TYPE_STRING) {
			final TextBox wizardStepAnswerTextBox = new TextBox();
			wizardStepAnswerTextBox.setStyleName("mf-wizardStepAnswerTextBox");
			setWidget(1, 0, wizardStepAnswerTextBox);
			
			Button makeItGoalButton=new Button(i18n.makeItGoal());
			ria.getLeftMenubar().newGrowButtonToNormalStyle();
			makeItGoalButton.setStyleName("mf-helpGuideButton");
			makeItGoalButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					wizardListener.wizardFinishedWithOk();
					ria.getLeftMenubar().createNewGrow(wizardStepAnswerTextBox.getText());
				}
			});
			setWidget(1, 1, makeItGoalButton);
			
			button = new Button(i18n.next(),new ClickHandler() {
				public void onClick(ClickEvent event) {
					handleWizardNextStep(wizardState.getQuestion().getId(), wizardStepAnswerTextBox.getText());
				}
			});
			button.setStyleName("mf-button");
			wizardButtonsPanel.add(button);
		} else {
			//TODO wizardButtonsPanel.add(new Button("Back"));
			button = new Button(i18n.yes(),new ClickHandler() {
				public void onClick(ClickEvent event) {
					handleWizardNextStep(wizardState.getQuestion().getId(), WizardQ.YES);
				}
			});
			button.setStyleName("mf-button");
			wizardButtonsPanel.add(button);
			button = new Button(i18n.ratherYes(),new ClickHandler() {
				public void onClick(ClickEvent event) {
					handleWizardNextStep(wizardState.getQuestion().getId(), WizardQ.RATHER_YES);
				}
			});
			button.setStyleName("mf-button");
			wizardButtonsPanel.add(button);
			button = new Button(i18n.ratherNo(),new ClickHandler() {
				public void onClick(ClickEvent event) {
					handleWizardNextStep(wizardState.getQuestion().getId(), WizardQ.RATHER_NO);
				}
			});
			button.setStyleName("mf-button");
			wizardButtonsPanel.add(button);
			button = new Button(i18n.no(),new ClickHandler() {
				public void onClick(ClickEvent event) {
					handleWizardNextStep(wizardState.getQuestion().getId(), WizardQ.NO);
				}
			});
			button.setStyleName("mf-button");
			wizardButtonsPanel.add(button);			
		}
		button = new Button(i18n.cancel(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				wizardListener.wizardFinishedWithOk();
			}
		});
		button.setStyleName("mf-buttonLooser");
		wizardButtonsPanel.add(button);
		setWidget(2, 0, wizardButtonsPanel);
		
		if(q.getHint()!=null && !"".equals(q.getHint())) {
			html = new HTML("<b>"+i18n.hint()+":</b>  "+q.getHint());
			html.setStyleName("mf-checkListWizardHint");
			setWidget(3, 0, html); 
		}
	}
	
	private void handleWizardNextStep(Integer questionId, String answer) {
		ria.getStatusLine().showProgress(i18n.storingChecklistAnswer());
		ria.getService().setCheckListAnswer(growId, mode, questionId, answer, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				ria.getStatusLine().hideStatus();
			}
			public void onFailure(Throwable caught) {
				ria.getRia().handleServiceError(caught);
			}
		});		

		if(wizardState.next()) {
			renderWizardStep();			
		} else {
			renderLastWizardStep();
		}
		
	}
	
	private void renderLastWizardStep() {
		removeAllRows();

		html = new HTML("<span class='mf-hint'>!</span>  "+wizardLastStepDocumentation);
		html.setStyleName("mf-checkListWizardQuestion"); 
		setWidget(0, 0, html);
		HorizontalPanel wizardButtonsPanel=new HorizontalPanel();
		wizardButtonsPanel.setStyleName("mf-checklistWizardButtons");
		button = new Button(i18n.done(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				wizardListener.wizardFinishedWithOk();
			}
		});
		button.setStyleName("mf-button");
		wizardButtonsPanel.add(button);
		setWidget(1, 0, wizardButtonsPanel);		
	}	
	
	public void setGrowId(String growId) {
		this.growId=growId;
	}
}
