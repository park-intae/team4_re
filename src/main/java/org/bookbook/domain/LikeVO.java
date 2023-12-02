package org.bookbook.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LikeVO {
	private int bookId;
	private String userId;
	private String title;
	private String author;
	private String publisher;
	private String genre;
	private String category;
	private String Topic;
	private String imageUrl;
}
