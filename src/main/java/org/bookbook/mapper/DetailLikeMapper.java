package org.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.LikeUserVO;

@Mapper
public interface DetailLikeMapper {
	public List<String> getLikeIds(int bookId);
	
	public List<LikeUserVO> getUsers(List<String> likeUserIds);
		
	public List<String> getLikeBookIdsByUserId(String userId);
	
	public List<BookVO> getBooks(List<String> bookIds);

}
