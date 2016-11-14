<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="main-contents">
<div class="header">
	<c:if test="${ empty loginUser }">
		<a href="login">ログイン</a>
		<a href="signup">登録する</a>
	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="./">ホーム</a>
		<a href="newMessage">新規投稿</a>
		<a href="setting">設定</a>
		<a href="logout">ログアウト</a>
	</c:if>
</div>


<form action="./" method="get"><br />

	<label for="message_id">カテゴリー</label>
	<select name="category" size="1">
		<c:forEach items="${ messagecatalogs }" var="messagecatalog">
			<option value="${ messagecatalog.category }" >${ messagecatalog.category }</option>
		</c:forEach>
	</select>
	<br />

	<label for="message_id">日付</label>
	<select name="message_id" size="1">
		<c:forEach items="${ messages }" var="message">
			<option value="${ message.id }" >${ message.insertDate }</option>
		</c:forEach>
	</select>
	<select name="message_id" size="1">
		<c:forEach items="${ messages }" var="message">
			<option value="${ message.id }" >${ message.insertDate }</option>
		</c:forEach>
	</select>
	<br />


	<input type="submit" value="絞り込み" /> <br />

</form>



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

<div class="messages">
	<c:forEach items="${messages}" var="message">
		<div class="message">
			<div class="account-name">
				<span class="account"><c:out value="${message.account}" /></span>
				<span class="name"><c:out value="${message.name}" /></span>
			</div>
			<div class="title"><c:out value="${message.title}" /></div>
			<div class="category"><c:out value="${message.category}" /></div>
			<div class="text"><c:out value="${message.text}" /></div>
			<div class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
			<br />
		</div>

		<div class="comment">
			<c:forEach items="${comments}" var="comment">

				<c:if test ="${comment.messageId == message.messageId}">
					<div class="comment">
						<div class="account-name">
							<span class="account"><c:out value="${comment.account}" /></span>
							<span class="name"><c:out value="${comment.name}" /></span>
						</div>
						<div class="text"><c:out value="${comment.text}" /></div>
						<div class="date"><fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
						<br />
					</div>
				</c:if>
			</c:forEach>
		</div>

		<div class="comments">
			<div class="message">
				<form action="comment" method="post" ><br />
					<textarea name="comment" cols="100" rows="5" class="tweet-box"></textarea>
					<br />
					<input type="submit" value="コメントする"">(500文字まで)
					<input type="hidden" name="messageId" value="${message.messageId}"></input>
				</form>
			</div>
		</div>
	</c:forEach>
</div>

</div>


<div class ="copyright">Copyright(c)Marin Sato</div>
</div>
</body>
</html>