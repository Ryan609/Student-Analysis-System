$("#searchBtn").click(function(){
	var student_code = $("#studentCodeInput").val();
	if(student_code == ""){
		$("#searchResult").html("Please input student code.");
		return;
	}
	
	var data = JSON.stringify({
		"student_code": student_code
	})
	
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/searchStudentByCode",
		data: data,
		success: function(result){
			if(result["data"]==0){
				$("#checkDetailBtn").hide();
				$("#searchResult").html("Student does not exist.");
			}else{
				$("#searchResult").text("Click Detail button to check student detail");
				$("#checkDetailBtn").attr("href", "/detail?student_code="+student_code);
				$("#checkDetailBtn").show();
			}
		}
	})
})