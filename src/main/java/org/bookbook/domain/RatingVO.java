package org.bookbook.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RatingVO {
	private int column1;
	private int ratingId;
	
	private int rating;
	private int totalRating;
	private String userId;
}
