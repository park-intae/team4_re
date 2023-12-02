package org.bookbook.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewVO {
	private int ratingId;
	private int column1;
	
	private String userId;
	private int rating;
	private String ratingReview;
	private Date ratingDate;
}
