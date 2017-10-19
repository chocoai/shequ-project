function topay(feetype){
	var param = {
		"feetype":feetype,
		"WYID" : WYID,
		"HTID" : HTID,
		"JFYF" : JFYF,
		"SOURCE" : SOURCE
	}
	var sign;
	var urlName;
		
	$.ajax({
		url : ctx+"/getSign",
		type : "post",
		data : param,
		async : false,
		dataType : "json",
		success:function(result){
			console.log(result);
			sign = result.data;
		},
		error:function(jqXHR, textStatus, errorThrown){
			alert("加密失败");
		}
	});

	if(feetype == "0"){
		urlName = "/wy-client/index.html?";
	}else{
		urlName = "/wy-client/index"+feetype+".html?";
	}

	var url = urlName+ 
		"x0="+feetype+"&x1="+param.WYID+"&x2="+param.HTID+"&x3="+param.JFYF+"&x4="+param.SOURCE+"&sign="+sign;

	window.location.href = url;
	
		
}