package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.recent.RecentPanel;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionsPanel.ConnectionsPanelMode;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionsRequestsListener;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;

public class LeftMenubar extends FlexTable implements ConnectionsRequestsListener, MindForgerConstants {
	
	public static final String HTML_MENU_SECTION_PREFIX="<div class=\"mf-leftMenuItemSection\">";
	public static final String HTML_MENU_SECTION_POSTFIX="</div>";
	public static final String HTML_MENU_DELIMITER = "<div class='mf-leftMenuHr'></div>";
	
	private RiaContext ctx;

	// MF
	Button dashboardButton;
	Button lifeDesignerButton;
	Button organizerButton;
	Button blueLifeButton;
	
	HTML growsDelimiter;
	
	// GROWs
	Button growsButton;
	Button lessonsButton;
	Button actionsButton;
	Button newGrowButton;
	
	// connections
	Button connectionsButton;
	Button profileButton;
	Button sharedGoalsButton;
	Button newConnectionButton;

	// help
	Button cheatSheetButton;

	private Ria ria;
	private StatusLine statusLine;
	private GrowPanel growPanel;
	private RiaMessages i18n;
	private MindForgerServiceAsync service;
	private RecentPanel recentPanel;

	public LeftMenubar(final RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void init() {
		this.ria=ctx.getRia();
		this.statusLine=ctx.getStatusLine();
		this.growPanel=ctx.getGrowPanel();
		this.i18n=ctx.getI18n();
		this.service=ctx.getService();
		
		this.recentPanel=new RecentPanel(ctx);
		
		setStyleName("mf-leftMenu");
				
		// build widgets
		dashboardButton = new Button(ctx.getI18n().dashboard(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().getGrowBeans()!=null && ctx.getState().getGrowBeans().length>0) {
					ria.refreshDashboard();
					showDashboard();					
				} else {
					showCheatSheet();
				}
			}			
		});		
		dashboardButton.setStyleName("mf-menuButtonOn");
		dashboardButton.setTitle(ctx.getI18n().dashboard());
		lifeDesignerButton = new Button(i18n.lifeDesigner(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showMyLife();
			}			
		});
		lifeDesignerButton.setStyleName("mf-menuButtonOff");
		lifeDesignerButton.setTitle(i18n.lifeDesignerButtonTitle());
		organizerButton = new Button(ctx.getI18n().organizer(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().getGrowBeans()!=null && ctx.getState().getGrowBeans().length>0) {
					showOrganizer();
				} else {
					showCheatSheet();
				}
			}			
		});
		organizerButton.setStyleName("mf-menuButtonOff");
		organizerButton.setTitle(ctx.getI18n().timeOrganizer());
		blueLifeButton = new Button(i18n.blueLife(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showBlueLife();
			}			
		});
		blueLifeButton.setStyleName("mf-menuButtonOff");
		blueLifeButton.setTitle(i18n.blueLifeButtonTitle());
		growsButton = new Button(ctx.getI18n().goals(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().getGrowBeans()!=null && ctx.getState().getGrowBeans().length>0) {
					int growsCount=(ctx.getState().getGrowBeans()!=null?ctx.getState().getGrowBeans().length:0);
					setGrowsCount(growsCount);
					showGrowsTable();
				} else {
					showCheatSheet();					
				}
			}			
		});
		growsButton.setStyleName("mf-menuButtonOff");
		growsButton.setTitle(ctx.getI18n().goalsProblemsAndChallenges());
		lessonsButton = new Button(ctx.getI18n().lessonsLearned(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().getGrowBeans()!=null && ctx.getState().getGrowBeans().length>0) {
					ctx.getStatusLine().showProgress(i18n.loadingExperiences());
					ctx.getService().getLessonsLearned(new AsyncCallback<QuestionAnswerBean[]>() {
						public void onSuccess(QuestionAnswerBean[] result) {
							ctx.getStatusLine().hideStatus();
							ctx.getLessonsLearnedTable().refresh(result);
							showLessonsLearnedTable();
						}
						public void onFailure(Throwable caught) {
							ria.handleServiceError(caught);
						}
					});
				} else {
					showCheatSheet();
				}
			}
		});
		lessonsButton.setTitle(i18n.experiencesAndLessonsLearned());
		lessonsButton.setStyleName("mf-menuButtonOff");
		actionsButton = new Button(ctx.getI18n().actions(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().getGrowBeans()!=null && ctx.getState().getGrowBeans().length>0) {
					ctx.getStatusLine().showProgress(i18n.loadingActions());
					ctx.getService().getActions(new AsyncCallback<QuestionAnswerBean[]>() {
						public void onSuccess(QuestionAnswerBean[] result) {
							ctx.getStatusLine().hideStatus();
							ctx.getActionsTable().refresh(result);
							showActionsTable();
						}
						public void onFailure(Throwable caught) {
							ria.handleServiceError(caught);
						}
					});
				} else {
					showCheatSheet();
				}
			}
		});
		actionsButton.setTitle(i18n.actionsAndTasks());
		actionsButton.setStyleName("mf-menuButtonOff");
		newGrowButton = new Button(ctx.getI18n().newGoal(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				createNewGrow(null);		
			}
		});
		newGrowButton.setTitle(ctx.getI18n().newGoalProblemOrChallenge());
	    newGrowButton.setStyleName("mf-helpGuideButton");
	    newGrowButton.addStyleName("mf-newGoalButton");	    		
		
		// social
		sharedGoalsButton = new Button(i18n.sharedGoals(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showSharedGoals();
			}
		});
		sharedGoalsButton.setStyleName("mf-menuButtonOff");
		sharedGoalsButton.setTitle(i18n.sharedGoalsButtonTitle());
		
		connectionsButton = new Button(getConnectionsButtonText(0), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showConnections();
			}
		});
		connectionsButton.setTitle(i18n.connectionsButtonTitle());
		connectionsButton.setStyleName("mf-menuButtonOff");
		
		profileButton = new Button(i18n.myProfile(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showProfile();
			}
		});
		profileButton.setStyleName("mf-menuButtonOff");
		profileButton.setTitle(i18n.yourProfile());
		
		newConnectionButton = new Button(i18n.findConnections(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				showNewConnection();
			}
		});
		newConnectionButton.setTitle(i18n.findConnectionsButtonTitle());
	    newConnectionButton.addStyleName("mf-button");
	    newConnectionButton.addStyleName("mf-newGoalButton");
				
		// help
		
		cheatSheetButton = new Button(i18n.home(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getCheatSheet().showAsWelcomePage(false);
				showCheatSheet();
			}
		});
		cheatSheetButton.setTitle(i18n.cheatSheetButtonTitle());
		cheatSheetButton.setStyleName("mf-menuButtonOff");
				
		reinitialize();
	}

	public void reinitialize() {
		removeAllRows();
		
		HTML inviteFriendHtml = new HTML("<a href='mailto:yourfriend@example.com?subject="+i18n.emailInviteFriendSubject()+"&body="+i18n.emailInviteFriendBody());
		inviteFriendHtml.setStyleName("mf-menuButtonOff");
		inviteFriendHtml.addStyleName("mf-inviteFriend");
		inviteFriendHtml.setTitle(i18n.emailInviteFriendTitle());

		HTML addTestimonialHtml = new HTML("<a href='mailto:feedback@mindforger.com?subject="+i18n.emailAddTestimonialSubject()+"&body="+i18n.emailAddTestimonialBody());
		addTestimonialHtml.setStyleName("mf-menuButtonOff");
		addTestimonialHtml.addStyleName("mf-inviteFriend");
		addTestimonialHtml.setTitle(i18n.emailAddTestimonialTitle());
		
		growsDelimiter=new HTML(HTML_MENU_DELIMITER);
		
		// assemble menu bar
		int row=0;
		
		// MindForger
		//setWidget(row++, 0, new HTML(HTML_MENU_SECTION_PREFIX+"MENU"+HTML_MENU_SECTION_POSTFIX));

		setWidget(row++, 0, cheatSheetButton);
		// Tools
		if(!ctx.getState().inPerspective(new String[]{PERSPECTIVE_PROBLEM_SOLVER, PERSPECTIVE_COACH})) {
			setWidget(row++, 0, lifeDesignerButton);
		}
		setWidget(row++, 0, organizerButton);
		if(ctx.getState().getGrowBeans().length>0 || !ctx.getState().inPerspective(new String[]{PERSPECTIVE_PROBLEM_SOLVER, PERSPECTIVE_COACH})) {
			setWidget(row++, 0, new HTML(HTML_MENU_DELIMITER));			
		}
		// GROWs
		setWidget(row++, 0, newGrowButton); // > New ... > Goals/SWOT
		setWidget(row++, 0, growsButton);
		setWidget(row++, 0, actionsButton);
		setWidget(row++, 0, lessonsButton);

		// SWOTs
		// ...

		// TODO setWidget(row++, 0, blueLifeButton);
		
		// Connections
		setWidget(row++, 0, new HTML(HTML_MENU_DELIMITER));		
		setWidget(row++, 0, newConnectionButton);

		//setWidget(row++, 0, newsFeedButton);
		setWidget(row++, 0, profileButton);
		setWidget(row++, 0, connectionsButton);
		setWidget(row++, 0, sharedGoalsButton);

		//			setWidget(row++, 0, new HTML(htmlHr));		
		//			setWidget(row++, 0, inviteFriendHtml);
		//			setWidget(row++, 0, addTestimonialHtml);
		// TODO advertise
		// TODO coach directory

		//setWidget(row++, 0, new HTML(HTML_MENU_SECTION_PREFIX+"RECENT"+HTML_MENU_SECTION_POSTFIX));
		setWidget(row++, 0, new HTML(HTML_MENU_DELIMITER));			
		setWidget(row++, 0, recentPanel);
	}
	
	private String getConnectionsButtonText(int connectionRequests) {
		String text=i18n.connections() +
			(connectionRequests>0?" <span class='mf-leftMenuPendingConnections'>"+connectionRequests+"</span>":"");
		return text;
	}

	private void switchOfAllButtons() {
		dashboardButton.setStyleName("mf-menuButtonOff");
		lifeDesignerButton.setStyleName("mf-menuButtonOff");
		organizerButton.setStyleName("mf-menuButtonOff");
		blueLifeButton.setStyleName("mf-menuButtonOff");
		sharedGoalsButton.setStyleName("mf-menuButtonOff");
		growsButton.setStyleName("mf-menuButtonOff");
		lessonsButton.setStyleName("mf-menuButtonOff");
		actionsButton.setStyleName("mf-menuButtonOff");
		cheatSheetButton.setStyleName("mf-menuButtonOff"); // mf-menuButtonOffCheatSheet
		connectionsButton.setStyleName("mf-menuButtonOff");		
		profileButton.setStyleName("mf-menuButtonOff");	
	}
	
	public void showDashboard() {
		ria.showDashboard();
		switchOfAllButtons();
		dashboardButton.setStyleName("mf-menuButtonOn");
	}
	public void showMyLife() {
		ctx.getLifeDesignerPanel().refresh();
		
		ria.showMyLife();
		switchOfAllButtons();
		lifeDesignerButton.setStyleName("mf-menuButtonOn");
	}
	public void showOrganizer() {
		ctx.getStatusLine().hideStatus();
		ria.showOrganizer();
		switchOfAllButtons();
		organizerButton.setStyleName("mf-menuButtonOn");
	}
	public void showBlueLife() {
		ria.showBlueLife();
		switchOfAllButtons();
		blueLifeButton.setStyleName("mf-menuButtonOn");
	}
	public void showGrowsTable() {
		ria.showGrowsTable();
		switchOfAllButtons();
		growsButton.setStyleName("mf-menuButtonOn");
	}
	public void showActionsTable() {
		ria.showActionsTable();
		switchOfAllButtons();
		actionsButton.setStyleName("mf-menuButtonOn");
	}
	public void showLessonsLearnedTable() {
		ria.showLessonsLearnedTable();
		switchOfAllButtons();
		lessonsButton.setStyleName("mf-menuButtonOn");
	}
	public void showConnections() {
		ria.showConnections(ConnectionsPanelMode.CONNECTIONS);
		switchOfAllButtons();
		connectionsButton.setStyleName("mf-menuButtonOn");
	}
	public void showProfile() {
		ctx.getUserProfilePanel().refreshProfile(ctx.getState().getCurrentUser().getUserId());
		ria.showUserProfile();
		switchOfAllButtons();
		profileButton.setStyleName("mf-menuButtonOn");
	}
	public void showNewConnection() {
		ria.showConnections(ConnectionsPanelMode.NEW_CONNECTION);
		switchOfAllButtons();
		connectionsButton.setStyleName("mf-menuButtonOn");
	}
	public void showCheatSheet() {
		ria.showCheatSheet();
		switchOfAllButtons();
		cheatSheetButton.setStyleName("mf-menuButtonOn");
	}
	public void showSharedGoals() {
		ria.showSharedGoals();
		switchOfAllButtons();
		sharedGoalsButton.setStyleName("mf-menuButtonOn");
	}

	/*
	 * GROW management
	 */
	
	public Button getNewGrowButton() {
		return newGrowButton;
	}
		
	public void newGrowButtonToNormalStyle() {
		newGrowButton.setStyleName("mf-button");
		newGrowButton.addStyleName("mf-newGoalButton");		
	}
	
	public void createNewGrow(final String growTitle) {
		service.newGrow(new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				ria.handleServiceError(caught);
			}
			public void onSuccess(String result) {
				if(result!=null && result.startsWith(UserLimitsBean.LIMIT_EXCEEDED)) {
					statusLine.showError(
							i18n.youExceededGoalsLimit()+
							" - "+
							result.substring(UserLimitsBean.LIMIT_EXCEEDED.length())+
							"!");
				} else {
					GWT.log("RIA - new grow succesfuly created! "+result);
					growPanel.onNewGrow(result);
					
					if(growTitle!=null) {
						growPanel.setGrowTitle(growTitle);
						growPanel.save(true);						
					}
					
					ria.showGrowTabs();
				    newGrowButton.setStyleName("mf-button");
					newGrowButton.addStyleName("mf-newGoalButton");
					statusLine.hideStatus();
				}
			}
		});
	}


	public void setConnectionRequests(int connectionRequests) {
		connectionsButton.setHTML(getConnectionsButtonText(connectionRequests)); // TODO
	}

	// TODO optimize this (perhaps one reinitialize doing everything is OK
	public void setGrowsCount(int count) {
		reinitialize();

		boolean visibility=true;
		if(count==0) {
			visibility=false;
		}
		dashboardButton.setVisible(visibility);
		organizerButton.setVisible(visibility);
		growsDelimiter.setVisible(visibility);
		growsButton.setVisible(visibility);
		actionsButton.setVisible(visibility);
		lessonsButton.setVisible(visibility);
		
		growsButton.setText(ctx.getI18n().goals()+" ("+count+")");		
	}
	
	public RecentPanel getRecentPanel() {
		return recentPanel;
	}
}
