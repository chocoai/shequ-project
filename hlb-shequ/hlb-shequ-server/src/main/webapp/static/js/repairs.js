/*function changePrivate() {
 $(".private").attr("checked", "checked");
 $(".public").removeAttr("checked");	
 }

 function changePublic() {
 $(".public").attr("checked", "checked");
 $(".private").removeAttr("checked");	
 }*/

function startCamera() {
	
	wx.chooseImage({
		count : 5, // 默认9
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
			$(".picture" + i).attr("src", result.data);
			$("#picture" + i).val(result.data);
			$(".picture" + i).css('display', 'block');
			$(".del" + i).css('display', 'block');
			$(".pic" + i).css("background-image", "url(" + result.data + ")");
			if (i == "1") {
				/*$(".del1").css("right", "50%");
				$(".del2").css("right", "30%");
				$(".del3").css("right", "10%");*/
				$("#picture2").val("");
				$(".picture2").css('display', 'none');
				$(".del2").css('display', 'none');
				$("#picture3").val("");
				$(".picture3").css('display', 'none');
				$(".del2").css('display', 'none');
			}
			$(".imgurl").css("height", $(".picture").width());
		},
		error : function(result) {
			alert(result.data);
		}
	});
}

function submit() {
	clickCount = clickCount + 1;
	if (clickCount > 1) {
		return;
	}
	var rname = $("#rname").val();
	var rphone = $("#rphone").val();
	var rcontent = $("#rcontent").val();
	var repairtype = $(".content1").find("input[type=radio]:checked").val();

	if (rname == "") {
		layer.open({
			content : "报修姓名不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	if (rphone == "") {
		layer.open({
			content : "报修电话不能为空",
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
			content : "报修内容不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	if (repairtype == undefined || repairtype == "") {
		layer.open({
			content : "请选择报修类型",
			skin : 'msg',
			time : 2
		});
		return;
	}

	var url;
	if ($("#contentid").val() == null || $("#contentid").val() == "") {
		url = ctx + '/wyPrivateRepair/applySave';
	} else {
		url = ctx + '/wyPrivateRepair/updateRepair';
	}

	var formdata = $('#repairs-form').serialize();
	$.ajax({ // 获取验证码之前先判断主会员是否存在系统中
		url : url,
		data : formdata,
		type : 'post',
		async : false,
		success : function(data) {
			// debugger;
			if (data.data == "add") {
				layer.open({
					content : "报修成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(ctx + "/wuye/myAffairs?flag=2");
			} else if (data.data == "edit") {
				layer.open({
					content : "编辑成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(ctx + "/wuye/myAffairs?flag=2");
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

function delphoto(id) {
	$.ajax({ // 获取验证码之前先判断主会员是否存在系统中
		url : ctx + '/wyPrivateRepair/delphoto',
		data : {
			"url" : $("#picture" + id).val(),
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
				$(".picture" + id).css("display", "none");
				$(".del" + id).css("display", "none");
				if (id == "1") {
					$(".del3").css("right", $(".del2").css("right"));
					$(".del2").css("right", "50%");
				} else if (id == "2") {
					$(".del3").css("right", $(".del2").css("right"));
					$(".del2").css("right", $(".del1").css("right"));
				}
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

function submit1() {
	clickCount = clickCount + 1;
	if (clickCount > 1) {
		return;
	}

	var rname = $("#rname").val();
	var rphone = $("#rphone").val();
	var rcontent = $("#rcontent").val();

	if (rname == "") {
		layer.open({
			content : "报修姓名不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	if (rphone == "") {
		layer.open({
			content : "报修电话不能为空",
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
			content : "报修内容不能为空",
			skin : 'msg',
			time : 2
		});
		return;
	}

	var url;
	/*
	 * if ($("#contentid").val() == null || $("#contentid").val() == "") { url =
	 * ctx + '/wyComplain/applySave'; } else { url = ctx +
	 * '/wyComplain/updateRepair'; }
	 */
	url = ctx + '/wyComplain/applySave';

	$.ajax({ // 获取验证码之前先判断主会员是否存在系统中
		url : url,
		data : $('#repairs-form').serialize(),
		type : 'post',
		async : false,
		success : function(data) {
			// debugger;
			if (data.data == "add") {
				layer.open({
					content : "投诉成功",
					skin : 'msg',
					time : 2
				});
				window.location.replace(ctx + "/wyComplain/myAffairs?flag=2");
			} else if (data.data == "edit") {
				layer.open({
					content : "编辑成功",
					skin : 'msg',
					time : 2
				});
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
