<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../layouts/header.jsp"%>


<!DOCTYPE html>
<html>
<head>
<title>좋아하는 도서 목록</title>
<style>
.card-container {
	display: flex;
	flex-wrap: wrap;
	gap: 40px;
	justify-content: center; /* 수평 가운데 정렬 */
}

.card {
	width: 200px;
	border: 1px solid #ccc;
	border-radius: 8px;
	overflow: hidden;
	margin-bottom: 20px;
}

.card img {
	width: 100%;
	height: 150px;
	object-fit: cover;
}

.card-body {
	padding: 10px;
	text-align: center;
}

.card-title {
	font-size: 18px;
	font-weight: bold;
	margin-bottom: 10px;
}

.card-text {
	margin-bottom: 8px;
}

/* class가 card이면서 그 안의 hr 태그를 선택 */
.card hr {  
	margin: 10px 0;
	border: none;
	border-top: 1px solid #ccc;
}

.delete-button {
	cursor: pointer;
	color: red;
	margin-top: 10px;
	background: none;
	border: none;
	font-size: 16px;
	text-decoration: underline;
	display: inline-block;
}
</style>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.user.userid" var="userid" />
</sec:authorize>

<script>
    function deleteBook(bookId) {
    	var userid = "${userid}";
        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                url: '/api/deleteLike',
                type: 'POST',
                data: { userId: userid , bookId: bookId }, 
                success: function () {
                    // 성공적으로 처리되면 화면에서 도서를 삭제
                    const cardElement = $('#card-' + bookId);
                    if (cardElement) {
                        cardElement.remove();
                    }
                }
            });
        }
    }
</script>

</head>
<body>

	<h1>좋아하는 도서 목록</h1>

	<c:if test="${not empty likes}">
		<div class="card-container">
			<c:forEach var="like" items="${likes}">
		 
				<div class="card" id="card-${like.bookId}">
					<img src="${like.imageUrl}" alt="${like.title}">
					<div class="card-body">
						<h5 class="card-title">${like.title}</h5>
						<p class="card-text">저자: ${like.author}</p>
						<p class="card-text">출판사: ${like.publisher}</p>
						<p class="card-text">장르: ${like.genre}</p>
						<p class="card-text">카테고리: ${like.category}</p>
						<p class="card-text">도서 ID: ${like.bookId}</p>
						<p class="card-text">사용자 ID: ${like.userId}</p>

						<!-- 삭제 버튼 추가 -->
						<button class="delete-button" onclick="deleteBook(${like.bookId})">삭제</button>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>

	<c:if test="${empty likes}">
		<p>좋아하는 도서가 없습니다.</p>
	</c:if>


</body>
</html>

<%@include file="../layouts/footer.jsp"%>