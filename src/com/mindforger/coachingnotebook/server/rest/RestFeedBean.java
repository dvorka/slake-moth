package com.mindforger.coachingnotebook.server.rest;

public class RestFeedBean {

	private int totalEntries;
	private int pageSize;
	private int lastPage;

	public RestFeedBean() {
	}
	
	public RestFeedBean(int entries, int pageSize, int lastPage) {
		this.totalEntries=entries;
		this.pageSize=pageSize;
		this.lastPage=lastPage;
	}

	public int getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(int totalEntries) {
		this.totalEntries = totalEntries;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
}
