package com.dasetova.springstudy.util.paginator;

public class PageItem {
	
	private int number;
	private boolean currentPage;
	
	public PageItem(int number, boolean currentPage) {
		this.number = number;
		this.currentPage = currentPage;
	}
	public int getNumber() {
		return number;
	}
	public boolean isCurrentPage() {
		return currentPage;
	}

}
