package org.bookbook.domain;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsVO {
    private int ratingid;
    private String userid;
    private int bookid;
    private String rating_review;
    private int rating;
    private Timestamp rating_date;

}
