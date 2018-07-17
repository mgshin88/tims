<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${check == 0 }">
	선택한 곡이 추가되었습니다
</c:if>

<c:if test="${check != 0}">
	이미 곡이 추가되어 있습니다
</c:if>
