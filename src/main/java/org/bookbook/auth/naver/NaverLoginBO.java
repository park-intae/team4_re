package org.bookbook.auth.naver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.bookbook.domain.NaverUserVO;
import org.bookbook.domain.UserVO;
import org.bookbook.mapper.NaverUserMapper;
import org.bookbook.mapper.UserMapper;
import org.bookbook.security.domain.CustomUser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class NaverLoginBO {
	/* 인증 요청문을 구성하는 파라미터 */
	// client_id: 애플리케이션 등록 후 발급받은 클라이언트 아이디
	// response_type: 인증 과정에 대한 구분값. code로 값이 고정돼 있습니다.
	// redirect_uri: 네이버 로그인 인증의 결과를 전달받을 콜백 URL(URL 인코딩). 애플리케이션을 등록할 때 Callback
	// URL에 설정한 정보입니다.
	// state: 애플리케이션이 생성한 상태 토큰
	private final static String CLIENT_ID = "kLjwl2DeIAbAiwjXyWU7";
	private final static String CLIENT_SECRET = "zU1xaaGxQi";
	private final static String REDIRECT_URI = "http://localhost:8080/callback";
	private final static String SESSION_STATE = "oauth_state";
	/* 프로필 조회 API URL */
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";

	private static final Logger logger = LoggerFactory.getLogger(NaverLoginBO.class);
	// UserMapper 및 NaverUserMapper 의존성 주입
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private NaverUserMapper naverUserMapper;

	/* 네이버 아이디로 인증 URL 생성 Method */
	public String getAuthorizationUrl(HttpSession session) {

		/* 세션 유효성 검증을 위하여 난수를 생성 */
		String state = generateRandomString();
		/* 생성한 난수 값을 session에 저장 */
		setSession(session, state);

		/* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 */
		OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI).state(state) // 앞서 생성한 난수값을 인증 URL생성시 사용함
				.build(NaverLoginApi.instance());
		System.out.println("Generated Naver Auth URL: " + REDIRECT_URI);

		return oauthService.getAuthorizationUrl();
	}

	/* 네이버아이디로 Callback 처리 및 AccessToken 획득 Method */
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {

		/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		String sessionState = getSession(session);
		if (StringUtils.pathEquals(sessionState, state)) {

			OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
					.callback(REDIRECT_URI).state(state).build(NaverLoginApi.instance());

			/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			return accessToken;
		}
		return null;
	}

	/* 세션 유효성 검증을 위한 난수 생성기 */
	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	/* http session에 데이터 저장 */
	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
	}

	/* http session에서 데이터 가져오기 */
	private String getSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_STATE);
	}

	/* Access Token을 이용하여 네이버 사용자 프로필 API를 호출 */
	public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {

		OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI).build(NaverLoginApi.instance());

		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();

		// API 응답 로깅
		String responseBody = response.getBody();
		logger.info("Naver API Response: " + responseBody);

		return response.getBody();
	}
	
	
	 private String defaultIfNull(String value, String defaultValue) {
	        return (value != null) ? value : defaultValue;
	    }
	 
	public NaverUserVO getNaverUserInfo(OAuth2AccessToken oauthToken) throws IOException, ParseException {
		// 네이버 사용자 프로필 API를 호출하여 사용자 정보를 가져옴

		String jsonProfile = getUserProfile(oauthToken);
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonProfile);
		JSONObject responseObj = (JSONObject) jsonObj.get("response");

		NaverUserVO naverUser = new NaverUserVO();
		naverUser.setId((String) responseObj.get("id"));
		naverUser.setName((String) responseObj.get("name"));
		naverUser.setNickname(defaultIfNull((String) responseObj.get("nickname"), "네이버 닉네임"));
		//naverUser.setNickname((String) responseObj.get("nickname"));
		naverUser.setEmail((String) responseObj.get("email"));
		 naverUser.setGender(defaultIfNull((String) responseObj.get("gender"), "남자"));
		//naverUser.setGender((String) responseObj.get("gender"));
		// naverUser.setBirthday(convertStringToDate((String) responseObj.get("birthday")));
		// responseObj.get("birthday"))); // 생일
	//	naverUser.setBirthday((String) responseObj.get("birthday"));
		 naverUser.setBirthday(defaultIfNull((String) responseObj.get("birthday"), "1993-03-11"));

		UserVO userVO;

		// 데이터베이스에서 해당 네이버 사용자 ID를 가진 사용자를 검색
		String userId = naverUserMapper.getUserIdByNaverId(naverUser.getId());

		if (userId == null) {

			// 사용자가 데이터베이스에 존재하지 않는 경우, 새 사용자를 추가함
			String tempPassword = UUID.randomUUID().toString(); // 임시 비밀번호생성 UUID

			userVO = new UserVO();
			userVO.setUserid(naverUser.getId());
			userVO.setUsername(naverUser.getName());
			userVO.setNickname(naverUser.getNickname());
			userVO.setEmail(naverUser.getEmail());
			userVO.setGender(naverUser.getGender());
			userVO.setPassword(tempPassword); // 임시 비밀번호로 설정
			// userVO.setBirthday(naverUser.getBirthday()); // 생일 설정 (Date 타입 변환 필요)

			// UserMapper를 사용하여 데이터베이스에 저장
			userMapper.insert(userVO);
			naverUserMapper.insertNaverUser(naverUser.getId(), userVO.getUserid());
			userVO = userMapper.read(userVO.getUserid()); // 새로 생성된 사용자 정보를 읽어옴
		} else {
			// 사용자가 이미 존재하는 경우 해당 사용자 정보를 가져옴
			userVO = userMapper.read(userId);
		}

		// CustomUser 객체 생성 및 SecurityContext에 인증 정보 설정
		CustomUser customUser = new CustomUser(userVO);
		Authentication authentication = new UsernamePasswordAuthenticationToken(customUser, null,
				customUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return naverUser;
	}

}