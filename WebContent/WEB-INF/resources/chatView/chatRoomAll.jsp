<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
<script src="/TIMS/JS/socket.io.js"></script>
  	
<style>
.inputFileBox {
position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0,0,0,0);
    border: 0;
}
</style>
<script>
	
	$(document).ready(function() {
		var socket = io.connect("http://192.168.0.76:12345");  //서버연결 
		
		if('${sessionUser}'== 1) { 
			var msg = '${sessionScope.roomMemId}';
		
			msg += "｀"+"님이 방에 입장했습니다."+"｀"+"text";
			socket.emit('msg', {msg:msg});
		}
		socket.on('response', function(msg){// 서버로부터 채팅메세지를 계속 받고있다. .. 
			appRoomNumber = msg.msg; // 3-modify-msg내용
			appRoomNumber = appRoomNumber.split("｀");
			
			my = '${sessionScope.roomMemId}';
			n = my.split("｀");
			
			if(appRoomNumber[0] == n[0]) {
				if(appRoomNumber[1] == n[1]) {
					if(appRoomNumber[4] == "text") {
					$('#msgs').append("<div class='mychatMsg'>"
							+ "<div class='mychatMsg_nameAndContent'>"
							+ "<span class='chatRoomName mychatName'>"+appRoomNumber[1]+"</span><br>"
							+ "<div class='balloon test_4'> <span class='chatRoomMsgContent'>"+appRoomNumber[3] 
							+ "</span></div>"
							+ "</div>"
							+ "<div class='mychatMsg_profile'>"
							+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
							+ "</div>"
							+ "</div><BR>"); // 채팅 메세지 받아 출력 부분..
					}
					else if(appRoomNumber[4] == "img") {
						$('#msgs').append("<div class='mychatMsg'>"
								+ "<div class='mychatMsg_nameAndContent'>"
								+ "<span class='chatRoomName mychatName'>"+appRoomNumber[1]+"</span><br>"
								+ "<div> <img class='requestimgStatus' src='/TIMS/IMG/chatRoomAll/room_"+appRoomNumber[0]+"/img/"+appRoomNumber[3]+"'>"
								+ "</div>"
								+ "</div>"
								+ "<div class='mychatMsg_profile'>"
								+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
								+ "</div>"
								+ "</div><BR>"); // 이미지를 받아 출력 부분..
					}
					else if(appRoomNumber[4] == "video") {
						$('#msgs').append("<div class='mychatMsg'>"
								+ "<div class='mychatMsg_nameAndContent'>"
								+ "<span class='chatRoomName mychatName'>"+appRoomNumber[1]+"</span><br>"
								+ "<div> <video width='300' controls src='/TIMS/IMG/chatRoomAll/room_"+appRoomNumber[0]+"/video/"+appRoomNumber[3]+"'>" 
								+ "</div>"
								+ "</div>"
								+ "<div class='mychatMsg_profile'>"
								+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
								+ "</div>"
								+ "</div><BR>"); // 이미지를 받아 출력 부분..
					}
					$("textarea[name=chat]").val('');
					$("textarea[name=chat]").focus();
				}
				else {
					if(appRoomNumber[4] == "text") {
				$('#msgs').append("<div class='chatMsg'>"
						+ "<div class='chatMsg_profile'>"
						+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"'' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
						+ "</div>"
						+ "<div class='chatMsg_nameAndContent'>"
						+ "<span class='chatRoomName youchatName'>"+appRoomNumber[1]+"</span><br>"
						+ "<div class='balloon test_3'> <span class='chatRoomMsgContent'>"+appRoomNumber[3] 
						+ "</span></div>"
						+ "</div></div><BR>"); // 채팅 메세지 받아 출력 부분..
					}
					else if(appRoomNumber[4] == "img") {
						$('#msgs').append("<div class='chatMsg'>"
								+ "<div class='chatMsg_profile'>"
								+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
								+ "</div>"
								+ "<div class='chatMsg_nameAndContent'>"
								+ "<span class='chatRoomName youchatName'>"+appRoomNumber[1]+"</span><br>"
								+ "<div> <img class='requestimgStatus' src='/TIMS/IMG/chatRoomAll/room_"+appRoomNumber[0]+"/img/"+appRoomNumber[3]+"'>"
								+ "</div>"
								+ "</div></div><BR>"); // 채팅 메세지 받아 출력 부분..
					}
					else if(appRoomNumber[4] == "video") {
						$('#msgs').append("<div class='chatMsg'>"
								+ "<div class='chatMsg_profile'>"
								+ "<img src='/TIMS/IMG/user/"+appRoomNumber[1]+"/"+appRoomNumber[2]+"' class='chatRoomMemImg' onerror=this.src='/TIMS/IMG/user/defaultprofile.png'>"
								+ "</div>"
								+ "<div class='chatMsg_nameAndContent'>"
								+ "<span class='chatRoomName youchatName'>"+appRoomNumber[1]+"</span><br>"
								+ "<div> <video width='300' controls src='/TIMS/IMG/chatRoomAll/room_"+appRoomNumber[0]+"/video/"+appRoomNumber[3]+"'>"
								+ "</div>"
								+ "</div></div><BR>"); // 채팅 메세지 받아 출력 부분..
					}
				}
			}
			
			$("#msgs").scrollTop($("#msgs")[0].scrollHeight);
			
			
		});
		
		$.ajax({
			type : "post",
			url : "/TIMS/musicPlayer/Playlist.com",
			data : {oc_num : '${crdto.oc_num}'},
			success : function(data) {
				$("#musicPlayer").html(data);
			}
		});
		
		function chatRoomExit(url) {
			var exitmsg = '${sessionScope.roomMemId}';
			
			exitmsg += "｀"+"님이 방을 나갔습니다."+"｀"+"text";
			socket.emit('msg', {msg:exitmsg});
			
			var oc_num = '${crdto.oc_num}';
			
			$.ajax({
				type : "post",
				url : "/TIMS/chatView/chatRoomExit.com",
				data : {oc_num : oc_num, url : url},
				success : function(data) {
					$("#messenger_view").html(data);
					socket.disconnect();
				}
			});
		}
		
		function chatRoomAllTalkPartner() {
			var oc_num = '${crdto.oc_num}';
			$.ajax({
				type : "post",
				url : "/TIMS/chatView/chatRoomAllTalkPartner.com",
				data : {oc_num : oc_num},
				success : function(data) {
					$(".chatRoomAddmenu_content").html(data);
				}
			});
		}
		
		function chatRoomAllNotice() {
			var oc_num = '${crdto.oc_num}';
			$.ajax({
				type : "post",
				url : "/TIMS/chatView/chatRoomAllNotice.com",
				data : {oc_num : oc_num},
				success : function(data) {
					$(".chatRoomAddmenu_content").html(data);
				}
			});
		}
		
		function chatRoomAllPicture() {
			var oc_num = '${crdto.oc_num}';
			$.ajax({
				type : "post",
				url : "/TIMS/chatView/chatRoomAllPicture.com",
				data : {oc_num : oc_num},
				success : function(data) {
					$(".chatRoomAddmenu_content").html(data);
				}
			});
		}
		
		function chatRoomAllVideo() {
			var oc_num = '${crdto.oc_num}';
			$.ajax({
				type : "post",
				url : "/TIMS/chatView/chatRoomAllVideo.com",
				data : {oc_num : oc_num},
				success : function(data) {
					$(".chatRoomAddmenu_content").html(data);
				}
			});
		}
		
		// 채팅 메시지를 직접 sendBtn을 클릭하여 텍스트박스내부의 채팅 내용 보내기
		$("#sendBtn").bind("click", function() {
			var textMsg = $.trim($("textarea[name=chat]").val());
			
			if(textMsg != '') {
				var msg = '${sessionScope.roomMemId}'; // 방 번호와 유저의 아이디를 담은 세션 내용 ex)1-modify
			
				msg += "｀"+$("textarea[name=chat]").val()+"｀"+"text"; // 1-modify-Thank you
			    
				$.ajax({
					type : "post",
					url : "/TIMS/chatView/chatAllSend.com",
					data : { sendMsg : msg},
					success : function(data) {
						
					}
				});
				socket.emit('msg', {msg:msg}); // 소켓에 전달
			}
		});
	
		// 엔터 키 입력 시 채팅 메시지 보냄 
		$("textarea[name=chat]").keydown(function (e) {
			var textMsg = $.trim($("textarea[name=chat]").val());
			
			if(e.keyCode == 13) {	 
				if(e.shiftKey === true) {
					// 쉬프트 키를 같이 입력하면 new line 입력 함
				} else {
					if(textMsg != '') {
						var msg = '${sessionScope.roomMemId}'; // 방 번호와 유저의 아이디를 담은 세션 내용 ex)1-modify
				
						msg += "｀"+$("textarea[name=chat]").val()+"｀"+"text"; // 1-modify-Thank you
						
						$.ajax({
							type : "post",
							url : "/TIMS/chatView/chatAllSend.com",
							data : { sendMsg : msg},
							success : function(data) {
								
							}
						});
						
						socket.emit('msg', {msg:msg}); // 소켓에 전달	
					}
				}
			}
		});
		
		// 이미지를 등록하려 할 때
		$("#imageUpload").on('change', function() {
			var count = 1;
			var msg = '${sessionScope.roomMemId}';
			if(window.FileReader){
	            // 파일명 추출
	            var filename = $(this)[0].files[0].name;
	        } 

	        else {
	            // Old IE 파일명 추출
	            var filename = $(this).val().split('/').pop().split('\\').pop();
	        };
	        
	        $("#chatRoomfilePro").ajaxForm({
	        	type : "post",
	        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	        	data : {count : count},
	        	url : "/TIMS/chatView/chatAllFileSend.com",
	        	enctype : "multipart/form-data",
	        	error : function() {
	        		alert("에러");
	        	},
	        	success : function(result) {
					console.log(result);
					msg += "｀"+result+"｀"+"img";
	    	        socket.emit('msg', {msg:msg}); // 소켓에 전달
	        	}
	        });
	        
	        $("#chatRoomfilePro").submit();
	        
		});
		
		$("#videoUpload").on('change', function(){
			var count = 2;
			var msg = '${sessionScope.roomMemId}';
			if(window.FileReader){
	            // 파일명 추출
	            var filename = $(this)[0].files[0].name;
	        } 

	        else {
	            // Old IE 파일명 추출
	            var filename = $(this).val().split('/').pop().split('\\').pop();
	        };
	        
	        $("#chatRoomvideoPro").ajaxForm({
	        	type : "post",
	        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	        	data : {count : count},
	        	url : "/TIMS/chatView/chatAllFileSend.com",
	        	enctype : "multipart/form-data",
	        	error : function() {
	        		alert("에러");
	        	},
	        	success : function(result) {
					console.log(result);
					msg += "｀"+result+"｀"+"video";
	    	        socket.emit('msg', {msg:msg}); // 소켓에 전달
	        	}
	        });
	        
	        $("#chatRoomvideoPro").submit();
		});
		
		$(".chatRoomTop_backbutton").bind("click", function() {
			var url = "chatRoomAllList.com";
			chatRoomExit(url);
		});
		
		/* 검색 버튼 클릭 시 발생하는 이벤트 */
		$(".chatRoomTop_searchbutton").bind("click", function() {
			$(".Msgs_search").css("display", "table");
		});
		
		/* 검색 창에서 X버튼을 누를 시 발생하는 이벤트 */
		$("#fa_Msgs_searchClose").bind("click", function() {
			$(".Msgs_search").css("display", "none");
		});
		
		/* 이미지 파일 업로드 버튼 누를 시 발생하는 이벤트 */
		$("#chatRoomImageUpload").bind("click", function() {
			$("input[name=imageUpload]").click();
		});
		
		$(".chatRoomVideoUpload").bind("click", function() {
			$("input[name=videoUpload]").click();
		});
		
		$(".chatRoomTop_addMenubutton").bind("click", function() {
			chatRoomAllTalkPartner();
			$(".chatRoomAddmenu_menubarTalkpartner").css("background-color", "#dbb5ff");
			$(".chatRoomAddmenu_menubarNotice").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarPicture").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarVideo").css("background-color", "transparent");
			$(".chatRoomAddmenu").css("display", "block");
			$(".chatRoomAddmenu").animate({
				marginLeft : "15%"
			}, 300);
		});
		
		$(".chatRoomAddmenu_topright").bind("click", function() {
			$(".chatRoomAddmenu").animate({
				marginLeft : "100%"
			}, 300);
			$(".chatRoomAddmenu").css("display", "none");
			
		});
		
		$(".chatRoomTop_addMenubutton").bind("click", function() {
			chatRoomAllTalkPartner();
		});
		
		$(".chatRoomAddmenu_menubarTalkpartner").bind("click", function() {
			$(".chatRoomAddmenu_menubarTalkpartner").css("background-color", "#dbb5ff");
			$(".chatRoomAddmenu_menubarNotice").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarPicture").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarVideo").css("background-color", "transparent");
			
			chatRoomAllTalkPartner();
		});
		
		$(".chatRoomAddmenu_menubarNotice").bind("click", function() {
			$(".chatRoomAddmenu_menubarNotice").css("background-color", "#dbb5ff");
			$(".chatRoomAddmenu_menubarTalkpartner").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarPicture").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarVideo").css("background-color", "transparent");
			
			chatRoomAllNotice();
		});
		
		$(".chatRoomAddmenu_menubarPicture").bind("click", function() {
			$(".chatRoomAddmenu_menubarPicture").css("background-color", "#dbb5ff");
			$(".chatRoomAddmenu_menubarTalkpartner").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarNotice").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarVideo").css("background-color", "transparent");
			
			chatRoomAllPicture();
		});
		
		$(".chatRoomAddmenu_menubarVideo").bind("click", function() {
			$(".chatRoomAddmenu_menubarVideo").css("background-color", "#dbb5ff");
			$(".chatRoomAddmenu_menubarTalkpartner").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarNotice").css("background-color", "transparent");
			$(".chatRoomAddmenu_menubarPicture").css("background-color", "transparent");
			
			chatRoomAllVideo();
		});
		
	})
