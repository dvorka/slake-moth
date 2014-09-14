package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mindforger.coachingnotebook.client.RiaMessages;

public class CheckListItems extends HashMap<String, CheckListItem[]> implements CheckListConstants {
	
	public CheckListItems(RiaMessages i18n) {
		put(WizardPanel.MODE_G,new CheckListItem[] {
				new CheckListItem(CI_G_SMARTER, i18n.CI_G_SMARTER()),
				new CheckListItem(CI_G_SPEFIFIC, i18n.CI_G_SPEFIFIC()),
				new CheckListItem(CI_G_MEASURABLE, i18n.CI_G_MEASURABLE()),
				new CheckListItem(CI_G_ATTAINABLE, i18n.CI_G_ATTAINABLE()),
				new CheckListItem(CI_G_REALISTIC, i18n.CI_G_REALISTIC()),
				new CheckListItem(CI_G_TIMEABLE, i18n.CI_G_TIMEABLE()),
				new CheckListItem(CI_G_EXCITING, i18n.CI_G_EXCITING()),
				new CheckListItem(CI_G_RESOURCED, i18n.CI_G_RESOURCED())
		});				
		put(WizardPanel.MODE_R,new CheckListItem[] {
				new CheckListItem(CI_R_STRENGTHS, i18n.CI_R_STRENGTHS()),
				new CheckListItem(CI_R_WEAKNESSES, i18n.CI_R_WEAKNESSES()),
		});				
		put(WizardPanel.MODE_O,new CheckListItem[] {
				new CheckListItem(CI_O_OPPORTUNITIES, i18n.CI_O_OPPORTUNITIES()),
				new CheckListItem(CI_O_THREATS, i18n.CI_O_THREATS()),
		});				
		put(WizardPanel.MODE_W,new CheckListItem[] {
		});				
		put(WizardPanel.MODE_I, new CheckListItem[] {
				new CheckListItem(CI_I_MISTAKES, i18n.CI_I_MISTAKES()),
				new CheckListItem(CI_I_LESSONS, i18n.CI_I_LESSONS()),
		});				
		
		put(WizardPanel.MODE_WHEEL_OF_LIFE, new CheckListItem[] {
				new CheckListItem(CI_WHEEL_HEALTH, i18n.CI_WHEEL_HEALTH()),
				new CheckListItem(CI_WHEEL_JOB, i18n.CI_WHEEL_JOB()),
				new CheckListItem(CI_WHEEL_MONEY, i18n.CI_WHEEL_MONEY()),
				new CheckListItem(CI_WHEEL_FAMILY, i18n.CI_WHEEL_FAMILY()),
				new CheckListItem(CI_WHEEL_LIVING, i18n.CI_WHEEL_LIVING()),
				new CheckListItem(CI_WHEEL_RELS, i18n.CI_WHEEL_RELS()),
				new CheckListItem(CI_WHEEL_EDU, i18n.CI_WHEEL_EDU()),
				new CheckListItem(CI_WHEEL_FUN, i18n.CI_WHEEL_FUN()),
		});						
	}		
	
	public List<CheckListItem> getItems(String mode) {
		CheckListItem[] template=get(mode);
		if(template!=null) {
			List<CheckListItem> result=new ArrayList<CheckListItem>();
			for (int i = 0; i < template.length; i++) {
				result.add(new CheckListItem(template[i].getId(), template[i].getLabel()));
			}
			return result;
		} else {
			return null;
		}
	}
	
	private static final long serialVersionUID = -976193080070222147L;
}
