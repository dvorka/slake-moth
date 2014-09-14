package com.mindforger.coachingnotebook.shared;


public interface MindForgerConstants {

	// URLs
	String MIND_FORGER_BASE_URL = "http://web.mindforger.com";
	String GRAVATAR_BASE_URL = "http://www.gravatar.com/avatar/";
	String GRAVATAR_PROXY_BASE_URL = "/rest/proxy/gravatar/";
	String GOOGLE_CHARTS_BASE_URL = "http://chart.apis.google.com/chart";

	// paths
	String MIND_FORGER_OPENID_LOGIN_PAGE_PATH = "/login-openid";
	
	// CSS styles
	String CSS_BUTTON_SMALL="mf-buttonSmall";
	String CSS_HELP_STYLE="mf-statusHelp";
	String CSS_INFO_STYLE="mf-statusInfo";
	String CSS_PROGRESS_STYLE="mf-statusProgress";
	String CSS_ERROR_STYLE="mf-statusError";
	
	// HTML containers
	String CONTAINER_CONNECTIONS= "friendsContainer";
	String CONTAINER_SEARCH = "searchContainer";
	String CONTAINER_PAGE_TITLE= "pageTitleContainer";
	String CONTAINER_RIGHT_CORNER= "rightCornerContainer";
	String CONTAINER_LEFT_MENUBAR= "leftMenubarContainer";
	String CONTAINER_TAGS= "tagsContainer";
	String CONTAINER_STATUS_LINE = "errorLabelContainer";
	String CONTAINER_GROWS_TABLE = "goalsTableContainer";
	String CONTAINER_SHARED_GROWS_TABLE = "sharedGoalsTableContainer";
	String CONTAINER_ACTIONS_TABLE = "actionsTableContainer";
	String CONTAINER_LESSONS_LEARNED_TABLE = "lessonsLearnedTableContainer";
	String CONTAINER_GROWS_TABS = "growTabsContainer";
	String CONTAINER_IMPORTANCE_URGENCY_CHART = "importanceUrgencyChartContainer";
	String CONTAINER_DASHBOARD= "dashboardContainer";
	String CONTAINER_MY_LIFE= "myLifeContainer";
	String CONTAINER_CHEATSHEET= "cheatsheetContainer";
	String CONTAINER_USER_PROFILE= "userProfileContainer";
	String CONTAINER_BLUE_LIFE= "blueLifeContainer";
	String CONTAINER_LOADING="mfLoadingStatusId";
	String CONTAINER_LOADING_PANEL="mfLoadingPanelId";
	String CONTAINER_TOP_HR="mfTopHrId";
	String CONTAINER_LOGO="mfLogoTdId";
	String CONTAINER_FOOTNOTE="mfFootnoteId";
	String CONTAINER_SEARCH_RESULTS= "searchResultsContainer";
		
	String PERSPECTIVE_LIFE_DESIGNER="LifeDesigner";
	String PERSPECTIVE_PROBLEM_SOLVER="ProblemSolver";
	String PERSPECTIVE_COACH="Coach";
	String PERSPECTIVE_COACHEE="Coachee";
	String PERSPECTIVE_HR="HR";
	String PERSPECTIVE_EMPLOYEE="Employee";
	String PERSPECTIVE_MIND_FORGER="MindForger";
	
	String[] ROOT_PANELS={
			CONTAINER_CONNECTIONS,
			CONTAINER_SEARCH,
			CONTAINER_PAGE_TITLE,
			CONTAINER_RIGHT_CORNER,
			CONTAINER_LEFT_MENUBAR,
			CONTAINER_TAGS,
			CONTAINER_STATUS_LINE,
			CONTAINER_GROWS_TABLE,
			CONTAINER_SHARED_GROWS_TABLE,
			CONTAINER_ACTIONS_TABLE,
			CONTAINER_LESSONS_LEARNED_TABLE,
			CONTAINER_GROWS_TABS,
			CONTAINER_IMPORTANCE_URGENCY_CHART,
			CONTAINER_DASHBOARD,
			CONTAINER_MY_LIFE,
			CONTAINER_CHEATSHEET,
			CONTAINER_USER_PROFILE,
			CONTAINER_BLUE_LIFE,
			CONTAINER_LOADING
	};
}
