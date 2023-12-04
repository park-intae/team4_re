package org.bookbook.domain;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RatingVO {
	private String userId;
	private String review_password;
	private int bookId;
	private String rating_reivew;
	private int rating;
	private Date rating_date;
}
