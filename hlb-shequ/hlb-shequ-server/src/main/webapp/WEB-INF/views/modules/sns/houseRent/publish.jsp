<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>物业投诉</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/base.css">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/layer.css" id="layui_layer_skinlayercss">
		<link rel="stylesheet" href="${ctxStatic}/css/repairs.css">
		<link rel="stylesheet" href="${ctxStatic}/LCalendar/LCalendar.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
        <script src="${ctxStatic}/js/zepto.min.js"></script>
        <script src="${ctxStatic}/js/base.js"></script>
        <script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/iscroll-probe.js"></script>
		<script src="${ctxStatic}/js/api.js"></script>
        <script src="${ctxStatic}/js/getscript.js"></script>
        <script src="${ctxStatic}/js/convertor.js"></script>
		<script src="${ctxStatic}/js/swiper.min.js"></script>
        <script src="${ctxStatic}/LCalendar/LCalendar.min.js"></script>
        <script src="${ctxStatic}/js/common.js"></script>
        <script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
        <script src="${ctxStatic}/js/picker.js"></script>

		<script type="text/javascript">
			var clickCount = 0;
            var ctx = "${ctx}";
            var bizKey = "${bizKey}";
            var ctxStatic = "${ctxStatic}/";
            $(function(){
                $("#time").datetimePicker({title:"选择日期时间",min:'2015-12-10',max:'2050-10-01'});

                if('${imgnum}'==1){
                    $(".picture1").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[0]}");
                    $(".picture1").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $("#picture1").val("${ctxStatic}/"+"${wyComplain.imgList[0]}");
                }
                if('${imgnum}'==2){
                    $(".picture1").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[0]}");
                    $(".picture2").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[1]}");
                    $(".picture1").css('display','block'); 
                    $(".picture2").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $(".del2").css('display','block'); 
                    $("#picture1").val("${ctxStatic}/"+"${wyComplain.imgList[0]}");
                    $("#picture2").val("${ctxStatic}/"+"${wyComplain.imgList[1]}");
                }
                if('${imgnum}'==3){
                    $(".picture1").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[0]}");
                    $(".picture2").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[1]}");
                    $(".picture3").attr("src", "${ctxStatic}/"+"${wyComplain.imgList[2]}");
                    $(".picture1").css('display','block'); 
                    $(".picture2").css('display','block'); 
                    $(".picture3").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $(".del2").css('display','block'); 
                    $(".del3").css('display','block'); 
                    $("#picture1").val("${ctxStatic}/"+"${wyComplain.imgList[0]}");
                    $("#picture2").val("${ctxStatic}/"+"${wyComplain.imgList[1]}");
                    $("#picture3").val("${ctxStatic}/"+"${wyComplain.imgList[2]}");
                }
            });
		</script>
        <script src="${ctxStatic}/js/repairs.js"></script>
		<style>
            body{
                margin:0;
                overflow-x:hidden;
                padding:0;
                background-color: #f8f8f8;
                font-size: 17px;
            }
        </style>
</head>
<body>
    <!-- 用户信息 -->
    <div class="info">
    	<div class="title">
    		<span class="titlename">物业投诉</span>
    	</div>
    	<div class="username">
    		<span class="infolist">客户名称 : ${member.memberName}</span>
    	</div>
    	<div class="mobile">
    		<span class="infolist">手机号码 : ${member.mobile}</span>
    	</div>
    	<div class="address">
    		<span class="infolist">详细地址 : ${room.WYName}${room.LYName}${room.roomNo}</span>
    	</div>
    </div>

    <div class="border" style="margin: 10px 3% 20px 3%;"></div>

    <!-- 报修内容 -->
    <div class="content1">
    	<form id="repairs-form" method="post">
            <input type="hidden" id="roomId" name="roomId" value="${room.roomId}"></input>
            <div class="repairsname">
                <div >投诉姓名</div>
                <input id="rname" name="rname" value="${wyComplain.applyname}"></input>
            </div>
    		<div class="phone">
    			<div>联系电话</div>
    			<input id="rphone" name="rphone" value="${wyComplain.phone}"></input>
    		</div>
    		<div class="repairscontent">
    			<div>投诉内容</div>
    			<input id="rcontent" name="rcontent" value="${wyComplain.content}"></input>
    		</div>
    		<div class="detail">
    			<div>详细描述</div>
    			<textarea rows="50" cols="3" name="rdetail" id="rdetail">${wyComplain.contentdetail}</textarea>
    		</div>
    		<div class="photo">
    			<div class="camera" onclick="startCamera()">
    				<image class="img img0" src="${ctxStatic}/image/timg.jpg"></image>
    			</div>
    			<div class="picture">
                    <input class="picturedata" id="picture1" name="picture1"></input>
                    <image class="img picture1" onclick="$('.weui-gallery1').fadeIn();"></image>
                    <img class="del del1" src="${ctxStatic}/image/del.png" onclick="delphoto('1')" style="top:315px"></img>
                    <div class="weui-gallery weui-gallery1" style="display: none">
                        <span class="weui-gallery-img pic1"></span>
                        <div class="weui-gallery-opr" style="height: 60px">
                            <a href="javascript:" class="weui-gallery-del" onclick="$('.weui-gallery1').fadeOut();" style="color: #FFF">
                                	返回
                            </a>
                        </div>
                    </div>
                </div>
                <div class="picture">
                    <input class="picturedata" id="picture2" name="picture2"></input>
                    <image class="img picture2" onclick="$('.weui-gallery2').fadeIn();"></image>
                    <img class="del del2" src="${ctxStatic}/image/del.png" onclick="delphoto('2')" style="top:315px"></img>
                    <div class="weui-gallery weui-gallery2" style="display: none">
                        <span class="weui-gallery-img pic2"></span>
                        <div class="weui-gallery-opr" style="height: 60px">
                            <a href="javascript:" class="weui-gallery-del" onclick="$('.weui-gallery2').fadeOut();" style="color: #FFF">
                                	返回
                            </a>
                        </div>
                    </div>
                </div>
                <div class="picture">
                    <input class="picturedata" id="picture3" name="picture3"></input>
                    <image class="img picture3" onclick="$('.weui-gallery3').fadeIn();"></image>
                    <img class="del del3" src="${ctxStatic}/image/del.png" onclick="delphoto('3')" style="top:315px"></img>
                    <div class="weui-gallery weui-gallery3" style="display: none">
                        <span class="weui-gallery-img pic3"></span>
                        <div class="weui-gallery-opr" style="height: 60px">
                            <a href="javascript:" class="weui-gallery-del" onclick="$('.weui-gallery3').fadeOut();" style="color: #FFF">
                                	返回
                            </a>
                        </div>
                    </div>
                </div>
            </div>    		
    	</form>
    </div>

    <div class="border"></div>

    <!-- 提交按钮 -->
    <div class="submit">
        <div class="submit1">
            <a onclick="submit1()" class="weui_btn weui_btn_mini bg-blue f20">提交</a>
        </div> 
        <div class="submit2">
            <a onclick="javascript:history.go(-1)" class="weui_btn weui_btn_mini weui_btn_plain_primary f20">返回</a>  
        </div>     
    </div>

</body>

<script type="text/javascript">
	$(function(){
        var targetUrl=location.href.split("#")[0];
        $.ajax({
            type: "post",
            url: "${ctx}/weixin/wxjssdk",
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
</script>
</html>