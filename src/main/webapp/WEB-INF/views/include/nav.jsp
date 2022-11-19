<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header id="header" class="fixed-top">
  <div class="container d-flex align-items-center justify-content-between">
    <h1 class="logo">
      <a href="/index">Homes</a>
    </h1>
    <!-- Uncomment below if you prefer to use an image logo -->
    <!-- <a href="index.html" class="logo"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->

    <nav id="navbar" class="navbar w-100">
      <ul class="navbar-left">
        <li><a class="nav-link scrollto" href="/index">Home</a></li>
        <li><a class="nav-link scrollto" href="/index">Info</a></li>
        <li><a class="nav-link scrollto" href="/index">News</a></li>
        <li><a class="nav-link scrollto" href="/board/">Board</a></li>
        <c:if test="${userInfo ne null}">
          <li><a class="nav-link scrollto" href="/apart/">Find</a></li>
        </c:if>
      </ul>
      <ul class="navbar-right">
        <c:if test="${userInfo eq null}">
          <li><a class="nav-link scrollto" href="/user/join">SignUp</a></li>
          <li><a class="nav-link scrollto" href="/user/login">Login</a></li>
        </c:if>
        <c:if test="${userInfo ne null}">
          <li><a class="nav-link scrollto" href="/user/mypage">MyPage</a></li>
          <li>
            <a class="nav-link scrollto" href="/user/logout">Logout</a>
          </li>
          <li style="color: #e43c5c; font-weight: 700">${userInfo.userId }(${userInfo.userName })</li>
        </c:if>
      </ul>
      <i class="bi bi-list mobile-nav-toggle navbar-right"></i>
    </nav>
    <!-- .navbar -->
  </div>
</header>
