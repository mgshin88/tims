<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>
	$(document).ready(function() {

		$("img").mousedown(function(){

			$(this).attr("src", function(index, attr){

				return attr.replace("/9676FF/", "/800080/");
			});
		});

		$("img").mouseup(function(){

			$(this).attr("src", function(index, attr){

				return attr.replace("/800080/", "/9676FF/");
			});
		});
	});
</script>

<c:if test="${empty itemsList}">

	<div id="list">

		<br> <br> <br>

		<h1>검색 결과가 없습니다.</h1>

	</div>

</c:if>

<c:if test="${!empty itemsList}">

	<div id="list" style="height: 240px; overflow-y: scroll;">

		<table cellpadding="10" style="width : 100%;">

			<c:forEach var="i" begin="0" end="${itemsList.size() - 1}">

			<tr>

				<td>

					<img id="img_thumbnail" alt="" src="https://i.ytimg.com/vi/${videoIdList.get(i)}/default.jpg">

				</td>

				<td>

					<p id="searchlist_title">${titleList.get(i)}</p> <br> ${duration_HMSList.get(i)} // <a href="javascript:;" onclick="preview('${videoIdList.get(i)}', '${titleList.get(i)}', '${duration_HMSList.get(i)}', '${duration_SecList.get(i)}')">미리 듣기</a>
				</td>

				<td>

					<img src="https://png.icons8.com/metro/50/9676FF/add-list.png" onclick="insertIntoPlaylist('${videoIdList.get(i)}', '${titleList.get(i)}', '${duration_SecList.get(i)}')">

				</td>

			</tr>

			</c:forEach>

		</table>

	</div>

</c:if>