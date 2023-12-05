package org.bookbook.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserUpdateVO {
		private String userid;
		private String username;
		private String nickname;
		private String email;
		private String password;
		private String orgPassword; // 현재 비밀번호
		private String newPassword; // 새 비밀번호
		private String newPassword2; // 새 비밀번호 확인
		private String encPassword; // 암호화된 새 비밀번호
		private Date birth;
		private String gender;
		private List<AuthVO> authList;
		// 기타 필요한 필드 추가

		// JSON 직렬화에서 이 메소드를 제외
		@JsonIgnore
		public Collection<SimpleGrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			for (AuthVO auth : authList) {
				if (auth.getAuth() != null && !auth.getAuth().isEmpty()) {
					authorities.add(new SimpleGrantedAuthority(auth.getAuth()));
				}
			}
			return authorities;
		}
		// JSON 변환을 위한 커스텀 메소드

		public List<String> getRoles() {
			return this.authList.stream().map(AuthVO::getAuth).collect(Collectors.toList());
		}
	}
