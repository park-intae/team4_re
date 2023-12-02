<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- 페이지당 아이템 수 설정 --%>
<c:set var="itemsPerPage" value="10" />

<%-- 총 아이템 수에 따른 총 페이지 수 계산 --%>
<c:set var="totalItems" value="${itemList.size()}" />
<c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" />

<%-- 현재 페이지를 URL에 추가하기 위한 파라미터 설정 --%>
<c:set var="currentPage" value="${pageMaker.cri.pageNum}" />
<c:set var="classi" value="${pageMaker.cri.classi}" />
<c:set var="dateNum" value="${pageMaker.cri.dateNum}" />
<c:set var="dateNum2" value="${pageMaker.cri.dateNum2}" />

<%-- 페이지 링크 생성 --%>
<ul class="pagination justify-content-center">
    <%-- 이전 페이지 링크 --%>
    <c:if test="${currentPage gt 1}">
        <li class="page-item">
            <a class="page-link" href="<c:url value="/getList${classi}" ><c:param name="pageNum" value="${currentPage - 1}" /><c:param name="dateNum" value="${dateNum}" /><c:param name="dateNum2" value="${dateNum2}" /></c:url>">
                <i class="fa-solid fa-angle-left"></i>
            </a>
        </li>
    </c:if>

    <%-- 페이지 번호 링크 --%>
    <c:forEach begin="1" end="${totalPages}" var="pageNum">
        <li class="page-item ${currentPage eq pageNum ? 'active' : ''}">
            <a class="page-link" href="<c:url value="/getList${classi}" ><c:param name="pageNum" value="${pageNum}" /><c:param name="dateNum" value="${dateNum}" /><c:param name="dateNum2" value="${dateNum2}" /></c:url>">${pageNum}</a>
        </li>
    </c:forEach>

    <%-- 다음 페이지 링크 --%>
    <c:if test="${currentPage lt totalPages}">
        <li class="page-item">
            <a class="page-link" href="<c:url value="/getList${classi}" ><c:param name="pageNum" value="${currentPage + 1}" /><c:param name="dateNum" value="${dateNum}" /><c:param name="dateNum2" value="${dateNum2}" /></c:url>">
                <i class="fa-solid fa-angle-right"></i>
            </a>
        </li>
    </c:if>
</ul>
