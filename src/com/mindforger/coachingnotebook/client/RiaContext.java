package com.mindforger.coachingnotebook.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.mindforger.coachingnotebook.client.ui.ActionsTable;
import com.mindforger.coachingnotebook.client.ui.BlueLifePanel;
import com.mindforger.coachingnotebook.client.ui.CheatSheetPanel;
import com.mindforger.coachingnotebook.client.ui.DashboardPanel;
import com.mindforger.coachingnotebook.client.ui.GrowPanel;
import com.mindforger.coachingnotebook.client.ui.GrowsTable;
import com.mindforger.coachingnotebook.client.ui.ImportanceUrgencyChart;
import com.mindforger.coachingnotebook.client.ui.LeftMenubar;
import com.mindforger.coachingnotebook.client.ui.LessonsLearnedTable;
import com.mindforger.coachingnotebook.client.ui.LifeDesignerPanel;
import com.mindforger.coachingnotebook.client.ui.PageTitlePanel;
import com.mindforger.coachingnotebook.client.ui.RightCornerPanel;
import com.mindforger.coachingnotebook.client.ui.SearchPanel;
import com.mindforger.coachingnotebook.client.ui.SearchResultsPanel;
import com.mindforger.coachingnotebook.client.ui.SharedGrowsTable;
import com.mindforger.coachingnotebook.client.ui.StatusLine;
import com.mindforger.coachingnotebook.client.ui.SwotForGrowPanel;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionsPanel;
import com.mindforger.coachingnotebook.client.ui.social.UserProfilePanel;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.verifiers.FieldVerifier;

/**
 * This is an analogy of Spring Application Context. It gives overview of the most
 * important beans, simplifies beans and avoid singletons across the source code:
 * <ul>
 *   <li>get*() methods are used to get SINGLETON objects</li>
 *   <li>create*() methods are used to get a new instance of the initialized object - FACTORY</li>
 *   <li>All beans are instantiated in the ctx's constructor using
 *       either default constructor or the constructor that takes ctx as parameter</li>
 *   <li>Ctx does LAZY initialization of beans in ctx.get*() methods.</li>
 * </ul>
 */
public class RiaContext implements MindForgerConstants {

	// RIA
	private Ria ria;
	
	// i18n and l10n
	private RiaMessages i18n;
	
	// server
	private MindForgerServiceAsync service;
	private RiaCache cache;
	
	// validation
	private FieldVerifier fieldVerifier;

	// UI components
	private PageTitlePanel pageTitle;
	private UserProfilePanel userProfilePanel;
	private LeftMenubar leftMenubar;
	private StatusLine statusLine;
	private LifeDesignerPanel lifeDesignerPanel;
	private GrowPanel growPanel;
	private GrowsTable growsTable;
	private SharedGrowsTable sharedGrowsTable;
	private ActionsTable actionsTable;
	private LessonsLearnedTable lessonsLearnedTable;
	private DashboardPanel dashboard;
	private ImportanceUrgencyChart importanceUrgencyChart;
	private CheatSheetPanel cheatsheet;
	private ConnectionsPanel connectionsPanel;
	private RightCornerPanel rightCornerPanel;
	private SwotForGrowPanel swotForGrowPanel;
	private BlueLifePanel blueLifePanel;
	private SearchPanel searchPanel;
	private SearchResultsPanel searchResultsPanel;
	
	// data
	private RiaState state;
	
	private Set<Object> initialized=new HashSet<Object>();
	
	public RiaContext(Ria ria) {
		this.ria=ria;
		
		i18n=GWT.create(RiaMessages.class);
		
		service=new MindForgerServiceAsyncCached(cache);
		cache=new RiaCache();
		
		fieldVerifier=new FieldVerifier();
		
		// UI
		statusLine=new StatusLine(this);
		pageTitle=new PageTitlePanel();		
		userProfilePanel=new UserProfilePanel(this);
		connectionsPanel=new ConnectionsPanel(this);
		blueLifePanel=new BlueLifePanel();
		searchPanel=new SearchPanel(this);
		searchResultsPanel=new SearchResultsPanel(this);
		rightCornerPanel=new RightCornerPanel(this);
		leftMenubar=new LeftMenubar(this);
		growPanel=new GrowPanel(this);
		growsTable=new GrowsTable(this);
		sharedGrowsTable=new SharedGrowsTable(this);
		importanceUrgencyChart=new ImportanceUrgencyChart(this);
		dashboard=new DashboardPanel(this);
		lifeDesignerPanel=new LifeDesignerPanel(this);
		actionsTable=new ActionsTable(this);
		lessonsLearnedTable=new LessonsLearnedTable(this);
		cheatsheet=new CheatSheetPanel(this);
		
		state=new RiaState();
	}

