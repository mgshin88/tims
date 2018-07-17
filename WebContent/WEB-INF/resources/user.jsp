<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/TIMS/CSS/master.css">
<link rel="stylesheet" type="text/css" href="/TIMS/CSS/user.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script language="javascript" src="/TIMS/JS/master.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js">
<script type="text/javascript" src="js/js-load.js"></script>
	

<link rel="stylesheet" href="/TIMS/CSS/autocomplete.css">
</head>
<script type="text/javascript">


	// 아이디 검색
	var availableTags = new Array();
	<c:forEach items="${usercheck}" var="user">
	availableTags.push("${user.m_name}" + "," + "${user.m_id}" + ","
			+ "${user.m_profileimage}");
	</c:forEach>
	function getName() {
		console.log(availableTags);
		$("#searchBar")
				.autocomplete({	
						source : availableTags,
						minLength : 2,
						create : function(event, ui) {
							$(this).data('ui-autocomplete')._renderItem = function(ul, item) {
								$(".ui-autocomplete").css("height", "200px");
								$(".ui-autocomplete").css("overflowY","scroll");
								var inf = item.label;
								var info = inf.split(",");
								console.log(info[2]);
								if (info[2] != 'defaultprofile.png') {
										return $('<li>')
												.append("<hr><img style='width:35px;height:35px;cursor:pointer;vertical-align:middle;margin-top:1px' src='/TIMS/IMG/user/"+info[1]+"/profile_IMG/"+info[2]+"'/>")
												.append("<span style='cursor:pointer;margin-left:5px;'>"+ info[0]+ "</span>")
												.append("("+ "<span style='cursor:pointer;font-size:12px;'>"+ info[1]+ "</span>"+ ")")
												.click(function() {window.location = "/TIMS/user.com?m_id="+ info[1]
														})
														.appendTo(ul);
									} else if (info[2] == 'defaultprofile.png') {
										return $('<li>')
												.append("<hr><img style='width:35px;height:35px;cursor:pointer;vertical-align:middle;margin-top:1px' src='/TIMS/IMG/user/defaultprofile.png' />")
												.append("<span style='cursor:pointer;margin-left:5px;'>"+ info[0]+ "</span>")
												.append("("+ "<span style='cursor:pointer;font-size:12px;'>"+ info[1]+ "</span>"+ ")")
												.click(function() {
															window.location = "/TIMS/user.com?m_id="+ info[1]
														})
														.appendTo(ul);
									}
								};
							}
						});
	}
	$(function(){
		var myid = "${sessionScope.memId}";
		var followid = "${dto.m_id}";
		var myid2 = "${sessionScope.memId}";
		var unfollow = "${dto.m_id}";
		$('#zlzlzl').click(function(){
			if($(this).val()=='팔로우하기'){
				$(this).val('팔로잉중');
				$.ajax({
					url:'/TIMS/user.com',
					data:{myid : myid,followid : followid,m_id : myid},
					type:'POST',
					success : function(data){
						$('#zlzlzl').val('팔로잉중');
					}
					
				})
			} else if($(this).val()=='팔로잉중'){
				$(this).val('팔로우하기');
				$.ajax({
					url:'/TIMS/user.com',
					type : 'POST',
					data:{myid2 : myid2 , unfollow : unfollow ,m_id : myid2}
				})
			}
		})
	})
</script>
<style type="text/css">
	.js-load {
	    display: none;
	}
	.js-load.active {
	    display: block;
	}
	.is_comp.js-load:after {
	    display: none;
	}
	.btn-wrap, .lists, .main {
	    display: block;
	}
	.main {
	    max-width: 640px;
	    margin: 0 auto;
	}
	.lists {
	    margin-bottom: 4rem;
	}
	.lists__item {
	    padding: 20px;
	    background: #EEE;
	}
	.lists__item:nth-child(2n) {
	    background: #59b1eb;
	    color: #fff;
	}
	.btn-wrap {
	    text-align: center;
	}
