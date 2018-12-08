package com.dasetova.springstudy.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int totalPages;
	private int pageElements;
	private int currentPage;
	
	private List<PageItem> pages;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		this.pageElements = page.getSize();
		this.totalPages = page.getTotalPages();
		this.currentPage = page.getNumber() + 1;
		
		int from, to;
		if (this.totalPages <= this.pageElements) {
			from = 1;
			to = this.totalPages;
		}else {
			if (currentPage <= pageElements/2) {
				from = 1;
				to = pageElements;
			} else if(currentPage >= totalPages - pageElements/2){
				from = totalPages - pageElements +1;
				to = pageElements;
			} else {
				from = currentPage - pageElements / 2;
				to = pageElements;
			}
		}
		
		for(int i = 0; i < to; i++) {
			pages.add(new PageItem(from + i, currentPage == from + 1));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}
