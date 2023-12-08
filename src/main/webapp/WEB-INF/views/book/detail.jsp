<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../layouts/header.jsp"%>
<%@ include file="../layouts/sidebar.jsp"%>

<link rel="stylesheet" href="/resources/css/detail.css" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="../../../resources/js/rest.js"></script>
<script src="../../../resources/js/comment.js"></script>
<script src="../../../resources/js/like.js"></script>



<script>
  const COMMENT_URL = `/api/book/detail/${book.bookid}/comment/`;
  
  const LIKE_URL = `/api/book/detail/${book.bookid}/like/`;

  $(document).ready(async function() {
    let book_id = ${book.bookid};
    let username = '${username}'; // 작성자(로그인 유저)

    loadComments(book_id, username); // 댓글 목록 불러오기
    loadLikes();

    $(document).on('click', '.like-user', function(e) {
    	  const clickedUser = $(this).find('.userid').text().trim();
    	  loadLikesBooks(clickedUser);
    	});




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
	<h1 class="book-title">${book.title}</h1>
</div>

 <style> 
.fixed-content { 
 margin-left: 335px; 
  margin-top: 60px; 
 } 
</style> 

<hr>

<div class="clearfix">


	<div class="image-panel float-left mr-3">

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
	/* 	margin: 20px 35px 6px 34px; */
	/* 위 20px, 오른쪽 35px, 아래 80px, 왼쪽 34px 여백 설정 */
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


<hr>


<link href="../../../resources/css/star.css" rel="stylesheet" />

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

<style>
#check-btn { display: none; }
/* #check-btn:checked ~ .like-list { display: block; } 
.like-list { display: none; } */
.dropdown p,a{
	display: inline-block;
}
.likeCount {
    display: inline-block;
}
</style>

<div class="dropdown">
  <button class="btn btn-outline-danger" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    ❤ 이 책을 좋아하는 사람들 ( 총
    <div class="likeCount"> 
	</div>
    명	)
  </button>
  <ul class="dropdown-menu">
	<li>
	<div class="like-list"></div>
	</li>
  </ul>
</div>



	<div>
		<button type="button" class="copy-btn" onclick="copyUrl()">링크
			복사</button>
	</div>



	<!-- 새 댓글 작성 -->
	<div class="bg-light p-2 rounded my-5">
		<div>${username == null ? '댓글을 작성하려면 먼저 로그인하세요' : '댓글 작성' }</div>


		<div class="starRate">

			<fieldset class="rate">
				<input type="radio" id="rating4" name="rating" value="4"
					onclick="handleRatingChange(4)"> <label for="rating4"
					title="4점"></label> <input type="radio" id="rating3" name="rating"
					value="3" onclick="handleRatingChange(3)"> <label
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