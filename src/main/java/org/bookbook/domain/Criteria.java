package org.bookbook.domain;


import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {

	private int pageNum;
	private int amount;
	private String type;
	private String keyword;
	private int classi;
	private int dateNum;
	private int dateNum2;

	public Criteria() {
		this(1, 10);
	}

	public Criteria(int pageNum) {
		this(pageNum, 10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public int getOffset() {

		return (pageNum - 1) * amount;
	}

	public String[] getTypeArr() {
		return type == null ? 
						new String[] {} : 	// 빈 배열 리턴
						type.split("");		// 한글자 단위로 분리된 배열 리턴
	
   }
	
	
	public String getLink() {
		return getLink("", pageNum);
	}

	public String getLink(int pageNum) {
		return getLink("", pageNum);
	}

	public String getLink(String base) {
		return getLink(base, pageNum);
	}


	public String getLink(String base, int pageNum) {

		UriComponentsBuilder builder = 
			UriComponentsBuilder.fromPath(base)
				.queryParam("pageNum", pageNum)
				.queryParam("amount", amount)
				.queryParam("type", type)
				.queryParam("keyword", keyword);

		return builder.toUriString();
	}

	public String getLinkWithColumn1(String base, int Column1) {
		return getLink(base, pageNum) + "&column1=" + Column1;
	}
	
	
	public String getLinkWithColumn(String base) {
		return getLink(base, pageNum);
	}
	public String getLinkWithColumn2(String base, int classi) {
		return getLink(base, pageNum) + "&classi=" + 2;
	}
	public String getLinkWithColumn3(String base, int classi) {
		return getLink(base, pageNum) + "&classi=" + 3;
	}
	public String getLinkWithColumn4(String base, int classi) {
		return getLink(base, pageNum) + "&classi=" + 4;
	}
	public String getLinkWithColumn5(String base, int dateNum) {
		return getLink(base, pageNum) + "&dateNum=" + 1;
	}
	public String getLinkWithColumn6(String base, int dateNum2) {
		return getLink(base, pageNum) + "&dateNum2=" + 1;
	}
}