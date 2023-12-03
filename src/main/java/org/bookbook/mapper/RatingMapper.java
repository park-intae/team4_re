package org.bookbook.mapper;

import org.bookbook.domain.RatingVO;

public interface RatingMapper {
	double getRating(int column1);

	int addRating(RatingVO vo);

//	public int deleteRating(RatingVO rating);

}
