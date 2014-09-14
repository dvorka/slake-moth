package com.mindforger.coachingnotebook.server.store.gae.beans;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.store.GaeBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.GaeBackupTranscoder;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GaeQuestionAnswerBean implements Serializable, GaeBackupTranscoder<BackupQuestionAnswerBean>, GaeBean {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key key;

	@Persistent
	private String ownerId;
		
	@Persistent
	private String questionLabel;
	@Persistent
	private String question;
	@Persistent
	private Text answer;
	@Persistent
	private Date deadline;
	@Persistent
	private int progress;
	// Important, Cool, Tricky, Obsolete, Urgent, Later
	@Persistent
	private String category;
	
	@Persistent
	private String growKey;
	// G, R, O, W, I
	@Persistent
	private String growType;
	@Persistent
	private int order;
	@Persistent
	private int depth;
	
	public GaeQuestionAnswerBean() {
		growType=QuestionAnswerBean.G_PART;
	}

	public GaeQuestionAnswerBean(QuestionAnswerBean pojo) {
		this();
		fromPojo(pojo);
	}

	public GaeQuestionAnswerBean(String questionLabel, String question, String answer, String owner) {
		this();
		this.questionLabel=questionLabel;
		this.question=question;
		this.answer=(answer==null?null:new Text(answer));
	}

	public GaeQuestionAnswerBean(String questionLabel, String question, String answer, String owner, Date deadline, int progress) {
		this(questionLabel,question,answer,owner);
		this.progress=progress;
		this.deadline=deadline;
	}	
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Text getAnswer() {
		return answer;
	}

	public void setAnswer(Text answer) {
		this.answer = answer;
	}

	public String getGrowKey() {
		return growKey;
	}

	public void setGrowKey(String growKey) {
		this.growKey = growKey;
	}

	public String getGrowType() {
		return growType;
	}

	public void setGrowType(String growType) {
		this.growType = growType;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void fromPojo(QuestionAnswerBean pojo) {
		key=Utils.stringToKey(pojo.getKey());
		growKey=pojo.getGrowKey();
		questionLabel=pojo.getQuestionLabel();
		question=pojo.getQuestion();
		growType=pojo.getGrowType();
		answer=(pojo.getAnswer()==null?null:new Text(pojo.getAnswer()));
		progress=pojo.getProgress();
		deadline=pojo.getDeadline();
		category=pojo.getCategory();
		order=pojo.getOrder();
		depth=pojo.getDepth();
	}
		
	public QuestionAnswerBean toPojo() {
		QuestionAnswerBean pojo=new QuestionAnswerBean(growType);
		pojo.setKey(Utils.keyToString(key));			
		pojo.setGrowKey(growKey);
		pojo.setQuestionLabel(questionLabel);
		pojo.setQuestion(question);
		pojo.setAnswer((answer==null?null:answer.getValue()));
		pojo.setProgress(progress);
		pojo.setDeadline(deadline);
		pojo.setDeadlineCssClass(Utils.getCssStyleNameForActionDeadline(deadline));
		pojo.setCategory(category);
		pojo.setOrder(order);
		pojo.setDepth(depth);
		return pojo;
	}
	
	public void fromBackup(BackupQuestionAnswerBean pojo) {
		key=Utils.stringToKey(pojo.getKey());
		growKey=pojo.getGrowKey();
		ownerId=pojo.getOwnerId();
		questionLabel=pojo.getQuestionLabel();
		question=pojo.getQuestion();
		growType=pojo.getGrowType();
		answer=(pojo.getAnswer()==null?null:new Text(pojo.getAnswer()));
		progress=pojo.getProgress();
		deadline=pojo.getDeadline();
		category=pojo.getCategory();
		order=pojo.getOrder();
		depth=pojo.getDepth();
	}
		
	public BackupQuestionAnswerBean toBackup() {
		BackupQuestionAnswerBean pojo=new BackupQuestionAnswerBean(growType);
		pojo.setKey(Utils.keyToString(key));			
		pojo.setGrowKey(growKey);
		pojo.setOwnerId(ownerId);
		pojo.setQuestionLabel(questionLabel);
		pojo.setQuestion(question);
		pojo.setAnswer((answer==null?null:answer.getValue()));
		pojo.setProgress(progress);
		pojo.setDeadline(deadline);
		pojo.setCategory(category);
		pojo.setOrder(order);
		pojo.setDepth(depth);
		return pojo;
	}
	
	private static final long serialVersionUID = -4967155712234491099L;
}
