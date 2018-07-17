<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
	$(document).ready(function() {
		alert("방이 개설 되었습니다.");
		
		var formData = $("#chatRoomGo").serialize();
		
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAll.com",
			data : formData,
			success : function(data) {
				$("#messenger_view").html(data);
			}
		})
	})
</script>

<form id="chatRoomGo" name="chatRoomGo" method="post">
	<input type="hidden" name="oc_num" value="${crdto.oc_num }">
	<input type="hidden" name="m_num" value="${crdto.m_num }">
	<input type="hidden" name="oc_name" value="${crdto.oc_name }">
	<input type="hidden" name="oc_content" value="${crdto.oc_content }">
	<input type="hidden" name="g_num" value="${crdto.g_num }">
	<input type="hidden" name="oc_thumbnail" value="${crdto.oc_thumbnail }">
	<input type="hidden" name="oc_max" value="${crdto.oc_max }">
	<input type="hidden" name="oc_now" value="${crdto.oc_now }">
	
</form>
