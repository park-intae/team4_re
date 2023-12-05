package org.bookbook.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bookbook.domain.AuthVO;
import org.bookbook.domain.UserUpdateVO;
import org.bookbook.domain.UserVO;
import org.bookbook.mapper.FollowerMapper;
import org.bookbook.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UserServiceImpl implements UserService {

	public static final String AVATAR_UPLOAD_DIR = "c:/upload/avatar";

	@Autowired
	UserMapper userMapper;

	@Autowired
	private FollowerMapper followerMapper;

	@Autowired
	private PasswordEncoder pwEncoder;

	//
	@Override
	public UserVO get(String userid) {
		return userMapper.read(userid);
	}

	// 회원 가입
	@Override
	public void register(UserVO userid) throws IOException {

		// 1. 비밀번호 암호화
		String encPassword = pwEncoder.encode(userid.getPassword());
		userid.setPassword(encPassword);

		// 2. users 테이블에 저장
		userMapper.insert(userid);

		// 3. users_auth에 저장
		AuthVO auth = new AuthVO(userid.getUserid(), "ROLE_USER");
		userMapper.insertAuth(auth);

	}

	@Override
	public List<UserVO> getAllUsers() {
		return userMapper.getAllUsers();
	}

	// 현재 로그인한 사용자를 제외한 모든 사용자 목록을 반환하는 메서드
	@Override
	public List<UserVO> getAllUsersWithFollowStatus(String currentUserId) {
		List<UserVO> users = userMapper.getAllUsers(); // 모든 사용자 목록을 가져옴
		List<UserVO> usersWithFollowStatus = new ArrayList<>(); // 팔로우 상태가 설정된 사용자 목록

		for (UserVO user : users) {
			if (!user.getUserid().equals(currentUserId)) { // 현재 로그인한 사용자 제외
				boolean isFollowing = isFollowing(currentUserId, user.getUserid());
				user.setFollowStatus(isFollowing); // 팔로우 상태 설정
				usersWithFollowStatus.add(user); // 리스트에 추가
			}
		}

		return usersWithFollowStatus; // 현재 로그인한 사용자를 제외한 목록 반환
	}

	// 팔로우 여부를 확인하는 메서드
	private boolean isFollowing(String currentUserId, String otherUserId) {
		return followerMapper.findFollowByUserIds(currentUserId, otherUserId) != null;
	}

	public boolean isUserIdAvailable(String userid) {
		// ID가 데이터베이스에 존재하는지 확인하는 로직
		UserVO user = userMapper.read(userid);
		return user == null;
	}

	@Override
	public void updateUserProfile(UserUpdateVO userUpdateVO) {
	    try {
	        UserVO existingUser = userMapper.read(userUpdateVO.getUserid());
	        if (existingUser == null) {
	            log.error("User not found with id: " + userUpdateVO.getUserid());
	            return;
	        }

	        // 비밀번호 변경 로직
	        if (userUpdateVO.getOrgPassword() != null && !userUpdateVO.getOrgPassword().isEmpty() &&
	            userUpdateVO.getNewPassword() != null && !userUpdateVO.getNewPassword().isEmpty() &&
	            pwEncoder.matches(userUpdateVO.getOrgPassword(), existingUser.getPassword())) {

	            String encPassword = pwEncoder.encode(userUpdateVO.getNewPassword());
	            userUpdateVO.setEncPassword(encPassword);
	        } else {
	            userUpdateVO.setEncPassword(existingUser.getPassword());
	        }

	        // 나머지 사용자 정보 업데이트
	        existingUser.setUsername(userUpdateVO.getUsername());
	        existingUser.setNickname(userUpdateVO.getNickname());
	        existingUser.setPassword(userUpdateVO.getEncPassword());
	        existingUser.setGender(userUpdateVO.getGender());

	        // 회원 정보 업데이트 로직
	        userMapper.updateProfile(existingUser);
	    } catch (Exception e) {
	        log.error("Error during updating user profile", e);
	    }
	}
}