<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script language="javascript" src="/TIMS/JS/member.js"></script>
<link rel="stylesheet" href="/TIMS/CSS/member.css">
<script>
	function idcheck(){
		var idcheck = ${check};
		if(idcheck == 1){
			alert('아이디 중복여부를 확인해주세요');
			return false;
		}
	}
</script>
</head>
<body>
	<div class="body">
	<h1 style="cursor:pointer;" onclick="window.location='/TIMS/main.com'">Welcome To TIMS</h1>
	<form method="post" action="/TIMS/member/inputPro.com" name="join" onsubmit="return checkPw();">
		<div class="join" style="background-color:#eeedf9">
			<h2 class="h22">회원가입</h2>
			<input type="text" id="m_id" name="m_id" placeholder="아이디" oninput="checkId()" style="ime-mode:inactive" required/><p id="confirmId" class="confirmId"></p><br/> 
			<input type="password" id="m_passwd" name="m_passwd" placeholder="비밀번호(숫자+영문자,7자이상)" required />
			<input type="password" id="m_passwd2" name="m_passwd2" placeholder="비밀번호 확인" required/><br />
			<input type="text" name="m_name" placeholder="이름" required/><br/>
	<!-- 		<input type="text" name="m_phone" placeholder="핸드폰번호" required><br/> -->
			<input type="text" name="m_phone1" class="m_phone" style="width:20%;text-align:center" placeholder="핸드폰번호"  maxlength="4" required>
			 -  <input type="text" name="m_phone2" style="width:35%;text-align:center"  maxlength="4" required>  -  <input type="text" style="width:35%;text-align:center"  maxlength="4" name="m_phone3" required>
			<br/>
			<input type="text" name="m_email1" class="email" placeholder="이메일" style="width:40%" required/> @ 
			<input type="text" class="email2" name="m_email2" readOnly style="width:30%"> 
				<select name="email" onchange="email_change()">
				<option value="0">선택하세요</option>
				<option value="9">직접입력</option>
				<option value="naver.com">naver.com</option>
				<option value="daum.net">daum.net</option>
				<option value="nate.com">nate.com</option>
				<option value="gmail.com">gmail.com</option>
				<option value="hotmail.com">hotmail.com</option></select><br/><hr>
			<p>* 좋아하는 음악장르를 선택해주세요(3개까지 선택가능)</p>
			<div align="center">
			<input type="checkbox" name="m_genre" value="발라드" id="g1"/><label for="g1"><span>발라드</span></label>
			<input type="checkbox" name="m_genre" value="댄스" id="g2"/><label for="g2"><span>댄스</span></label>
			<input type="checkbox" name="m_genre" value="랩/힙합" id="g3"/><label for="g3"><span>랩/힙합</span></label>
			<input type="checkbox" name="m_genre" value="R&B/Soul" id="g4"/><label for="g4"><span>R&B/Soul</span></label><br/>
			<input type="checkbox" name="m_genre" value="인디음악" id="g5"/><label for="g5"><span>인디음악</span></label>
			<input type="checkbox" name="m_genre" value="락/메탈" id="g6"/><label for="g6"><span>락/메탈</span></label>
			<input type="checkbox" name="m_genre" value="트로트" id="g7"/><label for="g7"><span>트로트</span></label>
			<input type="checkbox" name="m_genre" value="포크/블루스" id="g8"/><label for="g8"><span>포크/블루스</span></label><br/>
			<input type="checkbox" name="m_genre" value="OST" id="g9"/><label for="g9"><span>OST</span></label>
			<input type="checkbox" name="m_genre" value="클래식" id="g10"/><label for="g10"><span>클래식</span></label>
			<input type="checkbox" name="m_genre" value="해외 POP" id="g11"/><label for="g11"><span>해외 POP</span></label>
			<input type="checkbox" name="m_genre" value="일렉트로니카" id="g12"/><label for="g12"><span>일렉트로니카</span></label></div>
		</div>
		<center><input type="submit" value="가입하기" class="submitbutton" onclick="return idcheck();"/></center>
	</form></div>
</body>
</html>
