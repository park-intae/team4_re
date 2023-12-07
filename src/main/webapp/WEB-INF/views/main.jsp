<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="layouts/non_logo/header.jsp"%>

<%-- <%@include file="layouts/sidebar.jsp"%> --%>

	<div class="main">
		<!-- 이 안에 본문 내용 넣어주시고 class 명도 꼭 같이 넣어주세요 -->
		<img class="logo" src="/resources/images/logo1.png">
		<!-- 검색창 -->

		<form action="book/list" method="GET">
			<div class="mx-auto mt-5 input-group mb-3 search-bar">
				<div class="dropdown text">
					<button class="btn btn-secondary dropdown-toggle cate"
						type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
						aria-expanded="false">카테고리</button>

					<ul id="searchBar-category" class="dropdown-menu">

					</ul>

					<!-- <script>
					function changeCategory(selectedCategory) {
						// 버튼의 텍스트 내용을 선택된 값으로 업데이트
						document.getElementById('dropdownMenuButton1').textContent = selectedCategory;
					}
				</script> -->

					<script>
				
				 	function changeCategory(selected_category_key) {
				 		var searchBookList2 = ${ searchBook };
		     	        topics = Object.keys(searchBookList2);
				 		console.log(topics[selected_category_key]);
						document.getElementById('dropdownMenuButton1').textContent = topics[selected_category_key];
						// 선택된 버튼 값을 selectedTopic input으로 전달
						document.getElementById('selectedTopic').value = topics[selected_category_key];
					}
				
					 var ul = document.getElementById("searchBar-category");
	     	         var searchBookList = ${ searchBook };
	     	         topics = Object.keys(searchBookList);
	     	         
	     	         let topic = ''
	
					 for (let i = 0; i < topics.length; i++) {
					 	topic = topics[i]
					 	console.log(topic)
					
					 	var details = document.createElement('li');
					 	ul.appendChild(details);
					
					 	var summary = document.createElement('div');
					 	summary.id = 'topic';
		              	summary.classList.add('dropdown-item');
		              	summary.textContent = topic;
		              	summary.addEventListener('click', () => changeCategory(i));	
		              	summary.appendChild(document.createElement('br'));
		              	
		              	/* summary.onclick = function(){changeCategory(topic)}; */
		              	
		              	
		              	details.appendChild(summary);
					 	}
					 
					 
					
				</script>
				</div>
				<input id="searching" name="keywords" type="text"
					class="form-control text" placeholder="추천 키워드를 입력하세요"
					aria-label="Recipient's username" aria-describedby="button-addon2">
				<div class="input-group-append">
					<input type="hidden" id="selectedTopic" name="selectedTopics"
						value="" />
					<button type="submit" id="search-btn"
						class="btn btn-success rounded-0">
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