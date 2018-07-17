<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	function deleteFromPlaylist(pl_videoId) {
		var oc_num = '${oc_num}';
		$.ajax({

			type : "POST",

			url : "/TIMS/musicPlayer/DeletePlaylist.com",

			data : {

				pl_videoId : pl_videoId,

				oc_num : oc_num
			},

			success : function(result) {

				$("#player_trackNum").html(0);
				
				$("#player_video").html("");

				$("#player_title").html("");

				$("#player_uploader").html("");

				$("#div_playlistTab").html(result);

				alert("선택한 곡이 삭제되었습니다.");
			}
		});
	}

	function insertIntoPlaylist(pl_videoId, pl_title, pl_duration) {
		var oc_num = '${oc_num}';
		var m_num = '${sessionScope.memNum}';
		
		$.ajax({

			type : "POST",

			url : "/TIMS/musicPlayer/InsertPlaylist.com",

			data : {

				pl_videoId : pl_videoId,

				oc_num : oc_num,

				pl_title : pl_title,

				m_num : m_num,
				
				pl_duration : pl_duration
			},
			
			success : function(data) {
				alert(data.trim());
			}
		});
	}

	function playTrack(trackNum) {
		var oc_num = '${oc_num}';
		$.ajax({

			type : "POST",

			url : "/TIMS/musicPlayer/PlayTrack.com",

			data : {

				oc_num : oc_num,

				trackNum : trackNum
			},

			success : function(result) {

				$("#div_player_trackInfo").html(result);
			}
		});
	}

	function preview(videoId, title, duration_HMS, duration_Sec) {

		$.ajax({

			type : "POST",

			url : "/TIMS/musicPlayer/Preview.com",

			data : {

				videoId : videoId,

				title : title,

				duration_HMS : duration_HMS,

				duration_Sec : duration_Sec
			},

			success : function(result) {

				$("#div_player_trackInfo").html(result);
			}
		});
	}

	$(document).ready(function() {

		$("#div_chatingRoom").click(function() {

			$("#div_musicRoomTab").hide();

			$("#div_chatingRoomTab").height(600).css;

			$("#div_chatingRoom").height(600).css;
		});

		$("img").mousedown(function() {

			$(this).attr("src", function(index, attr) {

				return attr.replace("/9676FF/", "/800080/");
			});
		});

		$("img").mouseup(function() {

			$(this).attr("src", function(index, attr) {

				return attr.replace("/800080/", "/9676FF/");
			});
		});

		$("#img_show_playlistTab").click(function() {
			var oc_num = '${oc_num}';
			$.ajax({

				type : "POST",

				url : "/TIMS/musicPlayer/SelectPlaylist.com",

				data : {

					oc_num : oc_num
				},

				success : function(result) {

					$("#div_playlistTab").html(result);

					$("#div_searchlistTab").hide();

					$("#div_playlistTab").fadeIn();
				}
			});
		});

		$("#img_show_searchlistTab").click(function() {

			$("#div_playlistTab").hide();

			$("#div_searchlistTab").fadeIn();
		});

		$("#img_open_musicRoomTab").click(function() {
			var oc_num = '${oc_num}';
			if($("#div_musicRoomTab").css("display") == "none" ) {
				$.ajax({

					type : "POST",

					url : "/TIMS/musicPlayer/SelectPlaylist.com",

					data : {

						oc_num : oc_num
					},

					success : function(result) {

						$("#div_playlistTab").html(result);

						$("#div_musicRoomTab").fadeIn();
					}
				});
			}
			else {
				$("#div_musicRoomTab").fadeOut();
			}
		});

		$("#img_play_NextTrack").click(function() {
			var oc_num = '${oc_num}';
			$.ajax({

				type : "POST",

				url : "/TIMS/musicPlayer/PlayNext.com",

				data : {

					oc_num : oc_num,

					trackNum : $("#player_trackNum").text()
				},

				success : function(result) {

					$("#div_player_trackInfo").html(result);
				}
			});
		});

		$("#img_play_PreviousTrack").click(function() {
			var oc_num = '${oc_num}';
			$.ajax({

				type : "POST",

				url : "/TIMS/musicPlayer/PlayPrevious.com",

				data : {

					oc_num : oc_num,

					trackNum : $("#player_trackNum").text()
				},

				success : function(result) {

					$("#div_player_trackInfo").html(result);
				}
			});
		});

		$("#img_play_RandomTrack").click(function() {
			var oc_num = '${oc_num}';
			$.ajax({

				type : "POST",

				url : "/TIMS/musicPlayer/PlayRandom.com",

				data : {

					oc_num : oc_num,
					
					trackNum : $("#player_trackNum").text()
				},

				success : function(result) {

					$("#div_player_trackInfo").html(result);
				}
			});

			$.ajax({

				type : "POST",

				url : "/TIMS/musicPlayer/SelectPlaylist.com",

				data : {

					oc_num : oc_num
				},

				success : function(result) {

					$("#div_playlistTab").html(result);
				}
			});
		});

		$("#input_search").keydown(function(key) {

			if (key.keyCode == 13) {

				$.ajax({

					type : "POST",

					url : "/TIMS/musicPlayer/Searchlist.com",

					data : {

						search : encodeURIComponent($("#input_search").val())
					},

					success : function(result) {

						$("#div_searchlist").html(result);
					}
				});
			}
		});
	});
