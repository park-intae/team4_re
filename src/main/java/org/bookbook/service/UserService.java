package org.bookbook.service;

import java.io.IOException;
import java.util.List;

import org.bookbook.domain.ChangePasswordVO;
import org.bookbook.domain.UserUpdateVO;
import org.bookbook.domain.UserVO;


public interface UserService {
	public UserVO get(String userid);

	public void register(UserVO userid) throws IOException;

	public List<UserVO> getAllUsers();

	public List<UserVO> getAllUsersWithFollowStatus(String currentUserId); 
	
	public boolean isUserIdAvailable(String userid); // ID중복확인

	public void updateUserProfile(UserUpdateVO userUpdateVO);

}
