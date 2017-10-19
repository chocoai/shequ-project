<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
	<style>
		.photo .img{
			width: 23%;
			height:80px;
			margin: 2px 1% 1px 1%;
			float:left;
		}
		.img1 .img2 .img3{
			display: none;
		}
		.location{
			display: block;
			width: 25%;
			height:80px;
			margin: 10px 1% 1px 1%;
			float:left;
			background-color: gray;
		}
	</style>
</head>
<body>
	<div class="photo" onclick="startCamera()">
		<image class="img" src="${ctxStatic}/image/timg.jpg"></image>
	</div>
	<div class="photo">
		<image class="img img1"></image>
	</div>
	<div class="photo">
		<image class="img img2"></image>
	</div>
	<div class="photo">
		<image class="img img3"></image>
	</div>
	<div class="location" onclick="getLocation()">
		<a>获取地理位置</a>
	</div>
	<div class="location" onclick="openLocation()">
		<a>显示地理位置</a>
	</div>
</body>
</html>

<script type="text/javascript">
	$(function(){
		var targetUrl=location.href.split("#")[0];
		$.ajax({
		    type: "post",
		    url: "${ctx}/wxjssdk",
		    dataType: "json",
		    data:{"url":targetUrl},
		    async:false,
		    success: function (result) {
		    	console.log(result);
		        //加载
		        wx.config({
		            debug: false,
		            appId:result.data.appid,
		            timestamp: result.data.timestamp,
		            nonceStr: result.data.noncestr,
		            signature: result.data.signature,
		            jsApiList: ['checkJsApi', 'chooseImage','previewImage','uploadImage','downloadImage','openLocation','getLocation']
		        });

		        wx.ready(function(){
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		            wx.checkJsApi({
		                jsApiList: ['chooseImage','previewImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
		                success: function(res) {
		                     //alert("检测接口:"+res.err_msg);
		                }
		            });
		        });
		    }
		});
	});

	function startCamera(){
		wx.chooseImage({
		    count: 3, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        for(var i=0; i<localIds.length; i++){
		        	uploadImage(localIds[i], i+1);
		        }
		    }
		});
	};

	function uploadImage(localId, i){
		wx.uploadImage({
		    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		        var serverId = res.serverId; // 返回图片的服务器端ID
		        uploadphoto(serverId, i);
		    }
		});
	}

	function downloadImage(serverId){
		wx.downloadImage({
		    serverId: serverId, // 需要下载的图片的服务器端ID，由uploadImage接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		        var localId = res.localId; // 返回图片下载后的本地ID
		       
		    }
		});
	}

	function uploadphoto(serverId, i){
		$.ajax({
		    type: "post",
		    url: "${ctx}/weixin/uploadphoto",
		    dataType: "json",
		    data:{"media_id":serverId},
		    async:false,
		    success: function (result) {
		    	$(".img"+i).attr("src", "${ctxStatic}/"+result.data);
		    	//alert($("#img"+i).attr("src"));
		    	$(".img"+i).css('display','block'); 
		    },
		    error: function (result ){
		    	alert(result.data);
		    }
		});
	}

	var latitude = "";
	var longitude = "";
	var speed = "";
	var accuracy = "";

	function getLocation(){
		wx.getLocation({
		    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		    	$.ajax({
		            type: "POST",
		            url: "${ctx}/getLocation",
		            data: res, // 你的formid
		            async: true, //开启异步，使得进度条可以随时读取
		            error: function(request) {

		            },
		        });
		        latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        speed = res.speed; // 速度，以米/每秒计
		        accuracy = res.accuracy; // 位置精度
		        alert("获取成功");
		    }
		});
	}

	function openLocation(){
		//alert(latitude+"    "+longitude);
		if(latitude=="" || longitude==""){
			alert("请先获取地理位置");
			return;
		}
		wx.openLocation({
		    latitude: latitude+"", // 纬度，浮点数，范围为90 ~ -90
		    longitude: longitude+"", // 经度，浮点数，范围为180 ~ -180。
		    name: '', // 位置名
		    address: '', // 地址详情说明
		    scale: 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
		    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
		});
	}
</script>