</script>

<style>
.pl_img {
	width : 60%;
}


#div_musicRoomTab {
	display: none;
	position : absolute;
	top : -355px;
	width : 100%;
	height : 354px;
	background-color : #ffffff;
}

#div_playlistTab, #div_searchlistTab {
	height: 280px;
}

#div_searchlistTab, #div_videoTab {
	display: none;
}

#img_thumbnail:hover {
	transform: scale(1.2);
}

#list::-webkit-scrollbar {
	width: 0px;
	background: transparent;
}

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

#playlist_title, #searchlist_title {
	width: 410px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

#div_player {
	display : flex;
	height: 100%;
    text-align: center;
    align-items: center;
}

#playerTab {
	height : 100%;
	background-color : #ffffff;
	opacity : 0.8;
}

.col-12 {
	width : 12%;
}

.col-50 {
	width : 50%;
}

.musicRoomTab {
	margin-top : -67%;
}

#div_musicRoomTab .form-row {
	display : flex;
	padding : 10px;
}
</style>
		<div id="playerTab">

			<div id="div_player" class="form-row">

				<div class="col-12">

					<img class="pl_img" id="img_play_PreviousTrack" src="https://png.icons8.com/ios/80/9676FF/rewind-filled.png">

				</div>

				<div id="div_player_trackInfo" class="col-50">

					<c:if test="${empty playList}">
						
						<p id="player_trackNum" hidden>0</p>

						<p id="player_title"></p>

						<p id="player_uploader"></p>
						
						<p id="player_duration">-1</p>

					</c:if>

					<c:if test="${!empty playList}">
						<script>
						setTimeout(function() {

							$("#img_play_RandomTrack").trigger("click");
						}, (parseInt("${duration_Sec}") + 3) * 1000);
						</script>
						
						<p id="player_trackNum" hidden>1</p>

						<iframe id="player_video" width="640" height="360" src="https://www.youtube.com/embed/${videoId}?autoplay=1&controls=0&disablekb=1&loop=1&modestbranding=1&playlist=${videoId}&rel=0&showinfo=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>

						<p id="player_title">${title}</p>

						<p id="player_uploader">${m_name} // ${duration_HMS}</p>

						<p id="player_duration">${duration_Sec}</p>

					</c:if>

				</div>

				<div class="col-12">

					<img class="pl_img" id="img_play_RandomTrack" src="https://png.icons8.com/ios/80/9676FF/shuffle-filled.png">

				</div>

				<div class="col-12">

					<img class="pl_img" id="img_play_NextTrack" src="https://png.icons8.com/ios/80/9676FF/fast-forward-filled.png">

				</div>
				
				<div id="div_open_playlistTab" class="col-12">

					<img class="pl_img" id="img_open_musicRoomTab" src="https://png.icons8.com/metro/50/9676FF/list.png">

				</div>

			</div>
		</div>
		
			<div id="div_musicRoomTab">

				<div class="form-row">

					<div class="col-12 col-md-2">

						<img class="pl_img" id="img_show_playlistTab" src="https://png.icons8.com/metro/50/9676FF/music-folder.png">

					</div>

					<div class="col-12 col-md-2">

						<img class="pl_img" id="img_show_searchlistTab" src="https://png.icons8.com/metro/50/9676FF/search.png">

					</div>

				</div>


				<div id="div_playlistTab">

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

							<table cellpadding="10">

								<c:forEach var="i" begin="0" end="${playList.size() - 1}">

								<tr>

									<td>

										<img id="img_thumbnail" alt="" src="https://i.ytimg.com/vi/${videoIdArray[i]}/default.jpg">

									</td>

									<td>

										<p id="playlist_title">${titleArray[i]}</p> 

										<br>

										(by.${numArray[i]}) // <a href="javascript:;" onclick="deleteFromPlaylist('${videoIdArray[i]}')">삭제</a>

									</td>

									<td>

										<img src="https://png.icons8.com/metro/50/9676FF/play.png" onclick="playTrack('${videoIdArray[i]}', '${titleArray[i]}', '${numArray[i]}')">

									</td>

								</tr>

								</c:forEach>

							</table>

						</div>

					</c:if>

				</div>

				<div id="div_searchlistTab">

					<input id="input_search" type="text" class="form-control form-control-lg">

					<small class="form-text">

						<mark><b>특수문자(!, @, #, $...)</b></mark>는 제대로 검색이 되지 않을 수 있습니다.

					</small>

					<br>

					<div id="div_searchlist"></div>

				</div>

			</div>



