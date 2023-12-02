<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>


<sec:authentication property="principal.userid" var="loggedInUserId" />

<!-- 팔로우 목록 -->
<sec:authorize access="isAuthenticated()">
    <c:forEach var="follower" items="${followers}">
        <div class="follower">
            <c:choose>
                <c:when test="${follower.userid.startsWith('Naver_')}">
                    <!-- 네이버 사용자일 경우 표시 -->
                    <span>${follower.nickname}</span> <!-- 네이버 사용자 닉네임 -->
                </c:when>
                <c:otherwise>
                    <!-- 일반 사용자일 경우 표시 -->
                    <span>${follower.username}</span>
                </c:otherwise>
            </c:choose>
            <c:if test="${follower.userid != loggedInUserId}">
                <!-- 팔로우/언팔로우 버튼 표시 -->
                <button onclick="toggleFollow('${follower.userid}')">${follower.followStatus ? '언팔로우' : '팔로우'}</button>
            </c:if>
        </div>
    </c:forEach>
</sec:authorize>

<script>
    function toggleFollow(userId) {
        $.ajax({
            url: '/api/toggleFollow',
            type: 'POST',
            data: { userId: userId },
            success: function(isFollowing) {
                if(isFollowing) {
                    alert('팔로우되었습니다.');
                } else {
                    alert('언팔로우되었습니다.');
                }
            },
            error: function(error) {
                alert('오류가 발생했습니다.');
            }
        });
    }
</script>