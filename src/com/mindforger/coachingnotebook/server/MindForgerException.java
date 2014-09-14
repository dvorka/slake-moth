package com.mindforger.coachingnotebook.server;

public class MindForgerException extends RuntimeException {
	
	public static final int CODE_INTERNAL_SERVER_ERROR=0;
	public static final int CODE_MIND_RAIDER_FEED_ALREADY_EXISTS=1;
	public static final int CODE_LIMIT_EXCEEDED=2;
	public static final int CODE_INVALID_FEED = 3;
	
	int code;
	
	public MindForgerException() {
		super();
	}

	public MindForgerException(int code) {
		this();
		this.code=code;
	}

	public MindForgerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MindForgerException(String message) {
		super(message);
	}

	public MindForgerException(String message, int code) {
		super(message);
		this.code=code;
	}

	public MindForgerException(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}
	
	private static final long serialVersionUID = -6684966190880885799L;
}
