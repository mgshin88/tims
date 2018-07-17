<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
$(document).ready(function() {
	alert("방이 존재하지 않습니다.");
	$.ajax({
		type : "post",
		url : "/TIMS/chatView/chatRoomAllList.com",
		success : function(data) {
			$("#messenger_view").html(data);
		}
	})
})	
</script>
    