
////서치바 아이콘 이동
//
//function searchFunction() {
//  document.getElementById("searchBar").classList.toggle("transform-active");
//
//  document.getElementById("fa").classList.toggle("fa-search-active");
//}
//



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
