<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	function chatRoomSubmit(oc_num) {
		var formData = $("#chatRoomReserve"+oc_num).serialize();
		
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAll.com",
			data : formData,
			success : function(data) {
				$("#messenger_view").html(data);
			}
		});
	}
	
	function chatRoomCreate() {
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAllCreate.com",
			success : function(data) {
				$("#messenger_view").html(data);
			}
		})
	}
</script>
		<div class="messenger_list">
			<div class="messenger_list_create">
				<button class="messenger_list_createButton"
				 onclick="chatRoomCreate()">방 만들기</button>
			</div>
		<div class="messenger_list_view">
			
	<c:forEach var="chatRoom" items="${cdto}" varStatus="status">
	<form id="chatRoomReserve${chatRoom.oc_num }" class="chatRoomListView messenger_list_addChat" 
	name="chatRoomReserve${chatRoom.oc_num }" method="post">
			<input type="hidden" name="oc_num" value="${chatRoom.oc_num }">
			<input type="hidden" name="m_num" value="${chatRoom.m_num }">
			<input type="hidden" name="g_num" value="${chatRoom.g_num }">
			<input type="hidden" name="g_type" value="${chatRoom.g_type }">
			<input type="hidden" name="oc_name" value="${chatRoom.oc_name }">
			<input type="hidden" name="oc_content" value="${chatRoom.oc_content }">
			<input type="hidden" name="oc_now" value="${loc_now[status.index] }">
			<input type="hidden" name="oc_max" value="${chatRoom.oc_max }">
			
			<div class="messenger_list_AllchatContent">
				<div class="messenger_list_Allchatleft">
					<img class="messenger_list_thumbnail" src="/TIMS/IMG/chatRoomAll/${chatRoom.oc_thumbnail }">
				</div>
				
				<div class="messenger_list_Allchatmiddle">
					<span class="messenger_list_ocName"> ${chatRoom.oc_name } </span>
					<span class="messenger_list_ocNowMax"> [ ${loc_now[status.index] } / ${chatRoom.oc_max } ] </span>
					<span class="messenger_list_ocContent"> ${chatRoom.oc_content } </span>
				</div>
				
				<div class="messenger_list_Allchatright">
					<span class="messenger_list_g"> 장르 : ${chatRoom.g_type } </span>
					<div id="allChatjoinRoom" class="messenger_list_AllinputButton"
					 onclick="chatRoomSubmit('${chatRoom.oc_num }')">입장하기</div>
				</div>
				
			</div>
	</form>
</c:forEach>
	</div>
		</div>