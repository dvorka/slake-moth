package com.mindforger.coachingnotebook.client.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListItem;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListPanel;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListPanelRefreshCallback;
import com.mindforger.coachingnotebook.client.ui.checklist.CheckListUpdateListener;
import com.mindforger.coachingnotebook.client.ui.checklist.WizardPanel;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;

public class LifeDesignerPanel extends FlexTable implements CheckListUpdateListener, CheckListPanelRefreshCallback {
	
	public static final String[] WHEEL_OF_LIFE_VALUES=new String[]{
		"Health",
		"Career",
		"Finance",
		"Family",
		"Living",
		"Romance",
		"Education",
		"Lifestyle"				
	};
	public static String[] WHEEL_OF_LIFE_LABELS;
	
	static {
		RiaMessages i18n=GWT.create(RiaMessages.class);
		WHEEL_OF_LIFE_LABELS=new String[]{
				i18n.health(),
				i18n.career(),
				i18n.finances(),
				i18n.familyAndFriends(),
				i18n.living(),
				i18n.romance(),
				i18n.personalGrowth(),
				i18n.lifestyleAndRecreation()
		};
	}
	
	private boolean initialized;
	private HTML radarChart;
	private String chartValues;
	private String chartLegend;
	private LifeVisionTextArea lifeVisionTextArea;
	private CheckListPanel checklist;
	
	private int radarChartDocRow, radarChartRow, checkListRow, journeyDocRow;

	private RiaMessages i18n;
		
	public LifeDesignerPanel(RiaContext ctx) {
		this.i18n=ctx.getI18n();

		setStyleName("mf-wheelOfLifePanel");
						
		initialized=false;

		HTML documentation;
		String html;
		int row=0;

		// Life Designer panel definition...
		
		documentation=new HTML();
		documentation.setStyleName("mf-lifeDesignerIntroText");
		html=i18n.lifeDesignerIntroText();
		documentation.setHTML(html);
		setWidget(row++,0,documentation);

		
		documentation=new HTML();
		documentation.setStyleName("mf-lifeDesignerIntroDiagramEnvelope");
		html="<img src='./images/life-designer-overview.png' title='"+i18n.lifeDesignerOverview()+"' class='mf-lifeDesignerIntroDiagram'/>";
		documentation.setHTML(html);
		setWidget(row++,0,documentation);
		
		
		documentation=new HTML();
		documentation.setStyleName("mf-wheelOfLifeDoc");
		html=i18n.wheelOfLifeDoc1();
		documentation.setHTML(html);
		setWidget(row++,0,documentation);
		
		// life vision text area
		lifeVisionTextArea = new LifeVisionTextArea(row, ctx);
		// make load
		// MAKE IT LAZY lifeVisionTextArea.load();
		setWidget(row++, 0, lifeVisionTextArea);
		
		documentation = new HTML();
		documentation.setStyleName("mf-wheelOfLifeDoc");
		html = i18n.wheelOfLifeDoc2();
		documentation.setHTML(html);
		radarChartDocRow=row;
		setWidget(row++,0,documentation);
		
		chartLegend=getChartLegend();
		chartValues = "0,0,0,0,0,0,0,0,0";
		radarChart=getRadarChartHtml();
		radarChartRow=row;
		row++;

		documentation = new HTML();
		documentation.setStyleName("mf-wheelOfLifeDoc");
		html = i18n.wheelOfLifeDoc3(); 
		documentation.setHTML(html);
		journeyDocRow=row;
		setWidget(row++,0,documentation);
				
		String completionStatusTitle=i18n.wolCompletionStatusTitle();
		String completionStatusSubTitle=i18n.wolCompletionStatusSubTitle();
		String checklistDocumentation=i18n.wolChecklistDocumentation();
		String checklistDisclosurePanelTitle=i18n.wolChecklistDisclosurePanelTitle();
	    String wizardFirstStepDocumentation=i18n.wolWizardFirstStepDocumentation();
	    String wizardLastStepDocumentation=i18n.wolWizardLastStepDocumentation();
		checklist = new CheckListPanel(
				WizardPanel.MODE_WHEEL_OF_LIFE,
				completionStatusTitle,
				completionStatusSubTitle,
				checklistDocumentation,
				checklistDisclosurePanelTitle,
				wizardFirstStepDocumentation,
				wizardLastStepDocumentation,
				this,
				ctx);
		// MAKE IT LAZY checklist.load(WizardPanel.MODE_WHEEL_OF_LIFE);
		checklist.addListener(this);
		checkListRow=row;
		setWidget(row++,0,checklist);		
	}

