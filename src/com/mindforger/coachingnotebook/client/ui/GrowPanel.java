package com.mindforger.coachingnotebook.client.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaState;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.security.GrowSharingPanel;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class GrowPanel extends VerticalPanel {
    private RiaContext ctx;

	public static final String TABS_RENDERING_HORIZONTAL_TEXT="==";
	public static final String TABS_RENDERING_VERTICAL_TEXT="||";
	
    private String growKey;
    
    private GrowPanelMode panelMode;

	private HorizontalPanel topButtonsPanel;
	private HorizontalPanel goalFooterPanel;
    
	private GrowTitlePanel goalTitlePanel;
	private GrowTabs growTabs;
	
	private FiveButtonsPanel importancePanel;
	private FiveButtonsPanel urgencyPanel;
	private GrowProgressPanel progressPanel;

	private SwotForGrowPanel swotChartHook;
	private GrowSharingPanel sharingPanel;
	
	private Map<String,QuestionAnswerPanel> questionIdToQAPanelMap;

	private RiaState state;
	private RiaMessages i18n;
	private MindForgerServiceAsync service;
	private StatusLine statusLine;
	private Ria ria;
	private LeftMenubar leftMenubar;
	
	public GrowPanel(RiaContext ctx) {
		this.ctx=ctx;		
	}
	
	public void init() {		
		this.i18n=ctx.getI18n();
		this.service=ctx.getService();
		this.state=ctx.getState();
		this.statusLine=ctx.getStatusLine();
		this.ria=ctx.getRia();
		this.leftMenubar=ctx.getLeftMenubar();
		
		setStyleName("mf-growPanel");
		
		this.questionIdToQAPanelMap=new HashMap<String, QuestionAnswerPanel>();
		
		// top buttons
		topButtonsPanel = new HorizontalPanel();
		Button saveButtonTop = new Button(i18n.save());
		saveButtonTop.setStyleName("mf-button");
		saveButtonTop.setTitle(i18n.save());
		topButtonsPanel.add(saveButtonTop);
		Button discardTopButton = new Button(i18n.discardChanges());
		discardTopButton.setStyleName("mf-buttonLooser");
		discardTopButton.setTitle(i18n.discardChanges());
		topButtonsPanel.add(discardTopButton);
		Button shareTopButton = new Button(i18n.sharedWithNobody());
		shareTopButton.setStyleName("mf-button");
		shareTopButton.setTitle(i18n.shareDescription());
		topButtonsPanel.add(shareTopButton);
		//Button addLabelTopButton = new Button(i18n.addLabel());
		//topButtonsPanel.add(addLabelTopButton);
		//Button removeLabelTopButton = new Button(i18n.removeLabel());
		//topButtonsPanel.add(removeLabelTopButton);
		Button deleteTopButton= new Button(i18n.delete());
		deleteTopButton.setStyleName("mf-button");
		deleteTopButton.setTitle(i18n.delete());
		topButtonsPanel.add(deleteTopButton);
		add(topButtonsPanel);
				
		sharingPanel = new GrowSharingPanel(ctx, shareTopButton);
		add(sharingPanel);
		
		// importance and urgency buttons
		VerticalPanel importanceAndUrgencyPanel=new VerticalPanel();
		importanceAndUrgencyPanel.setStyleName("mf-importanceAndUrgencyPanel");
		importanceAndUrgencyPanel.setHorizontalAlignment(ALIGN_RIGHT);
		importancePanel = new FiveButtonsPanel("", i18n.importance(), "mf-importanceButtonOff", "mf-importanceButtonOn", ctx); // i18n
		urgencyPanel = new FiveButtonsPanel("!", i18n.urgency(), "mf-urgencyButtonOff", "mf-urgencyButtonOn", ctx); // i18n
		importanceAndUrgencyPanel.add(importancePanel);
		importanceAndUrgencyPanel.add(urgencyPanel);
		
		// wheel of life label
		//importanceAndUrgencyPanel.add(new GrowWolLabelPanel(ria));
		
		// grows rendering
		String buttonText=TABS_RENDERING_HORIZONTAL_TEXT;
		if(!state.getUserSettings().isHorizontalGrowTabsRendering()) {
			buttonText=TABS_RENDERING_VERTICAL_TEXT;
		}
		final Button growTabsRenderingButton = new Button(buttonText);
		growTabsRenderingButton.setTitle(i18n.toggleHorizontalVerticalRenderingOfGrows());
		growTabsRenderingButton.setStyleName("mf-button");
		growTabsRenderingButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedTab=growTabs.getSelectedTab();
				state.getUserSettings().setHorizontalGrowTabsRendering(!state.getUserSettings().isHorizontalGrowTabsRendering());
				configureGrowTabsRendering(ctx);
				growTabs.selectTab(selectedTab);
				
				statusLine.showProgress(i18n.savingYourSettings());
				service.saveUserSettings(state.getUserSettings(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						ria.handleServiceError(caught);
					}
					public void onSuccess(Void result) {
						statusLine.hideStatus();						
					}
				});
				
				if(state.getUserSettings().isHorizontalGrowTabsRendering()) {
					growTabsRenderingButton.setText(TABS_RENDERING_HORIZONTAL_TEXT);
				} else {
					growTabsRenderingButton.setText(TABS_RENDERING_VERTICAL_TEXT);					
				}
								
				// open the grow that was previously opened
				String growId=ctx.getGrowPanel().getGrowId();
				ria.loadGrow(growId, 0);
			}
		});
		importanceAndUrgencyPanel.add(growTabsRenderingButton);
		
		// goal title
		goalTitlePanel = new GrowTitlePanel(ctx);		
		progressPanel = new GrowProgressPanel(ctx);
		HorizontalPanel goalHeaderPanel=new HorizontalPanel();
		goalHeaderPanel.setStyleName("mf-goalHeaderPanel");
		goalHeaderPanel.add(goalTitlePanel);
		goalHeaderPanel.add(progressPanel);
		goalHeaderPanel.add(importanceAndUrgencyPanel);
		add(goalHeaderPanel);
		
		// tabs and their rendering
		configureGrowTabsRendering(ctx);
		
		goalFooterPanel = new HorizontalPanel();
		goalFooterPanel.setStyleName("mf-goalFooterPanel");
		Button saveButtonBottom = new Button(i18n.save());
		saveButtonBottom.setStyleName("mf-button");
		saveButtonBottom.setTitle(i18n.save());
		goalFooterPanel.add(saveButtonBottom);		
		Button printableViewButtonBottom=new Button(i18n.printableView(),new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		printableViewButtonBottom.setStyleName("mf-buttonLooser");
		printableViewButtonBottom.setTitle(i18n.getPrintableRepresentation());
		//goalFooterPanel.add(printableViewButtonBottom);		
		Button jsonButtonBottom=new Button(i18n.json(),new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open(getGrowUrl(getGrowId()), "_blank", "");
			}
		});
		jsonButtonBottom.setStyleName("mf-buttonLooser");
		jsonButtonBottom.setTitle(i18n.getGoalAsJson());
		goalFooterPanel.add(jsonButtonBottom);		
		Button discardBottomButton = new Button(i18n.discardChanges());
		discardBottomButton.setStyleName("mf-buttonLooser");
		discardBottomButton.setTitle(i18n.discardChanges());
		goalFooterPanel.add(discardBottomButton);
		Button shareBottomButton = new Button(i18n.share());
		shareBottomButton.setStyleName("mf-button");
		shareBottomButton.setTitle(i18n.shareThisGoalWithFriends());
		//goalFooterPanel.add(shareBottomButton);		
		Button deleteBottomButton = new Button(i18n.delete());
		deleteBottomButton.setStyleName("mf-button");
		deleteBottomButton.setTitle(i18n.delete());
		goalFooterPanel.add(deleteBottomButton);
		add(goalFooterPanel);
		
		ClickHandler saveButtonClickHandler = new ClickHandler() {
	          public void onClick(ClickEvent event) {	        	  
	        	  save(true);		
	        	  leftMenubar.showGrowsTable();
	          }
		};
		saveButtonTop.addClickHandler(saveButtonClickHandler);
		saveButtonBottom.addClickHandler(saveButtonClickHandler);
		
		ClickHandler sharingClickHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				sharingPanel.setVisible(true);
			}
		};
		shareTopButton.addClickHandler(sharingClickHandler);
		
		ClickHandler deleteButtonClickHandler = new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  // RIA 2 bean
	        	  GrowBean bean=new GrowBean();
	        	  bean.setKey(growKey);
	        	  service.deleteGrow(bean, new AsyncCallback<Void>() {
	        		  public void onFailure(Throwable caught) {
	        			  GWT.log("Delete error: "+caught.getLocalizedMessage(),caught);
	        			  ctx.getRia().handleServiceError(caught);
	        		  }
	        		  public void onSuccess(Void v) {
	        			  GWT.log("RIA - grow succesfuly deleted!");
	    	        	  ria.refreshRiaOnGrowSave();
	        		  }
	        	  });		
	        	  leftMenubar.showGrowsTable();
	          }
		};
		deleteTopButton.addClickHandler(deleteButtonClickHandler);
		deleteBottomButton.addClickHandler(deleteButtonClickHandler);

		ClickHandler discardButtonClickHandler = new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  leftMenubar.showGrowsTable();
	          }
		};
		discardTopButton.addClickHandler(discardButtonClickHandler);
		discardBottomButton.addClickHandler(discardButtonClickHandler);		
	}

	private void configureGrowTabsRendering(final RiaContext ria) {
		int index=3;
		if(growTabs!=null) {
			index = getWidgetIndex(growTabs.getTabsWidget());
			remove(growTabs.getTabsWidget());
		}
		if(ria.getState().getUserSettings()!=null && ria.getState().getUserSettings().isHorizontalGrowTabsRendering()) {
			growTabs = new GrowTabsHorizontal(ria);			
		} else {
			growTabs = new GrowTabsVertical(ria);			
		}
		insert(growTabs.getTabsWidget(),index);
	}

	public String getGrowUrl(String growId) {
		return "/rest/user/grows/"+getGrowId();
	}
		
	public String getGrowId() {
		return growKey;
	}
	
	public GrowTabs getGrowTabs() {
		return growTabs;
	}

	public void setGrowId(String growId) {
		this.growKey = growId;
	}
	
	public void setSwotChartHook(SwotForGrowPanel swotChartHook) {
		this.swotChartHook = swotChartHook;
	}
	
	public void setGrowTitle(String title) {
		goalTitlePanel.toRia(title, null);
	}

	public void onNewGrow(String growKey) {
		toReadWriteMode();
		
		questionIdToQAPanelMap.clear();
		
		this.growKey=growKey;
		GrowBean bean=new GrowBean();
		bean.setKey(growKey);
		bean.setOwnerId(state.getCurrentUser().getUserId());
		growTabs.toRia(bean);
		
		urgencyPanel.setMeasuredValue(0);
		importancePanel.setMeasuredValue(0);
		progressPanel.onNewGrow();
		
		goalTitlePanel.toRia(i18n.defaultGoalName(),i18n.defaultGoalDescription());
		goalTitlePanel.toEditTitleMode();
		goalTitlePanel.toEditDescriptionMode();
	}
	
	public void toRia(GrowBean bean) {
		if(state.getCurrentUser().getUserId().equals(bean.getOwnerId())) {
			toReadWriteMode();
		} else {
			toReadOnlyMode();
		}

		questionIdToQAPanelMap.clear();
		
		growKey=bean.getKey();
		goalTitlePanel.toRia(bean.getName(),bean.getDescription());
		growTabs.toRia(bean);
		urgencyPanel.setMeasuredValue(bean.getUrgency());
		importancePanel.setMeasuredValue(bean.getImportance());	
		progressPanel.toRia(bean.getProgress()+"");
		growTabs.getOTab().getMagicTriangle().setStrategy(bean.getMagicTriangle());
		sharingPanel.refresh(bean.getSharedTo());
	}

	private void toReadOnlyMode() {
		panelMode=GrowPanelMode.RD_ONLY;
		topButtonsPanel.setVisible(false);
		goalFooterPanel.setVisible(false);
	}

	private void toReadWriteMode() {
		panelMode=GrowPanelMode.RDWR;
		topButtonsPanel.setVisible(true);
		goalFooterPanel.setVisible(true);
	}

	public void save(final boolean loadAfterSaveNeeded) {
		// TODO optimize save:
		//  o load after save only if needed 
		//  o e.g. application that calls save may indidate (for shuffle questions order not required)
		//  o keep list of grows as array list in RIA, on save replace the object there (but don't reload)
		
		// RIA 2 bean
		GrowBean bean=new GrowBean();
		bean.setKey(growKey);
		goalTitlePanel.fromRia(bean); // name and description
		bean.setProgress(progressPanel.getProgressValue());
		bean.setModified(new Date());
		bean.setUrgency(urgencyPanel.getMeasuredValue());
		bean.setImportance(importancePanel.getMeasuredValue());
		growTabs.fromRia(bean);
		bean.setMagicTriangle(growTabs.getOTab().getMagicTriangle().getStrategy());
		bean.setSharedTo(sharingPanel.getSharedTo());
		
		// refresh
		if(swotChartHook!=null) {
			swotChartHook.toRia(bean);
		}

		statusLine.showProgress(i18n.savingGrow()+" '"+bean.getName()+"'...");
		service.saveGrow(bean, new AsyncCallback<GrowBean>() {
			public void onFailure(Throwable caught) {
				GWT.log("Save error: "+caught.getLocalizedMessage(),caught);
				ctx.getRia().handleServiceError(caught);
			}
			public void onSuccess(GrowBean result) {
				GWT.log("RIA - grow succesfuly saved!");
				if(loadAfterSaveNeeded) {
					ria.refreshRiaOnGrowSave();					
				} else {
					statusLine.hideStatus();
				}
			}
		});
	}
	
	public Map<String, QuestionAnswerPanel> getQuestionIdToQAPanelMap() {
		// STEP 1: each question registers itself to the map when GROW is loaded
		// STEP 2: when comments are asynchronously loaded, they find associated
		//         question in the map and add themselves there > comments find
		//         its position in the thread (ordered from oldest to newest) 
		return questionIdToQAPanelMap;
	}

	public boolean isRdWr() {
		return GrowPanelMode.RDWR.equals(panelMode);
	}
}
