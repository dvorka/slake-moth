package com.mindforger.coachingnotebook.shared.security;

public interface SecuritySharing {

	public String[] SHARING_OPTIONS_LABELS={
		"Nobody",
		"Everybody",
		"All Connections",
		"Selected Connections"
	};
	String SHARING_OPTION_VALUE_SELECTED_CONNECTIONS="SelectedConnections";
	String SHARING_OPTION_VALUE_NOBODY="Nobody";
	String SHARING_OPTION_VALUE_EVERYBODY="Everybody";
	String SHARING_OPTION_VALUE_ALL_CONNECTIONS="AllConnections";
	String[] SHARING_OPTIONS_VALUES={
		SHARING_OPTION_VALUE_NOBODY,
		SHARING_OPTION_VALUE_EVERYBODY,
		SHARING_OPTION_VALUE_ALL_CONNECTIONS,
		SHARING_OPTION_VALUE_SELECTED_CONNECTIONS
	};
}
