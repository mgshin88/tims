<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.where {
  display: block;
  margin: 25px 15px;
  font-size: 11px;
  color: #000;
  text-decoration: none;
  font-family: verdana;
  font-style: italic;
}

.textbox {position: relative; width: 100%; text-align:center; margin-top:3%;}

.textbox label {
  position: absolute;
  padding: .8em .5em;  /* input 요소의 padding 값 만큼 */
  color: #999;
  cursor: text;
}

.textbox input[type="text"],
.textbox input[type="password"] {
  width: 60%;  /* 원하는 너비 설정 */ 
  height: auto;  /* 높이값 초기화 */
  line-height : normal;  /* line-height 초기화 */
  padding: .8em .5em; /* 원하는 여백 설정, 상하단 여백으로 높이를 조절 */
  border: 1px solid #999;
  border-radius: 7px;  /* iSO 둥근모서리 제거 */
  outline-style: none;  /* 포커스시 발생하는 효과 제거를 원한다면 */
  -webkit-appearance: none;  /* 브라우저별 기본 스타일링 제거 */
  -moz-appearance: none;
  appearance: none;
}

.select-style {
    border: 1px solid #ccc;
    width: 60%;
    border-radius: 3px;
    overflow: hidden;
    background-color: #f9f9f9;
    margin : 3% auto;
}

.select-style select {
    padding: 5px 8px;
    width: 100%;
    border: none;
    box-shadow: none;
    background: transparent;
    background-image: none;
    -webkit-appearance: none;
}

.select-style select:focus {
    outline: none;
}

.select_menu { 
	-webkit-appearance: none; /* 네이티브 외형 감추기 */ 
	-moz-appearance: none; 
	appearance: none; 
	background: url(이미지 경로) no-repeat 95% 50%; /* 화살표 모양의 이미지 */ 
} /* IE 10, 11의 네이티브 화살표 숨기기 */
	 
.select_menu::-ms-expand { display: none; }

.select_menu { 
	width: 200px; /* 원하는 너비설정 */ 
	padding: .8em .5em; /* 여백으로 높이 설정 */ 
	font-family: inherit; /* 폰트 상속 */ 
	background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%; /* 네이티브 화살표 대체 */ 
	border: 1px solid #999; 
	border-radius: 7px; /* iOS 둥근모서리 제거 */ 
	-webkit-appearance: none; /* 네이티브 외형 감추기 */ 
	-moz-appearance: none; 
	appearance: none; 
}

.select_menuBox {
	text-align : center;
	margin-top : 2.5%;
}

.Allcreate_font {
	font-size : 14px;
	font-weight : bold;
}

.filebox {
	margin-top : 3%;
	text-align : center;
}

.filebox input[type="file"] {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip:rect(0,0,0,0);
    border: 0;
    
}

.filebox label {
    display: inline-block;
    padding: .5em .75em;
    color: #999;
    font-size: inherit;
    line-height: normal;
    vertical-align: middle;
    background-color: #fdfdfd;
    cursor: pointer;
    border: 1px solid #ebebeb;
    border-bottom-color: #e2e2e2;
    border-radius: .25em;
}

/* named upload */
.filebox .upload-name {
    display: inline-block;
    padding: .5em .75em;
    font-size: inherit;
    font-family: inherit;
    line-height: normal;
    vertical-align: middle;
    background-color: #f5f5f5;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: .25em;
  -webkit-appearance: none; /* 네이티브 외형 감추기 */
  -moz-appearance: none;
  appearance: none;
}

/* imaged preview */
.filebox .upload-display {
    margin-bottom: 5px;
}

@media(min-width: 768px) {
    .filebox .upload-display {
        display: inline-block;
        margin-right: 5px;
        margin-bottom: 0;
    }
}

.filebox .upload-thumb-wrap {
    display: inline-block;
    width: 54px;
    padding: 2px;
    vertical-align: middle;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #fff;
}

.filebox .upload-display img {
    display: block;
    max-width: 100%;
    width: 100% \9;
    height: auto;
}