	public void refresh() {
		if(!initialized) {
			lifeVisionTextArea.load();			
			checklist.load(WizardPanel.MODE_WHEEL_OF_LIFE);
			radarChart=getRadarChartHtml();
			setWidget(radarChartRow,0,radarChart);
		}
		showAllRows();
	}
	
	private int getCheckListRow() {
		return checkListRow;
	}

	private int getRadarChartDocRow() {
		return radarChartDocRow;
	}

	public int getJourneyDocRow() {
		return journeyDocRow;
	}

	public static String getChartLegend() {
		String myChartLegend="";
		for (int i = 0; i < WHEEL_OF_LIFE_LABELS.length; i++) {
			myChartLegend += WHEEL_OF_LIFE_LABELS[i];
			if(i<(WHEEL_OF_LIFE_LABELS.length-1)) {
				myChartLegend+="|";
			}
		}
		return myChartLegend;
	}

	private HTML getRadarChartHtml() {
		radarChart = new HTML();
		radarChart.setStyleName("mf-wheelOfLifeRadarChart");
		radarChart.setHTML(getRadarChartUrl());
		radarChart.setTitle(i18n.radarChartTitle());
		radarChart.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hideAllRows(new Integer[] {getRadarChartDocRow(), getCheckListRow()});
				getChecklist().launchWizard();
			}
		});
		return radarChart;
	}
	
	private String getRadarChartUrl() {
		if(checklist!=null && checklist.getCheckListStatus().getItems()!=null && checklist.getCheckListStatus().getItems().length>0) {
			CheckListItem[] items = checklist.getCheckListStatus().getItems();
			//legend="";
			chartValues="";
			for (int i = 0; i < items.length; i++) {
				//legend+=items[i].getLabel();
				chartValues+=items[i].getPercent();
				chartValues+=",";
				
				if(i<items.length-1) {
					//legend+=",";
				}
			}
			chartValues+=items[0].getPercent();
		}
		
		String chartBaseUrl;
		if(MindForgerSettings.IMAGE_PROXY_ENABLED) {
			chartBaseUrl="/rest/proxy/charts";
		} else {
			chartBaseUrl=MindForgerConstants.GOOGLE_CHARTS_BASE_URL;
		}
		
		return
		    //"<img src='http://chart.apis.google.com/chart?" +			
		    "<img src='"+chartBaseUrl+"?" +
			"chxl=0:|"+chartLegend+"&" +
			"chxs=0,000000,14.5,0,1,000000&" +
			//"chxs=1,000000,14.0,0,_,000000&" +
			"chxt=x,t&" +
			"chs=600x450&" +
			"cht=r&" +
			"chco=008000&" +
			"chd=t:"+chartValues+"&" +
			"chls=2,4,0&" +
			//"chma=8|0,5&" +
			"chm=B,00800082,0,0,0" +
			"'/>";
	}
	
	private CheckListPanel getChecklist() {
		return checklist;
	}
	
	public  void hideAllRows(Integer[] rowsToShow) {
		int rowCount = getRowCount();
		Set<Integer> filter = new HashSet<Integer>(Arrays.asList(rowsToShow));
		for (Integer r = 0; r < rowCount; r++) {
			if(!filter.contains(r)) {
				getRowFormatter().setVisible(r,false);				
			}				
		}
	}

	public void showAllRows() {
		int rowCount = getRowCount();
		for (int i = 0; i < rowCount; i++) {
			getRowFormatter().setVisible(i,true);				
		}
	}

	public void checklistChanged() {
		setWidget(radarChartRow, 0, getRadarChartHtml());
		showAllRows();
	}

	public void onCheckListWizardStart() {
		hideAllRows(new Integer[]{checkListRow-1, checkListRow});
	}
}
