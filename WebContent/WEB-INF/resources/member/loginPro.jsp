<%@ page contentType="text/html;charset=UTF-8" %>
<%
	if(session.getAttribute("memId")!=null){
		response.sendRedirect("/TIMS/main.com");
 	}else{%>
 	<script>
	 	alert("아이디&비밀번호가 맞지 않습니다.");
	 	history.go(-1);
 	</script>
 <%}%>