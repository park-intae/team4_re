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

<div>
	<hr>

	<div class="mt-5">
		${best.author} <br> ${best.publisher } <br>
		${best.publication_date}
	</div>

	<div class="clearfix">
		<div class="image-panel float-left mr-3">
			<img src="${best.images}" width="200" height="300" align="left">
		</div>

		<div>${best.intro}</div>
		<br>
	</div>


	<br>





	<link href="../../../resources/css/star.css" rel="stylesheet" />

	<form class="mb-3" name="myform" id="myform" method="post"
		action="/api/bookbook/rating/add">
		<fieldset>
			<span class="text-bold">${ratings.average_rating}</span> <input
				type="radio" name="rating" value="5" id="rate1"> <label
				for="rate1">★</label> <input type="radio" name="rating" value="4"
				id="rate2"> <label for="rate2">★</label> <input type="radio"
				name="rating" value="3" id="rate3"> <label for="rate3">★</label>
			<input type="radio" name="rating" value="2" id="rate4"> <label
				for="rate4">★</label> <input type="radio" name="rating" value="1"
				id="rate5"> <label for="rate5">★</label> <input
				type="submit" value="별점 제출">
		</fieldset>
	</form>

	<script>
document.addEventListener("DOMContentLoaded", function() {
	  document.getElementById("myform").addEventListener("submit", function(event) {
	    event.preventDefault(); // 기본 제출 동작 방지

	    var selectedRating = document.querySelector('input[name="rating"]:checked');
	    if (!selectedRating) {
	      alert("별점을 선택해주세요.");
	      return;
	    }

	    var ratingValue = selectedRating.value;

	    // 선택한 별점 값을 가져왔으므로, 서버로 전송하거나 다른 작업을 수행할 수 있습니다.
	    sendDataToServer(ratingValue); // 서버로 데이터를 전송하는 함수 호출
	  });
	});

	function sendDataToServer(rating) {
	  // Fetch API를 사용하여 서버로 데이터 전송
	  fetch("/api/bookbook/rating/add", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json"
	    },
	    body: JSON.stringify({ "rating": rating, "bookId": bookId })
	  })
	  .then(response => {
	    if (!response.ok) {
	      throw new Error("Network response was not ok.");
	    }
	    return response.json();
	  })
	  .then(data => {
	    // 서버로부터 받은 응답에 대한 처리
	    console.log("서버 응답:", data);
	    // 별도의 처리가 필요한 경우 여기에 작성
	  })
	  .catch(error => {
	    console.error("There has been a problem with your fetch operation:", error);
	    // 오류 처리가 필요한 경우 여기에 작성
	  });
	}

</script>







	<div>
		<div>
			<button type="button" class="copy-btn" onclick="copyUrl()">링크
				복사</button>
		</div>





		<script>

//현재 url 변수로 가져오기
let nowUrl = window.location.href;

function copyUrl(){ 
  //nowUrl 변수에 담긴 주소를
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
	<hr>

	<!-- 추천평 -->
	<div>
<%-- 		<c:forEach var="rating_review" items=" ${book_rating.rating_review}"> --%>
<%-- 		</c:forEach> --%>

<c:forEach var="rating" items="${ratings}">
        <p>User ID: ${book_rating.user_id}</p>
        <p>Rating: ${ratings.average_rating}</p>
        <p>Review: ${book_rating.rating_review}</p>
        <!-- 기타 필요한 출력 요소를 추가할 수 있습니다. -->
    </c:forEach>
	</div>

	<!-- 추천생성 -->
	<div>
		<form method="post">
    <input type="hidden" name="bookId" value="${book_rating.rating_review}">
    <div>
        <label for="title">제목:</label>
        <input type="text" id="title" name="title" required>
    </div>
    <div>
        <label for="content">내용:</label>
        <textarea id="content" name="content" rows="4" cols="50" required></textarea>
    </div>
    
    <div>
        <input type="submit" value="추천평 작성">
    </div>
</form>
	</div>
</div>
<%@ include file="../layouts/footer.jsp"%>