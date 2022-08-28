$("#uploadCsvButton").click(function(){
	var replace = $("#ifinstead").get(0).checked;
	var files = $("#csvFileChoose").prop('files');
    var fileName = $("#csvFileChoose").val();
    var strFileName = fileName.substring(fileName.lastIndexOf("\\")+1);
	
	if(files.length == 0){
		alert("Please choose csv file!");
		return;
	}else{
		var reader = new FileReader();
		reader.readAsText(files[0]);
		reader.onload = () => {
			var csv = reader.result;
			var data = JSON.stringify({
				"isReplace": replace,
				"csvData": csv
			})
			
			$.ajax({
				type: "POST",
				contentType: "application/json;charset=UTF-8",
				url: "/api/uploadCsvData",
				data: data,
				success: function(result){
					alert(result["msg"] + "    File: " + strFileName);
				}
			})
		}
	}
})