<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../layouts/header.jsp"%>

<%@include file="../layouts/sidebar.jsp"%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<!-- <h1>페이지 타이틀</h1> -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.user.userid" var="userid" />
</sec:authorize>

<script type="text/javascript">
	function likeBook(bookId, title, icon) {
		var userid = "${userid}";
		
		console.log("")
		$.ajax({			
			type : 'POST',
			url : '/api/addLike', // 좋아요를 처리하는 컨트롤러의 URL
			data : {
				userId : userid, // 사용자 아이디를 동적으로 설정하거나 가져와야 합니다.
				bookId : bookId, // 좋아요를 누른 도서의 아이디
				title : title
			},
			success : function(response) {
				// 성공적으로 요청이 완료된 경우 실행할 코드
				console.log(response);
				// 이 부분에 필요한 업데이트 로직을 추가할 수 있습니다.

	
				// 아이콘의 클래스를 토글
	            $(icon).toggleClass('bi-heart bi-heart-fill');
			}
		})
	}
	</script>



<style>
.card-container {
	display: flex;
	/* flex-wrap: wrap; */
	gap: 50px;
	justify-content: center; /* 수평 가운데 정렬 */
	align-items: center; /* 수직 가운데 정렬 */
	margin: 30px auto; /* 여백 추가 및 수평 가운데 정렬을 위해 auto 설정 */
}

.card {
	width: 200px;
	border: 1px solid #ccc;
	border-radius: 8px;
	overflow: hidden;
}

.card-text {
	font-size: 14px; /* 폰트 크기를 14px로 조정 */
	max-width: 100px; /* 최대 너비를 100px로 제한 */
/* 	overflow: hidden; /* 내용이 너무 길 경우 숨김 처리 */ */
/* 	text-overflow: ellipsis; /* 내용이 너무 길 경우 말줄임표로 표시 */ */
	white-space: nowrap; /* 줄 바꿈 방지 */
	 margin: 0 auto; /* 수평 가운데 정렬을 위해 추가 */
    text-align: center; /* 텍스트 가운데 정렬 */
}

.bold-text {
    font-weight: bold;
}

.card-img-top {
	width: 100%;
	height: 150px;
	object-fit: cover;

}

.card-body {
	padding: 10px;
	text-align: center;
}

.pageInfo {
	list-style: none;
	display: flex;
	justify-content: center; /* 가로 중앙 정렬 */
	align-items: center; /* 세로 중앙 정렬 */
	margin: 50px 0 0 0;
	justify-content: center; /* 가로 중앙 정렬 */
	align-items: center; /* 수정된 여백 설정 */
}

.pageInfo li {
	float: left;
	font-size: 20px;
	margin-left: 18xp;
	padding: 20px;
	font-weight: 500;
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: underline;
}

.active {
	background-color: #cdd5ec;
}

h1 {
	text-align: center; /* 텍스트를 오른쪽으로 정렬합니다. */
	border-bottom: 2px solid gray;
}

.result-container,.recommend-container *{
	 list-style: none;
	 padding: 0px 2%;
}

ul{
	padding: 0px;
}
</style>


<h1>Book List</h1>

