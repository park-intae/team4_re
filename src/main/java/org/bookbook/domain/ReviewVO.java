package org.bookbook.domain;

import lombok.Data;

@Data
public class ReviewVO {
	private int ratingId;
	private int bookId;
	
	private String userId;
	private int rating;
	private String ratingReview;
	private String ratingDate;
}
