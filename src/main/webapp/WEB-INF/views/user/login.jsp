<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
	<!-- ======= Header ======= -->
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<!-- End Header -->
	<!-- ======= Cta Section ======= -->
	<section id="cta" class="cta">
		<div class="container">
			<div class="text-md-start">
				<h3>안녕하세요</h3>

				<p>
					저희 웹 사이트는 고객님께 최고의 부동산 정보 제공서비스를 시행하고 있습니다. <br>HOMES를 통해 고객님의
					보금자리를 알아보세요.
				</p>
			</div>
		</div>
	</section>
	<!-- End Cta Section -->
	<div id="main" class="container d-flex flex-column vh-100">
		<section id="contact" class="contact d-flex flex-column"
			style="height: 100%; justify-content: center;">
			<div class="container">
				<div class="section-title">
					<h3>
						로그인<br /> <span>Where Is My Home</span>
					</h3>
				</div>
			</div>
			<div class="row ">
				<div class="col-lg-8 mt-3 mt-lg-0 w-65 p-3" style="margin: 0 auto;">
					<form onsubmit="return false" action="" method="post" id="form-login"
						role="form" class="php-email-form">
						<input type="hidden" name="act" value="login"/>
						<div class="row">
							<div class="col-md-6 form-group">
								<input type="text" name="ID" class="form-control" id="ID"
									placeholder="Your ID" required />
							</div>
							<div class="col-md-6 form-group mt-3 mt-md-0">
								<input type="Password" class="form-control" name="Password"
									id="Password" placeholder="Your Password" required />
							</div>
						</div>
						<div class="text-center mt-3">
							<button id="btn" type="submit">Login</button>
						</div>
					</form>
				</div>
			</div>
		</section>
	<!-- End Contact Section -->
	</div>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<!-- End Footer -->


	<!-- Template Main JS File -->

	<script type="text/javascript">
    document.querySelector("#btn").addEventListener("click", function () {
          let form = document.querySelector("#form-login");
          form.setAttribute("action", "/user/login");
          form.submit();
      });
    </script>
</body>
</html>
