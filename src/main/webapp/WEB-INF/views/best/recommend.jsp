<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<ul>
	<li>${book}</li>
	<div class="card-container">
		<c:forEach var="book" items="${list}">
			<div class="card">
				<img src="${book.imageUrl}" alt="${book.title}" class="card-img-top">
				<div class="card-body">
					<h5 class="card-title">${book.title}</h5>
					<p class="card-text">저자: ${book.author}</p>
					<p class="card-text">출판사: ${book.publisher}</p>
					<p class="card-text"></p>
					<button class="btn btn-primary"
						onclick="likeBook('${book.bookid}')">
						<i class="bi-heart"></i>좋아요
					</button>
					<button class="btn btn-primary" onclick="addToFavorites('')">즐겨찾기</button>
				</div>
			</div>
		</c:forEach>
	</div>
</ul>