package com.mindforger.coachingnotebook.server;

import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;

public class QuestionsRepository {

	/*
	 * demo data
	 */
	
	public static String[] grow={
		"I want to find a new job, but don't know what I want to do",
		"I want to start a new business",
		"I want to accelerate my career",
		"I want to find a new partner for life",
		"I want to run sub 3 hour marathon",
		"I want to make enough money to be retired at 40",
		"I want to heal my injury",
		"I don't like the flat where I live, I want to move elsewhere"
	};
	
	/*
	 * predefined questions labels
	 */

	public static String[] gLabels={
		"Question",
		"Idea"
	};	
	public static String[] rLabels={
		"Question",
		"Strength",
		"Weakness",
		"Fact"
	};	
	public static String[] oLabels={
		"Option",
		"Question",
		"Opportunity",
		"Threat"
	};	
	public static String[] wLabels={
		"Action",
		"Task",
		"Idea",
		"Question"
	};	
	public static String[] iLabels={
		"Result",
		"Solution",
		"Lesson",
		"Experience",
		"Question",
		"Conclusion"
	};	
	
	/*
	 * predefined set of  questions
	 */
	
	public static String[] g={
	    "How can I describe the goal?",
	    "What I wish to achieve?",
	    "What is my motivation?",
	    "How can I describe the problem I want to solve?",
	    "Why I want to achieve this goal?",
	    "How will I know that I have achieved the goal?",
	    "How will I know the problem is solved?",
	    "How will I measure it?",
		"Do I want to achieve this goal in short-term or long-term?",
		"What does success look like?",
		"How much personal control or influence do I have over my goal?",
		"What would be milestones on the way?",
		"When do I want to achieve it by?",
		"Is the goal positive?",
		"Is the goal challenging?",
		"Is the goal attainable?"
	};
	
	public static String[] r={
		"What is happening now? (WHAT, WHEN, WHERE, HOW MUCH, HOW OFTEN) ",
		"What is involved - directly and indirectly)?",
		"What things are going badly on this issue, what happens to me?",
		"What happens to others directly involved?",
		"What is the effect of this in 10 days, 10 months and 10 years? (10:10:10 rule)",
		"What is the effect on others?",
		"What have I done about this so far?",
		"What results did that produce?",
		"What is holding me back from finding a way forward?",
		"What is really going on? (Intuition)",		
		"How can I assess myself in the context of the goal?",
		"What I already did and what was the result?"
	};
	
	public static String[] o={
		"What options do I have?",
		"What are the benefits and downsides of each option?",
		"What are the obstacles related to each option?",
		"Who can support me? Who can I ask for help?",
		"What can help me?",
		"Who or where can I ask?",
		"What is the wrong approach? What I should definitely avoid?",
		"What are the options that I should definitely avoid?",
		"Do I know a promissing option with obstacles? How can I remove these obstacles one by one?",
		"What else could I do?",
		"What if analysis...? (triangle: time, power, money)",
		"Would I need another suggestion?",
		"How do I leverage my strengths to benefit from opportunities?",
		"How do I use my strengths to minimize impact of threats?",
		"How do I ensure my weaknesses will not stop me from opportunities?",
		"How will I fix weaknesses that can make threats have a real impact?"
	};
	
	public static String[] w={
		"Which options or option do I choose?",
		"To what extent does this meet all my objectives?",
		"What are my criteria and measurements for success?",
		"When precisely am I going to start and finish each action step?",
		"What could arise to hinder me in taking these steps?",
		"What personal resistance do I have, if any, to taking these steps?",
		"What will I do to eliminate these external and internal factors?",
		"Who needs to know what my plans are?",
		"What support do I need and from whom?",
		"What will I do to obtain that support and from whom?",
		"What will I do to obtain that support and when?",
		"What commitment on a 1 - 10 scale do I have taking these identified actions?",
		"What prevents this from being a 10?",
		"What could I do or alter to raise my commitment closer to 10?",
		"How can I start? What should I do first?",
		"Which actions I can do immediately?",
		"What are other tasks? What are the deadlines for each task?",
		"What is the meaningful order of actions and tasks?",
		"Which actions will I do later? Why can I afford to postpone them?",
		"What could stop me from moving forward?",
		"What will I do now and what later?",
		"How will I overcome problems that may slow me down or stop me?",
		"How likely is this option to succeed?",
		"What else can I do?",
		"Is there anything else I want to elaborate or the actions finished?"
	};
	
	public static String[] i={
		"What did I learned?",
		"What is the most valuable experience?",
		"How am I satisfied with the overall result?",
		"What are the results of actions I did up until now?",
		"What turned out well?",
		"With which result in particular I'm satisfied?",
		"What did worked?",
		"Did the result exceeded my expectations?",
		"What is the feedback of involved/impacted people?",
		"What turned out wrong?",
		"With which result in particular I'm not satisfied?",
		"What did not worked?",
		"Could I move faster? What would be the risk?",
		"Could I move more efficiently?",
		"What can I perform better next time?",
		"What would I do different if I have chance to return to the beginning again?",
		"What should I avoid next time?"
	};
	
	/*
	 * user defined questions (stored/loaded from repository)
	 */
	
	QuestionSetsBean questionSetsBean;
	
	public QuestionsRepository() {
		questionSetsBean=new QuestionSetsBean();
		questionSetsBean.setG(g);
		questionSetsBean.setR(r);
		questionSetsBean.setO(o);
		questionSetsBean.setW(w);
		questionSetsBean.setI(i);
		questionSetsBean.setgLabels(gLabels);
		questionSetsBean.setrLabels(rLabels);
		questionSetsBean.setoLabels(oLabels);
		questionSetsBean.setwLabels(wLabels);
		questionSetsBean.setiLabels(iLabels);
	}
	
	public QuestionSetsBean toQuestionSetsBean() {
		return questionSetsBean;
	}
}
