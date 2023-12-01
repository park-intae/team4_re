package org.bookbook.mapper;

import java.util.List;

import org.bookbook.domain.RatingVO;

public interface RatingMapper {
	public List<Long> getRatingList(String user_id);

	public int addRating(RatingVO rating);

	public int deleteRating(RatingVO rating);

}
