<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.2.js"></script>
<script language="javascript" src="/TIMS//JS/member.js"></script>
<link rel="stylesheet" type="text/css" href="/TIMS/CSS/master.css">
<link rel="stylesheet" type="text/css" href="/TIMS/CSS/modi.css">
<script src = "//code.jquery.com/jquery-1.9.1.js"> </script>
<script src = "//code.jquery.com/ui/1.10.4/jquery-ui.js"> </script>
<link rel="stylesheet" href="/TIMS/CSS/autocomplete.css">
<script type="text/javascript">
   // 아이디 검색
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
                       console.log(info[2]);
                       if(info[2] != 'defaultprofile.png') {
                       
                     return $('<li>')
                     .append("<hr><img style='width:35px;height:35px;cursor:pointer;vertical-align:middle;margin-top:1px' src='/TIMS/IMG/user/"+info[1]+"/profile_IMG/"+info[2]+"'/>" )   
                     .append("<span style='cursor:pointer;margin-left:5px;'>" + info[0] + "</span>")
                     .append("(" + "<span style='cursor:pointer;font-size:12px;'>" + info[1] + "</span>" + ")")
                        .click(function(){
                           window.location="/TIMS/user.com?m_id=" + info[1]
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
</script>
<body style="background-color:#f5f4fb;" onload="getName();">
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
            <div class="searchBar vertical_cell" onclick="searchFunction()">
               <span class="fa fa-search" id="fa"></span> <input id="searchBar" class="transform" type="search" id="review_search" name="search" placeholder="검색">
            </div>
         </div>
      </div>
      <div class="header_function_cover vertical_cell">
         <div class="header_function">
            <div class="header_function_navigator horizon_function vertical">
               <a href="#" class="header_function_link align vertical_cell"> <img src="/TIMS/IMG/admin/icons/notifications.png" alt="">
               </a>
            </div>
            <div class="header_function_alert horizon_function vertical">
               <a href="#" class="header_function_link align vertical_cell"> <img src="/TIMS/IMG/admin/icons/navigation.png" alt="">
               </a>
            </div>
            <div class="header_function_myPage horizon_function vertical">
               <a href="#" class="header_function_link align vertical_cell"> <img src="/TIMS/IMG/admin/icons/mypage.png" alt="">
               </a>
            </div>
         </div>
      </div>
   </div>
</nav>
<ul class="tab_wrap" style="margin-left:35%;margin-top:10%;position:fixed;">
   <li class="on"><a href="#">프로필 변경</a>
   <div>
      <form method="post" name="modifyform" id="modifyform" action="/TIMS/member/modifyPro.com">
         <div id="container">
            <table>
               <tr>
                  <td><c:if test="${dto.m_profileimage eq 'defaultprofile.png'}">
                     <img src="/TIMS/IMG/user/defaultprofile.png" class="profileimage" alt=""></c:if>
                     <c:if test="${dto.m_profileimage ne 'defaultprofile.png'}">
                     <img src="/TIMS/IMG/user/${dto.m_id}/profile_img/${dto.m_profileimage}" class="profileimage" alt=""></c:if></td>
                  <td><div id="contents">
                        <p>
                           <b>${dto.m_id}</b>
                        </p>
                        <a href="#layerPopup">프로필 사진 변경하기</a>
                        <div id="layerPopup">
                           <table>
                              <caption style="font-size: 15px;">
                                 <b>프로필 사진 변경하기</b>
                              </caption>
                              <tr>
                                 <td rowspan="2"><hr> <input name="m_profileimage" id="m_profileimage" type="file" onchange="uploadFile();" style="display: none;"></td>
                                 <td><p onclick="document.all.m_profileimage.click();" onchange="uploadFile();" style="cursor: pointer; margin-top: 30px; text-align: center">프로필 사진 변경</p>
                                    <hr>
                                    <p onclick="normalimg();" style="cursor: pointer; text-align: center; margin-top: 5px;">현재 프로필 사진 삭제</p>
                                    <hr> <br /></td>
                              </tr>
                           </table>
                           <center>
                              <button type="button">취소</button>
                           </center>
                        </div>
                     </div></td>
               </tr>
               <tr>
                  <td style="text-align: center">이름</td>
                  <td><input type="text" name="m_name" value="${dto.m_name}" style="border-radius: 10px; height: 30px; width: 200px;"></td>
               </tr>
               <tr>
                  <td style="text-align: center">소개</td>
                  <td><textarea maxlength="200" name="m_intro"
                        style="border-radius: 10px; height: 30px; width: 200px;">${dto.m_intro}</textarea></td>
               </tr>
               <tr>
                  <td style="text-align: center">이메일</td>
                  <td><input type="text" name="m_email" value="${dto.m_email}"
                     style="border-radius: 10px; height: 30px; width: 200px;"></td>
               </tr>
               <tr>
                  <td style="text-align: center">전화번호</td>
                  <td><input type="text" name="m_phone" value="${dto.m_phone}"
                     style="border-radius: 10px; height: 30px; width: 200px;"></td>
               </tr>

            </table>
            <center>
               <input type="submit" value="수정완료">
            </center>
         </div>
      </form></div></li>
   <li><a href="#">비밀번호 변경</a>
      <div>
      <form method="post" name="pwform" action="/TIMS/member/pwmodifyPro.com" onsubmit="return checkpw();" style="margin-left:20%;margin-top:35%;">
         <input type="hidden" name="m_password" value="${dto.m_passwd}">
         현재비밀번호&emsp;&nbsp;&nbsp;&nbsp;<input type="password" id="m_nowpasswd" name="m_nowpasswd" style="border-radius: 10px; height: 30px; width: 200px;"><br/>
         새 비밀번호&nbsp;&nbsp;&nbsp;&emsp;&nbsp;&nbsp;<input type="password" id="m_passwd" name="m_passwd" style="border-radius: 10px; height: 30px; width: 200px;"><br/>
         새 비밀번호 확인&nbsp;<input type="password" id="m_passwd2" name="m_passwd2" style="border-radius: 10px; height: 30px; width: 200px;"><br/>
         <input type="submit" value="비밀번호 변경">
      </form>
      </div></li>
      <li><a href="#">회원탈퇴</a>
      
      <div>
      <form method="post" action="/TIMS/member/delmem.com" name="delform" onsubmit="return yesorno();" style="margin-left:20%;margin-top:35%;">
         현재비밀번호&nbsp;&nbsp;<input type="password" id="m_passwd1" name="m_passwd" style="border-radius: 10px; height: 30px; width: 200px;">
         <input type="submit" value="회원탈퇴하기"></form>
      </div>
      </li>
</ul>
</body>

<script>
// 프로필 사진 변경하기
function uploadFile() {
   var form = $('#modifyform')
   var formData = new FormData(form);
   formData.append("m_profileimage",$("input[name=m_profileimage]")[0].files[0]);
   $.ajax({
            url : '/TIMS/member/imgmodify.com',
            data : formData,
            enctype : "multipart/form-data",
            processData : false,
            contentType : false,
            type : 'POST',
            success : function(data) {
               alert('프로필 사진이 변경되었습니다');
               window.location = "/TIMS/member/modify.com?m_id=${sessionScope.memId}"
            }
         });
}
//프로필 사진 삭제
function normalimg() {
   var normalimg = "defaultprofile.png";
   $.ajax({
         url : '/TIMS/member/normalmodi.com',
         data : {normalimg : normalimg},
         type : 'POST',
         success : function(data) {
            alert('프로필 사진이 삭제되었습니다');
            window.location = "/TIMS/member/modify.com?m_id=${sessionScope.memId}"
         }
      })
}
// 탭메뉴
$(".tab_wrap>li>a").click(function() {
   $(this).parent().addClass("on").siblings().removeClass("on");
   return false;
});
</script>