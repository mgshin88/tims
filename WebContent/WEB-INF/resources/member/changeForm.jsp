<%@ page language="java" contentType="text/html;" pageEncoding="utf-8"%>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
	crossorigin="anonymous"></script>

<script language="JavaScript">
	function checkIt() {
		var userinput = eval("document.userinput");// document.userinput이란 위치값 도큐먼트안에 유저인풋이란네임을가진폼 그냥단순문자열 을 축소해놓은거
		//eval이란 문자열을 저장하여 단순표출하는내장함수
		var pwtest = /^.*(?=.{8,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;


		if (!pwtest.test(userinput.pw.value)) {//폼  유저인풋 인 것 이거 밑에있음 중에서 passwd의 value가 없는거 !는 부정
			alert("올바른 비밀번호를 입력하세요(영문자,숫자 포함 특수문자 사용가능.8~20자리)");
			return false;
		}
		if (userinput.pw.value != userinput.pw2.value)//폼  유저인풋 인 것 이거 밑에있음 중에서 passwd, passwd2의 value가서로 같지않을떄 부정
		{
			alert("비밀번호를 동일하게 입력하세요");
			return false;
		}
	}
		</script>

</head>
<style>
	
	#Box {  
    position:absolute;  
    width:400px;  
    height:50px;  
    top:50%;  
    left:45%;  
    margin:-250px 0 0 -200px;  
    box-shadow: 10px 10px 5px gray;
    border-radius:5px;

} 
	</style>
<body>
<div id="Box">
<form name="userinput" method="post" action="/pickimages/member/changePro.do" name="userinput" onSubmit="return checkIt()" class="form-inline">

<input type="password" name="pw" size="15" maxlength="200" placeholder="비밀번호" class="form-control mb-2 mr-sm-2">


<input type="password" name="pw2" size="15" maxlength="200" placeholder="비밀번호 확인" class="form-control mb-2 mr-sm-2">



<input type="submit" name="confirm" value="완  료" class="btn btn-danger">



</form>

	</div>
</body>
</html>