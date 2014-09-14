package com.mindforger.coachingnotebook.server.store.gae;

public interface GaeBackupTranscoder<T> {
	T toBackup();
	void fromBackup(T t);
}
