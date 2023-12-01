package org.bookbook.service;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import javax.servlet.http.HttpSession;

import org.bookbook.domain.NaverUserVO;
import org.bookbook.domain.UserVO;

public interface NaverLoginService {

	// 네이버 로그인을 처리하고 사용자 정보를 반환하는 메서드.
	public UserVO processNaverUserLogin(String code, String state, HttpSession session)
			throws IOException, ParseException;

	// 네이버 사용자 정보를 기반으로 사용자 정보를 생성하거나 가져오는 메서드
	public UserVO createUserFromNaverUserInfo(NaverUserVO naverUserInfo);
}
