<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="../layouts/header.jsp"%>
<script src="/js/jquery-3.7.0.js"></script>

<link href="/resources/css/signup.css" rel="stylesheet" type="text/css">


<div class="wrap">
	<div class="title">
		<h1>회원정보수정</h1>
		<div class="hr"></div>
		<div class="content">
			<form:form action="/security/updateProfile" method="post"
				modelAttribute="userUpdate" id="updateProfile" autocomplete="off">
				<table class="s-tbl">

					<!-- 아이디 필드 -->
					<tr style="display: none;">
						<th><form:label path="userid">아이디</form:label></th>
						<td><form:input path="userid" class="s-input-form"
								id="userid" placeholder="아이디를 입력해주세요" readonly="true" /> <form:errors
								path="userid" cssClass="error" /></td>
					</tr>

					<!-- 이름 필드 -->
					<tr>
						<th><form:label path="username">이름</form:label></th>
						<td><form:input path="username" class="s-input-form"
								id="username" placeholder="이름을 입력해주세요" /> <form:errors
								path="username" cssClass="error" /></td>
					</tr>

					<!-- 닉네임 필드 -->
					<tr>
						<th><form:label path="nickname">닉네임</form:label></th>
						<td><form:input path="nickname" class="s-input-form"
								id="memberPhone" placeholder="닉네임을 입력해주세요" /> <form:errors
								path="nickname" cssClass="error" /></td>
					</tr>

					<!-- 현재 비밀번호 필드 -->
					<tr>
						<th><form:label path="orgPassword">현재 비밀번호</form:label></th>
						<td><form:password path="orgPassword" class="s-input-form"
								id="orgPassword" placeholder="현재 비밀번호를 입력해주세요" /> <form:errors
								path="orgPassword" cssClass="error" /></td>
					</tr>

					<!-- 새 비밀번호 필드 -->
					<tr>
						<th><form:label path="newPassword">새 비밀번호</form:label></th>
						<td><form:password path="newPassword" class="s-input-form"
								id="newPassword" placeholder="새 비밀번호를 입력해주세요" /> <form:errors
								path="newPassword" cssClass="error" /></td>
					</tr>

					<!-- 새 비밀번호 확인 필드 -->
					<tr>
						<th><form:label path="newPassword2">비밀번호 확인</form:label></th>
						<td><form:password path="newPassword2" class="s-input-form"
								id="newPassword2" placeholder="새 비밀번호를 한번 더 입력해주세요" /> <form:errors
								path="newPassword2" cssClass="error" /></td>
					</tr>

					<!-- 이메일 필드 -->
					
					<!-- 생일 필드 -->
					
					<!-- 성별 필드 -->
					<tr>
						<th><form:label path="gender">성별
						</form:label></th>
						<td>
							<div class="gender-option">
								<input type="radio" id="male" name="gender" value="남성"
									class="gender-radio"> <label for="male">남성</label> <input
									type="radio" id="female" name="gender" value="여성"
									class="gender-radio"> <label for="female">여성</label>
							</div> <form:errors path="gender" cssClass="error" />
						</td>
					</tr>
				</table>
				<div class="btn-box">
					<button type="submit" class="s-btn s-bc1">회원정보수정</button>
				</div>
			</form:form>
		</div>
	</div>
</div>


<%@ include file="../layouts/footer.jsp"%>