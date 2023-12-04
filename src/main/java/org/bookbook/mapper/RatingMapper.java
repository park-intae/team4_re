package org.bookbook.mapper;

import org.bookbook.domain.RatingVO;

public interface RatingMapper {
<<<<<<< HEAD
	double getRating(int column1);

	int addRating(RatingVO vo);

//	public int deleteRating(RatingVO rating);

=======
	RatingVO get(int bookId);
	
>>>>>>> main
}
