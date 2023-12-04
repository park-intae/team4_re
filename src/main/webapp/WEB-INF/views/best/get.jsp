<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<%@ include file="../layouts/header.jsp"%>
<%-- 개별 페이지 --%>
<div class="center">
	<h1>${best.title}</h1>
</div>
<style>
.fixed-content {
	margin-left: 335px;
	margin-top: 60px;
}

</style>
<div>
	<hr>
	<div class="clearfix">
		<div class="fixed-content">
			${best.author} <br> ${best.publisher } <br>
			${best.publication_date}
		</div>


		<div class="image-panel float-left mr-3">
			<img src="${best.images}" width="200" height="300" align="left">
		</div>

		<div>${best.intro}</div>
		<br>

	</div>

	<br>


	<hr>



	<link href="../../../resources/css/star.css" rel="stylesheet" />

	<form class="mb-3" name="myform" id="myform" method="post" action="/best/submitRating"
		>
		<fieldset>
<%-- 			<span class="text-bold">${ratings.average_rating}</span>  --%>
			
			<input type="hidden" name="column1" value="${best.column1}">
			<input type="hidden" name="userId" value="${userId}">
			<input type="radio" name="rating" value="5" id="rate1"> <label for="rate1">★</label> 
			<input type="radio" name="rating" value="4" id="rate2"> <label for="rate2">★</label> 
			<input type="radio" name="rating" value="3" id="rate3"> <label for="rate3">★</label>
			<input type="radio" name="rating" value="2" id="rate4"> <label for="rate4">★</label> 
			<input type="radio" name="rating" value="1" id="rate5"> <label for="rate5">★</label> 
			<input type="submit" value="별점 제출">
		</fieldset>
	</form>






	<div>
		<script>
let nowUrl = window.location.href;
function copyUrl(){ 
  	navigator.clipboard.writeText(nowUrl).then(res=>{
	  alert("주소가 복사되었습니다!");
	})
}
</script>
	</div>


	<!-- 	<div class="thumb-images my-5 d-flex"> -->
	<%-- 		<c:forEach var="image" items="${best.images}"> --%>
	<%-- 			<a href="${image}" data-fancybox="gallery"> <img src="${image}"> --%>
	<!-- 			</a> -->
	<%-- 		</c:forEach> --%>
	<!-- 	</div> -->

	<!-- 추천평 -->
	<div>
		<%-- 		<c:forEach var="rating_review" items=" ${book_rating.rating_review}"> --%>
		<%-- 		</c:forEach> --%>

		<c:forEach var="rating" items="${ReviewVO}">
			<p>User ID: ${rating.user_id}</p>
			<p>Rating: ${rating.rating}</p>
			<p>Review: ${rating.ratinReview}</p>
			<!-- 기타 필요한 출력 요소를 추가할 수 있습니다. -->
		</c:forEach>
	</div>

	<!-- 추천생성 -->
	<div>
		<form method="post">
			<input type="hidden" name="bookId" value="${book_rating.book_id}">
			<div>
				<label for="title">제목:</label> <input type="text" id="title"
					name="title" required>
			</div>
			<div>
				<label for="content">내용:</label>
				<textarea id="content" name="content" rows="4" cols="50" required></textarea>
			</div>

			<div>
				<input type="submit" value="추천평 작성">
				<button type="button" class="copy-btn" onclick="copyUrl()">링크
					복사</button>

			</div>
	</div>
	</form>
</div>
</div>
<%@ include file="../layouts/footer.jsp"%>