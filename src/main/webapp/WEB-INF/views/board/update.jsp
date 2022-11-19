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
	<c:if test="${empty userInfo}">
		<script type="text/javascript">
			alert("글이 삭제되었거나 정상적인 URL 접근이 아닙니다.");
			location.href = "/board?act=list";
		</script>
	</c:if>
	<div class="row justify-content-center mb-5">
		<div class="col-lg-8 col-md-10 col-sm-12">
			<h2 class="my-3 py-3 shadow-sm bg-light text-center">
				<mark class="sky">글보기</mark>
			</h2>
		</div>
		<div class="col-lg-8 col-md-10 col-sm-12">
			<form id="updateForm" method="post" action="/board">
				<input type="hidden" name="act" value="update"> <input
					type="hidden" id="npgno" name="userId" value="${userInfo.userId}">
				<input type="hidden" id="nkey" name="boardNo"
					value="${board.boardNo}"> <input type="hidden" id="boardno"
					name="registerTime" value="${board.registerTime}">
				<div class="row my-2 form-group">
					<h2 class="text-secondary px-5">${board.boardNo}.
						<input type="text" class="form-control" name="subject"
							value="${board.subject}">
					</h2>
				</div>
				<div class="row">
					<div class="col-md-8 px-5">
						<div class="clearfix align-content-center">
							<img class="avatar me-2 float-md-start bg-light p-2"
								src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
							<p>
								<span class="fw-bold">${board.userId}</span> <br /> <span
									class="text-secondary fw-light"> ${board.registerTime} </span>
							</p>
						</div>
					</div>
					<div class="col-md-4 align-self-center text-end">댓글 : 17</div>
					<div class="divider mb-3"></div>
					<input
						class="form-control text-secondary border border-info rounded mx-auto"
						style="width: 95%; height: 500px;" name="content"
						value="${board.content}"></input>
					<div class="divider mt-3 mb-3"></div>
					<div class="d-flex justify-content-end">
						<button type="button" id="btn-mv-modify"
							class="btn btn-outline-success mb-3 ms-1">글수정</button>
						<button type="button" id="btn-delete"
							class="btn btn-outline-danger mb-3 ms-1">글삭제</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<form id="form-param" method="get" action="">
		<input type="hidden" id="act" name="act" value=""> <input
			type="hidden" id="pgno" name="pgno" value="${param.pgno}"> <input
			type="hidden" id="key" name="key" value="${param.key}"> <input
			type="hidden" id="word" name="word" value="${param.word}">
	</form>
	<form id="form-no-param" method="get" action="/board">
		<input id="nact" type="hidden" name="act" value="update"> <input
			type="hidden" name="no" value="${board.boardNo}">
	</form>
	<script>
		document.querySelector("#btn-mv-modify").addEventListener("click",
				function() {
					let form = document.querySelector("#updateForm");
					let modifyInfo = {
						boardNo: "${board.boardNo}",
						subject: document.querySelector("[name='subject']").value,
						content: document.querySelector("[name='content']").value,
					}
					let config = {
						method : "PUT",
						headers: {
            	"Content-Type": "application/json",
          	},
						body: JSON.stringify(modifyInfo),
					}
					console.log(config);
					fetch("/board/modify/${board.boardNo}", config)
					.then(response=>{
						switch(response.status){
							case 200:
								response.text().then((data) => {
									alert("글 수정에 성공했습니다.");
									location.href = data;
								});
								break;
							case 406:
							case 500:
								alert("글 수정중 오류가 발생했습니다.");
								location.href = "/board/";
								break;
						}
					})
				});

		document.querySelector("#btn-delete").addEventListener("click",
				function() {
					if (confirm("정말 삭제하시겠습니까?")) {
						let form = document.querySelector("#form-no-param");
						document.querySelector("#nact").value = "delete";
						form.submit();
					}
				});
	</script>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>