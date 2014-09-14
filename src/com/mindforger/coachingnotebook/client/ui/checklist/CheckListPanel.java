package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.ImproveWoLButton;
import com.mindforger.coachingnotebook.client.ui.LifeDesignerPanel;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;

public class CheckListPanel extends VerticalPanel implements WizardFinishListener {
	
	private boolean active;
	private int completionStatus;
	private CheckListItem[] checkListItems;
	private Map<Integer, CheckListAnswerBean> answersMap;
	//	private WizardState wizardState;
	private FlexTable checkListItemsTable;
	private Button refreshCheckListButton;
	private HTML checklistDocumentation, html;		
	private CheckListState checkListStatus;
	
	private HorizontalPanel viewPanel;
	private WizardPanel wizardPanel;
	
	private String mode;
	private String growId;
	private RiaContext ria;
	
	private List<CheckListUpdateListener> listeners;
	private RiaMessages i18n;
	
	public CheckListPanel(
			final String mode, 
			String completionStatusTitle, 
			String completionStatusSubTitle, 
			String checklistDocumentationString, 
			String checklistDisclosurePanelTitle, 
			String wizardFirstStepDocumentation, 
			String wizardLastStepDocumentation, 
			final CheckListPanelRefreshCallback refreshCallback,
			final RiaContext ria) {
		
		i18n=ria.getI18n();
		
		CheckListItems checkList=new CheckListItems(ria.getI18n());

		active=true;
		if(WizardPanel.MODE_G.equals(mode)) {
			checkListStatus=new CheckListState(checkList, mode);
		} else {
			if(WizardPanel.MODE_I.equals(mode)) {
				checkListStatus=new CheckListState(checkList, mode);
			} else {
				if(WizardPanel.MODE_WHEEL_OF_LIFE.equals(mode)) {
					checkListStatus=new CheckListState(checkList, mode);
				} else {
					// mode not supported
					active=false;
					setVisible(false);
					return;
				}
			}
		}

		setStyleName("mf-checklistPanel");
		
		this.ria=ria;
		this.mode=mode;
		
		completionStatus = 0;
		answersMap = new HashMap<Integer, CheckListAnswerBean>();
		listeners=new ArrayList<CheckListUpdateListener>();				
		
		/*
		 * view mode
		 */
		
		checkListItems = new CheckListItem[0];		
		viewPanel = new HorizontalPanel();
		viewPanel.setStyleName("mf-checklistHead");
		
		VerticalPanel completionStatusPanel=new VerticalPanel();
		refreshCheckListButton = new Button();
		refreshCheckListButton.setText(completionStatus+"%");
		refreshCheckListButton.setTitle(completionStatusTitle);
	    refreshCheckListButton.setStyleName("mf-checklistCompletionStatus");
	    HTML qualityHtml=new HTML(completionStatusSubTitle);
	    qualityHtml.setStyleName("mf-checklistQualityHtml");
	    completionStatusPanel.add(refreshCheckListButton);
	    completionStatusPanel.add(qualityHtml);
		viewPanel.add(completionStatusPanel);
		
		VerticalPanel rightPanel=new VerticalPanel();
		rightPanel.setStyleName("mf-checklistRightPanel");

		checklistDocumentation=new HTML(checklistDocumentationString);
	    checklistDocumentation.setStyleName("mf-checklistDocumentation");
		rightPanel.add(checklistDocumentation);
								
		checkListItemsTable = new FlexTable();
		checkListItemsTable.setStyleName("mf-viewChecklist");
						
		DisclosurePanel disclosurePanel=new DisclosurePanel(checklistDisclosurePanelTitle);
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(checkListItemsTable);
		if(WizardPanel.MODE_WHEEL_OF_LIFE.equals(mode)) {
			disclosurePanel.setOpen(true);
		}		
		rightPanel.add(disclosurePanel);

		viewPanel.add(rightPanel);
		add(viewPanel);		
		
		/*
		 * wizard mode
		 */

		wizardPanel=new WizardPanel(
				wizardFirstStepDocumentation,
				wizardLastStepDocumentation,
				null,
				ria,
				mode,
				this);
		wizardPanel.setVisible(false);
		add(wizardPanel);

		/*
		 * wizard launcher
		 */
		
	    ClickHandler launchWizardButtonHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(WizardPanel.MODE_WHEEL_OF_LIFE.equals(mode) ||
				   (WizardPanel.MODE_G.equals(mode) || WizardPanel.MODE_I.equals(mode)) && ria.getGrowPanel().isRdWr()) {
					if(refreshCallback!=null) {
						refreshCallback.onCheckListWizardStart();
					}
					launchWizard();					
				}
			}
	    };
		refreshCheckListButton.addClickHandler(launchWizardButtonHandler);
	}	
	
	public void load(String growId) {
		if(active) {
			this.growId=growId;
			wizardPanel.setGrowId(growId);
			ria.getStatusLine().showProgress(i18n.loadingChecklistAnswers());
			ria.getService().getCheckListAnswers(growId, mode, new AsyncCallback<CheckListAnswerBean[]>() {
				public void onSuccess(CheckListAnswerBean[] result) {
					ria.getStatusLine().hideStatus();
					refresh(result);
					notifyListeners();
					viewPanel.setVisible(true);
					wizardPanel.setVisible(false);
				}
				public void onFailure(Throwable caught) {
					ria.getRia().handleServiceError(caught);
				}
			});					
		}
	}
	
	private void refresh(CheckListAnswerBean[] result) {
		if(isVisible()) {
			// process answers from the server
			wizardPanel.resetWizardState();
			answersMap.clear();
			if(result!=null) {
				for (int i = 0; i < result.length; i++) {
					answersMap.put(result[i].getId(), result[i]);				
				}
			}
			
			recountCheckListStatus();
			
			// render checklist items
			refreshCheckListButton.setText(completionStatus+"%");
			if(checkListItems!=null) {
				for (int i = 0; i < checkListItems.length; i++) {
					if(CheckListItemStatusEnum.UNKNOWN.equals(checkListItems[i].getStatus())) {
						html = new HTML(checkListItems[i].getStatus().label);					
					} else {
						html = new HTML(checkListItems[i].getPercent()+"%");
					}
					html.setStyleName(checkListItems[i].getStatus().cssClass);
					checkListItemsTable.setWidget(i, 0, html);
					html = new HTML(checkListItems[i].getLabel());
					html.setStyleName("mf-checklistText");
					checkListItemsTable.setWidget(i, 1, html);
					if(WizardPanel.MODE_WHEEL_OF_LIFE.equals(mode) && checkListItems[i].getPercent()!=100) {
						checkListItemsTable.setWidget(i, 2, new ImproveWoLButton(LifeDesignerPanel.WHEEL_OF_LIFE_LABELS[i], ria, this));
					} else {
						checkListItemsTable.setWidget(i, 2, new HTML());						
					}
				}
			}			
		}
	}
	
	private void recountCheckListStatus() {
		checkListStatus.setValuesAndEvaluate(answersMap);
		checkListItems=checkListStatus.getItems();
		completionStatus=checkListStatus.evaluateCompletionStatus();
	}

	public void launchWizard() {
		viewPanel.setVisible(false);
		wizardPanel.renderFirstWizardStep();
	}
	
	public CheckListState getCheckListStatus() {
		return checkListStatus;
	}
	
	public void addListener(CheckListUpdateListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).checklistChanged();
		}
	}
	
	/*
	 * Checklist as listener to wizard
	 */

	public void wizardFinishedWithOk() {
		// TODO OPTIMIZE this should stay in memory and no server call should be needed
		// is asynchronous, therefore dispatch must be moved elsewhere
		load(growId);
	}
	
	public void wizardFinishedWithCancel() {
		viewPanel.setVisible(true);
		notifyListeners();
	}
}
