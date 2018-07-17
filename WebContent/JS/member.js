$(document).ready(function() {
	$("input[name=m_id]").keyup(function(event){
		if(!(event.keyCode >= 37 && event.keyCode <= 40)){
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}
	});
});

function focusIt() {
	document.inform.m_id.focus();
}

// 메인페이지 아이디/비밀번호 입력여부
function checkIt() {
	if (!document.loginform.m_id.value) {
		alert("아이디를 입력하세요");
		document.loginform.m_id.focus();
		return false;
	}
	if (!document.loginform.m_passwd.value) {
		alert("비밀번호를 입력하세요");
		document.loginform.m_passwd.focus();
		return false;
	}
}

// 실시간 아이디 중복확인
function checkId() {
	var id = $('#m_id').val();
	$.ajax({
		url : '/TIMS/member/confirmId.com',
		/* type:'post', */
		data : {m_id : id},
		success : function(data) {
			da = data.trim();
			if (da == 0) {
				$('#confirmId').css("color", "red");
				$('#confirmId').css("font-size", "11px");
				$('#confirmId').html("사용 가능한 아이디입니다.");
			} else {
				$('#confirmId').css("color", "red");
				$('#confirmId').css("font-size", "11px");
				$('#confirmId').html("이미 사용 중인 아이디입니다.");
/*				$('#m_id').val("");*/
				$('#m_id').focus();
			}
		}
	})
}


// 비밀번호 양식 확인
function checkPw() {
	 if(!join.m_passwd.value ) {
         alert("비밀번호를 입력하세요");
         return false;
     }
     if(join.m_passwd.value != join.m_passwd2.value)
     {
         alert("비밀번호를 동일하게 입력하세요");
         return false;
     }
	var passwd = $("#m_passwd").val();
	var pattern = /^[0-9]+&/;

	if (!/^[a-zA-Z0-9]{7,16}$/.test(passwd)) {
		alert("비밀번호는 숫자와 영문자 조합으로 7~16자 내로 입력해주세요.");
		$("#m_passwd").focus();
		return false;
	}
}
// 이메일 뒷부분 (선택/직접입력)
function email_change() {
	if (document.join.email.options[document.join.email.selectedIndex].value == '0') {
		document.join.m_email2.readOnly = true;
		document.join.m_email2.value = "";
	}
	if (document.join.email.options[document.join.email.selectedIndex].value == '9') {
		document.join.m_email2.readOnly = false;
		document.join.m_email2.value = "";
		document.join.m_email2.focus();
	} else {
		document.join.m_email2.readOnly = true;
		document.join.m_email2.value = document.join.email.options[document.join.email.selectedIndex].value;
	}
}
// 좋아하는 장르 3가지이상 선택하게하기
jQuery(document).ready(function($) {
	$("input[name=m_genre]:checkbox").change(function() {// 체크박스들이 변경됬을때
		var cnt = 3
		if (cnt == $("input[name=m_genre]:checkbox:checked").length) {
			$(":checkbox:not(:checked)").attr("disabled", "disabled");
		} else {
			$("input[name=m_genre]:checkbox").removeAttr("disabled");
		}
	});
});
// 회원탈퇴 여부
function yesorno(){
	if(confirm("회원 탈퇴를 진행하시겠습니까?")){
		var pw = "${dto.m_passwd}";
		if(delform.m_passwd.value != pw){
			alert('현재 비밀번호를 정확하게 입력해주세요');
			return false;
		} return true;
	} else{
		return false;
	}		
}
// 레이어 팝업
$(document).ready(function() {
	$(document).mouseup(function(e) {
		var container = $("#layerPopup");
		if (container.has(e.target).length === 0)
			container.hide();
	});
	$("#layerPopup").hide();
	$("#contents > a").click(function() {
		$("#contents > a").blur();
		$("#layerPopup").show();
		$("#layerPopup a").focus();
		return false;
	});
	$("#layerPopup a").keydown(function(e) {
		if (e.shiftKey && e.keyCode == 9) { // Shift + Tab 키를 의미합니다.
			$("#contents > a").focus();
			$("#layerPopup").hide();
			return false;
		}
	});

	$("#layerPopup button").click(function() {
		$("#contents > a").focus();
		$("#layerPopup").hide();
	});

});
// 비밀번호 변경 검사
function checkpw(){
	var passwd = document.getElementById("m_passwd").value;
	var pwReg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{7,16}$/;
	 if(!pwReg.test(passwd)){
		alert("비밀번호는 숫자와 영문자 조합으로 7~16자 내로 입력해주세요");
		$("#m_passwd").focus();
		return false;
		
	} 
	if(!pwform.m_nowpasswd.value){
		alert("비밀번호를 입력해주세요");
		return false;
	}
	if(pwform.m_nowpasswd.value != pwform.m_password.value){
		alert("현재 비밀번호가 틀립니다");
		$("#m_nowpasswd").focus();
		return false;
	}	else if(pwform.m_passwd.value != pwform.m_passwd2.value){
		alert("비밀번호를 동일하게 입력해주세요")
		$("#m_passwd").focus();
		return false;
	} else {
		return true;
	}
} 

