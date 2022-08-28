$("body").keydown(function(){
	if(event.keyCode == "13"){
		$("#loginBtn").click();
	}
})

$("#loginBtn").click(function(){
	if($("#username").val() == ""){
		$("#statusText").html("Empty username.");
		$("#statusWindow").show();
		return;
	}
	if($("#password").val() == ""){
		$("#statusText").html("Empty passowrd.");
		$("#statusWindow").show();
		return;
	}
	
	var username = $("#username").val();
	var password = $("#password").val();
	
	var data = JSON.stringify({
		"username" : username,
		"password" : hex_sha256($("#password").val())
	})
	
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/login",
		data: data,
		success: function(result){
			if(result["code"] == 200){
				$(location).attr('href', '/index/');
			}else{
				$("#statusText").html("Incorrect username or password.");
				$("#statusWindow").show();
			}
		}
	})
})

$("#statusClean").click(function(){
	$("#statusWindow").hide();
})