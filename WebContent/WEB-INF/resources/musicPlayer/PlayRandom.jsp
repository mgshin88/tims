<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
setTimeout(function() {

	$("#img_play_RandomTrack").trigger("click");
}, (parseInt("${duration_Sec}") + 3) * 1000);
</script>

<c:if test="${empty playList}">

	<p id="player_trackNum">0</p>

	<p id="player_title"></p>

	<p id="player_uploader"></p>

	<p id="player_duration">-1</p>

</c:if>

<c:if test="${!empty playList}">

	<p id="player_trackNum">${trackNum_Random}</p>

	<iframe id="player_video" width="640" height="360" src="https://www.youtube.com/embed/${videoId}?autoplay=1&controls=0&disablekb=1&loop=1&modestbranding=1&playlist=${videoId}&rel=0&showinfo=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>

	<p id="player_title">${title}</p>

	<p id="player_uploader">${m_name} // ${duration_HMS}</p>

	<p id="player_duration">${duration_Sec}</p>

</c:if>