	public RiaMessages getI18n() {
		return i18n;
	}

	public MindForgerServiceAsync getService() {
		return service;
	}

	public RiaCache getCache() {
		return cache;
	}

	public FieldVerifier getFieldVerifier() {
		return fieldVerifier;
	}

	public PageTitlePanel getPageTitle() {
		return pageTitle;
	}

	public UserProfilePanel getUserProfilePanel() {
		if(!initialized.contains(userProfilePanel)) {
			initialized.add(userProfilePanel);
			userProfilePanel.init();
		}
		return userProfilePanel;
	}

	public LeftMenubar getLeftMenubar() {
		if(!initialized.contains(leftMenubar)) {
			initialized.add(leftMenubar);
			leftMenubar.init();
		}
		return leftMenubar;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public LifeDesignerPanel getLifeDesignerPanel() {
		return lifeDesignerPanel;
	}

	public GrowPanel getGrowPanel() {
		if(!initialized.contains(growPanel)) {
			initialized.add(growPanel);
			growPanel.init();
			// TODO register refresh hooks - rethink to make refresh callback and registrations more systematic
			growPanel.setSwotChartHook(swotForGrowPanel);			
		}
		return growPanel;
	}

	public GrowsTable getGrowsTable() {
		if(!initialized.contains(growsTable)) {
			initialized.add(growsTable);
			growsTable.init();
		}
		return growsTable;
	}

	public SharedGrowsTable getSharedGrowsTable() {
		if(!initialized.contains(sharedGrowsTable)) {
			initialized.add(sharedGrowsTable);
			sharedGrowsTable.init();
		}
		return sharedGrowsTable;
	}

	public ActionsTable getActionsTable() {
		return actionsTable;
	}

	public LessonsLearnedTable getLessonsLearnedTable() {
		return lessonsLearnedTable;
	}

	public DashboardPanel getDashboard() {
		if(!initialized.contains(dashboard)) {
			initialized.add(dashboard);
			dashboard.init();
		}
		return dashboard;
	}

	public ImportanceUrgencyChart getImportanceUrgencyChart() {
		if(!initialized.contains(importanceUrgencyChart)) {
			initialized.add(importanceUrgencyChart);
			importanceUrgencyChart.init();
		}
		return importanceUrgencyChart;
	}

	public CheatSheetPanel getCheatSheet() {
		if(!initialized.contains(cheatsheet)) {
			initialized.add(cheatsheet);
			cheatsheet.init();
		}
		return cheatsheet;
	}

	public ConnectionsPanel getConnectionsPanel() {
		if(!initialized.contains(connectionsPanel)) {
			initialized.add(connectionsPanel);
			connectionsPanel.init();
			connectionsPanel.addConnectionsRequestsListener(leftMenubar);
		}
		return connectionsPanel;
	}

	public RightCornerPanel getRightCornerPanel() {
		if(!initialized.contains(rightCornerPanel)) {
			initialized.add(rightCornerPanel);
			rightCornerPanel.init();
		}
		return rightCornerPanel;
	}

	public BlueLifePanel getBlueLifePanel() {
		if(!initialized.contains(blueLifePanel)) {
			initialized.add(blueLifePanel);
			blueLifePanel.init();
		}
		return blueLifePanel;
	}
	
	public SearchPanel getSearchPanel() {
		return searchPanel;
	}

	public RiaState getState() {
		return state;
	}	
	
	public Ria getRia() {
		return ria;
	}

	public void setSwotForGrowPanel(SwotForGrowPanel swotForGrowPanel) {
		this.swotForGrowPanel=swotForGrowPanel;
	}

	public SearchResultsPanel getSearchResultsPanel() {
		return searchResultsPanel;
	}
}
