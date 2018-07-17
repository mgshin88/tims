<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.rosuda.REngine.Rserve.RConnection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html dir="ltr">

<head>
<meta charset="utf-8">
<title>TIMS.Taste In Music</title>
<link rel="stylesheet" href="/TIMS/CSS/master.css">
<script src="/TIMS/JS/master.js"></script>
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src = "//code.jquery.com/ui/1.10.4/jquery-ui.js"> </script>
<script type="text/javascript" src="JS/jquery.form.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:700"rel="stylesheet">
<link rel="stylesheet" href="/TIMS/CSS/kyuCSS.css">  
<link rel="stylesheet" href="/TIMS/CSS/autocomplete.css">
<script>
//아이디 검색
var availableTags = new Array();
<c:forEach items="${usercheck}" var="user">
	availableTags.push("${user.m_name}" + "," + "${user.m_id}"+"," + "${user.m_profileimage}");
</c:forEach>
function getName() {
	console.log(availableTags);
	$("#searchBar").autocomplete({
		source:availableTags,
		minLength : 2,
		create: function (event,ui){
               $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
            	   $(".ui-autocomplete").css("height", "200px");
            	   $(".ui-autocomplete").css("overflowY", "scroll");
            	  	var inf = item.label;
            	  	var info = inf.split(",");
            	  	var yourid = info[1];
            	  	console.log(info[2]);
            	  	if(info[2] != 'defaultprofile.png') {
            	   return $('<li>')
            	   .append("<hr><img style='width:35px;height:35px;cursor:pointer;vertical-align:middle;margin-top:1px' src='/TIMS/IMG/user/"+info[1]+"/profile_IMG/"+info[2]+"'/>" )	
            	   .append("<span style='cursor:pointer;margin-left:5px;'>" + info[0] + "</span>")
            	   .append("(" + "<span style='cursor:pointer;font-size:12px;'>" + info[1] + "</span>" + ")")
	          	  	.click(function(){
	          	  		window.location="/TIMS/user.com?m_id=" + info[1];
	          	  	})
                    .appendTo(ul); 
            	  	}
            	  	else if(info[2] == 'defaultprofile.png') {
	            	  	
 	            	   return $('<li>')
 	            	   .append("<hr><img style='width:35px;height:35px;cursor:pointer;vertical-align:middle;margin-top:1px' src='/TIMS/IMG/user/defaultprofile.png' />" )	
 	            	   .append("<span style='cursor:pointer;margin-left:5px;'>" + info[0] + "</span>")
 	            	   .append("(" + "<span style='cursor:pointer;font-size:12px;'>" + info[1] + "</span>" + ")")
 		          	  	.click(function(){
 		          	  		window.location="/TIMS/user.com?m_id=" + info[1]
 		          	  	})
 	                    .appendTo(ul); 
 	            	  	}
            };          
		}			
	});
}

$(document).ready(function() {
	$("#friendsList").bind("click", function() {
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/friendsList.com",
			success : function(data) {
				$("#messenger_view").html(data);
			}
		})
	});
	
	$("#openChatList").bind("click", function() {
		$.ajax({
			type : "post",
			url : "/TIMS/chatView/chatRoomAllList.com",
			success : function(data) {
				$("#messenger_view").html(data);
			}
		})
	});
});
	
</script>
</head>

