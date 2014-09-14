package com.mindforger.coachingnotebook.client;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mindforger.coachingnotebook.client.ui.ActionsTable;
import com.mindforger.coachingnotebook.client.ui.CheatSheetPanel;
import com.mindforger.coachingnotebook.client.ui.DashboardPanel;
import com.mindforger.coachingnotebook.client.ui.GrowPanel;
import com.mindforger.coachingnotebook.client.ui.GrowsTable;
import com.mindforger.coachingnotebook.client.ui.ImportanceUrgencyChart;
import com.mindforger.coachingnotebook.client.ui.LeftMenubar;
import com.mindforger.coachingnotebook.client.ui.PageTitlePanel;
import com.mindforger.coachingnotebook.client.ui.QuestionAnswerPanel;
import com.mindforger.coachingnotebook.client.ui.RightCornerPanel;
import com.mindforger.coachingnotebook.client.ui.SharedGrowsTable;
import com.mindforger.coachingnotebook.client.ui.StatusLine;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionsPanel;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionsPanel.ConnectionsPanelMode;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.RiaBootImageBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;
import com.mindforger.coachingnotebook.shared.verifiers.FieldVerifier;
import com.mindforger.coachingnotebook.shared.verifiers.FieldVerifier.Field;

public class Ria implements EntryPoint, MindForgerConstants, MindForgerSettings {
	private static final Logger LOG=Logger.getLogger("MindForgerRia");
					
	private RiaContext ctx;
	
	private RiaState state;
	private RiaMessages i18n;
	private MindForgerServiceAsync service;
	private FieldVerifier fieldVerifier;
	private PageTitlePanel pageTitle;
	private LeftMenubar leftMenubar;
	private StatusLine statusLine;
	private GrowPanel growPanel;
	private GrowsTable growsTable;
	private SharedGrowsTable sharedGrowsTable;
	private ActionsTable actionsTable;
	private DashboardPanel dashboard;
	private ImportanceUrgencyChart importanceUrgencyChart;
	private CheatSheetPanel cheatsheet;
	private ConnectionsPanel connectionsPanel;
	private RightCornerPanel rightCornerPanel;
	
	public Ria() {
		ctx=new RiaContext(this);
		i18n=ctx.getI18n();
	}
	
