<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="imageList" items="${imageListResult }" varStatus="status">
	<img id="AddimageList_${status.count }" class="chatRoomAddmenu_imgList_img" 
	src="/TIMS/IMG/chatRoomAll/room_${oc_num }/img/${imageList}" alt="${idListResult[status.index] }">
</c:forEach>