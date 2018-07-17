<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="videoList" items="${videoListResult }" varStatus="status">
	<video controls id="AddvideoList_${status.count }" class="chatRoomAddmenu_videoList_img" 
	src="/TIMS/IMG/chatRoomAll/room_${oc_num }/video/${videoList}">
	</video>
</c:forEach>