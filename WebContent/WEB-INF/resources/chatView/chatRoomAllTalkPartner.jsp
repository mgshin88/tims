<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="chatRoomAll_addPeople">
	<div class="chatRoomAll_addPeopleText">
		<i class="fa fa-plus-circle" style="font-size:20px; padding-left:2%;"></i>
		<span class="chatRoomAll_addPeopleSpan">대화상대 초대</span>
	</div>
<c:forEach var="chatRoom_MIdList" items="${chatRoom_MIdList}">
	<div class="chatRoomAll_chatPeopleList listViewDesign">
		<img class='chatRoomMemImg PeopleList' src="/TIMS/IMG/user/${chatRoom_MIdList.m_id }/${chatRoom_MIdList.m_profileimage }" onerror='this.src="/TIMS/IMG/user/defaultprofile.png"'  >
		<span class="PeopleListName"> ${chatRoom_MIdList.m_id} </span>
	</div>
</c:forEach>

</div>