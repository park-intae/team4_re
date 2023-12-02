package org.bookbook.domain;

import lombok.Data;

@Data
public class RatingVO {
	private int bookId;
	private int ratingId;
	private int rating;
	private String userId;
}