<body onload='getName()'>
	<nav class="header">
	<div class="header_bar vertical">
		<div class="header_logo_cover vertical_cell">
			<div class="header_logo vertical">
				<a href="main.com">
					<h1>tims</h1>
					<p>TASTE IN MUSIC</p>
				</a>
			</div>
		</div>
		<div class="header_searchBar_cover vertical_cell">
			<div class="header_searchBar vertical">
				<div class="searchBar vertical_cell" onclick="searchFunction()">
					<span class="fa fa-search" id="fa"></span> <input id="searchBar"
						class="transform" type="text" name="search" placeholder="검색">
				</div>
			</div>
		</div>
		<div class="header_function_cover vertical_cell">
			<div class="header_function">
				<div class="header_function_navigator horizon_function vertical">
					<a href="#" class="header_function_link align vertical_cell"> <img
						src="/TIMS/IMG/admin/icons/notifications.png" alt=""></a>
				</div>
				<div class="header_function_alert horizon_function vertical">
					<a href="#" class="header_function_link align vertical_cell"> <img
						src="/TIMS/IMG/admin/icons/navigation.png" alt=""></a>
				</div>
				<div class="header_function_myPage horizon_function vertical">
					<a href="#" class="header_function_link align vertical_cell"> <img
						src="/TIMS/IMG/admin/icons/mypage.png" alt=""></a>
				</div>
			</div>
		</div>
	</div>
	</nav>

	<!-- feed -->

	<content class="content">
	<div class="contents">


		<div class="feed">

			<form class="feed_post" action="feed.com" method="post"
				enctype="multipart/form-data" onSubmit="tweet()">
				<div class="feed_post_top">
					<div class="feed_post_user">
						<div class="feed_post_user_thumbnail_img">
							<a class="" href="/TIMS/user.com?m_id=${memId}">
							<c:if test="${m_profile eq 'defaultprofile.png'}">
								<img src="/TIMS/IMG/user/defaultprofile.png" alt="">
							</c:if> 
							<c:if test="${m_profile ne 'defaultprofile.png'}">
								<img src="/TIMS/IMG/user/${memId}/profile_img/${m_profile}" alt="">
							</c:if>
							</a>
						</div>
					</div>
					<div class="feed_post_cover vertical">
						<textarea class="feed_post_textArea vertical_cell"
							placeholder="${m_name}님, 당신의 소식을 알려주세요!" id="tweet_cell"
							name="tweet_cell"></textarea>
					</div>
				</div>
				<div class="feed_post_bottom">
					<div class="feed_post_upload horizon_function">
						<label class="feedUpload_label color" for="feedUpload">사진/동영상</label>
						<input type="file" id="feedUpload" name="feedUpload"
							value="사진/동영상" class="feedUpload">
					</div>
					<div class="feed_post_accessLimit horizon_function">
						<select class="feed_post_accessLimit_option color" name="select">
							<option value="1">전체공개</option>
							<option value="2">친구만공개</option>
							<option value="3">나만보기</option>
						</select>
					</div>
					<div class="feed_post_submit horizon_function">
						<input type="hidden" name="postSeperator" value="1"> <input
							class="feed_post_submit_button color" type="submit" name="post"
							value="게시하기">
					</div>
				</div>
			</form>
			<c:choose>
				<c:when test="${cntFr!=null }">
					<c:forEach var="cnt" items="${cntFr}">
						<div class="feed_box">
							<div class="feed_header">
								<div class="feed_header_user">
									<div class="feed_header_user_thumbnail_cover vertical">
										<div class="feed_header_user_thumbnail vertical_cell align">
											<div class="feed_header_user_thumbnail_img vertical">
												<a class="vertical_cell align" href="/TIMS/user.com?m_id=${cnt[0] }"> 
												<c:forEach var="k" items="${abb}">
												<c:choose>
												<c:when test="${cnt[0] eq k.m_id}">
												<c:if test="${k.m_profileimage eq 'defaultprofile.png'}">
							<img src="/TIMS/IMG/user/defaultprofile.png" class="profileimage" alt=""></c:if>
							<c:if test="${k.m_profileimage ne 'defaultprofile.png'}">
							<img src="/TIMS/IMG/user/${cnt[0]}/profile_img/${k.m_profileimage}" class="profileimage" alt=""></c:if>
												</c:when>
												</c:choose>
												</c:forEach>
												</a>
											</div>
										</div>
									</div>
									<div class="feed_header_user_id_cover">
										<div class="feed_header_user_id vertical">
											<a class="vertical_cell" href="/TIMS/user.com?m_id=${cnt[0] }">
												<p>${cnt[0]}</p>
											</a>
										</div>
										<div class="feed_header_user_id_time vertical">
											<p>10분전</p>
										</div>
									</div>
								</div>
							</div>
							<div class="feed_content">
								<div class="feed_content_img">
									<img src="/TIMS/IMG/user/${cnt[0]}/twt_IMG/${cnt[4]}" alt="">
								</div>
							</div>
							<div class="feed_reply">
								<div class="feed_reply_function">
									<div class="feed_reply_function_textarea vertical">
										<p class="vertical_cell">${cnt[3]}</p>
									</div>
									<div class="feed_reply_function_icons_cover">
										<div class="feed_reply_function_icons">
											<div
												class="feed_reply_function_icons_like horizon_function vertical">
												<form
													class="feed_reply_function_icons_like_button align vertical_cell"
													action="feed.com" method="post"
													enctype="multipart/form-data">
													<input type="hidden" name="postSeperator" value="3">
													<input type="hidden" name="authorId" value="${cnt[0]}">
													<input type="hidden" name="authorDate" value="${cnt[6]}">
													<input type="hidden" name="authorTime" value="${cnt[7]}">
													<input type="hidden" name="like111" value="${lkl==null}">
													
													<c:choose>
													<c:when test="${lkl==null }">
														<input type="hidden" class="likeNum" name="likeNum"
																	value="1">
													</c:when>
													<c:when test="${lkl!=null }">
													<c:forEach var="i" items="${lkl}">
													<c:if test="${cnt[10] eq i[1]}">
														<c:choose>

															<c:when test="${fn:contains(i,id)}">
																<input type="hidden" class="likeNum" name="likeNum"
																	value="2">
															</c:when>
															<c:when test="${!fn:contains(i,id)}">
																<input type="hidden" class="likeNum" name="likeNum"
																	value="1">
															</c:when>
														</c:choose>
														</c:if>
													</c:forEach>
													</c:when>
													</c:choose>
													
													<button class="likeBtn" name="likeBtn">
														<img id="likeBtnImg" src="/TIMS/IMG/admin/icons/like.png"
															alt="">
													</button>
												</form>
											</div>
											<div
												class="feed_reply_function_icons_reply horizon_function vertical">
												<div
													class="feed_reply_function_icons_comment_button align vertical_cell">
													<img src="/TIMS/IMG/admin/icons/comment.png" alt="">
												</div>
											</div>
											<div
												class="feed_reply_function_icons_bookmark horizon_function vertical">
												<div
													class="feed_reply_function_icons_bookmark_button align vertical_cell">
													<img src="/TIMS/IMG/admin/icons/bookmark.png" alt="">
												</div>
											</div>
										</div>
										<div class="feed_reply_function_likeNum">
											<p>좋아요 ${cnt[5]}개</p>
											<p>댓글달기</p>
											<p>북마크</p>
										</div>
									</div>
								</div>
								<div class="feed_reply_list">
									<div class="feed_reply_list_row">
										<c:forEach var="rpl" items="${rplFr}">
											<c:if test="${cnt[10] eq rpl[10]}">
												<p>
													<a href="#">${rpl[0]}</a> <span>${rpl[3]}</span>
												</p>
											</c:if>
										</c:forEach>
									</div>
								</div>
								<form class="feed_reply_post" action="feed.com" method="post"
									enctype="multipart/form-data">
									<div class="feed_reply_post_cell vertical">
										<textarea class="feed_reply_post_cell_textArea vertical_cell"
											placeholder="댓글 달기.." name="reply_cell"></textarea>
									</div>
									<div class="feed_reply_submit horizon_function">
										<input type="hidden" name="postSeperator" value="2"> <input
											type="hidden" name="authorId" value="${cnt[0]}"> <input
											type="hidden" name="authorDate" value="${cnt[6]}"> <input
											type="hidden" name="authorTime" value="${cnt[7]}"> <input
											class="feed_post_submit_button color" type="submit"
											name="reply" value="게시하기">
									</div>
								</form>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:when test="${cntFr==null }">
				<div class="empty_cell1">
					<h2 >당신의 이야기를 들려주세요</h2>
					</div>
					<div class="empty_cell2">
					<h1>Whispering Us your story</h1>
					<h1>with</h1>
					<h1>taste in music</h1>
					</div>
				</c:when>
			</c:choose>
		</div>


		<div class="messenger">
			<div class="messenger_box">
				<div class="messenger_header">
					<div class="messenger_header_user">
						<div class="messenger_header_user_thumbnail_cover vertical">
							<div class="messenger_header_user_thumbnail vertical_cell align">
								<div class="messenger_header_user_thumbnail_img vertical">
									<a class="vertical_cell align" href="#"> 
									<img src="/TIMS/IMG/user/profileimg/${dto.m_profileimage}" alt="">
									</a>
								</div>
							</div>
						</div>
						<div class="messenger_searchBar_cover vertical">
							<div class="messenger_searchBar vertical">
								<div class="searchBar vertical_cell">
									<input id="searchBar" class="messenger_search" type="search"
										name="search" placeholder="검색">
								</div>
							</div>
						</div>
					</div>
				</div>


				<div class="messenger_division">
					<div class="messenger_division_cover horizon_function vertical">
						<div id="friendsList"
							class="messenger_division_friendList vertical_cell align hovering">
							<img class="hovering_img" src="/TIMS/IMG/admin/icons/friend.png"
								alt="">
							<p class="hovering_p">Friends</p>
						</div>
						<div
							class="messenger_division_chatList vertical_cell align hovering">
							<img class="hovering_img" src="/TIMS/IMG/admin/icons/chat.png"
								alt="">
							<p class="hovering_p">Chat</p>
						</div>
						<div id="openChatList" class="messenger_division_openChatList vertical_cell align hovering">
							<img class="hovering_img"
								src="/TIMS/IMG/admin/icons/openchat.png" alt="">
							<p class="hovering_p">Open</p>
						</div>
					</div>
				</div>


				<div id="messenger_view" class="messenger_list_cover">
           
           <div class="messenger_list">
              <div class="messenger_list_add">
                <div class="messenger_list_user">
                  <div class="messenger_list_user_thumbnail_cover">
                    <div class="messenger_list_user_thumbnail">
                      <div class="messenger_list_user_thumbnail_img vertical">
                        <a class="vertical_cell align" href="#">
                          <img src="/TIMS/IMG/user/usernum/thumnail/1.jpg" alt="">
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="messenger_list_userName_cover vertical">
                    <div class="messenger_list_userName vertical_cell align">
                      <p>mgmgmgmg</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="messenger_list_add">
                <div class="messenger_list_user">
                  <div class="messenger_list_user_thumbnail_cover">
                    <div class="messenger_list_user_thumbnail">
                      <div class="messenger_list_user_thumbnail_img vertical">
                        <a class="vertical_cell align" href="#">
                          <img src="/TIMS/IMG/user/usernum/thumnail/1.jpg" alt="">
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="messenger_list_userName_cover vertical">
                    <div class="messenger_list_userName vertical_cell align">
                      <p>mgmgmgmg</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="messenger_list_add">
                <div class="messenger_list_user">
                  <div class="messenger_list_user_thumbnail_cover">
                    <div class="messenger_list_user_thumbnail">
                      <div class="messenger_list_user_thumbnail_img vertical">
                        <a class="vertical_cell align" href="#">
                          <img src="/TIMS/IMG/user/usernum/thumnail/1.jpg" alt="">
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="messenger_list_userName_cover vertical">
                    <div class="messenger_list_userName vertical_cell align">
                      <p>mgmgmgmg</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="messenger_list_add">
                <div class="messenger_list_user">
                  <div class="messenger_list_user_thumbnail_cover">
                    <div class="messenger_list_user_thumbnail">
                      <div class="messenger_list_user_thumbnail_img vertical">
                        <a class="vertical_cell align" href="#">
                          <img src="/TIMS/IMG/user/usernum/thumnail/1.jpg" alt="">
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="messenger_list_userName_cover vertical">
                    <div class="messenger_list_userName vertical_cell align">
                      <p>mgmgmgmg</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
              <div class="messenger_list_add">

              </div>
            </div>
          </div>

			</div>
		</div>
	</div>
	</content>
</body>

</html>