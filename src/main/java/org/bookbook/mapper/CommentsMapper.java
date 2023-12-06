package org.bookbook.mapper;

import java.util.List;

import org.bookbook.domain.CommentsVO;

public interface CommentsMapper {
	List<CommentsVO> readAll(int bookid);

	CommentsVO get(int ratingid);
	
	void create(CommentsVO vo);
	void update(CommentsVO vo);
	void delete(int ratingid);

	public List<CommentsVO> readCommentsByUserId(String userId);

}
