$("#logoutBtn").click(function(){
	$.ajax({
		type: "GET",
		url: "/logout",
		success: function(result){
			$(location).attr("href","/login");
		}
	})
})

$("#menu-home").click(function(){
	$(location).attr("href", "/index/home");
})

$("#menu-course-management").click(function(){
	$(location).attr("href", "/index/course");
})

$("#menu-course-management-enrolled").click(function(){
	$(location).attr("href", "/index/course/enrolled")
})

$("#menu-course-management-result").click(function(){
	$(location).attr("href", "/index/course/result")
})

$("#menu-student-management").click(function(){
	$(location).attr("href", "/index/student");
})

$("#menu-student-management-academic").click(function(){
	$(location).attr("href", "/index/student/academic");
})

$("#menu-student-management-honorsoffers").click(function(){
	$(location).attr("href", "/index/student/honorsoffers");
})

$("#menu-student-management-riskstudent").click(function(){
	$(location).attr("href", "/index/student/riskstudent");
})

function gradePointToGrade(num){
	switch(num){
		case 7:
			return "HD";
		case 6:
			return "D";
		case 5:
			return "C";
		case 4:
			return "P";
		default:
			return "F";
	}
}