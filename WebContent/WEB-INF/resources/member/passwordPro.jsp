<%@ page language="java" contentType="text/html;" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<style>
	
	#Box {  
    position:absolute;  
    width:470px;  
    height:100px;  
    top:50%;  
    left:45%;  
    margin:-250px 0 0 -200px;  
    box-shadow: 10px 10px 5px gray;
    border-radius:5px;

} 
	</style>
</head>
<c:if test="${error==1}">
<script>
alert("인증번호가맞지않습니다.");
</script>
</c:if>
<body>
<div id="Box">
	<center>해당메일로 인증번호를 전송하였습니다 인증번호를 입력하세요.</center><br/>

		<form name="inform" method="post"
			action="/pickimages/member/changeForm.do"
			onSubmit="return checkIt();"  class="form-inline">

			
	<input type="text" name="usercert" size="30" maxlength="200" placeholder="인증번호" class="form-control mb-2 mr-sm-2" >
				

	<input type="submit" name="confirm" value="비밀번호재설정" class="btn btn-danger">
		
			
		</form>

</div>
</body>
</html>