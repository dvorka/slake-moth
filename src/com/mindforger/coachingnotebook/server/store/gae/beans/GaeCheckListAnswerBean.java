package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeCheckListAnswerBean implements Serializable, GaeBackupTranscoder<BackupCheckListAnswerBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;
	
	@Persistent
	private String ownerId;
	@Persistent
	private String growKey;
	@Persistent
	private Integer questionId;
	@Persistent
	private Text answer;
	@Persistent
	private String mode;
	
	public GaeCheckListAnswerBean() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growKey) {
		this.growKey = growKey;
	}

	public Text getAnswer() {
		return answer;
	}

	public void setAnswer(Text answer) {
		this.answer = answer;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public CheckListAnswerBean toPojo() {
		CheckListAnswerBean result=new CheckListAnswerBean();
		result.setId(questionId);
		result.setAnswer((answer==null?null:answer.getValue()));
		return result;
	}

	public void fromPojo(CheckListAnswerBean pojo) {
		questionId=pojo.getId();
		answer=(pojo.getAnswer()==null?null:new Text(pojo.getAnswer()));
	}

	public BackupCheckListAnswerBean toBackup() {
		BackupCheckListAnswerBean backup=new BackupCheckListAnswerBean();
		backup.setKey(Utils.keyToString(key));
		backup.setGrowKey(growKey);
		backup.setMode(mode);
		backup.setQuestionId(questionId);
		backup.setAnswer((answer==null?null:answer.getValue()));
		backup.setOwnerId(ownerId);

		return backup;
	}

	public void fromBackup(BackupCheckListAnswerBean backup) {
        key=Utils.stringToKey(backup.getKey());    		
		ownerId=backup.getOwnerId();
		growKey=backup.getGrowKey();
		questionId=backup.getQuestionId();
		answer=(backup.getAnswer()==null?null:new Text(backup.getAnswer()));
		mode=backup.getMode();
	}
	
	private static final long serialVersionUID = 1677797646953887673L;
}
