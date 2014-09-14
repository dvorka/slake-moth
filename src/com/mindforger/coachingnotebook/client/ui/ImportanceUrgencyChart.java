package com.mindforger.coachingnotebook.client.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByImportance;
import com.mindforger.coachingnotebook.client.ui.comparators.ComparatorGrowBeanByUrgency;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class ImportanceUrgencyChart extends FlexTable {
	RiaContext ctx;

	private VerticalPanel ninu, inu, niu, iu;

	private RiaMessages i18n;

	public ImportanceUrgencyChart(RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void init() {
		this.i18n=ctx.getI18n();

		GrowBean[] result=ctx.getState().getGrowBeans(); 

		addStyleName("mf-iuChart");
		
		// row, column, widget
		setHTML(0, 0, "<span class='mf-iuChartLegendImportant' title='"+i18n.highUrgency()+"'>!&nbsp;<br/>!&nbsp;<br/>!&nbsp;</span>");
		setHTML(1, 0, "<span class='mf-iuChartLegendNotImportant' title='"+i18n.lowUrgency()+"'>!&nbsp;</span>");
		setHTML(2, 0, "");
		setHTML(2, 1, "<span class='mf-iuChartLegendNotUrgent' title='"+i18n.lowImportance()+"'><img src='./images/star-gray-white.png'/></span>");
		setHTML(2, 2, "<span class='mf-iuChartLegendUrgent' title='"+i18n.highImportance()+"'><img src='./images/star-black-white.png'/>&nbsp;<img src='./images/star-black-white.png'/>&nbsp;<img src='./images/star-black-white.png'/><span>");
		
		VerticalPanel notImportantNotUrgent=new VerticalPanel();
		HTML html = new HTML(i18n.doSometime());
		html.setStyleName("mf-iuChartSometime");
		notImportantNotUrgent.add(html);
		notImportantNotUrgent.setStyleName("mf-chartNotImportantNotUrgent");
		notImportantNotUrgent.setWidth("400px");
		notImportantNotUrgent.setHeight("300px");
		setWidget(1, 1, notImportantNotUrgent);
		notImportantNotUrgent = ninu = encapsulateInnerPanel(notImportantNotUrgent);		
		VerticalPanel importantNotUrgent=new VerticalPanel();
		html = new HTML(i18n.planDedicatedTime());
		html.setStyleName("mf-iuChartSoon");
		importantNotUrgent.add(html);
		importantNotUrgent.setStyleName("mf-chartImportantNotUrgent");
		importantNotUrgent.setWidth("400px");
		importantNotUrgent.setHeight("300px");
		setWidget(1, 2, importantNotUrgent);		
		importantNotUrgent = inu = encapsulateInnerPanel(importantNotUrgent);		
		VerticalPanel notImportantUrgent=new VerticalPanel();
		html = new HTML(i18n.doSoon());
		html.setStyleName("mf-iuChartPlan");
		notImportantUrgent.add(html);		
		notImportantUrgent.setStyleName("mf-chartNotImportantUrgent");
		notImportantUrgent.setWidth("400px");
		notImportantUrgent.setHeight("300px");
		setWidget(0, 1, notImportantUrgent);
		notImportantUrgent = niu = encapsulateInnerPanel(notImportantUrgent);		
		VerticalPanel importantUrgent=new VerticalPanel();
		html = new HTML(ctx.getI18n().doFirst());
		html.setStyleName("mf-iuChartFirst");
		importantUrgent.add(html);
		importantUrgent.setStyleName("mf-chartImportantUrgent");		
		importantUrgent.setWidth("400px");
		importantUrgent.setHeight("300px");
		setWidget(0, 2, importantUrgent);	
		importantUrgent = iu = encapsulateInnerPanel(importantUrgent);		
				
		// split GROWS to panels
		// TODO cut to a maximum number in each quadrant: 7 constant
		// TODO sort them in each quadrant
		beanToRia(result);
		
		// help
		getFlexCellFormatter().setColSpan(3, 1, 2);
		setWidget(3,1,new HTML("<div class='mf-organizerDoc'><span class='mf-hint' title='"+i18n.hint()+"'>"+i18n.hint()+"</span> "+
                "<a href='http://beanoriginal.net/sketchcast-2-using-the-eisenhower-matrix/' target='_blank'>"+i18n.eisenhowerMatrix()+"</a> - " +
                i18n.eisenhowerMatrixDescription()+
                "</div>"				
				));
	}
	
	private void beanToRia(GrowBean[] result) {
		String styleName="mf-iuChartGoalButton";
				
		List<GrowBean> iuArray=new ArrayList<GrowBean>();
		List<GrowBean> inuArray=new ArrayList<GrowBean>();
		List<GrowBean> niuArray=new ArrayList<GrowBean>();
		List<GrowBean> ninuArray=new ArrayList<GrowBean>();
		
		for (int i = 0; i < result.length; i++) {
			if(result[i].getProgress()==100) {
				continue;
			}
			if(result[i].getImportance()>2) {
				if(result[i].getUrgency()>2) {
					iuArray.add(result[i]);
				} else {
					inuArray.add(result[i]);
				}
			} else {
				if(result[i].getUrgency()>2) {
					niuArray.add(result[i]);
				} else {
					ninuArray.add(result[i]);
				}				
			}
		}
		
		Collections.sort(iuArray, new ComparatorGrowBeanByUrgency(true));
		Collections.sort(inuArray, new ComparatorGrowBeanByImportance(true));
		Collections.sort(niuArray, new ComparatorGrowBeanByUrgency(true));
		Collections.sort(ninuArray, new ComparatorGrowBeanByImportance(true));
		
		for (int i = 0; i < iuArray.size(); i++) {
			String trimmedName=RiaUtilities.trimName(iuArray.get(i).getName(), 50);
			iu.add(new GrowsTableToGrowButton(trimmedName, iuArray.get(i).getDescription(), iuArray.get(i).getKey(), styleName, GrowsTableToGrowButton.FIRST_TAB, ctx));
		}
		for (int i = 0; i < inuArray.size(); i++) {
			String trimmedName=RiaUtilities.trimName(inuArray.get(i).getName(), 50);
			inu.add(new GrowsTableToGrowButton(trimmedName, inuArray.get(i).getDescription(), inuArray.get(i).getKey(), styleName, GrowsTableToGrowButton.FIRST_TAB, ctx));
		}
		for (int i = 0; i < niuArray.size(); i++) {
			String trimmedName=RiaUtilities.trimName(niuArray.get(i).getName(), 50);
			niu.add(new GrowsTableToGrowButton(trimmedName, niuArray.get(i).getDescription(), niuArray.get(i).getKey(), styleName, GrowsTableToGrowButton.FIRST_TAB, ctx));
		}
		for (int i = 0; i < ninuArray.size(); i++) {
			String trimmedName=RiaUtilities.trimName(ninuArray.get(i).getName(), 50);
			ninu.add(new GrowsTableToGrowButton(trimmedName, ninuArray.get(i).getDescription(), ninuArray.get(i).getKey(), styleName, GrowsTableToGrowButton.FIRST_TAB, ctx));
		}
	}

	private VerticalPanel encapsulateInnerPanel(VerticalPanel importantNotUrgent) {
		VerticalPanel inner=new VerticalPanel();
		inner.setWidth("100%");
		importantNotUrgent.add(inner);
		return inner;
	}
	
	public void refresh(GrowBean[] result) {
		ninu.clear();
		niu.clear();
		inu.clear();
		iu.clear();

		beanToRia(result);		
	}	
}