</style> 
<body onload="getName();" style="height: 500px;">
	<nav class="header">
		<div class="header_bar vertical">
			<div class="header_logo_cover vertical_cell">
				<div class="header_logo vertical">
					<a href="/TIMS/main.com"><h1>tims</h1>
						<p>TASTE IN MUSIC</p> </a>
				</div>
			</div>
			<div class="header_searchBar_cover vertical_cell">
				<div class="header_searchBar vertical">
					<div class="searchBar vertical_cell">
						<span class="fa fa-search" id="fa"></span> <input id="searchBar"
							class="transform" type="text" name="search" placeholder="검색">
					</div>
				</div>
			</div>
			<div class="header_function_cover vertical_cell">
				<div class="header_function">
					<div class="header_function_navigator horizon_function vertical">
						<a href="#" class="header_function_link align vertical_cell">
							<img src="/TIMS/IMG/admin/icons/notifications.png" alt="">
						</a>
					</div>
					<div class="header_function_alert horizon_function vertical">
						<a href="#" class="header_function_link align vertical_cell">
							<img src="/TIMS/IMG/admin/icons/navigation.png" alt="">
						</a>
					</div>
					<div class="header_function_myPage horizon_function vertical">
						<a href="#" class="header_function_link align vertical_cell">
							<img src="/TIMS/IMG/admin/icons/mypage.png" alt="">
						</a>
					</div>
				</div>
			</div>
		</div>
	</nav>
	<!-- 
	<a href="/TIMS.../ffdssdfsd.com?m_id="${dto.m_id } type="_blank"></a>
	-->
	<!-- 헤더부분 -->

	<div id="container">
		<div class="userinfoma">
			<div class="divv">
				<c:if test="${dto.m_profileimage eq 'defaultprofile.png'}">
							<img src="/TIMS/IMG/user/defaultprofile.png" class="profileimage" alt=""></c:if>
							<c:if test="${dto.m_profileimage ne 'defaultprofile.png'}">
							<img src="/TIMS/IMG/user/${dto.m_id}/profile_img/${dto.m_profileimage}" class="profileimage" alt=""></c:if><br />
			</div>
			<div class="divv">
				<c:if test="${sessionScope.memId != dto.m_id}">
					<span class="m_id"><b>${dto.m_id}</b></span>
					<p style="margin-left: 20%">${dto.m_name}${dto.m_intro}</p>
					<p style="margin-left: 20%; font-size: 12px; white-space: pre; max-width: 100px; margin-top: 5px;">${dto.m_intro}</p>
					<c:if test="${a==0}"><p style="margin-left: 20%">팔로워 <b>0</b> 팔로잉 <b>0</b> 친구 <b>0</b></p></c:if>
					<c:if test="${a!=0}"><p style="margin-left: 20%">
					팔로워<c:if test="${Followernum==0}"><b>${Followernum}</b></c:if>
					<c:if test="${Followernum!=0}"><b onclick="window.location='/TIMS/Followerlist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Followernum}</b></c:if>
					팔로잉<c:if test="${Followingnum==0}"><b>${Followingnum}</b></c:if> 
					<c:if test="${Followingnum!=0}"><b onclick="window.location='/TIMS/Followinglist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Followingnum}</b></c:if>
					<c:if test="${Friendsnum==0}">친구<b>${Friendsnum}</b></c:if>
					<c:if test="${Friendsnum!=0 }">
					 친구 <b onclick="window.location='/TIMS/Friendslist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Friendsnum}</b></c:if></p></c:if>
				</c:if>
				<c:if test="${sessionScope.memId == dto.m_id}">
					<span class="m_id"><b>${dto.m_id}</b>
					<input type="button" style="margin-left: 10px;" value="프로필 설정" onclick="window.location='/TIMS/member/modify.com?m_id=${dto.m_id}'"></span>
					<p style="margin-left: 20%">${dto.m_name}${dto.m_intro}</p>
					<p style="margin-left: 20%; font-size: 12px; white-space: pre; max-width: 100px; margin-top: 5px;">${dto.m_intro}</p>
					<c:if test="${a==0}"><p style="margin-left: 20%">팔로워 <b>0</b> 팔로잉 <b>0</b> 친구 <b>0</b></p></c:if>
					<c:if test="${a!=0}"><p style="margin-left: 20%">
					팔로워<c:if test="${Followernum==0}"><b>${Followernum}</b></c:if>
					<c:if test="${Followernum!=0}"><b onclick="window.location='/TIMS/Followinglist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Followernum}</b></c:if>
					팔로잉<c:if test="${Followingnum==0}"><b>${Followingnum}</b></c:if> 
					<c:if test="${Followingnum!=0}"><b onclick="window.location='/TIMS/Followinglist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Followingnum}</b></c:if>
					<c:if test="${Friendsnum==0}">친구<b>${Friendsnum}</b></c:if>
					<c:if test="${Friendsnum!=0 }">
					 친구 <b onclick="window.location='/TIMS/Friendslist.com?m_id=${dto.m_id}'" style="cursor:pointer">${Friendsnum}</b></c:if></p></c:if>
				</c:if>
			</div><br/>
			<c:if test="${sessionScope.memId != dto.m_id}">
			<c:if test="${folcheck != 1}">
				<input type="button" style="margin-left:20%" name="follow" value="팔로우하기" id="zlzlzl"/></c:if>
			<c:if test="${folcheck == 1}">
				<input type="button" style="margin-left:20%" name="follow" value="팔로잉중" id="zlzlzl"/></c:if></c:if>
			
		</div>
	</div>
	<!-- 프로필부분 -->
	<!-- 	<div id="css_tabs" style="position:absolute;top:50%;left:55%;transform:translate(-50%,-50%);width:1000px;">
		<input id="tab1" type="radio" name="tab" checked="checked" />
		 <label for="tab1">게시글</label> -->
<div style="margin-left:600px;margin-top:300px">
 <c:forEach items="${cntList}" var="cntList">
	<div class="list">
		<c:if test="${cntList[4] ne 'null'}"><img src="/TIMS/IMG/user/${cntList[0]}/twt_IMG/${cntList[4]}" style="width:100px;height:100px" alt=""></c:if>
	   <span>${cntList[6]},${cntList[7]}</span>
       <span>${cntList[3]}</span>
        </div></c:forEach></div>


</body>
</html>