<%@ page language="java" contentType="text/html;" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	<style type="text/css">
	#Box {  
    position:absolute;  
    width:470px;  
    height:50px;  
    top:50%;  
    left:50%;  
    margin:-250px 0 0 -200px;  
    box-shadow: 10px 10px 5px gray;
    border-radius:5px;

} 
	</style>
	
</head>
<script language="JavaScript">
	function checkIt() {
		var userinput = eval("document.inform");// document.userinput이란 위치값 도큐먼트안에 유저인풋이란네임을가진폼 그냥단순문자열 을 축소해놓은거
		//eval이란 문자열을 저장하여 단순표출하는내장함수
		var emailtest = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		
		if (!emailtest.test(userinput.email.value)) { //폼  유저인풋 인 것 이거 밑에있음 중에서 id의 value가 없는거 !는 부정
			alert("올바른 이메일 형식을 입력하세요");
			return false;
		}
	}
		</script>

<body>
<div id="Box">
<form name="inform" method="get" action="/pickimages/member/passwordPro.do" onSubmit="return checkIt();" class="form-inline">


	
	
<input type="text" name="email" size="30" maxlength="200"placeholder="이메일입력" class="form-control mb-2 mr-sm-2" >
			
	


	<input type="submit" name="confirm" value="인증메일전송" class="btn btn-danger">&nbsp;
	<input type="button" value="취  소" onclick="javascript:window.location='/pickimages/member/main.do'" class="btn btn-danger">

</form>
</div>

</body>
</html>