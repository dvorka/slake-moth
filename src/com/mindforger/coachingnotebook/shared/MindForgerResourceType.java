package com.mindforger.coachingnotebook.shared;

import com.mindforger.coachingnotebook.server.search.Fts;

public enum MindForgerResourceType {

	GROW,
	QUESTION,
		
	ATTACHMENT,
	COMMENT;
	
	public static MindForgerResourceType stringToType(String s) {
		if(s!=null) {
			if(Fts.DOCUMENT_GROW.equals(s)) {
				return GROW;
			}
			if(Fts.DOCUMENT_QUESTION.equals(s)) {
				return QUESTION;
			}
			if(Fts.DOCUMENT_ATTACHMENT.equals(s)) {
				return ATTACHMENT;
			}
			if(Fts.DOCUMENT_COMMENT.equals(s)) {
				return COMMENT;
			}			
		}
		return GROW;
	}
}
