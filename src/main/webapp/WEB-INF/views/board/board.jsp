<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<style type="text/css">
header {
	background-color: black;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<section class="min-vh-100">
		<div class="row justify-content-center mt-5">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="bg-light">게시판</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<div class="row align-self-center mb-2">
					<div class="col-md-2 text-start">
						<c:if test="${userInfo ne null }">
							<button type="button" id="btn-mv-register"
								class="btn btn-outline-primary btn-sm">글쓰기</button>
						</c:if>
					</div>
					<div class="col-md-7 offset-3">
						<form class="d-flex" id="form-search" action="">
							<select class="form-select form-select-sm ms-5 me-1 w-50"
								name="key" aria-label="검색조건">
								<option value="" selected>검색조건</option>
								<option value="subject">제목</option>
								<option value="userid">작성자</option>
							</select>
							<div class="input-group input-group-sm">
								<input type="text" class="form-control" name="word"
									placeholder="검색어..." />
								<button id="btn-search" class="btn btn-dark" type="button">검색</button>
							</div>
						</form>
					</div>
				</div>
				<table class="table table-hover">
					<thead>
						<tr class="text-center">
							<th scope="col">글번호</th>
							<th scope="col">제목</th>
							<th scope="col">작성자</th>
							<th scope="col">작성일</th>
							<th scope="col">좋아요</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${list }">
							<tr class="text-center">
								<th scope="row">${dto.boardNo }</th>
								<td class="text-start"><a href="/board/${dto.boardNo}"
									class="article-title link-dark" data-no="${dto.boardNo }"
									style="text-decoration: none">${dto.subject}</a></td>
								<td>${dto.userId}</td>
								<td>${dto.registerTime}</td>
								<td>100</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="row">
				<ul class="pagination justify-content-center">
					<li class="page-item"><a class="page-link" href="#">이전</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item active"><a class="page-link" href="#">2</a>
					</li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#">4</a></li>
					<li class="page-item"><a class="page-link" href="#">5</a></li>
					<li class="page-item"><a class="page-link" href="#">다음</a></li>
				</ul>
			</div>
		</div>

	</section>


	<form id="form-no-param" method="get" action="">
		<input type="hidden" id="act" name="act" value=""> <input
			type="hidden" id="pgno" name="pgno" value=""> <input
			type="hidden" id="key" name="key" value=""> <input
			type="hidden" id="word" name="word" value=""> <input
			type="hidden" id="articleno" name="articleno" value="">
	</form>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script>
		let titles = document.querySelectorAll(".article-title");
		titles.forEach(function(title) {
			title.addEventListener("click", function() {
				this.getAttribute("data-no");
			});
		});

		document.querySelector("#btn-mv-register").addEventListener("click",
				function() {
					location.href = "${root}/board/write";
				});

		document.querySelector("#btn-search").addEventListener("click",
				function() {
					let form = document.querySelector("#form-search");
					form.setAttribute("action", "${root}/board?act=list");
					form.submit();
				});
	</script>
</body>
</html>