	public void onModuleLoad() {
		GWT.log("Loading MindForger!");

		RootPanel.get(CONTAINER_STATUS_LINE).add(ctx.getStatusLine());
		RootPanel.get(CONTAINER_PAGE_TITLE).add(ctx.getPageTitle());
		
		ctx.getStatusLine().showProgress(i18n.loadingYourGoalsEtc());

		ctx.getService().getRiaBootImage(new AsyncCallback<RiaBootImageBean>() {
			public void onSuccess(RiaBootImageBean bean) {
				ctx.getStatusLine().showProgress(i18n.initializingMf());

				ctx.getState().init(bean);
				
				int growsCount=(ctx.getState().getGrowBeans()!=null?ctx.getState().getGrowBeans().length:0);

				RootPanel.get(CONTAINER_MY_LIFE).add(ctx.getLifeDesignerPanel());						
				RootPanel.get(CONTAINER_USER_PROFILE).add(ctx.getUserProfilePanel());
				RootPanel.get(CONTAINER_USER_PROFILE).setVisible(false);
				RootPanel.get(CONTAINER_CONNECTIONS).add(ctx.getConnectionsPanel());
				RootPanel.get(CONTAINER_CONNECTIONS).setVisible(false);
								
				if(BLUE_LIFE_ENABLED) {
					RootPanel.get(CONTAINER_BLUE_LIFE).add(ctx.getBlueLifePanel());
					RootPanel.get(CONTAINER_BLUE_LIFE).setVisible(false);		
				}
				
				RootPanel.get(CONTAINER_SEARCH_RESULTS).add(ctx.getSearchResultsPanel());				

				RootPanel.get(CONTAINER_SEARCH).add(ctx.getSearchPanel());
				ctx.getSearchPanel().setVisible(true);

				RootPanel.get(CONTAINER_RIGHT_CORNER).add(ctx.getRightCornerPanel());

				RootPanel.get(CONTAINER_LEFT_MENUBAR).add(ctx.getLeftMenubar());
				ctx.getConnectionsPanel().refreshConnections(ConnectionsPanelMode.CONNECTIONS, true);

				RootPanel.get(CONTAINER_GROWS_TABS).add(ctx.getGrowPanel());

				ctx.getLeftMenubar().setGrowsCount(growsCount);

				// TODO do result processing later
				RootPanel.get(CONTAINER_GROWS_TABLE).add(ctx.getGrowsTable());
				// TODO do result processing later
				RootPanel.get(CONTAINER_SHARED_GROWS_TABLE).add(ctx.getSharedGrowsTable());
				// TODO do result processing later
				RootPanel.get(CONTAINER_IMPORTANCE_URGENCY_CHART).add(ctx.getImportanceUrgencyChart());

				RootPanel.get(CONTAINER_DASHBOARD).add(ctx.getDashboard());

				// TODO do result processing later
				RootPanel.get(CONTAINER_ACTIONS_TABLE).add(ctx.getActionsTable());
				// TODO do result processing later
				RootPanel.get(CONTAINER_LESSONS_LEARNED_TABLE).add(ctx.getLessonsLearnedTable());
				RootPanel.get(CONTAINER_CHEATSHEET).add(ctx.getCheatSheet());

				// ctx initialized > set up beans
				state=ctx.getState();
				service=ctx.getService();
				fieldVerifier=ctx.getFieldVerifier();
				pageTitle=ctx.getPageTitle();
				leftMenubar=ctx.getLeftMenubar();
				statusLine=ctx.getStatusLine();
				growPanel=ctx.getGrowPanel();
				growsTable=ctx.getGrowsTable();
				sharedGrowsTable=ctx.getSharedGrowsTable();
				actionsTable=ctx.getActionsTable();
				dashboard=ctx.getDashboard();
				importanceUrgencyChart=ctx.getImportanceUrgencyChart();
				cheatsheet=ctx.getCheatSheet();
				connectionsPanel=ctx.getConnectionsPanel();
				rightCornerPanel=ctx.getRightCornerPanel();
				
				// hide loading stuff
				RootPanel.get(CONTAINER_LOADING_PANEL).setVisible(false);
				RootPanel.get(CONTAINER_TOP_HR).setVisible(true);
				RootPanel.get(CONTAINER_LOGO).setVisible(true);
				RootPanel.get(CONTAINER_FOOTNOTE).setVisible(true);
				
				// show welcome page if there are no grows
				if(ctx.getState().getGrowBeans().length==0) {
					ctx.getCheatSheet().showAsWelcomePage(true);
					leftMenubar.showCheatSheet();
				} else {
					if(ctx.getState().inPerspective(new String[]{PERSPECTIVE_PROBLEM_SOLVER})) {
						leftMenubar.showGrowsTable();
					} else {
						if(ctx.getState().inPerspective(new String[]{PERSPECTIVE_MIND_FORGER})) {
							ctx.getLeftMenubar().showOrganizer();
						} else {
							if(ctx.getState().inPerspective(new String[]{PERSPECTIVE_COACH})) {
								ctx.getLeftMenubar().showSharedGoals();
							} else {
								ctx.getLeftMenubar().showMyLife();
							}													
						}						
					}				
					ctx.getDashboard().refreshGrows(ctx.getState().getGrowBeans());									
				}

				initHistorySupport();		
				
				ctx.getStatusLine().hideStatus();
			}
			public void onFailure(Throwable caught) {
				handleServiceError(caught);
			}			
		});				
	}
	