<div class="result-container">
	<c:if test="${not empty list}">
		<ul>

			<%-- <li>${book}</li> 이 코드 용도가 뭡니까?--%>
			<div class="card-container">
				<c:forEach var="book" items="${list}">
					<div class="card">
						<a href="/book/detail?bookid=${book.bookid}"> <img
							src="${book.imageUrl.split(',')[0]}" alt="${book.title}"
							class="card-img-top">
						</a>
						<div class="card-body">
							<a href="/book/detail?bookid=${book.bookid}">
								<h5 class="card-title">${book.title}</h5>
							</a>
							<p class="card-text"><span class="bold-text">저자:</span>${book.author}</p>
						<p class="card-text"><span class="bold-text">출판사:</span>${book.publisher}</p>
						<p class="card-text"><span class="bold-text">장르:</span>${book.genre}</p>
						<p class="card-text"><span class="bold-text">카테고리:</span>${book.category}</p>
							<i class="bi bi-heart text-danger" style="cursor: pointer;"
								onclick="likeBook('${book.bookid}', '${book.title}', this)"></i>

						</div>
					</div>
				</c:forEach>
			</div>

		</ul>

		<form id="paginationForm" action="/book/list" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
			<input type="hidden" name="keywords" value="${param.keywords}">
			<input type="hidden" name="selectedCategories"
				value="${param.selectedCategories}"> <input type="hidden"
				name="selectedTopics" value="${param.selectedTopics}"> <input
				type="hidden" name="bookType" value="${param.bookType}">

			<!-- 필요한 경우 다른 검색 매개변수를 추가로 숨은 필드로 포함합니다 -->
		</form>

		<div class="pageInfo_wrap">
			<div class="pageInfo_area">
				<ul id="pageInfo" class="pageInfo">
					<!-- 이전페이지 버튼 -->
					<c:if test="${pageMaker.prev}">
						<li class="pageInfo_btn previous"><a
							href="javascript:void(0);"
							onclick="submitPage(${pageMaker.startPage-1})"><i class="fa-solid fa-chevron-left"></i></a></li>
					</c:if>

					<!-- 각 번호 페이지 버튼 -->
					<c:forEach var="num" begin="${pageMaker.startPage}"
						end="${pageMaker.endPage}">
						<li
							class="pageInfo_btn ${pageMaker.cri.pageNum == num ? 'active' : ''}">
							<a href="javascript:void(0);" onclick="submitPage(${num})">${num}</a>
						</li>
					</c:forEach>

					<!-- 다음페이지 버튼 -->
					<c:if test="${pageMaker.next}">
						<li class="pageInfo_btn next"><a href="javascript:void(0);"
							onclick="submitPage(${pageMaker.endPage + 1})"><i class="fa-solid fa-chevron-right"></i></a></li>
					</c:if>
				</ul>
			</div>
		</div>

		<script>
    function submitPage(pageNum) {
        document.getElementById('paginationForm').elements['pageNum'].value = pageNum;
        document.getElementById('paginationForm').submit();
    }
</script>


	</c:if>
</div>
<c:if test="${empty list}">
	<p>No books found.</p>
</c:if>






<h1>recommend book</h1>

<c:if test="${not empty bookByCBF}">
	<ul>
		<div class="card-container">
			<c:forEach var="bookAI" items="${bookByCBF}">
				<div class="card">
					<a href="/book/detail?bookid=${bookAI.bookid}"> <img
						src="${bookAI.imageUrl.split(',')[0]}" alt="${bookAI.title}"
						class="card-img-top">
					</a>

					<div class="card-body">
						<a href="/book/detail?bookid=${bookAI.bookid}">
							<h5 class="card-title">${bookAI.title}</h5>
						</a>
						<p class="card-text"><span class="bold-text">저자:</span>${bookAI.author}</p>
						<p class="card-text"><span class="bold-text">출판사:</span>${bookAI.publisher}</p>
						<p class="card-text"><span class="bold-text">장르:</span>${bookAI.genre}</p>
						<p class="card-text"><span class="bold-text">카테고리:</span>${bookAI.category}</p>
						<p class="card-text"></p>
						<i class="bi bi-heart text-danger" style="cursor: pointer;"
							onclick="likeBook('${bookAI.bookid}', '${bookAI.title}', this)"></i>
					</div>
				</div>
			</c:forEach>
		</div>
	</ul>

</c:if>


<c:if test="${empty bookByCBF}">
	<ul>
		<div class="card-container">
			<c:forEach var="best" items="${best}">
				<div class="card">
					<a href="/best/get?column1=${best.column1}"> <img
						src="${best.images}" alt="${best.title}" class="card-img-top">
					</a>
					<div class="card-body">
						<a href="/best/get?column1=${best.column1}">
							<h5 class="card-title">${best.title}</h5>
						</a>

						<p class="card-text"><span class="bold-text">저자:</span>${best.author}</p>
						<p class="card-text"><span class="bold-text">출판사:</span>${best.publisher}</p>
						<p class="card-text"></p>
						<i class="bi bi-heart text-danger" style="cursor: pointer;"
							onclick="likeBook('${best.column1}', '${best.title}', this)"></i>
					</div>
				</div>
			</c:forEach>
		</div>
	</ul>
</c:if>

<%@include file="../layouts/footer.jsp"%>
