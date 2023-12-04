<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<%@ include file="../layouts/header.jsp"%>
<%@include file="../layouts/sidebar.jsp"%>
<div class="center">
	<h2>베스트셀러 TOP10</h2>
</div>
<div>
	<%@ include file="category_bar.jsp"%>

	<div class="row">
		<c:forEach var="best" items="${list}" varStatus="status" begin="0"
			end="9">
			<div class="stylee">
				<div class="card-deck">
					<div class="card"
						style="width: 100%; height: 550px; border: 5px solid transparent; border-color: ${status.count == 1 ? 'gold' : (status.count == 2 ? 'silver' : (status.count == 3 ? 'darkgoldenrod' : 'transparent'))}">

						<div class="card-header">
							<c:out value="${status.count}" />
							위
						</div>

<%-- 						<a href="/best/get?column1=${best.column1}">  --%>
								<img class="card-img-top" src="${best.images}" alt="${best.title}">
<!-- 						</a> -->
						<div class="card-body">
							<h5 class="card-title">
<%-- 							<a href="/best/get?column1=${best.column1}">  --%>
									${best.title} 
<!-- 							</a> -->
							</h5>

							<p class="card-text">${best.author}</p>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

</div>
<%-- <%@include file="pagination.jsp"%> --%>
<%@ include file="../layouts/footer.jsp"%>