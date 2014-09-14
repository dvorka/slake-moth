package com.mindforger.coachingnotebook.client.ui.security;

public class MindForgerSecurityException extends RuntimeException {
	
	public MindForgerSecurityException() {
		super();
	}

	public MindForgerSecurityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MindForgerSecurityException(String message) {
		super(message);
	}

	public MindForgerSecurityException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = -6684966190880885799L;
}
