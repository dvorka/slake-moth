package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.LifeDesignerPanel;

public class WizardQuestions extends HashMap<String, WizardQ[]> implements CheckListConstants {

	public WizardQuestions(RiaMessages i18n) {
		put(WizardPanel.MODE_G,new WizardQ[]{
				new WizardQ(CQ_G_S1,i18n.CQ_G_S1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_S2,i18n.CQ_G_S2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_M1,i18n.CQ_G_M1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_M2,i18n.CQ_G_M2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_A1,i18n.CQ_G_A1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_A2,i18n.CQ_G_A2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_A3,i18n.CQ_G_A3(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_G_A3_DESCRIPTION()),
				new WizardQ(CQ_G_R1,i18n.CQ_G_R1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_R2,i18n.CQ_G_R2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_T1,i18n.CQ_G_T1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_E1,i18n.CQ_G_E1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_E2,i18n.CQ_G_E2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_G_RR1,i18n.CQ_G_RR1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
		});
		put(WizardPanel.MODE_I,new WizardQ[]{
				new WizardQ(CQ_I_M1,i18n.CQ_I_M1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_I_M1_DESCRIPTION()),
				new WizardQ(CQ_I_L1,i18n.CQ_I_L1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
		});
		put(WizardPanel.MODE_WHEEL_OF_LIFE,new WizardQ[]{
				new WizardQ(CQ_WHEEL_W1,i18n.CQ_WHEEL_W1(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W1()),
				new WizardQ(CQ_WHEEL_W2,i18n.CQ_WHEEL_W2(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W2_DESCRIPTION()),
				new WizardQ(CQ_WHEEL_W3,i18n.CQ_WHEEL_W3(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,""),
				new WizardQ(CQ_WHEEL_W4,i18n.CQ_WHEEL_W4(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W4_DESCRIPTION()),
				new WizardQ(CQ_WHEEL_W5,i18n.CQ_WHEEL_W5(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W5_DESCRIPTION()),
				new WizardQ(CQ_WHEEL_W6,i18n.CQ_WHEEL_W6(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W6_DESCRIPTION()),
				new WizardQ(CQ_WHEEL_W7,i18n.CQ_WHEEL_W7(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W7_DESCRIPTION()),
				new WizardQ(CQ_WHEEL_W8,i18n.CQ_WHEEL_W8(),WizardQ.QUESTION_TYPE_YES_RATHER_NO,i18n.CQ_WHEEL_W8_DESCRIPTION())
		});
		
		// Health
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[0],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Career
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[1],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Finances
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[2],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Family
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[3],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Living
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[4],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Romance
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[5],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Growth
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[6],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
		// Lifestyle
		put(WizardPanel.MODE_WHEEL_OF_LIFE+LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[7],new WizardQ[]{
				new WizardQ(CQ_IMPROVE_WHEEL_1,
						i18n.CQ_IMPROVE_WHEEL_1(CQ_IMPROVE_WHEEL_VAR_AREA),
						WizardQ.QUESTION_TYPE_STRING,
						i18n.CQ_IMPROVE_WHEEL_1_DESCRIPTION(CQ_IMPROVE_WHEEL_VAR_AREA))
		});
	}
	
	public void customizeQuestions(WizardQ[] in, Map<String, String> contextualCustomization) {
		if(contextualCustomization!=null && contextualCustomization.size()>0) {
			Iterator<String> iterator = contextualCustomization.keySet().iterator();
			while (iterator.hasNext()) {
				String key= (String)iterator.next();
				for (int i = 0; i < in.length; i++) {
					in[i].setQuestion(in[i].getQuestion().replace(key, contextualCustomization.get(key)));
					in[i].setHint(in[i].getHint().replace(key, contextualCustomization.get(key)));
				}
			}
		}
	}
	
	private static final long serialVersionUID = 1841716621006898953L;
}
