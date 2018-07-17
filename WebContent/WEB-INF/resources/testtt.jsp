<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<c:forEach var="frs" items="${result}">
	<h2>content ==========>>>${frs[3]}</h2>
	<h2>content ==========>>>${frs[6]}</h2>
</c:forEach>

