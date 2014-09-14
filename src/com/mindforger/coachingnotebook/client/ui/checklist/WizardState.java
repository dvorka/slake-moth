package com.mindforger.coachingnotebook.client.ui.checklist;

import java.util.Map;

import com.mindforger.coachingnotebook.client.RiaMessages;

public class WizardState {
	
	private WizardQ[] questions;
	private int step;
	
	public WizardState(String mode, Map<String, String> contextualCustomization, RiaMessages i18n) {
		WizardQuestions wizardQuestions=new WizardQuestions(i18n);
		questions=wizardQuestions.get(mode);
		if(contextualCustomization!=null) {
			wizardQuestions.customizeQuestions(questions, contextualCustomization);
		}
		step=0;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public boolean next() {
		step++;
		if(step>questions.length-1) {
			step=questions.length-1;
			return false;
		} else {
			return true;
		}
	}
	
	public boolean last() {
		step--;
		if(step<0) {
			step=0;
			return false;
		} else {
			return true;
		}
	}
	
	public void reset() {
		step=0;
	}
	
	public WizardQ getQuestion() {
		return questions[step];
	}
}
