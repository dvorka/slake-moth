package com.mindforger.coachingnotebook.client.ui.checklist;

/**
 * Interface used to subscribe for the wizard end - user clicks cancel or finish buttons.
 */
public interface WizardFinishListener {

	void wizardFinishedWithOk();
	void wizardFinishedWithCancel();
}
