package com.mindforger.coachingnotebook.client.ui.social.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;

public class QuestionCommentsPanel extends FlexTable {
	private RiaContext ctx;
	private int row;
	private List<Date> sortedTimestamps;
	private String questionId;
	
	public QuestionCommentsPanel(final String questionId, final RiaContext ctx) {
		setStyleName("mf-questionCommentsPanel");
		this.ctx=ctx;
		this.questionId=questionId;
		row=0;
		setWidget(row, 0, new NewCommentPanel(questionId, this, ctx));
		row++;
		sortedTimestamps=new ArrayList<Date>();
		sortedTimestamps.add(new Date());
	}

	public void addNewComment() {
		setWidget(row, 0, new NewCommentPanel(questionId, this, ctx));
		row++;
	}
	
	public void addComment(CommentBean commentBean) {
		boolean commentAdded=false;
		for (int i = 0; i < sortedTimestamps.size(); i++) {
			if(sortedTimestamps.get(i).after(commentBean.getCreated())) {
				sortedTimestamps.add(i, commentBean.getCreated());
				int j=insertRow(i);
				row++;
				setWidget(j, 0, new ViewCommentPanel(j, this, commentBean, ctx));
				commentAdded=true;
				break;
			}
		}
		if(!commentAdded) {
			setWidget(row, 0, new ViewCommentPanel(row, this, commentBean, ctx));
			row++;
		}
		// no need to check whether it was added - new row is always youngest ;-)
	}	
}
