<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="/WEB-INF/views/include/head.jsp"%>
    <%@ include file="/WEB-INF/views/include/confirm.jsp"%>
    <style type="text/css">
      header {
        background-color: black;
      }
    </style>
  </head>

  <body>
    <!-- ======= Header ======= -->
    <%@ include file="/WEB-INF/views/include/nav.jsp"%>
    <!-- End Header -->

    <!-- 중앙 content start -->
    <section>
        <div class="container d-flex flex-column min-vh-100">
          <div style="height: 70px"></div>
          <h2 class="col-3 ms-1 mt-5 mb-4 d-inline">아파트 매매 정보</h2>
          <div class="row col-md-12 justify-content-center mb-2">
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="sido">
                <option value="">시도선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="gugun">
                <option value="">구군선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-secondary text-light" id="dong">
                <option value="">동선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-dark text-light" id="year">
                <option value="">매매년도선택</option>
              </select>
            </div>
            <div class="form-group col-md-2">
              <select class="form-select bg-dark text-light" id="month">
                <option value="">매매월선택</option>
              </select>
            </div>
            <div class="form-group col-sm">
              <button type="button" id="list-btn" class="btn btn-outline-primary d-inline">
                검색
              </button>
            </div>
            <div class="form-group col-sm">
              <button
                id="inter-sido"
                type="button"
                class="btn btn-outline-secondary d-inline text-warning"
                style="width: 40px"
              >
                <i id="inter-sido-i" class="bi bi-star"></i>
              </button>
            </div>
          </div>
          <!-- 검색 결과와 지도 -->
          <div id="result" class="container mt-4 mb-5">
            <div class="row w-100 m-auto">
              <div id="aptlist" class="col-4 p-0" style="visibility: hidden">
                <h3>거래 정보</h3>
                <!-- <%--
              <ul class="list-group list-group-flush">
                <li class="list-group-item mb-1">
                  <div class="card" style="min-width: 18rem">
                    <div class="card-body">
                      <div class="row">
                        <div class="col-lg-9 col-sm-12 text-lg-start text-center">
                          <h5 class="card-title mb-3">${apt.apartmentName}</h5>
                        </div>
                        <div class="col-lg-2 col-sm-12 text-lg-end text-center ms-3">
                          <button type="button" class="inter-apt btn" style="width: 40px">
                            <i class="bi bi-star" data-code="${apt.aptCode}"></i>
                          </button>
                        </div>
                      </div>
                      <h6 class="card-subtitle mb-1">거래금액: ${apt.dealAmount}만원</h6>
                      <p class="m-0 text-muted">면적: ${apt.area}</p>
                      <p class="m-0 text-muted">층: ${apt.floor}</p>
                      <p class="card-text">${apt.date}</p>
                    </div>
                  </div>
                </li>
              </ul>
              --%>
            -->
              </div>
              <div class="col-8">
                <!-- Kakao Map start -->
                <div class="mt-3 p-0 text-center">
                  <h3>매매 위치</h3>
                </div>
                <div id="map" style="width: 100%; height: 500px"></div>
                <script
                  type="text/javascript"
                  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3211c5a2b8ac67e629572729d0cd625e&libraries=services"
                ></script>
                <script src="/assets/js/kakaomap.js"></script>
                <!-- Kakao Map end -->
              </div>
            </div>
          </div>
        </div>
    </section>

    <!-- ======= Footer ======= -->
    <%@ include file="/WEB-INF/views/include/footer.jsp"%>
    <!-- js -->
    <script src="/assets/js/aptlist.js"></script>
  </body>
</html>
