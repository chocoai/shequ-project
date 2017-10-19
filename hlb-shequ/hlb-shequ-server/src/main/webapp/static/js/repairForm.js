function submit(){
	var s1 = $(".val1").val();
	var s2 = $(".val2").val();
	var s3 = $(".val3").val();
	var s4 = $(".val4").val();
	var s5 = $(".val5").val();
	var s6 = $(".val6").text();
	var repairId=$("#repairId").val();

	if(s2==null||s2==""){
		layer.open({
            content: '<font>物料名称不能为空</font>',
            skin: 'msg',
            time: 2 //2秒后自动关闭
        });
        return;
	}
	if(s4==null||s4==""){
		layer.open({
            content: '<font>价格不能为空</font>',
            skin: 'msg',
            time: 2 //2秒后自动关闭
        });
        return;
	}
	if(s5==null||s5==""){
		layer.open({
            content: '<font>数量不能为空</font>',
            skin: 'msg',
            time: 2 //2秒后自动关闭
        });
        return;
	}
	if(s6==null||s6==""){
		layer.open({
            content: '<font>合计不能为空</font>',
            skin: 'msg',
            time: 2 //2秒后自动关闭
        });
        return;
	}

	$.ajax({
        url: ctx + '/wuye/sumbitRepairForm',
        data: {"repairId":repairId,"materialNo":s1, "materialName":s2, "specificationModel":s3, "price":s4, "num":s5, "count":s6},
        type: 'post',
        async: false,
        success: function(data) {
            window.location.replace(ctx + '/wuye/repairFormList?repairId='+repairId);
        }
    });
}

function count(){
	var s4 = $(".val4").val();
	var s5 = $(".val5").val();
	if(!((s4==null||s4=="")||(s5==null||s5==""))){
		var price = parseFloat(s4) * parseInt(s5);
		price = price.toFixed(2);
		$(".val6").html(price);
	}else{
        $(".val6").html("");
    }
}