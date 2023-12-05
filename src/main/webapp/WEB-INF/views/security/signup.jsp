<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="../layouts/header.jsp"%>
<script src="/js/jquery-3.7.0.js"></script>

<link href="/resources/css/signup.css" rel="stylesheet" type="text/css">

<div class="wrap">

	<div class="title">
		<h1>회원가입</h1>
		<div class="hr"></div>
		<div class="content">
			<form:form action="/security/signup" method="post"
				modelAttribute="user" id="signupFrm" autocomplete="off">
				<table class="s-tbl">
					<!-- 아이디 필드 -->
					<tr>
						<th><form:label path="userid">아이디</form:label></th>
						<td>
							<div class="id-input-container">
								<form:input path="userid" class="s-input-form" id="userid" placeholder="4~12자의 영문자 및 숫자만 가능합니다" />
								<button type="button" onclick="checkUserId()" class="check-btn">중복확인</button>
							</div> <form:errors path="userid" cssClass="error" /> <span
							id="useridCheckResult"></span>
						</td>
					</tr>


					<!-- 이름 필드 -->
					<tr>
						<th><form:label path="username">이름
						</form:label></th>
						<td><form:input path="username" class="s-input-form"
								id="username" placeholder="이름을 입력해주세요"  /> <form:errors path="username"
								cssClass="error" /></td>
					</tr>
					<!-- 닉네임 필드 -->
					<tr>
						<th><form:label path="nickname">닉네임
						</form:label></th>
						<td><form:input path="nickname" class="s-input-form"
								id="memberPhone" placeholder="닉네임을 입력해주세요" /> <form:errors path="nickname"
								cssClass="error" /></td>
					</tr>
					<!-- 비밀번호 필드 -->
					<tr>
						<th><form:label path="password">비밀번호
						</form:label></th>
						<td><form:password path="password" class="s-input-form"
								id="password" placeholder="비밀번호를 입력해주세요" /> <form:errors path="password" cssClass="error" />
						</td>
					</tr>
					<!-- 비밀번호 확인 필드 -->
					<tr>
						<th><form:label path="passwordcheck">비밀번호 확인
						</form:label></th>
						<td><form:password path="passwordcheck" class="s-input-form"
								id="passwordcheck" placeholder="비밀번호를 한번 더 입력해주세요" /> <form:errors path="passwordcheck"
								cssClass="error" /></td>
					</tr>
					<!-- 이메일 필드 -->
					<tr>
						<th><form:label path="email">이메일
						</form:label></th>
						<td>
							<div id="SuperviserEmail" class="email-group">
								<form:input path="email" class="s-input-form" id="viserEmail"
								 />
								<span id="displayEmail"></span> <input class="s-input-form box"
									id="domain-txt" type="text" /> <select
									class="s-input-form box" id="domain-list">
									<option value="naver.com">naver.com</option>
									<option value="google.com">google.com</option>
									<option value="kakao.com">kakao.com</option>
									<option value="type">직접 입력</option>
								</select>
							</div> <form:errors path="email" cssClass="error" />
						</td>
					</tr>

					<script>
    const domainListEl = document.querySelector('#domain-list');
    const domainInputEl = document.querySelector('#domain-txt');
    const emailDisplay = document.querySelector('#displayEmail');
    const viserEmail = document.querySelector('#viserEmail');

    domainListEl.addEventListener('change', (event) => {
    	
        if (event.target.value !== "type") {
            domainInputEl.value = event.target.value;
            domainInputEl.disabled = true;
            
         // domainInputEl이 직접 입력일 때는 emailDisplay에 표시하지 않도록
            emailDisplay.textContent = `${viserEmail.value}@${event.target.value}`;
        } else {
            domainInputEl.value = "";
            domainInputEl.disabled = false;
       //     emailDisplay.textContent = viserEmail.value; // 직접 입력시 emailDisplay에 @type을 포함시키지 않음
        }

        // Email 생성 및 표시 로직
        const domain = domainInputEl.value;
       emailDisplay.textContent = `${viserEmail.value}@${domain}`;
    });

    // 초기화
    domainInputEl.disabled = true;
</script>

					<!-- 생일 필드 -->
					<tr>
						<th><form:label path="birth">생일</form:label></th>
						<td>
							<div class="birthday-select-group">
								<select class="s-input-form box" id="birth.year"
									name="birth.year">
									<!-- 연도 옵션들 -->
									<option disabled selected>Year</option>
									<c:forEach var="year" begin="1950" end="2023">
										<option value="${year}">${year}</option>
									</c:forEach>
								</select> <select class="s-input-form box" id="birth.month"
									name="birth.month">
									<!-- 월 옵션들 -->
									<option disabled selected>월</option>
									<c:forEach var="month" begin="1" end="12">
										<option value="${month}">${month}</option>
									</c:forEach>
								</select> <select class="s-input-form box" id="birth.day"
									name="birth.day">
									<!-- 일 옵션들 -->
									<option disabled selected>일</option>
									<c:forEach var="day" begin="1" end="31">
										<option value="${day}">${day}</option>
									</c:forEach>
								</select>
							</div> <form:errors path="birth" cssClass="error" />
						</td>
					</tr>
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
					<button type="submit" class="s-btn s-bc1">회원가입</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script>
function checkUserId() {
    var userid = document.getElementById('userid').value;
    if (!userid) {
        alert('확인할 ID를 입력해주세요');
        return;
    }

    // 입력 검증 (예시: 길이와 문자 유형)
    if (!/^[a-zA-Z0-9]{4,12}$/.test(userid)) {
        alert('아이디는 4~12자의 영문자 및 숫자만 가능합니다');
        return;
    }

    // 로딩 상태 표시 (예시)
    document.getElementById('useridCheckResult').innerText = '확인 중...';

    // 서버로 AJAX 요청 보내기
    fetch('/security/checkUserId?userid=' + encodeURIComponent(userid))
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 에러 발생');
            }
            return response.json();
        })
        .then(data => {
            if (data.isAvailable) {
                document.getElementById('useridCheckResult').innerText = '사용 가능한 ID입니다';
            } else {
                document.getElementById('useridCheckResult').innerText = '이미 사용 중인 ID입니다';
            }
        })
        .catch(error => {
            console.error('에러 발생:', error);
            document.getElementById('useridCheckResult').innerText = 'ID 확인 중 에러 발생';
        });
}
</script>

<%@ include file="../layouts/footer.jsp"%>