	public void initHistorySupport() {
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO implement handling of the history
			}
		});
		
		// check to see if there are any tokens passed at startup via the browser's URI
		String token = History.getToken();
		if (token.length() == 0) {
			onHistoryChanged(null);
		}
		else {
			onHistoryChanged(token);
		}
	}

	public void onHistoryChanged(String historyToken) {
		if(historyToken==null) {
			LOG.log(Level.INFO, "There is no token passed in the browser URL");
		}
		else {
			LOG.log(Level.INFO, "Got a new browser history change token:" + historyToken);
			if("connections".equals(historyToken)) {
				leftMenubar.showConnections();
			} else {
				
				if("actions".equals(historyToken)) {
					statusLine.showProgress(i18n.loadingActions());
					service.getActions(new AsyncCallback<QuestionAnswerBean[]>() {
						public void onSuccess(QuestionAnswerBean[] result) {
							statusLine.hideStatus();
							actionsTable.refresh(result);
							leftMenubar.showActionsTable();
						}
						public void onFailure(Throwable caught) {
							handleServiceError(caught);
						}
					});
				} else {
					if("cheatsheet".equals(historyToken)) {
						if(state.getGrowBeans().length==0) {
							cheatsheet.showAsWelcomePage(true);
						} else {
							cheatsheet.showAsWelcomePage(false);						
						}
						leftMenubar.showCheatSheet();
					}
				}
			}
		}
	}

	public void loadGrow(final String growId, final int tabToSelect) {
		service.getGrow(growId, new AsyncCallback<GrowBean>() {
			public void onSuccess(GrowBean bean) {
				// TODO this one should replace the bean in RIA array of grows
				// TODO this method should be somewhere on ria
				
				leftMenubar.getNewGrowButton().setStyleName("mf-button");
				leftMenubar.getNewGrowButton().addStyleName("mf-newGoalButton");

				// bean 2 RIA
				growPanel.getGrowTabs().selectTab(tabToSelect);
				growPanel.toRia(bean);
				
				// show grow after it is loaded and pushed to RIA
				showGrowTabs();

				statusLine.showInfo(bean.getNumberOfQuestions()+" "+i18n.itemsLowerCase());
				
				// load also comments - but separately from grow (there might be many comments)
				service.getCommentsForGrow(growId, new AsyncCallback<CommentBean[]>() {
					public void onFailure(Throwable caught) {
						handleServiceError(caught);
					}
					public void onSuccess(CommentBean[] result) {
						if(result!=null && result.length>0) {
							Map<String, QuestionAnswerPanel> map = growPanel.getQuestionIdToQAPanelMap();
							for (int i = 0; i < result.length; i++) {
								QuestionAnswerPanel panel = map.get(result[i].getQuestionId());
								if(panel!=null) {
									panel.addComment(result[i]);
								}
							}
						}
					}
				});
				
				ctx.getLeftMenubar().getRecentPanel().addRow(growId, "", bean.getName(), bean.getDescription(), MindForgerResourceType.GROW);
			}
			public void onFailure(Throwable caught) {
				handleServiceError(caught);
			}
		});
	}
	
	public void refreshRiaOnGrowSave() {
		statusLine.showProgress(i18n.loadingGrows());
		service.getGrows(new AsyncCallback<GrowBean[]>() {
			public void onFailure(Throwable caught) {
				handleServiceError(caught);
			}
			public void onSuccess(GrowBean[] result) {
				statusLine.hideStatus();

				state.setGrowBeans(result);

				int growsCount=(result!=null?result.length:0);
				leftMenubar.setGrowsCount(growsCount);
				
				// TODO dashboard not refreshed
				growsTable.refresh(result);
				importanceUrgencyChart.refresh(result);				
			}
		});
	}

	private void hideAllContainers() {
		pageTitle.setHTML("");
		
		RootPanel.get(CONTAINER_DASHBOARD).setVisible(false);
		RootPanel.get(CONTAINER_MY_LIFE).setVisible(false);
		RootPanel.get(CONTAINER_IMPORTANCE_URGENCY_CHART).setVisible(false);
		RootPanel.get(CONTAINER_GROWS_TABLE).setVisible(false);
		RootPanel.get(CONTAINER_SHARED_GROWS_TABLE).setVisible(false);
		RootPanel.get(CONTAINER_ACTIONS_TABLE).setVisible(false);
		RootPanel.get(CONTAINER_LESSONS_LEARNED_TABLE).setVisible(false);
		RootPanel.get(CONTAINER_GROWS_TABS).setVisible(false);		
		RootPanel.get(CONTAINER_SEARCH_RESULTS).setVisible(false);		
		
		RootPanel.get(CONTAINER_CONNECTIONS).setVisible(false);		
		RootPanel.get(CONTAINER_CHEATSHEET).setVisible(false);		
		RootPanel.get(CONTAINER_USER_PROFILE).setVisible(false);		
		RootPanel.get(CONTAINER_BLUE_LIFE).setVisible(false);		
	}
	
	public void showDashboard() {
		hideAllContainers();
		pageTitle.setHTML(i18n.dashboard());
		// TODO to be LAZILY refreshed - same as organizer and grows table
		RootPanel.get(CONTAINER_DASHBOARD).setVisible(true);
	}

	public void showMyLife() {
		hideAllContainers();
		pageTitle.setHTML(i18n.lifeDesigner());
		RootPanel.get(CONTAINER_MY_LIFE).setVisible(true);
	}

	public void showUserProfile() {
		hideAllContainers();
		pageTitle.setHTML(i18n.userProfile());
		RootPanel.get(CONTAINER_USER_PROFILE).setVisible(true);
	}

	public void showBlueLife() {
		hideAllContainers();
		pageTitle.setHTML(i18n.blueLife());
		RootPanel.get(CONTAINER_BLUE_LIFE).setVisible(true);
	}

	public void showSearchResults() {
		hideAllContainers();
		pageTitle.setHTML(i18n.searchResults());
		RootPanel.get(CONTAINER_SEARCH_RESULTS).setVisible(true);
	}

	public void showOrganizer() {
		hideAllContainers();
		pageTitle.setHTML(i18n.organizer());
		// TODO add LAZY refresh - which is OK, but elsewhere are refreshes that are no longer needed because of this one
		RootPanel.get(CONTAINER_IMPORTANCE_URGENCY_CHART).setVisible(true);
	}

	public void showGrowTabs() {
		hideAllContainers();
		pageTitle.setHTML(i18n.goal());
		RootPanel.get(CONTAINER_GROWS_TABS).setVisible(true);
	}
	
	public void showGrowsTable() {
		hideAllContainers();
		pageTitle.setHTML(i18n.goals());
		// TODO add LAZY refresh - which is OK, but elsewhere are refreshes that are no longer needed because of this one
  		RootPanel.get(CONTAINER_GROWS_TABLE).setVisible(true);	        	  
	}
	
	public void showActionsTable() {
		hideAllContainers();
		pageTitle.setHTML(i18n.actions());
  		RootPanel.get(CONTAINER_ACTIONS_TABLE).setVisible(true);	        	  
	}

	public void showLessonsLearnedTable() {
		hideAllContainers();
		pageTitle.setHTML(i18n.lessonsLearned());
  		RootPanel.get(CONTAINER_LESSONS_LEARNED_TABLE).setVisible(true);	        	  
	}

	public void showSharedGoals() {
		hideAllContainers();
		pageTitle.setHTML(i18n.sharedGoals());
		sharedGrowsTable.refreshWithNewSortingCriteria();
  		RootPanel.get(CONTAINER_SHARED_GROWS_TABLE).setVisible(true);	        	  
	}
	
	public void showConnections(ConnectionsPanelMode connectionsPanelMode) {
		hideAllContainers();
		pageTitle.setHTML(i18n.connections());
  		RootPanel.get(CONTAINER_CONNECTIONS).setVisible(true);
  		connectionsPanel.refreshConnections(connectionsPanelMode, false);
	}
	
	public void showCheatSheet() {
		hideAllContainers();
		pageTitle.setHTML(i18n.home());
  		RootPanel.get(CONTAINER_CHEATSHEET).setVisible(true);	        	  
	}

	public boolean verifyField(Field fieldType, String field, String fieldName) {
		int limit=fieldVerifier.verify(fieldType, field);
		if(limit<0) {
			statusLine.hideStatus();
			return true;
		} else {
			statusLine.showError(i18n.errorFieldMustBeShorterThan(fieldName, ""+limit));
			return false;
		}
	}
	
	public void handleServiceError(Throwable caught) {
		final String errorMessage = caught.getMessage();
		GWT.log("Error: "+errorMessage, caught);
		LOG.log(Level.SEVERE, errorMessage,caught);
		
		statusLine.showError(i18n.ooops());
	}

	public void handleSetPerspective(final String value) {
		state.getUserSettings().setPerspective(value);
		
		leftMenubar.reinitialize();
		rightCornerPanel.setPerspectiveName();
		cheatsheet.refresh();
		
		statusLine.showProgress(i18n.savingThePerspectiveSelection());
		service.saveUserSettings(state.getUserSettings(), new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				handleServiceError(caught);
			}
			public void onSuccess(Void result) {
				statusLine.hideStatus();
			}
		});
	}
		
	public void refreshDashboard() {
		dashboard.refreshGrows(state.getGrowBeans());
	}
	
	// TODO OPTIMIZE - prepare hashtable
	public String getGrowNameForId(String id) {
		GrowBean[] growBeans = state.getGrowBeans();
		if(growBeans!=null && id!=null) {
			for (int i = 0; i < growBeans.length; i++) {
				if(id.equals(growBeans[i].getKey())) {
					return growBeans[i].getName();
				}
			}
		}
		return "";
	}	
}
