$(function(){
	var data = JSON.stringify({

	})
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/getHonorsList",
		data: data,
		success: function(result){
			$("#resultTable").show();
			$("#resultTableBody").empty();
			for(var i = 0; i < result["data"].length; i++){
				var tablerow = "<tr>";
				tablerow += "<td>" + result["data"][i]["student_code"] + "</td>";
				tablerow += "<td>" + result["data"][i]["major"] + result["data"][i]["adv"] + "</td>";
				tablerow+= "<td>" + result["data"][i]["start_date"] + "-" + result["data"][i]["end_date"] + "</td>";
 				//tablerow+= "<td>" + result["data"][i]["gpa"] + "</td>";
				tablerow += "<td><a target='_blank' href='/detail?student_code=" + result["data"][i]["student_code"] + "'>check</a></td>";
				tablerow += "</tr>";
				$("#resultTableBody").append(tablerow);
			}
		}
	})
	
})