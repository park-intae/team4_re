package org.bookbook.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.bookbook.auth.naver.NaverLoginBO;
import org.bookbook.domain.NaverUserVO;
import org.bookbook.domain.UserVO;
import org.bookbook.mapper.NaverUserMapper;
import org.bookbook.mapper.UserMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Service
public class NaverLoginServiceImpl implements NaverLoginService {

	@Autowired
	private NaverLoginBO naverLoginBO;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private NaverUserMapper naverUserMapper;

	// 네이버 로그인을 처리하고 사용자 정보를 반환하는 메서드
	public UserVO processNaverUserLogin(String code, String state, HttpSession session)
			throws IOException, ParseException {
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		if (oauthToken != null) {
			NaverUserVO naverUserInfo = naverLoginBO.getNaverUserInfo(oauthToken);

			return createUserFromNaverUserInfo(naverUserInfo);

		}
		return null;
	}

	// 네이버 사용자 정보를 기반으로 사용자 정보를 생성하거나 가져오는 메서드
	public UserVO createUserFromNaverUserInfo(NaverUserVO naverUserInfo) {
		// 네이버 사용자의 ID를 이용하여 기존 사용자를 검색

		String userId = naverUserMapper.getUserIdByNaverId(naverUserInfo.getId());
		if (userId == null) {
			// 기존 사용자가 없을 경우, 새로운 사용자 정보 생성
			UserVO newUser = new UserVO();

			newUser.setUserid(naverUserInfo.getId());
			newUser.setUsername(naverUserInfo.getName());
			newUser.setNickname(naverUserInfo.getNickname());
			newUser.setEmail(naverUserInfo.getEmail());
			newUser.setGender(naverUserInfo.getGender());
		

			userMapper.insert(newUser);

			// naver_user 테이블에 매핑 정보 저장
			naverUserMapper.insertNaverUser(naverUserInfo.getId(), newUser.getUserid());
			return newUser;

		} else {

			return userMapper.read(userId);
		}
	}

	
}
