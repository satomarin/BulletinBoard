<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2>ログイン画面</h2>
<div class="main-contents">

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="login" method="post"><br />

	<label for="account">ログインID</label><br />
	<input name="account" value="${account}" id="account"/> <br /><br />

	<label for="password">パスワード</label><br />
	<input name="password" type="password" id="password"/> <br />

	<input type="submit" value="ログイン" /> <br />
	<c:remove var="account" scope="session"/>
</form>
<div class="copyright">Copyright(c)Sato Marin</div>
</div>
</body>
</html>
