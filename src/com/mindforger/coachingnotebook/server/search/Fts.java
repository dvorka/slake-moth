package com.mindforger.coachingnotebook.server.search;

import java.util.List;
import java.util.Locale;

import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public interface Fts {
	
	public static int SEARCH_RESULTS_LIMIT=50;
	
	String DOCUMENT_GROW = "DOCUMENT_GROW";
	String DOCUMENT_QUESTION = "DOCUMENT_QUESTION";
	String DOCUMENT_ATTACHMENT = "DOCUMENT_ATTACHMENT";
	String DOCUMENT_COMMENT = "DOCUMENT_COMMENT";
		
	String INDEX_NAME_GLOBAL="globalIndex";
	String INDEX_NAME_PERSONAL_PREFIX="personalIndex";
	
	void add(GrowBean growBean, Locale locale, String userId);
	void add(List<GaeQuestionAnswerBean> questions, String growKey, Locale uk, String userId);
	
	DescriptorBean[] find(String query, String userId);
	
	void delete(MindForgerResourceType grow, String key,  String userId);
}
