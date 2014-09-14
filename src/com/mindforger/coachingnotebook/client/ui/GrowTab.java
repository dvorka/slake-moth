package com.mindforger.coachingnotebook.client.ui;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListPanel;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorQuestionAnswerBeanByOrder;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.GrowPartBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;

public class GrowTab extends VerticalPanel {

	final RiaContext ctx;
	final MindForgerServiceAsync mfService;
	final RiaMessages i18n;
	
	public final QuestionsStream questionsStream;
	final QuestionSetsBean questionSet;
	String[] questionsLabels;
	final String[] questions;
	final String mode;
	HTML goalTabDocumentation;
    Button addQuestionButton;
	private GrowTabs growTabs;
	private MagicTrianglePanel magicTriangle;
	private CheckListPanel checkListPanel;
	private ActionPlanForGrowPanel actionPlanForGrowPanel;
	private SwotForGrowPanel swotForGrowPanel;
	
	public GrowTab(
			final String mode, 
			final QuestionSetsBean questionSets,
			final GrowTabs growTabs,
			final RiaContext ctx) {
		setStyleName("mf-goalTab");

		this.ctx=ctx;
		this.mfService=ctx.getService();
		this.i18n=ctx.getI18n();
		this.questionSet=questionSets;
		this.mode=mode;
		this.growTabs=growTabs;

		questions=initializeBasedOnMode(mode, i18n);
		
		questionsStream = new QuestionsStream();
		
		HorizontalPanel headPanel=new HorizontalPanel();
		headPanel.setStyleName("mf-goalTabHead");
		
		addQuestionButton = new Button(i18n.addQuestion());
		addQuestionButton.setText(i18n.add()+" "+questionSets.getLabelsForTab(mode)[0]);
	    addQuestionButton.setStyleName("mf-addQuestionButton mf-button");
	    addQuestionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int numberOfquestions =
					growTabs.getGTab().getNumberOfQuestions()+
					growTabs.getRTab().getNumberOfQuestions()+
					growTabs.getOTab().getNumberOfQuestions()+
					growTabs.getWTab().getNumberOfQuestions()+
					growTabs.getITab().getNumberOfQuestions();
				if(numberOfquestions>=ctx.getState().getAccountLimits().getLimitQuestionsPerGrow()) {
					ctx.getStatusLine().showError(
							i18n.youExceededQuestionsPerGoalLimit()+
							" - "+
							numberOfquestions+"/"+ctx.getState().getAccountLimits().getLimitQuestionsPerGrow()+"!");
					return;
				}
								
				// create new question to get its ID & avoid the need save the whole GROW & have identifier for comments
				ctx.getStatusLine().showProgress(i18n.creatingANewQuestion());
				// determine active tab and question with lowest order
				final int headOrder=questionsStream.getHeadQuestionOrder() - 1;
				ctx.getService().newQuestionAnswer(ctx.getGrowPanel().getGrowId(), headOrder, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						ctx.getRia().handleServiceError(caught);
					}
					public void onSuccess(String questionId) {
						ctx.getStatusLine().hideStatus();
						QuestionAnswerPanel questionAnswerPanel 
							= new QuestionAnswerPanel(questionId, headOrder, questionsLabels, questions, mode, getInstance(), ctx);
						questionAnswerPanel.toEditMode();
						questionsStream.insertQuestion(questionAnswerPanel, 0);

						addQuestionButton.setStyleName("mf-button");						
					}
				});				
			}
	    });
		headPanel.add(addQuestionButton);
		
	    goalTabDocumentation.setStyleName("mf-goalTabDocumentation");
		headPanel.add(goalTabDocumentation);
		magicTriangle = new MagicTrianglePanel(goalTabDocumentation, ctx);
		if(!QuestionAnswerBean.O_PART.equals(mode)) {
			magicTriangle.setVisible(false);
		}
	    headPanel.add(magicTriangle);

	    
	    String checklistDocumentation=i18n.growChecklistDocumentation();
	    String completionStatusTitle=i18n.growCompletionStatusTitle();
	    String completionStatusSubTitle=i18n.growCompletionStatusSubTitle();
	    String checklistDisclosurePanelTitle=i18n.growChecklistDisclosurePanelTitle();
	    String wizardFirstStepDocumentation=i18n.growWizardFirstStepDocumentation();
	    String wizardLastStepDocumentation=i18n.growWizardLastStepDocumentation();
	    checkListPanel = new CheckListPanel(
	    		mode,
	    		completionStatusTitle,
	    		completionStatusSubTitle,
	    		checklistDocumentation,
	    		checklistDisclosurePanelTitle,
	    		wizardFirstStepDocumentation,
	    		wizardLastStepDocumentation,
	    		null,
	    		ctx);
	    actionPlanForGrowPanel = new ActionPlanForGrowPanel(mode, ctx);
	    
		// insert widgets
	    add(headPanel);	   
		add(checkListPanel);
		add(actionPlanForGrowPanel);
		if(QuestionAnswerBean.O_PART.equals(mode)) {
		    swotForGrowPanel = new SwotForGrowPanel(mode, ctx);
			add(swotForGrowPanel);
		}
	    add(questionsStream);
	}

    public GrowTab getInstance() {
    	return this;
    }
    
	public MagicTrianglePanel getMagicTriangle() {
		return magicTriangle;
	}

	public void setMagicTriangle(MagicTrianglePanel magicTriangle) {
		this.magicTriangle = magicTriangle;
	}

	private String[] initializeBasedOnMode(String mode, final RiaMessages i18n) {
		String[] questions;
		if(QuestionAnswerBean.G_PART.equals(mode)) {
			goalTabDocumentation = new HTML(i18n.gTabDocumentation());
			questionsLabels=questionSet.getgLabels();
			questions=questionSet.getG();
		} else {
			if(QuestionAnswerBean.R_PART.equals(mode)) {
				goalTabDocumentation = new HTML(i18n.rTabDocumentation());
				questionsLabels=questionSet.getrLabels();				
				questions=questionSet.getR();
			} else {
				if(QuestionAnswerBean.O_PART.equals(mode)) {
					goalTabDocumentation = new HTML(i18n.oTabDocumentation());									
					questionsLabels=questionSet.getoLabels();
					questions=questionSet.getO();
				} else {
					if(QuestionAnswerBean.W_PART.equals(mode)) {
						goalTabDocumentation = new HTML(i18n.wTabDocumentation());										
						questionsLabels=questionSet.getwLabels();
						questions=questionSet.getW();
					} else {
						if(QuestionAnswerBean.I_PART.equals(mode)) {
							goalTabDocumentation = new HTML(i18n.iTabDocumentation());											
							questionsLabels=questionSet.getiLabels();
							questions=questionSet.getI();
						} else {
							// unknown part
							questions=questionSet.getG();
						}
					}
				}
			}
		}
		return questions;
	}
	
	public GrowPartBean fromRia() {
		int rowCount = questionsStream.getRowCount();
		
		GrowPartBean result=new GrowPartBean();
		List<QuestionAnswerBean> questionsList=result.getQuestions();
		if(rowCount>0) {
			int column=0;
			for (int i = 0; i < rowCount; i++) {
				QuestionAnswerPanel questionAnswerPanel=(QuestionAnswerPanel)questionsStream.getWidget(i, column);
				final QuestionAnswerBean question = questionAnswerPanel.fromRia();
				questionsList.add(question);
			}
			result.setQuestions(questionsList);
		}
		
		// refresh on any need to save - action plan hook - needs to refresh whenever W tab changes
		if(QuestionAnswerBean.W_PART.equals(mode)) {
			actionPlanForGrowPanel.refresh(result.getQuestions());
		}
				
		return result;
	}

	public void toRia(GrowBean bean) {
		if(ctx.getGrowPanel().isRdWr()) {
			addQuestionButton.setVisible(true);
		} else {
			addQuestionButton.setVisible(false);
		}
		
		questionsStream.removeAllRows();
		
		final List<QuestionAnswerBean> questionInstances;
		if(QuestionAnswerBean.G_PART.equals(mode)) {
			questionInstances = bean.getG().getQuestions();			
		} else {
			if(QuestionAnswerBean.R_PART.equals(mode)) {
				questionInstances = bean.getR().getQuestions();				
			} else {
				if(QuestionAnswerBean.O_PART.equals(mode)) {
					questionInstances = bean.getO().getQuestions();					
				} else {
					if(QuestionAnswerBean.W_PART.equals(mode)) {
						questionInstances = bean.getW().getQuestions();						
					} else {
						if(QuestionAnswerBean.I_PART.equals(mode)) {
							questionInstances = bean.getI().getQuestions();													
						} else {
							// do nothing
							throw new RuntimeException("Unknown part: " + mode);
						}
					}
				}
			}
		}
		initializeBasedOnMode(mode, i18n);
		if(questionInstances!=null) {
			Map<String, QuestionAnswerPanel> map = ctx.getGrowPanel().getQuestionIdToQAPanelMap();
			Collections.sort(questionInstances, new ComparatorQuestionAnswerBeanByOrder());
			for (int i = 0; i < questionInstances.size(); i++) {
				QuestionAnswerBean questionBean = questionInstances.get(i);
				final QuestionAnswerPanel questionAnswerPanel
					= new QuestionAnswerPanel(questionBean.getKey(), questionBean.getOrder(), questionsLabels, questions, mode, getInstance(), ctx);
				questionAnswerPanel.toRia(questionBean);
				questionsStream.insertQuestion(questionAnswerPanel, i);
				// register panel in the map so that comments can pushed after they are loaded
				map.put(questionBean.getKey(), questionAnswerPanel);
			}
		}
	    addQuestionButton.setStyleName("mf-helpGuideButton");	
	    
	    checkListPanel.load(bean.getKey());
	    actionPlanForGrowPanel.toRia(bean);
	    if(swotForGrowPanel!=null) {
		    swotForGrowPanel.toRia(bean);	    	
	    }
	}

	public int getNumberOfQuestions() {
		return questionsStream.getRowCount();
	}
	
	public void save(boolean reloadAfterSave) {
		growTabs.save(reloadAfterSave);
	}
}
