$(function() {
	/*$("#rent").click(function() {
		$(".rent").css("display", "block");
		$(".sale").css("display", "none");
	});
	$("#sale").click(function() {
		$(".rent").css("display", "none");
		$(".sale").css("display", "block");
	});*/

	$("#rent").click(function() {
		$(".rents").css("display", "flex");
		$(".sales").css("display", "none");
	});
	$("#sale").click(function() {
		$(".rents").css("display", "none");
		$(".sales").css("display", "flex");
	});
	/*
	 * $(".deldiv").click(function(){ var deldiv = $(this).parent(); var url =
	 * deldiv.children("img").attr("src"); $.ajax({ type : "post", url : ctx +
	 * '/wyPrivateRepair/delphoto', data : {"url" : url}, success :
	 * function(data) { deldiv.css("display","none"); }, error:function(data){
	 * alert(data.data); } }) });
	 */
	$(".showphoto").on("click", ".deldiv", function() {
		var deldiv = $(this).parent();
		var url = deldiv.children("img").attr("src");
		$.ajax({
			type : "post",
			url : ctx + '/wyPrivateRepair/delphoto',
			data : {
				"url" : url
			},
			success : function(data) {
				deldiv.remove();
			},
			error : function(data) {
				alert(data.data);
			}
		})
	});
	$(".imgurl").css("height", $(".imgurl").width());
	$(".camerabg").css("height", $(".camerabg").width());
});

