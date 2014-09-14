package com.mindforger.coachingnotebook.server.admin;

public class AdminConsoleStatus {
	public static final String COLOR_RED="#B02B2C";
	public static final String COLOR_WHITE="#FFFFFF";
	public static final String COLOR_BLACK="#000000";
	public static final String COLOR_YELLOW="#FFFF88";
	
	public static boolean show=false;
	public static String message="";
	public static String foregroundColor="";
	public static String backgroundColor="";
	
	public static void showInfo(String newMessage) {
		show=true;
		message=newMessage;
		foregroundColor=COLOR_BLACK;
		backgroundColor=COLOR_YELLOW;
	}

	public static void showError(String newMessage) {
		show=true;
		message=newMessage;
		foregroundColor=COLOR_WHITE;
		backgroundColor=COLOR_RED;
	}
	
	public static void hide() {
		show=false;
	}
}
