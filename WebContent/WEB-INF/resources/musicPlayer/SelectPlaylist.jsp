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

<c:if test="${empty playList}">

	<div id="list">

		<br> <br> <br>

		<h1>재생목록이 비어있습니다.</h1>

		<br>

		<h3>원하시는 곡을 검색하여 추가하세요.</h3>

	</div>

</c:if>

<c:if test="${!empty playList}">

	<div id="list" style="height: 280px; overflow-y: scroll;">

		<table cellpadding="10" style="width : 100%;">

			<c:forEach var="i" begin="0" end="${playList.size() - 1}">

			<tr>

				<td>

					<img id="img_thumbnail" alt="" src="https://i.ytimg.com/vi/${videoIdArray[i]}/default.jpg">

				</td>

				<td>

					<p id="playlist_title">${titleArray[i]}</p> <br>

						${numArray[i]} // ${durationArray[i]} // <a href="javascript:;" onclick="deleteFromPlaylist('${videoIdArray[i]}')">삭제</a>

				</td>

				<td>

					<img src="https://png.icons8.com/metro/50/9676FF/play.png" onclick="playTrack('${i + 1}')">

				</td>

			</tr>

			</c:forEach>

		</table>

	</div>

</c:if>