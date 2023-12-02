<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../layouts/header.jsp"%>
<%@ page import="org.bookbook.domain.LikeVO"%>

<!DOCTYPE html>
<html>
<head>
    <title>좋아하는 도서 목록</title>
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

        .card hr {
            margin: 10px 0;
            border: none;
            border-top: 1px solid #ccc;
        }
    </style>
</head>
<body>

    <h1>좋아하는 도서 목록</h1>

    <c:if test="${not empty likes}">
        <div class="card-container">
            <c:forEach var="like" items="${likes}">
                <div class="card">
                    <img src="${like.imageUrl}" alt="${like.title}">
                    <div class="card-body">
                        <h5 class="card-title">${like.title}</h5>
                        <p class="card-text">저자: ${like.author}</p>
                        <p class="card-text">출판사: ${like.publisher}</p>
                        <p class="card-text">장르: ${like.genre}</p>
                        <p class="card-text">카테고리: ${like.category}</p>
                        <p class="card-text">도서 ID: ${like.bookId}</p>
                        <p class="card-text">사용자 ID: ${like.userId}</p>
                        <hr>
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