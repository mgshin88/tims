<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
	$(document).ready(function(){
		alert("너 실행 안되냐?");
		var oc_num = '${crdto.oc_num}';
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAllNotice.com",
			data : {oc_num : oc_num},
			success : function(data) {
				$(".chatRoomAddmenu_content").html(data);
			}
		});
	});
</script>