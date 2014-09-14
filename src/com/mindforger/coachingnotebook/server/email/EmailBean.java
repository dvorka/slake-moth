package com.mindforger.coachingnotebook.server.email;

public class EmailBean {
	
	public String toEmail;
	public String toNickname; 
	public String subject;
	public String body;
	
	public EmailBean(String toEmail, String toNickname, String subject, String body) {
		super();
		this.toEmail = toEmail;
		this.toNickname = toNickname;
		this.subject = subject;
		this.body = body;
	}

	public EmailBean() {
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getToNickname() {
		return toNickname;
	}

	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
