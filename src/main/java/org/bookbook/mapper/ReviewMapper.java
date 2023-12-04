package org.bookbook.mapper;

import org.bookbook.domain.ReviewVO;

public interface ReviewMapper {
	void create(ReviewVO vo);
	ReviewVO get(int bookId);
}
