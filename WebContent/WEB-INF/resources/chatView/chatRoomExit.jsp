<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
$(document).ready(function() {
	var urlAddress = '${url}';
	$.ajax({
		type : "post",
		url : "/TIMS/chatView/"+urlAddress,
		success : function(data) {
			$("#messenger_view").html(data);
		}
	})
})	
</script>
