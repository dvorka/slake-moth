package com.mindforger.coachingnotebook.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByImportance;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByNumberOfQuestions;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByProgress;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByUrgency;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class DashboardPanel extends FlexTable {
	
	public static final int LIMIT_NAME_LENGTH=30;
	
	private int finishedGrowsCount;
	
	private List<GrowBean> byUrgency;
	private List<GrowBean> byImportance;
	private List<GrowBean> byProgress;
	private List<GrowBean> byNumberOfQuestions;
	
	private RiaContext ria;

	public DashboardPanel(RiaContext ria) {
		this.ria=ria;
	}
	
	public void init() {
		insertRow(0);
		insertRow(0);
				
		setStyleName("mf-dashboard");

		finishedGrowsCount=0;
		byUrgency=new ArrayList<GrowBean>();
		byImportance=new ArrayList<GrowBean>();
		byProgress=new ArrayList<GrowBean>();		
		byNumberOfQuestions=new ArrayList<GrowBean>();		
	}

	
	public void refreshGrows(GrowBean[] grows) {
		if(grows==null || grows.length==0) {
			setVisible(false);
			return;
		} else {
			setVisible(true);
		}
		
		finishedGrowsCount=0;
		List<GrowBean> notFinishedGrows=new ArrayList<GrowBean>();
		for (int i = 0; i < grows.length; i++) {
			if(grows[i].getProgress()!=100) {
				notFinishedGrows.add(grows[i]);
			} else {
				++finishedGrowsCount;
			}
		}
		grows=notFinishedGrows.toArray(new GrowBean[notFinishedGrows.size()]);
		
		byUrgency.clear();
		byImportance.clear();
		byProgress.clear();
		byNumberOfQuestions.clear();
		
		if(grows!=null && grows.length>0) {
			byUrgency.addAll(Arrays.asList(grows));
			Collections.sort(byUrgency, new ComparatorGrowBeanByUrgency(true));
			byImportance.addAll(Arrays.asList(grows));
			Collections.sort(byImportance, new ComparatorGrowBeanByImportance(true));
			byProgress.addAll(Arrays.asList(grows));
			Collections.sort(byProgress, new ComparatorGrowBeanByProgress(true));
			byNumberOfQuestions.addAll(Arrays.asList(grows));
			Collections.sort(byNumberOfQuestions, new ComparatorGrowBeanByNumberOfQuestions(true));
		}
		
		int row=0;
		int[] urgency=new int[6];
		int[] importance=new int[6];
		
		for (int i = 0; i < byImportance.size(); i++) {
			GrowBean growBean = byImportance.get(i);
			importance[growBean.getImportance()]++;
			urgency[growBean.getUrgency()]++;
		}
				
		int portletRows=5+1;

		row=1;
		
		FlexTable urgencyPortlet=new FlexTable();
		urgencyPortlet.setStyleName("mf-urgencyPortlet");
		setWidget(row, 2, urgencyPortlet);
		for (int i = 0; i < portletRows; i++) {
			if(byUrgency.size()>=i+1) {
				urgencyPortlet.setWidget(i, 0, new HTML(getMeasureAsHtml(byUrgency.get(i).getUrgency(), "!")));
				urgencyPortlet.setWidget(i, 1, 
						new GrowsTableToGrowButton(
								RiaUtilities.trimName(byUrgency.get(i).getName(),LIMIT_NAME_LENGTH), 
								byUrgency.get(i).getDescription(), 
								byUrgency.get(i).getKey(), 
								"mf-growsTableGoalButton",
								GrowsTableToGrowButton.FIRST_TAB,
								ria));
			} else {
				break;
			}
		}
		urgencyPortlet.insertRow(0);
		HTML html = new HTML(ria.getI18n().goalsByUrgency());
		html.setStyleName("mf-portletTitle");
		urgencyPortlet.setWidget(0, 1, html);
		
		FlexTable progressPortlet=new FlexTable();
		progressPortlet.setStyleName("mf-progressPortlet");
		setWidget(row, 1, progressPortlet);
		for (int i = 0; i < portletRows; i++) {
			if(byProgress.size()>=i+1) {
				progressPortlet.setWidget(i, 0, new HTML(byProgress.get(i).getProgress()+"%"));
				progressPortlet.setWidget(i, 1, 
						new GrowsTableToGrowButton(
								RiaUtilities.trimName(byProgress.get(i).getName(),LIMIT_NAME_LENGTH), 
								byProgress.get(i).getDescription(), 
								byProgress.get(i).getKey(), 
								"mf-growsTableGoalButton", 
								GrowsTableToGrowButton.FIRST_TAB,
								ria));
			} else {
				break;
			}
		}
		progressPortlet.insertRow(0);
		html=new HTML(ria.getI18n().goalsByProgress());
		html.setStyleName("mf-portletTitle");
		progressPortlet.setWidget(0, 1, html);
		
		FlexTable importancePortlet=new FlexTable();
		importancePortlet.setStyleName("mf-importancePortlet");
		setWidget(row, 0, importancePortlet);
		for (int i = 0; i < portletRows; i++) {
			if(byImportance.size()>=i+1) {
				importancePortlet.setWidget(i, 0, new HTML(getMeasureAsHtml(byImportance.get(i).getImportance(), "&#9733;")));
				importancePortlet.setWidget(i, 1,
						new GrowsTableToGrowButton(
								RiaUtilities.trimName(byImportance.get(i).getName(),LIMIT_NAME_LENGTH), 
								byImportance.get(i).getDescription(), 
								byImportance.get(i).getKey(), 
								"mf-growsTableGoalButton", 
								GrowsTableToGrowButton.FIRST_TAB,
								ria));
			} else {
				break;
			}
		}		
		importancePortlet.insertRow(0);
		html=new HTML(ria.getI18n().goalsByImportance());
		html.setStyleName("mf-portletTitle");
		importancePortlet.setWidget(0, 1, html);

		row=2;

		FlexTable numberOfQuestionsPortlet=new FlexTable();
		numberOfQuestionsPortlet.setStyleName("mf-numberOfQuestionsPortlet");
		//setWidget(row, 1, numberOfQuestionsPortlet);
		for (int i = 0; i < portletRows; i++) {
			if(byNumberOfQuestions.size()>=i+1) {
				numberOfQuestionsPortlet.setWidget(i, 0, new HTML(""+ComparatorGrowBeanByNumberOfQuestions.countQuestions(byNumberOfQuestions.get(i))));
				numberOfQuestionsPortlet.setWidget(i, 1,
						new GrowsTableToGrowButton(
								RiaUtilities.trimName(byNumberOfQuestions.get(i).getName(),LIMIT_NAME_LENGTH), 
								byNumberOfQuestions.get(i).getDescription(), 
								byNumberOfQuestions.get(i).getKey(), 
								"mf-growsTableGoalButton", 
								GrowsTableToGrowButton.FIRST_TAB,
								ria));
			} else {
				break;
			}
		}		
		numberOfQuestionsPortlet.insertRow(0);
		html=new HTML(ria.getI18n().goalsBySize());
		html.setStyleName("mf-portletTitle");
		numberOfQuestionsPortlet.setWidget(0, 1, html);
		
	}
	
	private static String getMeasureAsHtml(int measuredValue, String sign) {
		StringBuffer sb=new StringBuffer();
		for (int i = 1; i <= 5; i++) {
			String color="#fff";
			if("&#9733;".equals(sign)) {
				if(i<=measuredValue) {
					sb.append("<img src='./images/star-black-white.png' style='width: 0.8em;'/>");
				} else {
					sb.append("<img src='./images/star-gray-white.png' style='width: 0.8em;'/>");
				}
			} else {
				if(i<=measuredValue) {
					color="#000"; // TODO to CSS
				} else {
					color="#ccc"; // TODO to CSS
				}
				sb.append("<span style='color: ");
				sb.append(color);
				sb.append("; font-weight: bolde;'>"+sign+"&nbsp;</span>");				
			}
		}
		return sb.toString();		
	}
}
