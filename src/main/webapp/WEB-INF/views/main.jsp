<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="layouts/non_logo/header.jsp"%>

<%@include file="layouts/sidebar.jsp"%>

<div class="main">
	<!-- 이 안에 본문 내용 넣어주시고 class 명도 꼭 같이 넣어주세요 -->
	<img class="logo" src="/resources/images/logo1.png">
	<!-- 검색창 -->

	<form action="book/list" method="GET">
		<div class="mx-auto mt-5 search-bar input-group mb-3 search-bar">
			<div class="dropdown text">
				<button class="btn btn-secondary dropdown-toggle cate" type="button"
					id="dropdownMenuButton1" data-bs-toggle="dropdown"
					aria-expanded="false">카테고리</button>
				<ul id="searchBar-category" class="dropdown-menu">
					<li><a class="dropdown-item" href="#"
						onclick="changeCategory('대분류1')">대분류1</a></li>
					<li><a class="dropdown-item" href="#"
						onclick="changeCategory('대분류2')">대분류2</a></li>
					<li><a class="dropdown-item" href="#"
						onclick="changeCategory('대분류3')">대분류3</a></li>
				</ul>
				<script>
					function changeCategory(selectedCategory) {
						document.getElementById('dropdownMenuButton1').textContent = selectedCategory;
					}
				</script>
				<!-- <script>
					 var ul = document.getElementById("searchBar-category");
	     	         var searchBookList = ${ searchBook };
	     	         var topics = Object.keys(searchBookList);
	
	
					 for (var i = 0; i < topics.length; i++) {
					 	topic = topics[i]
					
					 	var details = document.createElement('li');
					 	ul.appendChild(details);
					
					 	var summary = document.createElement('a');
					 	summary.id = 'topic';
		              	summary.textContent = topic;
		              	summary.classList.add('dropdown-item');
					 	summary.href='#';
		              	summary.appendChild(document.createElement('br'));
		              	summary.onclick="changeCategory(topic)";
		              	details.appendChild(summary);
					 	}
					</script> -->
			</div>
			<input id="searching" name="keywords" type="text"
				class="form-control text" placeholder="추천 키워드를 입력하세요"
				aria-label="Recipient's username" aria-describedby="button-addon2">
			<div class="input-group-append">
				<input type="hidden" id="selectedCategory" name="selectedCategory"
					value="">
				<button type="button" id="search-btn"
					class="btn btn-success rounded-0" onclick="prepareAndSubmitForm()">
					<i class="fa-solid fa-magnifying-glass"></i>
				</button>
			</div>
		</div>
	</form>
	<div class="center-container">
		<button type="button" id="best" class="btn btn-secondary"
			onclick="location.href='best/list'">
			<!-- 코드보기 편하게용 주석 -->
			베스트셀러 페이지
		</button>
	</div>
</div>

<%@include file="layouts/footer.jsp"%>