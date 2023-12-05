package org.bookbook.model;

public class Criteria {
	/* �쁽�옱 �럹�씠吏� */
	private int pageNum;

	/* �븳 �럹�씠吏� �떦 蹂댁뿬吏� 寃뚯떆臾� 媛��닔 */
	private int amount;

	/* �뒪�궢 �븷 寃뚯떆臾� �닔( (pageNum-1) * amount ) */
	private int skip;

	/* 湲곕낯 �깮�꽦�옄 -> 湲곕큶 �꽭�똿 : pageNum = 1, amount = 10 */
	public Criteria() {
		this(1, 5);
		this.skip = 0;
	}

	/* �깮�꽦�옄 => �썝�븯�뒗 pageNum, �썝�븯�뒗 amount */
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {

		this.skip = (pageNum - 1) * this.amount;

		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {

		this.skip = (this.pageNum - 1) * amount;

		this.amount = amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + "]";
	}

	private String[] bookType;

	public String[] getBookType() {
		return bookType;
	}

	public void setBookType(String[] bookType) {
		this.bookType = bookType;
	}

	private String[] selectedCategories;

	public String[] getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(String[] selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	private String[] topics;

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}

	private String[] keywords;

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
        this.keywords = keywords;
	}
}
