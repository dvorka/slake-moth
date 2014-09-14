package com.mindforger.coachingnotebook.shared.security;

public interface SecurityLimits {

	int LIMIT_GOALS=5;
	int LIMIT_QUESTIONS_PER_GOAL=50;	
	int LIMIT_QUESTIONS_TOTAL=LIMIT_GOALS*LIMIT_QUESTIONS_PER_GOAL;	
	int LIMIT_MAX_ANSWER_SIZE=4096;
	int LIMIT_COMMENTS_PER_QUESTION=50;
		
	int LIMIT_FRIEND_REQUESTS=1000;
}
