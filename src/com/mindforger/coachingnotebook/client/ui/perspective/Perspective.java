package com.mindforger.coachingnotebook.client.ui.perspective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;

public class Perspective implements MindForgerConstants {

	public static PerspectiveDescriptor[] LIST;
	public static Map<String,PerspectiveDescriptor> MAP;
	
	static {
		List<PerspectiveDescriptor> result=new ArrayList<PerspectiveDescriptor>();
		RiaMessages i18n=GWT.create(RiaMessages.class);
		
		result.add(new PerspectiveDescriptor(
				PERSPECTIVE_LIFE_DESIGNER,
				i18n.lifeDesigner(), 
				i18n.lifeDesignerDescription()));
		result.add(new PerspectiveDescriptor(
				PERSPECTIVE_PROBLEM_SOLVER,
				i18n.problemSolver(), 
				i18n.problemSolverDescription()));
		result.add(new PerspectiveDescriptor(
				PERSPECTIVE_COACH,
				i18n.coach(), 
				i18n.coachDescription()));
		result.add(new PerspectiveDescriptor(
				PERSPECTIVE_COACHEE,
				i18n.coachee(), 
				i18n.coacheeDescription()));
		// HR
		// Employee
		result.add(new PerspectiveDescriptor(
				PERSPECTIVE_MIND_FORGER,
				i18n.expert(), 
				i18n.expertDescription()));
		
		LIST=result.toArray(new PerspectiveDescriptor[result.size()]);
		
		MAP=new HashMap<String, PerspectiveDescriptor>();
		for(PerspectiveDescriptor p:LIST) {
			MAP.put(p.getId(), p);
		}
	};
	
}
