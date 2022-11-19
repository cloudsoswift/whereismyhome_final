<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	<section id="contact" class="contact mt-5">
		<div class="container">
			<div class="section-title">
				<h3>
					<span>마이페이지</span>
				</h3>
			</div>

			<div class="row mt-5">
				<div class="col-lg-4">
					<div class="info">
						<div class="address">
							<i class="bi bi-envelope"></i>
							<h4>회원 정보 수정하기</h4>
							<p>아이디: ${dto.userId }</p>
						</div>
							<i class="bi bi-envelope"></i>
							<h4 class="d-inline ps-3">회원 탈퇴하기</h4>
							<button class="btn btn-secondary d-inline" id="deleteBtn">삭제</button>
					</div>
				</div>

				<div class="col-lg-8 mt-5 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
					<div class="row">
						<div class=" form-group mt-3 ">
							<label for="Password">비밀번호</label>
							<input type="Password"
								class="form-control" name="Password" id="userPassword"
								placeholder="Your Password" required
								value="${dto.userPassword }" />
						</div>
					</div>
					<div class="form-group mt-3">
						<label for="userName">이름</label><input type="text"
							class="form-control" name="Name" id="Name"
							placeholder="Your Name" required value="${dto.userName }" />
					</div>
					<div class="form-group mt-3">
						<label for="userAddress">주소</label><input type="text"
							class="form-control" name="Address" id="Address"
							placeholder="Your Address" required value="${dto.userAddress }" />
					</div>
					<div class="form-group mt-3">
						<label for="userPhone">휴대폰번호</label><input type="text"
							class="form-control" name="Phone" id="Phone"
							placeholder="Your Phone Number" required
							value="${dto.userPhone }" />
					</div>
					<div class="text-center mt-3">
						<button id="modifyBtn" class="btn btn-danger ">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="contact" class="contact mt-5">
		<div class="container">
			<div class="section-title">
				<h3>
					<span>관심지역 / 매물 정보</span>
				</h3>
			</div>

			<div class="row">
				<div class="col-lg-4">
					<div class="info">
						<div class="email">
							<i class="bi bi-geo-alt"></i>
							<h4>관심 지역</h4>
						</div>
					</div>
				</div>

				<table id="interTable" class="table table-hover text-center">
					<thead>
						<th>시도</th>
						<th>구군</th>
					</thead>
					<tbody id="interRegion">
						<c:if test="${list != null} ">
							<c:forEach var="row" items="${list}">
								<c:if test="${row.sidoname != null}">
									<tr>
							            <td>${row.sidoname}</td>
							            <td>${row.gugunName}</td>
							        </tr>
								</c:if>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>

			<div class="row mt-5">
				<div class="col-lg-4">
					<div class="info">
						<div class="email">
							<i class="bi bi-geo-alt"></i>
							<h4>관심 매물</h4>
						</div>
					</div>
				</div>

				<table class="table table-hover text-center">
					<thead>
						<tr>
							<th>아파트이름</th>
							<th>면적</th>
							<th>층</th>
							<th>거래금액</th>
							<th>거래날짜</th>
						</tr>
					</thead>
					<tbody id="interApt">
						<c:if test="${list != null} ">
							<c:forEach var="row" items="${list}">
								<c:if test="${row.apartmentName != null}">
							       <tr>
							           <td>${row.apartmentName}</td>
							           <td>${row.area} m<sup>2</sup></td>
							           <td>${row.floor}층</td>
							           <td>${row.dealAmount}원</td>
							           <td>${row.date}</td>
							       </tr>
								</c:if>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</section>
	<!-- End Contact Section -->
	</main>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<!-- End Footer -->

	<!-- js -->
	<script src="/assets/js/mypage.js"></script>
</body>
</html>
