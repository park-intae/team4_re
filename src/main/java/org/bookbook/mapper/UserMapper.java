package org.bookbook.mapper;

import java.util.List;

import org.bookbook.domain.AuthVO;
import org.bookbook.domain.ChangePasswordVO;
import org.bookbook.domain.UserUpdateVO;
import org.bookbook.domain.UserVO;

public interface UserMapper {
	public UserVO read(String userid);
	
	public void insert(UserVO userid);
	
	public void insertAuth(AuthVO auth);
	
	public List<UserVO> getAllUsers();
	
	public void update(UserVO user);
	
	public void updateProfile(UserVO user);
}
