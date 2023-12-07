package org.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.bookbook.domain.UserVO;

@Mapper
public interface DetailLikeMapper {
	public List<String> getLikeIds(int bookId);
	
	public List<UserVO> getUsers(List<String> likeUserIds);
		
	public List<String> getLikeBookIdsByUserId(String userId);

}