</script>
<div class="chatRoomAddmenu">
		<div class="chatRoomAddmenu_top">
			<div class="chatRoomAddmenu_topleft">
				<span class="chatRoomAddmenu_topTitle">채팅방 게시판</span>	
			</div>
			<div class="chatRoomAddmenu_topright">
				<i class="fa fa-chevron-circle-right" style="font-size:24px;"></i>
			</div>
			
		</div>
		
		<div class="chatRoomAddmenu_menubar">
			<div class="chatRoomAddmenu_menubarTalkpartner">
				<span class="chatRoomAddmenu_menubarText">대화상대</span>
			</div>
			<div class="chatRoomAddmenu_menubarNotice">
				<span class="chatRoomAddmenu_menubarText">공지</span>
			</div>
			<div class="chatRoomAddmenu_menubarPicture">
				<span class="chatRoomAddmenu_menubarText">사진</span>
			</div>
			<div class="chatRoomAddmenu_menubarVideo">
				<span class="chatRoomAddmenu_menubarText">동영상</span>
			</div>
		</div>
		
		<div class="chatRoomAddmenu_content">
			
		</div>
	</div>

<div class="chatRoomTop">
	<div class="chatRoomTop_backbutton">
		<i class="fa fa-chevron-left"></i>
	</div>
	<div class="chatRoomTop_title">
		<span class="chatRoomTop_oc_name">${crdto.oc_name }</span>
		<span>${oc_now }</span>
		<span>${crdto.oc_max }</span>
	</div>
	<div class="chatRoomTop_searchAndmenulist">
		<div class="chatRoomTop_searchAndmenulist_tt">
			<div class="chatRoomTop_searchbutton">
				<i class="fa fa-search" style="margin-left:0px;color:black;top:0px;"></i>
			</div>
			<div class="chatRoomTop_addMenubutton">
				<i class="fa fa-navicon"></i>	
			</div>
		</div>
	</div>