.filebox.bs3-primary label {
  color: #fff;
  background-color: #337ab7;
    border-color: #2e6da4;
}

.Allcreate_submit {
	background-color : #9676ff;
	color : white;
	border : none;
	text-align : center;
	cursor : pointer;
	padding : 2.5% 2.5%;
	border-radius : 7px;
}

.Allcreate_submitBox {
	margin-top : 5%;
	text-align : center;
}

.Allcreate_mainBox {
	margin-top : 15%;
}

</style>

<script>

function createRoomPro() {
	$("#createRoom").ajaxForm({
		type : "post",
		url : "/TIMS/chatView/chatRoomAllCreatePro.com",
		enctype : "multipart/form-data",
		error : function() {
			alert("에러");
		},
		success : function(result) {
			$("#messenger_view").html(result);
		}
	});
	
	$("#createRoom").submit();
	
}

$(document).ready(function() {
	  var placeholderTarget = $('.textbox input[type="text"], .textbox input[type="password"]');
	  
	  //포커스시
	  placeholderTarget.on('focus', function(){
	    $(this).siblings('label').fadeOut('fast');
	  });

	  //포커스아웃시
	  placeholderTarget.on('focusout', function(){
	    if($(this).val() == ''){
	      $(this).siblings('label').fadeIn('fast');
	    }
	  });
	  
	  var fileTarget = $('.filebox .upload-hidden');

	    fileTarget.on('change', function(){
	        if(window.FileReader){
	            // 파일명 추출
	            var filename = $(this)[0].files[0].name;
	        } 

	        else {
	            // Old IE 파일명 추출
	            var filename = $(this).val().split('/').pop().split('\\').pop();
	        };

	        $(this).siblings('.upload-name').val(filename);
	    });

	    //preview image 
	    var imgTarget = $('.preview-image .upload-hidden');

	    imgTarget.on('change', function(){
	        var parent = $(this).parent();
	        parent.children('.upload-display').remove();

	        if(window.FileReader){
	            //image 파일만
	            if (!$(this)[0].files[0].type.match(/image\//)) return;
	            
	            var reader = new FileReader();
	            reader.onload = function(e){
	                var src = e.target.result;
	                parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
	            }
	            reader.readAsDataURL($(this)[0].files[0]);
	        }

	        else {
	            $(this)[0].select();
	            $(this)[0].blur();
	            var imgSrc = document.selection.createRange().text;
	            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

	            var img = $(this).siblings('.upload-display').find('img');
	            img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";        
	        }
	    });
	  
	});
</script>

<div class="Allcreate_mainBox">

<center>
<h2>채팅방 개설</h2>
</center>

<form id="createRoom" name="createRoom" enctype="multipart/form-data" method="post">
	<input type="hidden" name="m_num" value="${sessionScope.memNum }">
	<div class="textbox">
		<label for="ex_input">방 이름</label>
		<input type="text" id="ex_input" name="oc_name">
	</div>
	
	<div class="textbox">
		<label for="ex_input">방 한줄 소개</label>
		<input type="text" id="ex_input" name="oc_content">
	</div>

	<div class="select_menuBox">
	<span class="Allcreate_font"> 장르 선택 : </span> 
	<select class="select_menu" name="g_num">
		<c:forEach var="g_option" items="${g_option }">
			<option value="${g_option.g_num }">${g_option.g_type }</option>
		</c:forEach>
	</select>

	<span class="Allcreate_font"> 최대 인원 : </span> 
	<select class="select_menu" name="oc_max">
		<option value="5">5명</option>
		<option value="10">10명</option>
		<option value="20">20명</option>
		<option value="30">30명</option>
	</select>
	
	</div>
	
	<div class="filebox bs3-primary preview-image">
    	<input class="upload-name" value="파일선택" disabled="disabled" style="width:40%;">

        <label for="input_file">업로드</label> 
        <input type="file" id="input_file" name="oc_thumbnail" class="upload-hidden"> 
    </div>
	
	<div class="Allcreate_submitBox">
		<div class="Allcreate_submit" onclick="createRoomPro()">채팅방 개설</div>
	</div>
</form>

</div>
