<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"

		prefix="sec"%>
<link 
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" 
		rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

<%@ include file="../layouts/header.jsp"%>


<sec:authorize access="isAuthenticated()">

    <sec:authentication property="principal.user.userid" var="userid" />

	<!-- 현재 로그인한 사용자의 정보를 불러옵니다. -->
	<c:set var="user"
		value="${not empty sessionScope.naverUser ? sessionScope.naverUser : user}" />
	</sec:authorize>
	<script>
    // 전역 변수로 선언합니다.
    var loggedInUserId = '${userid}';
	</script>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script>
	var currentUserId = "${loggedInUserId}";
</script>

<!-- 프로필 정보 섹션 -->

<div class="page-container">
	<div class="line-separator"></div>

	<div class="main-border">
		<!-- <div class="upper-div"></div> -->

		<div class="inner-container">

			<div class="round-div">	<sec:authorize access="isAuthenticated()">
        <c:choose>
            <c:when test="${not empty sessionScope.naverUser}">
                <!-- 네이버 로그인 사용자의 프로필 이미지 -->
                <img src="https://api.dicebear.com/7.x/identicon/svg?seed=${sessionScope.naverUser.id}" alt="Profile Image" class="profile-image" />
            </c:when>
            <c:otherwise>
                <!-- 일반 로그인 사용자의 프로필 이미지 -->
                <img src="https://api.dicebear.com/7.x/identicon/svg?seed=${user.userid}" alt="Profile Image" class="profile-image" /> 
            </c:otherwise>
        </c:choose>
    </sec:authorize>   
    
    </div>
		


			<div class="info-follow-container">
				<div class="info-container">
					<c:choose>
						<c:when test="${not empty sessionScope.naverUser}">
							<!-- 네이버 로그인 사용자 정보 표시 -->
							<!-- 이름과 닉네임을 함께 배치 -->
							<div class="flex-row-container">
								<div class="label-item-container" >
								
									<div class="label" >이름</div>
									<div class="item-name" >${sessionScope.naverUser.name}</div>
								</div>
								<div class="label-item-container">
									<div class="label">닉네임</div>
									<div class="item-nickname">${sessionScope.naverUser.nickname}</div>
								</div>
							</div>

							<!-- 생일과 성별을 함께 배치 -->
							<div class="flex-row-container">
								<div class="label-item-container">
									<div class="label">생일</div>
									<div class="item-birth">${sessionScope.naverUser.birthday}</div>
								</div>
								<div class="label-item-container">
									<div class="label">성별</div>
									<div class="item-gender">${sessionScope.naverUser.gender}</div>
								</div>
							</div>

							<!-- 이메일 주소 -->
							<div class="label-item-container">
								<div class="label">이메일주소</div>
								<div class="item-email">${sessionScope.naverUser.email}</div>
							</div>
						</c:when>
						<c:otherwise>
							<!-- 일반 사용자 정보 표시 -->
							<!-- 이름과 닉네임을 함께 배치 -->
							<div class="flex-row-container">
								<div class="label-item-container">
									<div class="label">이름</div>
									<div class="item-name">${user.username}</div>
								</div>
								<div class="label-item-container">
									<div class="label">닉네임</div>
									<div class="item-nickname">${user.nickname}</div>
								</div>
							</div>

							<!-- 생일과 성별을 함께 배치 -->
							<div class="flex-row-container">
								<div class="label-item-container">
									<div class="label">생일</div>
									<div class="item-birth">
										<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd" />
									</div>
								</div>
								<div class="label-item-container">
									<div class="label">성별</div>
									<div class="item-gender">${user.gender}</div>
								</div>
							</div>

							<!-- 이메일 주소 -->
							<div class="label-item-container">
								<div class="label">이메일주소</div>
								<div class="item-email">${user.email}</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="button-container">
					<button id="updateButton" class="btn btn-secondary"><i class="fa-solid fa-user-pen"></i> 정보수정 </button>
<script>
    document.getElementById('updateButton').addEventListener('click', function() {
    	  window.location.href = '/security/updateProfile';

    });
