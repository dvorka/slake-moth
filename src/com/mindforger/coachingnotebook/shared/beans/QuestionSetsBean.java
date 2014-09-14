package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;

public class QuestionSetsBean implements Serializable {

	String[] g;
	String[] r;
	String[] o;
	String[] w;
	String[] i;

	String[] gLabels;
	String[] rLabels;
	String[] oLabels;
	String[] wLabels;
	String[] iLabels;
	
	public QuestionSetsBean() {
	}

	public String[] getG() {
		return g;
	}

	public void setG(String[] g) {
		this.g = g;
	}

	public String[] getR() {
		return r;
	}

	public void setR(String[] r) {
		this.r = r;
	}

	public String[] getO() {
		return o;
	}

	public void setO(String[] o) {
		this.o = o;
	}

	public String[] getW() {
		return w;
	}

	public void setW(String[] w) {
		this.w = w;
	}

	public String[] getI() {
		return i;
	}

	public void setI(String[] i) {
		this.i = i;
	}
	
	public String[] getgLabels() {
		return gLabels;
	}

	public void setgLabels(String[] gLabels) {
		this.gLabels = gLabels;
	}

	public String[] getrLabels() {
		return rLabels;
	}

	public void setrLabels(String[] rLabels) {
		this.rLabels = rLabels;
	}

	public String[] getoLabels() {
		return oLabels;
	}

	public void setoLabels(String[] oLabels) {
		this.oLabels = oLabels;
	}

	public String[] getwLabels() {
		return wLabels;
	}

	public void setwLabels(String[] wLabels) {
		this.wLabels = wLabels;
	}

	public String[] getiLabels() {
		return iLabels;
	}

	public void setiLabels(String[] iLabels) {
		this.iLabels = iLabels;
	}

	private static final long serialVersionUID = 6970829595513086973L;

	public String[] getLabelsForTab(String mode) {
		if(QuestionAnswerBean.G_PART.equals(mode)) {
			return gLabels;
		} else {
			if(QuestionAnswerBean.R_PART.equals(mode)) {
				return rLabels;
			} else {
				if(QuestionAnswerBean.O_PART.equals(mode)) {
					return oLabels;
				} else {
					if(QuestionAnswerBean.W_PART.equals(mode)) {
						return wLabels;
					} else {
						if(QuestionAnswerBean.I_PART.equals(mode)) {
							return iLabels;
						} else {
							return gLabels;
						}
					}
				}
			}
		}
	}
}
