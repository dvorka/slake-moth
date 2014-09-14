package com.mindforger.coachingnotebook.client.ui;

import java.util.Arrays;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.QuestionsStream.QuestionMoveCommnad;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class QuestionEditPanel extends VerticalPanel {
	
	public String[] questionsSet;

	private TextArea answerTextArea;
    private final SuggestBox questionTextBox;
    private final String mode;
	private ViewQuestionPanel viewQuestionPanel;
	private QuestionEditPanel editQuestionPanel;
    private final ListBox questionLabel;
    private final Button doneButton;
    private DateBox deadlineDataBox;
	private final TextBox progress;
	private Label answerLabel;
	
	private int keyUpSuggestBugsCount;

	private String questionId;
	private int questionOrder;

	private RiaMessages i18n;
	
	public QuestionEditPanel(
			String questionId,
			int questionOrder,
			String[] labels, 
			String[] questionsSet, 
			String mode, 
			final QuestionAnswerPanel questionAnswerPanel,
			final RiaContext ctx) {
		i18n = ctx.getI18n();
		
		setStyleName("mf-questionAnswer");
		
		this.questionId=questionId;
		this.questionOrder=questionOrder;
		this.editQuestionPanel=this;
		this.questionsSet=questionsSet;
		this.mode=mode;
		
		keyUpSuggestBugsCount=0;
		
		questionLabel = new ListBox(false);
	    for (int i = 0; i < labels.length; i++) {
			questionLabel.addItem(labels[i]);
		}
	    questionLabel.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(!ctx.getI18n().question().equals(questionLabel.getItemText(questionLabel.getSelectedIndex()))) {
					setAnswerLabel(ctx.getI18n().description());
				}
			}
		});
	    
	    MultiWordSuggestOracle oracle=new MultiWordSuggestOracle();
	    oracle.addAll(Arrays.asList(questionsSet));
	    questionTextBox=new SuggestBox(oracle);
		questionTextBox.setTitle(ctx.getI18n().hitEnterForViewMode());
	    questionTextBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
	                if (++keyUpSuggestBugsCount%2== 0){
						// avoid double save
	                    return;
	                } else {
						if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
			                 finishEditationAndSave(questionAnswerPanel);
						}	                	
	                }
			}
		});
	    
	    final ListBox questionSuggestions=new ListBox(false);
	    questionSuggestions.setStyleName("mf-questionSuggestions");
	    questionSuggestions.addItem("...");			
	    for (int i = 0; i < questionsSet.length; i++) {	    	
		    questionSuggestions.addItem(questionsSet[i]);			
		}
	    questionSuggestions.setVisible(false);
	    final Button suggestButton = new Button(ctx.getI18n().suggest());
	    suggestButton.setTitle(ctx.getI18n().suggestQuestions());
	    suggestButton.setStyleName("mf-helpGuideButton");
	    questionSuggestions.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String q = questionSuggestions.getItemText(questionSuggestions.getSelectedIndex());
				questionTextBox.setText(q);
				questionTextBox.setVisible(true);
			    questionSuggestions.setVisible(false);	
			    suggestButton.setVisible(true);
			    suggestButton.setStyleName("mf-button");
			    doneButton.setStyleName("mf-helpGuideButton");
			}
		});
	    suggestButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				questionTextBox.setVisible(false);
			    questionSuggestions.setVisible(true);
			    suggestButton.setVisible(false);
			}
		});
	    
	    HorizontalPanel questionPanel=new HorizontalPanel();
	    questionPanel.setStyleName("mf-questionPanel");

	    VerticalPanel editableList=new VerticalPanel();
	    editableList.setStyleName("mf-questionEditableList");
	    	    
	    HorizontalPanel questionTextPanel=new HorizontalPanel();
	    questionTextPanel.setStyleName("mf-questionTextPanel");
		questionTextPanel.add(questionLabel);
		editableList.add(questionTextBox);
	    editableList.add(questionSuggestions);
	    questionTextPanel.add(editableList);
		questionTextPanel.add(suggestButton);
	    
	    HorizontalPanel questionButtonsPanel=new HorizontalPanel();
	    questionButtonsPanel.setStyleName("mf-questionButtonsPanel");
	    questionButtonsPanel.setHorizontalAlignment(ALIGN_RIGHT);
	    
		doneButton = new Button(ctx.getI18n().ok());
	    doneButton.setTitle(ctx.getI18n().finishEditation());
	    doneButton.setStyleName("mf-button");
	    doneButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				finishEditationAndSave(questionAnswerPanel);
			    doneButton.setStyleName("mf-button");
			}	    	
	    });
		questionButtonsPanel.add(doneButton);
	    Button upButton = new Button("", new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleMoveUp(questionAnswerPanel, ctx);
			}	    	
	    });
	    upButton.setTitle(ctx.getI18n().moveItUp());
	    upButton.setStyleName("mf-buttonUp");
		questionButtonsPanel.add(upButton);
	    Button downButton = new Button("", new ClickHandler() { // &#8681;
			public void onClick(ClickEvent event) {
				handleMoveDown(questionAnswerPanel, ctx);
			}	    	
	    });
	    downButton.setTitle(ctx.getI18n().moveItDown());
	    downButton.setStyleName("mf-buttonDown");
		questionButtonsPanel.add(downButton);
	    Button deleteButton = new Button(ctx.getI18n().delete());
	    deleteButton.setStyleName("mf-buttonLooser");
	    deleteButton.setTitle(ctx.getI18n().deleteIt());
		questionButtonsPanel.add(deleteButton);
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				questionAnswerPanel.selfDestroy();
				questionAnswerPanel.save(true);
			}			
		});
	    
		answerLabel = new Label(ctx.getI18n().answer()+":");
		answerLabel.setStyleName("mf-answerLabel");
		answerTextArea = new TextArea();
		answerTextArea.setStyleName("mf-answerTextArea");
		answerTextArea.setTitle(ctx.getI18n().hitCtrlEnterToFinishEdit());
		answerTextArea.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER && event.isControlKeyDown()) {
					finishEditationAndSave(questionAnswerPanel);
				} else {
					if(event.isShiftKeyDown() && event.isControlKeyDown()) {
						if(event.getNativeKeyCode()==KeyCodes.KEY_UP) {
							handleMoveUp(questionAnswerPanel, ctx);
							return;
						} 
						if(event.getNativeKeyCode()==KeyCodes.KEY_DOWN) {
							handleMoveDown(questionAnswerPanel, ctx);
							return;
						} 
					}
				}
			}
		});

		HorizontalPanel deadlineAndProgressPanel=new HorizontalPanel();
		deadlineAndProgressPanel.setStyleName("mf-deadlineAndProgressPanel");
		Label deadlineLabel = new Label(i18n.deadline()+": ");
		deadlineLabel.setStyleName("mf-deadlineLabel");
		deadlineAndProgressPanel.add(deadlineLabel);
	    DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		deadlineDataBox = new DateBox();
	    deadlineDataBox.setFormat(new DateBox.DefaultFormat(dateFormat));
	    deadlineAndProgressPanel.add(deadlineDataBox);
		Label progressLabel = new Label(i18n.progress()+": ");
		progressLabel.setStyleName("mf-progressLabel");
		deadlineAndProgressPanel.add(progressLabel);
		progress = new TextBox();
		progress.setTitle(ctx.getI18n().hitEnterForViewMode());
		progress.setStyleName("mf-growProgress");
		progress.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				ctx.getStatusLine().hideStatus();
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
					finishEditationAndSave(questionAnswerPanel);
				} else {
					String text=progress.getText();
					int parseInt=0;
					if(text!=null && !"".equals(text)) {
						try {
							parseInt = Integer.parseInt(text);
							if(parseInt < 0  || parseInt > 100) {
								ctx.getStatusLine().showError(ctx.getI18n().errorProgressBetweenZeroAndHundred());
								progress.setText("0");
								return;
							}
						} catch(Exception e) {
							ctx.getStatusLine().showError(ctx.getI18n().errorProgressBetweenZeroAndHundred());
							progress.setText("0");
							return;
						}
					}					
				}
			}
		});
		deadlineAndProgressPanel.add(progress);
		Label progressPercent = new Label("%");
		progressPercent.setStyleName("mf-progressPercentLabel");
		deadlineAndProgressPanel.add(progressPercent);
		
		questionPanel.add(questionTextPanel);
		questionPanel.add(questionButtonsPanel);
		
		add(questionPanel);
		add(answerLabel);
		add(answerTextArea);
		
		// TODO i18n vs. drop down IDs
		if("Action".equals(questionLabel.getValue(questionLabel.getSelectedIndex())) || 
				"Task".equals(questionLabel.getValue(questionLabel.getSelectedIndex()))) {
			add(deadlineAndProgressPanel);
		}
	}

	private void setAnswerLabel(String label) {
		answerLabel.setText(label+":");
	}
	
	public void setViewQuestionPanel(ViewQuestionPanel viewQuestionPanel) {
		this.viewQuestionPanel=viewQuestionPanel;
	}
	
	public ViewQuestionPanel getViewQuestionPanel() {
		return viewQuestionPanel;
	}
	
	public String getQuestionTitle() {
		return questionTextBox.getText();		
	}
	
	public QuestionAnswerBean fromRia() {
		QuestionAnswerBean result=new QuestionAnswerBean(mode);
		result.setKey(questionId);
		result.setQuestion(questionTextBox.getText());
		result.setAnswer(answerTextArea.getText());
		result.setQuestionLabel(questionLabel.getValue(questionLabel.getSelectedIndex()));
		result.setDeadline(deadlineDataBox.getValue());
		result.setProgress((progress.getText()!=null&&!"".equals(progress.getText())?Integer.valueOf(progress.getText()):0));
		result.setOrder(questionOrder);
		return result;
	}

	public void toRia(QuestionAnswerBean questionBean) {
		questionId=questionBean.getKey();
		questionOrder=questionBean.getOrder();
		questionTextBox.setText(questionBean.getQuestion());
		answerTextArea.setText(questionBean.getAnswer());
		questionLabel.setSelectedIndex(getIndexForValue(questionLabel, questionBean.getQuestionLabel()));
		progress.setText(""+questionBean.getProgress());
		deadlineDataBox.setValue(questionBean.getDeadline());
	}
	
	private void finishEditationAndSave(final QuestionAnswerPanel questionAnswerPanel) {
		viewQuestionPanel.toRia(
				questionLabel.getValue(questionLabel.getSelectedIndex()), 
				questionTextBox.getText(), 
				answerTextArea.getText(),
				deadlineDataBox.getValue(),
				progress.getText());
		viewQuestionPanel.setVisible(true);
		editQuestionPanel.setVisible(false);
		
		questionAnswerPanel.save(true);
	}

	public int getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(int order) {
		this.questionOrder=order;
	}

	public String getQuestionId() {
		return questionId;
	}

	private static int getIndexForValue(ListBox listBox, String value) {
		int result=0;
		if(listBox!=null && listBox.getItemCount()!=0) {
			for (int i = 0; i < listBox.getItemCount(); i++) {
				if(value.equals(listBox.getItemText(i))) {
					result=i;
					break;
				}
			}
		}
		return result;
	}

	private void handleQuestionMove(final RiaContext ria, QuestionMoveCommnad move) {
		if(move.doSwitch) {
			ria.getStatusLine().showProgress(i18n.savingNewQuestionsOrder());
			ria.getService().switchQuestionAnswers(move.questionIdA, move.questionIdB, new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					ria.getStatusLine().hideStatus();
				}
				public void onFailure(Throwable caught) {
					ria.getRia().handleServiceError(caught);
				}
			});
		}
		if(answerTextArea.isVisible()) {
			answerTextArea.setFocus(true);
		}
	}

	private void handleMoveUp(final QuestionAnswerPanel questionAnswerPanel, final RiaContext ria) {
		QuestionMoveCommnad move = questionAnswerPanel.moveUp();
		handleQuestionMove(ria, move);
	}

	private void handleMoveDown(final QuestionAnswerPanel questionAnswerPanel, final RiaContext ria) {
		QuestionMoveCommnad move = questionAnswerPanel.moveDown();
		handleQuestionMove(ria, move);
	}
}
