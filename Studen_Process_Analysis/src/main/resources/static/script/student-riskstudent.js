$("#searchInput").focus(function(){
	searchByKeyword();
	$("#keywordTips").show();
}).bind("input propertychange", function(){
	searchByKeyword(keyword);
	$("#keywordTips").show();
})

function searchByKeyword(){
	var result = ['All', 'Risk1', 'Risk2', 'Unsatisfactory'];
	$("#keywordSearchResult").empty();
	for(var i = 0; i < result.length; i++){
		$("#keywordSearchResult").append("<li onclick='searchResult(\"" + result[i] + "\")'>" + result[i] + "</li>")
	}
}


$("#searchBtnAll").click(function(){
	searchResult("All");
})

$("#searchBtnRisk1").click(function(){
	searchResult("Risk1");
})

$("#searchBtnRisk2").click(function(){
	searchResult("Risk2");
})

$("#searchBtnUnsatisfactory").click(function(){
	searchResult("Unsatisfactory");
})


function searchResult(keyword){
	$("#searchInput").val(keyword);
	$("#keywordTips").hide();
	var risk = keyword;
	var data = JSON.stringify({
		"risk": risk
	})
	
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/getStudentDataByRisk",
		data: data,
		success: function(result){
			$("#resultTable").show();
			$("#resultTableBody").empty();
			for(var i = 0; i < result["data"].length; i++){
				var tablerow = "<tr>";
				tablerow += "<td>" + result["data"][i]["no"] + "</td>";
				tablerow += "<td>" + result["data"][i]["student_code"] + "</td>";
				tablerow += "<td>" + result["data"][i]["risk_level"] + "</td>";
				tablerow += "<td><a target='_blank' href='/detail?student_code=" + result["data"][i]["student_code"] + "'>Check</a></td>";
				tablerow += "</tr>";
				$("#resultTableBody").append(tablerow);
			}
		}
	})
	
}
