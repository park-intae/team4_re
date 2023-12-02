<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layouts/header.jsp"%>
<%-- 개별 페이지 --%>
<div class="center">
	<h1>${book.title}</h1>
</div>


<div>




	<hr>

	<div class="clearfix">
		<div class="image-panel float-left mr-3">
			<img src="${book.imageUrl}" width="200" height="300" align="left">
		</div>

		<div>${book.bookintro}</div>
		<br>
	</div>

	<div class="mt-5">
		${book.author} <br> ${book.publisher } <br>
		${book.publicationdate}
	</div>


	<!-- 	<div class="thumb-images my-5 d-flex"> -->
	<%-- 		<c:forEach var="image" items="${best.images}"> --%>
	<%-- 			<a href="${image}" data-fancybox="gallery"> <img src="${image}"> --%>
	<!-- 			</a> -->
	<%-- 		</c:forEach> --%>
	<!-- 	</div> -->
	<hr>

	<!-- 	<div style="border: 1px solid; padding: 10px; border-radius: 15%;"> -->
	<%-- 		<c:forEach var="recommend" items=" ${best.recommend}"> --%>
	<%-- 		</c:forEach> --%>
	<!-- 	</div> -->

	<div>
		<a href="">추천 생성</a>

	</div>
</div>





<style>
.card-container {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
}

.card {
	width: 200px;
	border: 1px solid #ccc;
	border-radius: 8px;
	overflow: hidden;
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
	margin: 50px 0 0 0; /* 수정된 여백 설정 */
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
</style>



<c:if test="${not empty bookByCBF}">
	<ul>
		<div class="card-container">
			<c:forEach var="bookAI" items="${bookByCBF}">
				<div class="card">
					<a href="/book/detail?bookid=${bookAI.bookid}"> <img
						src="${bookAI.imageUrl}" alt="${bookAI.title}"
						class="card-img-top">
					</a>
					<div class="card-body">
						<a href="/book/detail?bookid=${bookAI.bookid}">
							<h5 class="card-title">${bookAI.title}</h5>
						</a>
						<p class="card-text">저자: ${bookAI.author}</p>
						<p class="card-text">출판사: ${bookAI.publisher}</p>
						<p class="card-text">장르: ${bookAI.genre}</p>
						<p class="card-text">카테고리: ${bookAI.category}</p>
						<p class="card-text"></p>
						<button class="btn btn-primary"
							onclick="likeBook('${bookAI.bookid}')">
							<i class="bi-heart"></i>좋아요
						</button>
						<button class="btn btn-primary" onclick="addToFavorites('')">즐겨찾기</button>
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

						<p class="card-text">저자: ${best.author}</p>
						<p class="card-text">출판사: ${best.publisher}</p>
						<p class="card-text"></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</ul>
</c:if>



<%@ include file="../layouts/footer.jsp"%>