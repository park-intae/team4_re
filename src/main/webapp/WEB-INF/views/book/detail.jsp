<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../layouts/header.jsp"%>
<link rel="stylesheet" href="/resources/css/detail.css" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="../../../resources/js/rest.js"></script>
<script src="../../../resources/js/comment.js"></script>

<script>
  const COMMENT_URL = `/api/book/detail/${book.bookid}/comment/`;

  $(document).ready(async function() {
    let book_id = ${book.bookid};
    let username = '${username}'; // 작성자(로그인 유저)

    loadComments(book_id, username); // 댓글 목록 불러오기

    // 댓글 추가 버튼 처리
    $('.comment-add-btn').click(function(e) {
      createComment(book_id, username, ratingValue);
    });

    $('.comment-list').on('click', '.comment-update-show-btn', showUpdateComment);

    // 수정 확인 버튼 클릭
    $('.comment-list').on('click', '.comment-update-btn', function(e){
      const el = $(this).closest('.comment');
      updateComment(el, username);
    });

    // 수정 취소 버튼 클릭
    $('.comment-list').on('click', '.comment-update-cancel-btn', cancelCommentUpdate);

    // 삭제 버튼 클릭
    $('.comment-list').on('click', '.comment-delete-btn', deleteComment);



    // 별점 선택 시 이벤트 추가
    $('input[name="rating"]').on('change', function() {
      handleRatingChange(parseInt($(this).val()));
    });
  });
</script>


<%-- 개별 페이지 --%>
<div class="center">
	<h1>${book.title}</h1>
</div>


<style>
.fixed-content {
	margin-left: 215px;
	margin-top: 60px;
	margin-bottom: 13px;
}
</style>

<hr>

<div class="clearfix">


	<div class="image-panel float-left mr-3 ">

		<style>
#book-carousel {
	width: 200px; /* 고정된 너비 */
	height: 300px; /* 고정된 높이 */
}

.carousel-item {
	width: 200px; /* 고정된 너비 */
	height: 300px; /* 고정된 높이 */
	overflow: hidden; /* 크기를 벗어나는 부분을 잘라냅니다. */
}

.carousel-item img {
	width: 100%; /* 부모 요소에 대해 100% 크기로 조절 */
	height: 100%; /* 부모 요소에 대해 100% 크기로 조절 */
	object-fit: cover;
}
</style>


		<div class="image-lay">
			<div id="book-carousel" class="carousel slide" data-ride="carousel">

				<!-- Indicators -->
				<ul class="carousel-indicators">
					<c:forEach items="${book.imageUrl.split(',')}" varStatus="status">
						<li data-target="#book-carousel" data-slide-to="${status.index}"
							class="<c:if test="${status.first}">active</c:if>"></li>

					</c:forEach>

				</ul>

				<!-- The slideshow -->
				<div class="carousel-inner">
					<c:forEach var="image" items="${book.imageUrl.split(',')}"
						varStatus="status">
						<div
							class="carousel-item <c:if test="${status.first}">active</c:if>">
							<img src="${image}" alt="${book.title}">
						</div>
					</c:forEach>
				</div>

				<!-- Left and right controls -->
				<a class="carousel-control-prev" href="#book-carousel"
					data-slide="prev"> <span class="carousel-control-prev-icon"></span>
				</a> <a class="carousel-control-next" href="#book-carousel"
					data-slide="next"> <span class="carousel-control-next-icon"></span>
				</a>

			</div>

		</div>

	</div>

	<div class="intro">
		<div class="fixed-content">
			${book.author} <br> ${book.publisher} <br>
			${book.publicationdate}
		</div>
		<div class="bookIntro">${book.bookintro}</div>
	</div>


	<br>
</div>

<hr>


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
</style>



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
						<p class="card-text">저자: ${bookAI.author}</p>
						<p class="card-text">출판사: ${bookAI.publisher}</p>
						<p class="card-text">장르: ${bookAI.genre}</p>
						<p class="card-text">카테고리: ${bookAI.category}</p>
						<p class="card-text"></p>
						<i class="bi bi-heart text-danger" style="cursor: pointer;"
							onclick="likeBook('${bookAI.bookid}', '${bookAI.title}', this)"></i>
					</div>
				</div>
			</c:forEach>
		</div>
	</ul>

</c:if>


<hr>


<link href="../../../resources/css/star.css" rel="stylesheet" />

<<<<<<< HEAD
<form class="ratings" name="myform" id="myform" method="post"
	action="/api/bookbook/rating/add">
	<fieldset>
		<span class="text-bold">${ratings.average_rating}</span> <input
			type="radio" name="rating" value="5" id="rate1"> <label
			for="rate1">★</label> <input type="radio" name="rating" value="4"
			id="rate2"> <label for="rate2">★</label> <input type="radio"
			name="rating" value="3" id="rate3"> <label for="rate3">★</label>
		<input type="radio" name="rating" value="2" id="rate4"> <label
			for="rate4">★</label> <input type="radio" name="rating" value="1"
			id="rate5"> <label for="rate5">★</label> <input type="submit"
			value="별점 제출">
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


=======
>>>>>>> main
<div class="bottom">

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




	<div>
		<button type="button" class="copy-btn" onclick="copyUrl()">링크
			복사</button>
	</div>
<<<<<<< HEAD
=======

>>>>>>> main


	<!-- 새 댓글 작성 -->
	<div class="bg-light p-2 rounded my-5">
		<div>${username == null ? '댓글을 작성하려면 먼저 로그인하세요' : '댓글 작성' }</div>
<<<<<<< HEAD
=======

		<div class="starRate">

			<fieldset class="rate">
					<input type="radio" id="rating4" name="rating"
					value="4" onclick="handleRatingChange(4)"> <label
					for="rating4" title="4점"></label> <input type="radio" id="rating3"
					name="rating" value="3" onclick="handleRatingChange(3)"> <label
					for="rating3" title="3점"></label> <input type="radio" id="rating2"
					name="rating" value="2" onclick="handleRatingChange(2)"> <label
					for="rating2" title="2점"></label> <input type="radio" id="rating1"
					name="rating" value="1" onclick="handleRatingChange(1)"> <label
					for="rating1" title="1점"></label>
			</fieldset>
			
			<script>
		    // 등급 변경 함수
		    function handleRatingChange(rating) {
		      ratingValue = rating;
		    }
			</script>

		</div>


>>>>>>> main
		<div>
			<textarea class="form-control new-comment-content" rows="3"
				${username == null ? 'disabled' : '' }></textarea>
			<div class="text-right">
				<button class="btn btn-primary btn-sm my-2 comment-add-btn"
					${username == null ? 'disabled' : '' }>
					<i class="fa-regular fa-comment"></i> 확인
				</button>
			</div>
		</div>
	</div>


	<div class="my-5">
		<i class="fa-regular fa-comments"></i> 댓글 목록
		<hr>
		<div class="comment-list"></div>
	</div>


</div>

<%@ include file="../layouts/footer.jsp"%>