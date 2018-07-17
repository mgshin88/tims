<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$("#noticeCreateButton").bind("click", function(){
		if($("#chatRoomAll_noticeInput").css("display") == "none") {
			$("#chatRoomAll_noticeInput").css("display", "flex");		
		} else {
			$("#chatRoomAll_noticeInput").css("display", "none");		
		}
	});
	
	$("#noticeSubmitButton").bind("click", function() {
		var oc_num = '${oc_num}';
		var noticeContent = $("#noticeText").val();
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAllNoticeSend.com",
			data : {oc_num : oc_num, noticeContent : noticeContent},
			success : function(data) {
				alert("작성이 완료되었습니다.");
				$(".chatRoomAddmenu_content").html(data);
			}
		});
	});
</script>

<div class="chatRoomAll_noticebtn">
	<input type="button" id="noticeCreateButton" class="chatRoomAll_noticeCreateButton" value="공지 작성">
</div>

<div id="chatRoomAll_noticeInput" class="chatRoomAll_noticeInput">
	<textarea class="noticeText" id="noticeText" name="notice" placeholder="공지 할 내용을 입력해주세요"></textarea>
	<input type="button" id="noticeSubmitButton" class="chatRoomAll_noticeSubmitButton" value="작성">
</div>

<div class="noticeList">
<c:forEach var="subjectList" items="${subjectListResult }" varStatus="status">
	<ul id="noticeSubject${status.count }" class="pointerCursor">
		<li>
			<i class="fa fa-user" aria-hidden="true"></i>
			<span class="noticeSubject_id">${idListResult[status.index] }</span>
			<span class="noticeSubject_regDate">${sendTimeListResult[status.index] }</span>
		</li>
		<li id="noticeSubjectcontents">
			${subjectList }
		</li>
	</ul>
</c:forEach>
</div>