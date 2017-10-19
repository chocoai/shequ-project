function changeRoom(roomId) {
    window.location.href = ctx + "/selectRoom?roomId=" + roomId;
}

function changeInfo(){
	$.ajax({
		url : ctx+"/updateRoomInfo",
		type : 'post',
		async : false,
		success : function(data) {
			// debugger;
			if (data.data == "success") {
				layer.open({
					content : "更新成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(ctx + "/index");
			} else if (data.data == "fails") {
				layer.open({
					content : "查不到合同",
					skin : 'msg',
					time : 2
				});
				return;
			}
		}
	});
}
