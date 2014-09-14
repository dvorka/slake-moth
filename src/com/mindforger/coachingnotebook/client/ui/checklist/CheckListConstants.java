package com.mindforger.coachingnotebook.client.ui.checklist;

public interface CheckListConstants {

	/* Checklist items
	 *  - they sets below are used in checklists for G/R.../W/!
	 *  - each set MUST start with 0 and be growing sequence, because it is used
	 *    in the array indexation. Also the order matters.
	 */

	// Checklist items for Goal
	
	int CI_G_SMARTER=0;
	int CI_G_SPEFIFIC=1;
	int CI_G_MEASURABLE=2;
	int CI_G_ATTAINABLE=3;
	int CI_G_REALISTIC=4;
	int CI_G_TIMEABLE=5;
	int CI_G_EXCITING=6;
	int CI_G_RESOURCED=7;

	// Checklist items for Reality

	int CI_R_STRENGTHS = 0;
	int CI_R_WEAKNESSES = 1;
	
	// Checklist items for Options

	int CI_O_OPPORTUNITIES = 0;
	int CI_O_THREATS = 1;
	
	// Checklist items for !
	
	int CI_I_MISTAKES=0;
	int CI_I_LESSONS=1;

	// Checklist items for Wheel of Life
	
	int CI_WHEEL_HEALTH=0;
	int CI_WHEEL_JOB=1;
	int CI_WHEEL_MONEY=2;
	int CI_WHEEL_FAMILY=3;
	int CI_WHEEL_LIVING=4;
	int CI_WHEEL_RELS=5;
	int CI_WHEEL_EDU=6;
	int CI_WHEEL_FUN=7;
	
	/* Checklist questions
	 *  - checklist questions are used to evaluate checklist items
	 *  - each question must have unique identifier
	 *  - identifiers must be stable (they are used in the store to identify the answer
	 */

	// Checklist questions for Goal
	
	Integer CQ_G_S1=1;
	Integer CQ_G_S2=2;
	Integer CQ_G_M1=3;
	Integer CQ_G_M2=4;
	Integer CQ_G_A1=5;
	Integer CQ_G_A2=6;
	Integer CQ_G_A3=7;
	Integer CQ_G_R1=8;
	Integer CQ_G_R2=9;
	Integer CQ_G_T1=10;
	Integer CQ_G_E1=11;
	Integer CQ_G_E2=12;
	Integer CQ_G_RR1=13;
	
	// Checklist questions for Q
	
	Integer CQ_I_M1 = 114;
	Integer CQ_I_L1 = 115;

	// Checklist questions for Wheel of life

	Integer CQ_WHEEL_W1 = 216;
	Integer CQ_WHEEL_W2 = 217;
	Integer CQ_WHEEL_W3 = 218;
	Integer CQ_WHEEL_W4 = 219;
	Integer CQ_WHEEL_W5 = 220;
	Integer CQ_WHEEL_W6 = 221;
	Integer CQ_WHEEL_W7 = 222;
	Integer CQ_WHEEL_W8 = 223;

	// Checklist questions for Wheel of Life/[Area 1]

	Integer CQ_IMPROVE_WHEEL_1 = 500;
	Integer CQ_IMPROVE_WHEEL_2 = 501;
	
	/*
	 * Contextual fix
	 */
	
	String CQ_IMPROVE_WHEEL_VAR_AREA="@@@AREA_OF_LIFE@@@";	
}
