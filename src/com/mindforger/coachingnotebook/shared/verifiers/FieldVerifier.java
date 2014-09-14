package com.mindforger.coachingnotebook.shared.verifiers;


public class FieldVerifier {
	
	public enum Field {
		USER_NAME(50),
		USER_DESCRIPTION(250),
		USER_WWW(50),
		GROW_TITLE(150),
		GROW_DESCRIPTION(1024),
		QA_QUESTION(150),
		QA_ANSWER(1024),
		LIFE_VISION(1024);
		
		private final int limit;
		
		Field(final int limit) {
			this.limit=limit;
		};
		
		public int limit() {
			return limit;
		}
	};

	public FieldVerifier() {
	}

	public int verify(Field fieldType, String field) {
		if(field!=null && field.length()>fieldType.limit) {
			return fieldType.limit; 
		} else {
			return -1;
		}
	}
}
