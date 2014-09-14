package com.mindforger.coachingnotebook.server.search.dummy;

import java.util.List;
import java.util.Locale;

import com.mindforger.coachingnotebook.server.search.Fts;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

// TODO implement search on top of titles manually (substrings only)
public class DummyFts implements Fts {

	public DummyFts() {
	}

	@Override
	public void add(GrowBean growBean, Locale locale, String userId) {
		// TODO Auto-generated method stub
	}

	@Override
	public DescriptorBean[] find(String query, String userId) {
		DescriptorBean[] result=new DescriptorBean[1];
		result[0]=new DescriptorBean();
		result[0].setId("123");
		result[0].setName("Some result");
		result[0].setDescription("Some description");
		return result;
	}

	@Override
	public void delete(MindForgerResourceType type, String key, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(List<GaeQuestionAnswerBean> questions, String growKey, Locale uk, String userId) {
		// TODO Auto-generated method stub
	}
}
