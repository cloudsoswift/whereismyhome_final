<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="../include/head.jsp"%>
    <style type="text/css">
      header {
        background-color: black;
      }
    </style>
  </head>
  <body>
    <%@ include file="../include/nav.jsp"%>
    <div class="row justify-content-center mb-5">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row my-2">
          <h2 class="text-secondary px-5">${board.boardNo}. ${board.subject}</h2>
        </div>
        <div class="row">
          <div class="col-md-8 px-5">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">${board.userId}</span> <br />
                <span class="text-secondary fw-light"> ${board.registerTime} 좋아요 : 100 </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div
            class="text-secondary border border-info rounded mx-auto"
            style="width: 95%; height: 500px"
          >
            ${board.content}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" id="btn-list" class="btn btn-outline-primary mb-3">글목록</button>
            <c:if test="${board.userId eq userInfo.userId}">
              <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">
                글수정
              </button>
              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">
                글삭제
              </button>
            </c:if>
          </div>
        </div>
      </div>
    </div>
    <form id="form-param" method="get" action="">
      <input type="hidden" id="act" name="act" value="" />
      <input type="hidden" id="pgno" name="pgno" value="${param.pgno}" />
      <input type="hidden" id="key" name="key" value="${param.key}" />
      <input type="hidden" id="word" name="word" value="${param.word}" />
    </form>
    <form id="form-no-param" method="get" action="/board">
      <input type="hidden" id="nact" name="act" value="mvUpdate" />
      <input type="hidden" id="boardno" name="no" value="${board.boardNo}" />
    </form>
    <script>
      document.querySelector("#btn-list").addEventListener("click", function () {
        /* let form = document.querySelector("#form-param");
        document.querySelector("#act").value = "list";
        form.setAttribute("action", "/board");
        form.submit(); */
        location.href = "/board/";
      });

      document.querySelector("#btn-mv-modify").addEventListener("click", function () {
        /* let form = document.querySelector("#form-no-param");

      form.setAttribute("action", "/board?act=mvUpdate");
      form.submit(); */
        location.href = "/board/modify/${board.boardNo}";
      });

      document.querySelector("#btn-delete").addEventListener("click", function () {
        if (confirm("정말 삭제하시겠습니까?")) {
          /* let form = document.querySelector("#form-no-param");
      	document.querySelector("#nact").value = "delete";
      	form.setAttribute("action", "/board");
      	form.submit(); */
          let config = {
            method: "DELETE",
          };
          fetch("/board/${board.boardNo}", config)
            .then((response) => {
              switch (response.status) {
                case 200:
                  response.text().then((data) => {
                    alert("글 삭제에 성공했습니다.");
                    location.href = data;
                  });
                  break;
                case 406:
                  alert("글 삭제중 오류가 발생했습니다.");
                  location.href = "/board/";
                  break;
                case 500:
                  return response.json();
                  break;
              }
            })
            .then((data) => {
              if (data != undefined) {
                console.log(data.message);
                alert("글 삭제중 오류가 발생했습니다.");
                location.href = "/board/";
              }
            });
        }
      });
    </script>
    <%@ include file="../include/footer.jsp"%>
  </body>
</html>