function startCamera() {
	var count = $("photo").length;
	if(count < 8){
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
	}	
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

function uploadphoto(serverId, i) {
	$.ajax({
		type : "post",
		url : ctx + "/weixin/uploadphoto",
		dataType : "json",
		data : {
			"media_id" : serverId,
			"type" : "houseRent"
		},
		async : false,
		success : function(result) {
			var src = result.data;
			var html = $(".showphoto").html();
			$(".showphoto").html(getphoto(src) + html);
			$(".photo").css("height", $(".photo").offsetWidth);
			$(".imgurl").css("height", $(".imgurl").width());
		},
		error : function(result) {
			alert(result.data);
		}
	});
}

function getphoto(src) {
	return "<div class='photo'><div class='deldiv' ><img src='" + ctxStatic
			+ "/image/删除图片.png' class='del'></div><img src='" + src
			+ "' class='imgurl'></div>"
}

function clearNoNum(obj, id) {
	// 先把非数字的都替换掉，除了数字和.
	var vv = $("#" + id).val();
	if ((vv.indexOf(".") == -1) && vv.length > 0) {
		return;
	}

	vv = vv.replace(/[^\d.]/g, "");
	// 必须保证第一个为数字而不是.
	vv = vv.replace(/^\./g, "");
	// 保证只有出现一个.而没有多个.
	vv = vv.replace(/\.{2,}/g, ".");
	// 保证.只出现一次，而不能出现两次以上
	vv = vv.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	// 小数点后保留两位
	var pos = vv.indexOf(".");
	// alert(pos);
	if (pos > -1) {
		var pos1 = vv.substring(pos);
		// alert(pos1);
		if (pos1.length > 3) {
			vv = vv.substring(0, pos + 3);
		}
	}
	// alert(vv);
	$("#" + id).val(vv);
}

function formSubmit() {
	// 租售类型
	var rentalType = $("#houseRentForm")
			.find("input[name=rental_type]:checked").val();
	if (rentalType == undefined || rentalType == '') {
		layer.open({
			content : "请选择租售类型",
			skin : 'msg',
			time : 2
		});
		return;
	}

	// 标题
	var title = $("#houseRentForm").find("input[name=title]").val();
	if (title == undefined || title == '' || title.length < 1) {
		layer.open({
			content : "请填写标题",
			skin : 'msg',
			time : 2
		});
		return;
	}

	// 房屋描述
	var house_desc = $("#house_desc_textarea").val();
	if (house_desc == undefined || house_desc == '' || house_desc.length < 1) {
		layer.open({
			content : "请填写房屋描述",
			skin : 'msg',
			time : 2
		});
		return;
	}
	if (house_desc.length > 300) {
		layer.open({
			content : "您输入的房屋描述字数超过300",
			skin : 'msg',
			time : 2
		});
		return;
	}

	// 价格
	var monthlyRent = 0;
	if (rentalType == 0) {
		monthlyRent = $("#rent1").val();
	} else {
		monthlyRent = $("#rent2").val();
	}
	if (monthlyRent == undefined || monthlyRent == '' || monthlyRent == 0) {
		layer.open({
			content : "请输入租售价格",
			skin : 'msg',
			time : 2
		});
		return;
	}

	// 获取图片
	var imgDiv = $(".showphoto").find(".imgurl");	
	
	var images = new Array();
	$.each(imgDiv, function(index, item) {
		images.push($(this).attr("src"));
	});
	if(images.length <1){
		layer.open({
			content : "请至少上传一张图片",
			skin : 'msg',
			time : 2
		});
		return;
	}	
	var imagesStr=images.join(",");	
	var param={
		rentalType : rentalType,
		title : title,
		houseDesc : house_desc,
		monthlyRent : monthlyRent,
		imgs : imagesStr			
	};

	var msg = "是否确认提交？\n\n请确认！";
	if (confirm(msg)==false){return;}
	
	$.ajax({
		type : "post",
		url : ctx + "/houseRent/publishSave",
		dataType : "json",
		data : param,
		async : false,
		success : function(result) {
			console.info(result);
			
			var data = result.data;			
			if(data && result.code==200){
				layer.open({
					content : "发布成功",
					skin : 'msg',
					time : 2
				});	
				window.location.href=ctx+"/houseRent/list";
			}else{
				layer.open({
					content : data.msg,
					skin : 'msg',
					time : 2
				});
			}
		},
		error : function(result) {
			alert(result.msg);
		}
	});

}

//点赞
function dianzan(id, jq, a, b){
	var param = {
		relationId : id
	};
	$.ajax({
		type : "post",
		url : ctx + "/houseRent/dianzan",
		dataType : "json",
		data : param,
		async : false,
		success : function(result) {
			if(result.code==200){
				if(result.data !=null || result.data !=undefined){
					$(jq).parent().find("span").html(result.data);
				}
				$(jq).parent().find("."+a).css("display", "");
				$(jq).parent().find("."+b).css("display", "none");
			}else if(result.code==500){
				var url=ctx+"/index";
				window.location.href = url;
			}				
		},
		error : function(result) {
			alert(result.msg);
			return;
		}
	});
	
}

//搜索
function search(){
	var formdata=$("#search-form").serialize();
	
	
}

//跳转到详情
function toShowDetail(id){
	var url=ctx+"/houseRent/detail?id="+id+"&dirType="+ignore;
	window.location.href = url;
	
}

function showImg(url){
	$(".weui-gallery-img").css("background-image", "url("+url+")");
	$(".weui-gallery").fadeIn();
}

function toShowDetail1(id){
	var url=ctx+"/fleaMarket/detail?id="+id+"&dirType="+ignore;
	window.location.href = url;			
}

function dianzan1(id,jq,a,b){
	var param = {
		relationId : id
	};
	$.ajax({
		type : "post",
		url : ctx + "/fleaMarket/dianzan",
		dataType : "json",
		data : param,
		async : false,
		success : function(result) {
			if(result.code==200){
				if(result.data !=null || result.data !=undefined){
					$(jq).parent().find("span").html(result.data);
				}
				$(jq).parent().find("."+a).css("display", "");
				$(jq).parent().find("."+b).css("display", "none");
			}else if(result.code==500){
				var url=ctx+"/index";
				window.location.href = url;
			}				
		},
		error : function(result) {
			alert(result.msg);
			return;
		}
	});
}