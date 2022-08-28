$("#searchInput").focus(function(){
	var keyword = $("#searchInput").val();
	searchByKeyword(keyword);
	$("#keywordTips").show();
}).bind("input propertychange", function(){
	var keyword = $("#searchInput").val();
	searchByKeyword(keyword);
	$("#keywordTips").show();
})

function searchByKeyword(keyword){
	var data = JSON.stringify({
		"keyword": keyword
	})
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/searchCourseByKeyword",
		data: data,
		success: function(result){
			$("#keywordSearchResult").empty();
			for(var i = 0; i < result["data"].length; i++){
				$("#keywordSearchResult").append("<li onclick='selectCourse(\"" + result["data"][i]["name"] + "\")'>" + result["data"][i]["name"] + "</li>")
			}
		}
	})
}

function selectCourse(courseName){
	$("#searchInput").val(courseName);
	$("#keywordTips").hide();
	
	var data = JSON.stringify({
		"course_name": courseName
	})
	
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/selectYearAndSemesterByCourseName",
		data: data,
		success: function(result){
			$("#timeSelect").empty();
			$("#timeSelect").show();
			for(var i = 0; i < result["data"].length; i++){
				$("#timeSelect").append("<option value='" + result["data"][i]["year"] + result["data"][i]["semester"] + "'>" + result["data"][i]["year"] + " Semester " + result["data"][i]["semester"] + "</option>");
			}
			searchResult();
		}
	})
}

$("#timeSelect").change(function(){
	searchResult();
})

function searchResult(){
	var course_name = $("#searchInput").val();
	var time = $("#timeSelect").val();
	
	var data = JSON.stringify({
		"course_name": course_name,
		"year": parseInt(time.slice(0,4)),
		"semester": parseInt(time.charAt(time.length - 1))
	})
	
	$.ajax({
		type: "POST",
		contentType: "application/json;charset=UTF-8",
		url: "/api/getCourseDataByNameAndTime",
		data: data,
		success: function(result){
			$("#course_info").show();
			$("#enrolledNum").text("Enrolled Number: "+result["data"].length);
			$("#course_units").text("Units: "+result["data"][0]["units"]);
			$("#resultTable").show();
			$("#resultTableBody").empty();
			for(var i = 0; i < result["data"].length; i++){
				var tablerow = "<tr>";
				tablerow += "<td>" + result["data"][i]["studentCode"] + "</td>";
				tablerow += "<td>" + result["data"][i]["courseName"] + "</td>";
				tablerow += "<td>" + result["data"][i]["year"] + " Semester " + result["data"][i]["semester"] + "</td>";
				tablerow += "<td>Enrolled</td>";
				tablerow += "</tr>";
				$("#resultTableBody").append(tablerow);
			}
		}
	})
	
}