<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- 개별 페이지 --%>
<div>
	<a href="${cri.getLinkWithColumn('list')}">종합</a> 
	<a href="${cri.getLinkWithColumn2('list', classi)}">소설</a> 
	<a href="${cri.getLinkWithColumn3('list', classi)}">만화/동화</a>
	<a href="${cri.getLinkWithColumn4('list', classi)}">비소설</a> 
	<a href="${cri.getLinkWithColumn5('list', dateNum)}">월간</a>
	<a href="${cri.getLinkWithColumn6('list', dateNum2)}">3달</a>

</div>
