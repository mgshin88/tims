<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>

<script>
setTimeout(function() {

	$("#img_play_RandomTrack").trigger("click");
}, (parseInt("${duration_Sec}") + 3) * 1000);
</script>

<style>
#player_duration, #player_trackNum, #player_video {
	display: none;
}

#player_title {
	width: 300px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

#player_uploader {
	width: 300px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>

<c:if test="${empty playList}">

	<p id="player_trackNum">0</p>

	<p id="player_title"></p>

	<p id="player_uploader"></p>

	<p id="player_duration">-1</p>

</c:if>

<c:if test="${!empty playList}">

	<p id="player_trackNum">${trackNum}</p>

	<iframe id="player_video" width="640" height="360" src="https://www.youtube.com/embed/${videoId}?autoplay=1&controls=0&disablekb=1&loop=1&modestbranding=1&playlist=${videoId}&rel=0&showinfo=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>

	<p id="player_title">${title}</p>

	<p id="player_uploader">${m_name} // ${duration_HMS}</p>

	<p id="player_duration">${duration_Sec}</p>

</c:if>