</script>
						<button id="showFollowModal" class="btn btn-primary"><i class="fa-brands fa-twitter"></i> 팔로우 </button>
						
						<button id="likeButton" class="btn btn-secondary"> <i class="far fa-thumbs-up"></i> 좋아요 </button>
						<script>

							document.getElementById('likeButton')
									.addEventListener('click', function() {
                                        window.location.href = `/book/likes?userId=${userid}`;
									});
						</script>
						</div>
				</div>

				<div class="item-follow">
					<!-- -->
					<!--회원 정보 수정 버튼 자리 예정 -->
				</div>
			</div>
		</div>
	</div>




	<div class="line-separator"></div>

	<div class="review-title">
	<div class="review-title-text">나의 리뷰</div>
	
	</div>
	<div class="review-content"></div>

	<div class="line-separator"></div>



	<div class="alarm-title"> 
	<div class="alarm-title-text">알림 내역</div>
</div>
	<div class="alarm-content">  
	<div class="os-content">
        <!-- 여기에 알림 내용이 동적으로 추가됩니다 -->
    </div>
	 </div>



	<div class="line-separator"></div>


<!-- 사용자 프로필 및 정보 -->
<div class="user-profile">
	<!-- 프로필 정보 -->

</div>


<!-- 팔로우 모달창 -->
<link rel="stylesheet" href="/resources/css/modal.css" />
<div id="followModal" class="modal_wrapper" style="display: none;">
	<div class="modal-content">
		<span class="close" onclick="closeFollowModal()">&times;</span>
		<h2>팔로우</h2>
		<div id="userList">
			<!-- 사용자 목록 표시 -->
		</div>
	</div>
</div>

<!-- 모달창 overlayscrollbars -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/css/OverlayScrollbars.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/js/OverlayScrollbars.js"></script>

<!-- 모달창 overlayscrollbars script-->
<script>
	document.addEventListener("DOMContentLoaded", function() {
		OverlayScrollbars(document.querySelectorAll('.modal-content'), {});
	});
</script>




<link href="/resources/css/mypage.css" rel="stylesheet" type="text/css">


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	//팔로우 모달창 표시
	$('#showFollowModal').click(function() {
		$('#followModal').show();
		loadUsers();
	});

	// 모달창 닫기 함수
	function closeFollowModal() {
		$('#followModal').hide();
	}

	// 사용자 목록 로드
	function loadUsers() {
		console.log("Sending AJAX request");
		$
				.ajax({
					url : '/api/usersWithFollowStatus',
					method : 'GET',
					success : function(users) {
						var userList = $('#userList');
						userList.empty();
						var table = $('<table>').addClass('table');

						var tbody = $('<tbody>');

						users
								.forEach(function(user) {
									if (user.userid !== currentUserId) { // 현재 로그인한 사용자 제외
										var tr = $('<tr>').append(
												'<td>' + user.nickname
														+ '</td>').append(
												'<td>' + "" + '</td>');
										var followText = user.followStatus ? '언팔로우'
												: '팔로우';
										tr
												.append('<td><button data-userid="' + user.userid + '" class="btn btn-outline-primary">'
														+ followText
														+ '</button></td>');
										tbody.append(tr);
									}
								});

						table.append(tbody);
						userList.append(table);
					},
					error : function(error) {
						// 오류 처리
					}
				});
	}

	// 팔로우 상태 토글
	function toggleFollow(userId) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			url : '/security/toggleFollow',
			method : 'POST',
			data : {
				userId : userId
			},
			beforeSend : function(xhr) {
				if (header) {
					xhr.setRequestHeader(header, token);
				}
			},
			success : function(isFollowing) {
				console.log("Toggle follow response for user " + userId + ": "
						+ isFollowing);
				var button = $('button[data-userid="' + userId + '"]');
				updateButtonState(button, isFollowing);
			},
			error : function(error) {
				console.error('Error toggling follow:', error);
			}
		});
	}

	//버튼 상태 업데이트 
	function updateButtonState(button, isFollowing) {
		if (isFollowing) {
			button.text('언팔로우');
		} else {
			button.text('팔로우');
		}
	}

	$('#userList').on('click', 'button', function() {
		var userId = $(this).data('userid');
		console.log("Toggling follow for user " + userId);
		toggleFollow(userId);
	});

	//  모달창 닫기
	window.onclick = function(event) {
		if (event.target == document.getElementById('followModal')) {
			closeFollowModal();
		}
	};
</script>

<script src="/resources/js/myreview.js"></script>
<%@ include file="../layouts/footer.jsp"%>