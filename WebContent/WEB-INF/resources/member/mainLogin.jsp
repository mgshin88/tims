<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Welcome To TIMS</title>
<link rel="stylesheet" href="/TIMS/CSS/style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
<script	src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script language="javascript" src="/TIMS/JS/member.js"></script>
</head>
<body>
	<div class="body"></div>
	<div class="grad"></div>
	<div class="header">
		<div>TIMS</div>
		<br /> <span>Taste In Music</span>
	</div>
	<br>
	<div class="login">
	<form method="post" name="loginform" action="/TIMS/member/loginPro.com" onSubmit="return checkIt()" >
		<input type="text" placeholder="username" name="m_id" autofocus><br>
		<input type="password" placeholder="password" name="m_passwd"><br>
		<input type="submit" value="Login"></form><br>
		<div class="inputpage">
			회원이 아니신가요? <a href="/TIMS/member/inputForm.com">회원가입</a>
		</div>
	</div>
</body>
</html>
