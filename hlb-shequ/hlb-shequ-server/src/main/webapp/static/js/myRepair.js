function del(id, evt){
	if(confirm("确认要删除该维修单吗？")){
		$.ajax({
			url: ctx + '/wyPrivateRepair/delRepair',
			data: 'id=' + id,
			type: "post",
			async: false,
			success: function(data){
				layer.open({
	                content: data.data,
	                skin: 'msg',
	                time: 2
	            });
	            //$("#"+id).css("display", "none");
	            $("#"+id).remove();
			},
			error: function(data){
				layer.open({
	                content: data.data,
	                skin: 'msg',
	                time: 2
	            });
			}
		});
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble=true; 
		} else { 
		//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 
	}
}

function edit(id, evt){
	window.location.href=ctx+'/wyPrivateRepair/editRepair?id='+id;
	var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
	if (window.event) { 
		e.cancelBubble=true; 
	} else { 
	//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
		e.stopPropagation(); 
	} 
}

function show(bizId,procInsId){
	window.location.href=ctx+'/wuye/form2?bizId=' + bizId + "&procInsId=" + procInsId;
} 

function deal(flag){
	window.location.replace(ctx+'/wuye/myRepair?flag='+flag);
}

