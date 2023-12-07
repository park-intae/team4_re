<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- 개별 페이지 --%>
<script>
document.addEventListener("DOMContentLoaded", function() {
  var buttons = document.querySelectorAll("button");

  buttons.forEach(function(button) {
    button.addEventListener("click", function() {
      buttons.forEach(function(btn) {
        btn.classList.remove("active"); // 모든 버튼에서 'active' 클래스 제거
      });
      button.classList.add("active"); // 클릭한 버튼에 'active' 클래스 추가
    });
  });
});
</script>

<div class="center active">
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn('list')}'">종합</button>
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn2('list', classi)}'">소설</button>
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn3('list', classi)}'">만화/동화</button>
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn4('list', classi)}'">비소설</button>
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn5('list', dateNum)}'">월간</button>
	<button class="btn btn-outline-primary"
		onclick="window.location.href='${cri.getLinkWithColumn6('list', dateNum2)}'">세달</button>


</div>
