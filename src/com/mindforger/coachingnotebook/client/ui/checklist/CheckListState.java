package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class CheckListState implements CheckListConstants {
	
	private CheckListItems itemsRepository;
	private String mode;
	
	private int completion;
	private List<CheckListItem> items;
		
	public CheckListState(CheckListItems itemsRepository, String mode) {
		this.itemsRepository=itemsRepository;
		this.mode=mode;
		
		items=new ArrayList<CheckListItem>();
		completion=0;
	}
	
	public CheckListItem[] getItems() {
		return items.toArray(new CheckListItem[items.size()]);
	}

	public void setValuesAndEvaluate(Map<Integer, CheckListAnswerBean> answersMap) {
		items=itemsRepository.getItems(mode);
		CheckListStatusEvaluator e=new CheckListStatusEvaluator(answersMap);

		float value;

		if(QuestionAnswerBean.G_PART.equals(mode)) {
			value = 
				e.p(CQ_G_S1)/2.0f +
				e.p(CQ_G_S2)/2.0f;
			setStatus(CI_G_SPEFIFIC, value);
			
			value = 
				e.p(CQ_G_M1)/2.0f +
				e.p(CQ_G_M2)/2.0f;
			setStatus(CI_G_MEASURABLE, value);
			
			value = 
				e.p(CQ_G_A1)/3.0f +
				e.p(CQ_G_A2)/3.0f +
				e.p(CQ_G_A3)/3.0f;
			setStatus(CI_G_ATTAINABLE, value);
			
			value = 
				e.p(CQ_G_R1)/2.0f +
				e.p(CQ_G_R2)/2.0f;
			setStatus(CI_G_REALISTIC, value);
			
			value = 
				e.p(CQ_G_T1);
			setStatus(CI_G_TIMEABLE, value);
			
			value = 
				e.p(CQ_G_E1)/2.0f +
				e.p(CQ_G_E2)/2.0f;
			setStatus(CI_G_EXCITING, value);
			
			value = 
				e.p(CQ_G_RR1);
			setStatus(CI_G_RESOURCED, value);
			
			// evaluate I_G_SMARTER
			float p=0.0f;
			for (int i = CI_G_SPEFIFIC; i <= CI_G_RESOURCED; i++) {
				p+=(float)items.get(i).getPercent()/100.0f/7.0f;
			}
			completion = Math.round(p*100.0f);
			// set status for smarter item
			items.get(CI_G_SMARTER).setPercent(completion);
			
			return;
		}		
		
		if(QuestionAnswerBean.I_PART.equals(mode)) {
			value = 
				e.p(CQ_I_M1);
			setStatus(CI_I_MISTAKES, value);
			
			value = 
				e.p(CQ_I_L1);
			setStatus(CI_I_LESSONS, value);

			float p=0.0f;
			for (int i = CI_I_MISTAKES; i <= CI_I_LESSONS; i++) {
				p+=(float)items.get(i).getPercent()/100.0f/2.0f;
			}
			completion = Math.round(p*100.0f);
			
			return;
		}		
		
		if(WizardPanel.MODE_WHEEL_OF_LIFE.equals(mode)) {
			value = 
				e.p(CQ_WHEEL_W1);
			setStatus(CI_WHEEL_HEALTH, value);
			value = 
				e.p(CQ_WHEEL_W2);
			setStatus(CI_WHEEL_JOB, value);
			value = 
				e.p(CQ_WHEEL_W3);
			setStatus(CI_WHEEL_MONEY, value);
			value = 
				e.p(CQ_WHEEL_W4);
			setStatus(CI_WHEEL_FAMILY, value);
			value = 
				e.p(CQ_WHEEL_W5);
			setStatus(CI_WHEEL_LIVING, value);
			value = 
				e.p(CQ_WHEEL_W6);
			setStatus(CI_WHEEL_RELS, value);
			value = 
				e.p(CQ_WHEEL_W7);
			setStatus(CI_WHEEL_EDU, value);
			value = 
				e.p(CQ_WHEEL_W8);
			setStatus(CI_WHEEL_FUN, value);
			
			float p=0.0f;
			for (int i = CI_WHEEL_HEALTH; i <= CI_WHEEL_FUN; i++) {
				p+=(float)items.get(i).getPercent()/100.0f/8.0f;
			}
			completion = Math.round(p*100.0f);
			
			return;
		}
	}
	
	private void setStatus(int id, float percent) {
		int rounded = Math.round(percent*100.0f);
		items.get(id).setPercent(rounded);
	}
	
	public int evaluateCompletionStatus() {
		return completion;
	}	
}