</div>
	
	<div class="chatRoomBottom_chatinput">
		
	
		<div class="chatRoomBottom_chating">
			<textarea class="chatText" name="chat"></textarea>
		</div>
		<div class="chatRoomBottom_chatSendAndEtc">
			<div class="chatRoomBottom_chatSend">
				<input class="sendButton" type="button" value="전송" id="sendBtn" />
			</div>
			<div class="chatRoomBottom_chatEtc">
				<div id="chatRoomImageUpload" class="chatRoomImageUpload">
					<i class="far fa-image"></i>
				</div>
				<div class="chatRoomVideoUpload">
					<i class="fa fa-video"></i>	
				</div>
			</div>
		</div>
		
	</div>
	
	
	<form id="chatRoomfilePro" name="chatRoomfilePro" enctype="multipart/form-data" method="post" accept-charset="UTF-8">
		<input type="hidden" name="oc_num" value="${crdto.oc_num }"/>
		<input type="file" class="inputFileBox" name="imageUpload" id="imageUpload" accept="image/*">
	</form>
	
	<form id="chatRoomvideoPro" name="chatRoomvideoPro" enctype="multipart/form-data" method="post" accept-charset="UTF-8">
		<input type="hidden" name="oc_num" value="${crdto.oc_num }"/>
		<input type="file" class="inputFileBox" name="videoUpload" id="videoUpload" accept="video/*">
	</form>	
	<div id="msgs">
		<div class="Msgs_search">
			<div class="Msgs_searchText">
				<input class="chatRoom_searchText" type="text" name="chatRoomSearchText" placeholder="Search..">
			</div>
			<div class="Msgs_searchClose">
				<i id="fa_Msgs_searchClose" class="fa fa-times" style="font-size:28px;"></i>
			</div>
		</div>
	</div>
	
	<div id="musicPlayer" class="chatRoomBottom_musicPlayer">
		
	</div>
	
	

	
	