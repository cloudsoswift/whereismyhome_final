<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <c:if test="${empty userInfo }">
      <script type="text/javascript">
        alert("글이 삭제되었거나 정상적인 URL 접근이 아닙니다.");
        location.href = "/board?act=list";
      </script>
    </c:if>
    <section class="min-vh-100">
      <div class="row justify-content-center mt-5">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="bg-light">게시글 작성</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-register" method="POST" action="">
            <div class="mb-3">
              <label for="subject" class="form-label">제목 : </label>
              <input
                type="text"
                class="form-control"
                id="subject"
                name="subject"
                placeholder="제목..."
              />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용 : </label>
              <textarea class="form-control" id="content" name="content" rows="7"></textarea>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-register" class="btn btn-outline-primary mb-3">
                글작성
              </button>
              <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
                목록으로이동...
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>

    <script>
      document.querySelector("#btn-register").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
          let config = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              subject: document.querySelector("#subject").value,
              content: document.querySelector("#content").value,
            }),
          };
          fetch("/board/write", config).then((response) => {
            switch (response.status) {
              case 200:
                response.text().then((data) => {
                  alert("글 등록에 성공했습니다.");
                  location.href = data;
                });
                break;
              case 406:
              case 500:
                alert("글 등록중 오류가 발생했습니다.");
                location.href = "/board/";
                break;
            }
          });
        }
      });

      document.querySelector("#btn-list").addEventListener("click", function () {
        if (confirm("취소를 하시면 작성한 글은 삭제됩니다.\n취소하시겠습니까?")) {
          location.href = "/board/";
        }
      });
    </script>
    <%@ include file="/WEB-INF/views/include/footer.jsp"%>
  </body>
</html>
