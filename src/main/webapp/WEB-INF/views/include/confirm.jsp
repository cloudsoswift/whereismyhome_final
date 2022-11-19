<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- jstl 자동완성하기 위한 코드 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${userInfo ne null}">
<script>
	alert("로그인 후 사용 가능합니다.");
	location.href="/user/login";
</script>
</c:if>