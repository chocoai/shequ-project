var clickCount = 0;

function startCamera() {
	wx.chooseImage({
		count : 1, // 默认9
		sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
		sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
		success : function(res) {
			var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			for ( var i = 0; i < localIds.length; i++) {
				uploadImage(localIds[i], i + 1);
			}
		}
	});
};

function uploadImage(localId, i) {
	wx.uploadImage({
		localId : localId, // 需要上传的图片的本地ID，由chooseImage接口获得
		isShowProgressTips : 1, // 默认为1，显示进度提示
		success : function(res) {
			var serverId = res.serverId; // 返回图片的服务器端ID
			uploadphoto(serverId, i);
		}
	});
}

function downloadImage(serverId) {
	wx.downloadImage({
		serverId : serverId, // 需要下载的图片的服务器端ID，由uploadImage接口获得
		isShowProgressTips : 1, // 默认为1，显示进度提示
		success : function(res) {
			var localId = res.localId; // 返回图片下载后的本地ID

		}
	});
}

function uploadphoto(serverId, i) {
	$.ajax({
		type : "post",
		url : ctx + "/weixin/uploadphoto",
		dataType : "json",
		data : {
			"media_id" : serverId,
			"type" : "repair"
		},
		async : false,
		success : function(result) {
			var src = result.data;
			/*var src = '${ctxStatic}/image/temp/03.jpg';*/
			var html = $("#uploaderFiles").html();
			var newhtml = packaging(src);
			$("#uploaderFiles").html(newhtml+html);
		},
		error : function(result) {
			alert(result.data);
		}
	});
}

function submit(dealType) {
	clickCount = clickCount + 1;
	/*if (clickCount > 1) {
		return;
	}*/
	var rname = $("#rname").val();
	var rphone = $("#rphone").val();
	var rcontent = $("#rcontent").val();
	//var repairtype = $(".content1").find("input[type=radio]:checked").val();
	var typeName;
	var url, addurl, editurl;
	switch(dealType){
		case 1:
			typeName = "报修";
			if ($("#contentid").val() == null || $("#contentid").val() == "") {
				url = ctx + '/wyPrivateRepair/applySave';
			} else {
				url = ctx + '/wyPrivateRepair/updateRepair';
			}
			addurl = ctx + "/wyComplain/myAffairs?flag=2";
			editurl = ctx + "/wyComplain/myAffairs?flag=2";
			break;
		case 2:
			url = ctx + '/wyComplain/applySave';
			/*if ($("#contentid").val() == null || $("#contentid").val() == "") {
				url = ctx + '/wyComplain/applySave';
			} else {
				url = ctx + '/wyPrivateRepair/updateRepair';
			}*/
			addurl = ctx + "/wuye/myAffairs?flag=2";
			editurl = ctx + "/wuye/myAffairs?flag=2";
			typeName = "投诉";
			break;
	}

	if (rname == "") {
		layer.open({
			content : typeName+"人姓名不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	if (rphone == "") {
		layer.open({
			content : typeName+"人电话不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	} else {
		if (!isMobile(rphone)) {
			layer.open({
				content : '请输入正确的手机号码',
				skin : 'msg',
				time : 2
			});
			return;
		}
	}

	if (rcontent == "") {
		layer.open({
			content : typeName+"内容不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	/*if (repairtype == undefined || repairtype == "") {
		layer.open({
			content : "请选择报修类型",
			skin : 'msg',
			time : 2
		});
		return;
	}*/

	// 获取图片
	var imgDiv = $("#uploaderFiles").find("li");	
	
	var images = new Array();
	$.each(imgDiv, function(index, item) {
		var bgurl = $(this).css("background-image").split("(")[1].split(")")[0];
		bgurl = bgurl.substring(1, bgurl.length-1);
		images.push(bgurl);
	});
	var imagesStr=images.join(",");	
	console.log(imagesStr);
	var param={
		rname : rname,
		rphone : rphone,
		rcontent : rcontent,
		beginTime : $("#datetime-picker").val(),
		rdetail : $("#rdetail").val(),
		imgs : imagesStr			
	};
	$.ajax({ // 获取验证码之前先判断主会员是否存在系统中
		url : url,
		data : param,
		type : 'post',
		async : false,
		success : function(data) {
			// debugger;
			if (data.data == "add") {
				layer.open({
					content : typeName+"成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(addurl);
			} else if (data.data == "edit") {
				layer.open({
					content : "编辑成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(editurl);
			} else {
				layer.open({
					content : data.msg,
					skin : 'msg',
					time : 2
				});
				flag = false;
			}
		}
	});
}

function delphoto(jq) {
	var url = $(jq).parent().find("li").css("background-image").split("(")[1].split(")")[0];
	url = url.substring(1, url.length-1);
	$.ajax({ 
		url : ctx + '/wyPrivateRepair/delphoto',
		data : {
			"url" : url,
		},
		type : 'post',
		async : false,
		success : function(data) {
			// debugger;
			if (data.code == 200) {
				layer.open({
					content : "删除成功",
					skin : 'msg',
					time : 2
				});
				$(jq).parent().remove();
			} else {
				layer.open({
					content : "删除失败",
					skin : 'msg',
					time : 2
				});
			}
		}
	});
}

function packaging(src){
	var p1 = "<div class='myweui_upload_img'>";
	var p2 = "<div class='myweui_del_icon' onclick='delphoto(this)'>";
	var p3 = "<img src='"+ctxStatic+"/image/del.png'></img>";
	var p4 = "</div>";
	var p5 = "<li class='weui-uploader__file' style='background-image:url("+src+")'></li>";
	var p6 = "</div>";
	return p1+p2+p3+p4+p5+p6;
}

function test(){
	var src = ctxStatic+'image/temp/03.jpg';
	var html = $("#uploaderFiles").html();
	var newhtml = packaging(src);
	$("#uploaderFiles").html(newhtml+html);
}