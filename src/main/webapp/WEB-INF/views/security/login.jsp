<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../layouts/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$
								.get(
										"/naver-login-url",
										function(data) {
											$("#naver_id_login")
													.html(
															'<a href="' + data + '"><img width="223" src="/resources/images/naver_Bn_Green.png" alt="네이버 로그인" /></a>');
										});
					});
</script>

<link href="/resources/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
	<!-- 로그인 폼 -->
	<div class="wrap">
		<div class="title">
			<h1>로그인</h1>

			<p class="text-end"></p>
			<div class="hr-solid"></div>

			<!-- 로그인 폼 코드 -->
			<c:if test="${param.error == 'true' }">
				<div class="error">사용자 ID 또는 비밀번호가 일치하지 않습니다.</div>
			</c:if>

			<c:if test="${param.error == 'login_required' }">
				<div class="error">로그인이 필요한 서비스입니다.</div>
			</c:if>

			<form action="/security/login" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				<div class="form-group">
					<label for="userid"></label> <input type="text" name="userid"
						id="userid" class="s-input-form" placeholder="아이디를 입력해주세요"
					/>
				</div>

				<div class="form-group">
					<label for="password"> </label> <input type="password"
						name="password" id="password" class="s-input-form"
						placeholder="비밀번호를 입력해주세요" />
				</div>

				<!-- 로그인 유지 버튼 -->
				<div class="form-remember">
					<input type="checkbox" name="remember-me" id="remember-me"
						class="form-check-input" /> <label for="remember-me"
						class="form-check-label">로그인 유지</label>
				</div>
				
				
				

				<button type="submit" class="s-btn s-bc1">로그인</button>

				<button type="button" onclick="location.href='signup'"
					class="s-btn s-bc2">회원가입</button>

				<!-- 네이버 로그인 버튼 & 추가 기능 구현 필요 
				<div id="naver_id_login"
					style="text-align: center; margin-top: 20px;"></div>
		</div> -->

<form id="logoutForm" action="/security/logout" method="post">

		</form>
</body>
</html>
<%@include file="../layouts/footer.jsp"%>