package org.bookbook.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.bookbook.domain.UserUpdateVO;
import org.bookbook.domain.UserVO;
import org.bookbook.exception.DateConversionUtil;
import org.bookbook.service.NotificationServiceimpl;
import org.bookbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/security")
@Log4j
public class SecurityController {

	@Autowired
	NotificationServiceimpl notificationService;

	@Autowired
	UserService service;

	@GetMapping("/login")
	public void login() {

		log.info("login page");
	}

	@GetMapping("/signup")
	public void signup(@ModelAttribute("user") UserVO user) {

	}

	@GetMapping("/checkUserId")
	@ResponseBody
	public Map<String, Boolean> checkUserId(@RequestParam String userid) {
		boolean isAvailable = service.isUserIdAvailable(userid);
		Map<String, Boolean> response = new HashMap<>();
		response.put("isAvailable", isAvailable);
		return response;
	}

	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("user") UserVO user, Errors errors,
			@RequestParam("birth.year") String year, @RequestParam("birth.month") String month,
			@RequestParam("birth.day") String day) throws IOException {

		// 1. 비밀번호, 비밀번호확인 일치여부
		if (!user.getPassword().equals(user.getPasswordcheck())) {
			// 에러 추가
			errors.rejectValue("passwordcheck", "비밀번호 불일치", "비밀번호 확인이 일치하지 않습니다.");
		}

		// 2. 아이디 중복
		if (!errors.hasFieldErrors("userid")) { // username 유효성 통과한 경우에
			// DB에서 userid을 검사
			if (service.get(user.getUserid()) != null) { // 중복일때
				errors.rejectValue("userid", "ID 중복", "이미 사용중인 ID입니다.");
			}
		}

		if (errors.hasErrors()) {
			return "security/signup";
		}

		try {
			user.setBirth(DateConversionUtil.convertToDate(year, month, day));
		} catch (ParseException e) {
			e.printStackTrace(); // 날짜 변환 실패 시 예외 처리
		}

		String email = user.getEmail().replace(",", "@");
		if (email.contains("@type")) {
			email = email.replace("@type", ""); // @type을 빈 문자열로 대체하여 제거
			user.setEmail(email); // @type이 제거된 이메일로 설정
		}

		user.setEmail(email); // 변경된 이메일을 다시 UserVO에 설정

		// DB 저장
		service.register(user);

		return "redirect:/";
	}

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
		// 세션에서 사용자 정보를 가져옵니다.
		UserVO user = (UserVO) session.getAttribute("user");

		// 가져온 사용자 정보를 모델에 추가합니다.
		if (user != null) {
			model.addAttribute("user", user);
			model.addAttribute("userId", user.getUserid()); // 사용자 ID를 모델에 추가
		}

		return "/security/profile";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) throws IOException {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/updateProfile")
	public String showUpdateProfileForm(Model model, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		if (user != null) {
			UserUpdateVO userUpdateVO = new UserUpdateVO();
			// user 객체의 데이터를 userUpdateVO에 설정
			userUpdateVO.setUserid(user.getUserid());
			userUpdateVO.setUsername(user.getUsername());
			userUpdateVO.setNickname(user.getNickname());
			userUpdateVO.setEmail("");
			userUpdateVO.setPassword(user.getPassword());
			userUpdateVO.setGender(user.getGender());
			// 나머지 필드 설정

			model.addAttribute("userUpdate", userUpdateVO);
			return "security/updateProfile";
		} else {
			return "redirect:/security/login";
		}
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@Valid @ModelAttribute("userUpdate") UserUpdateVO userUpdateVO, Errors errors,
			HttpSession session) {
		if (userUpdateVO.getNewPassword() != null && !userUpdateVO.getNewPassword().isEmpty()
				&& !userUpdateVO.getNewPassword().equals(userUpdateVO.getNewPassword2())) {

			errors.rejectValue("newPassword2", "비밀번호 불일치", "새 비밀번호 확인이 일치하지 않습니다.");
			return "security/updateProfile";
		}

	
		service.updateUserProfile(userUpdateVO);

		updateSessionUser(session, userUpdateVO);

		if (errors.hasErrors()) {
			return "security/updateProfile";
		}

		return "redirect:/security/profile";
	}

	private void updateSessionUser(HttpSession session, UserUpdateVO userUpdateVO) {
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		if (sessionUser != null && sessionUser.getUserid().equals(userUpdateVO.getUserid())) {
			sessionUser.setUsername(userUpdateVO.getUsername());
			sessionUser.setNickname(userUpdateVO.getNickname());
			sessionUser.setGender(userUpdateVO.getGender());
			// 세션 정보 업데이트
			session.setAttribute("user", sessionUser);
		}
	}
}
