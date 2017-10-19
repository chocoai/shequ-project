<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>房屋租售-发布</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/houseRent.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/houseRent.js"></script>
		<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
		<script src="${ctxStatic}/js/ad_translation.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}"
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;}
		</style>
</head>
<body>
	<!-- 广告 -->
	<div class="focus" id="focus">
        <div class="hd">
            <ul>
                <li class="on">
                    1
                </li>
                <li class="">
                    2
                </li>
            </ul>
        </div>
        <!--下面的limit="0,2"是幻灯的个数，2代表2张图，以此类推，site_id=57是你广告位的ID-->
        <div class="bd">
            <div class="tempWrap" style="overflow:hidden; position:relative;">
                <ul style="width: 640px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translate(0px, 0px) translateZ(0px);">
                    <c:forEach items="${fns:getAds()}" var="ads">
                    <li style="display: table-cell; vertical-align: top; width: 320px;">
                        <a>
                            <img src="${ads.imgSrc}" class="bdimg"/>
                        </a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <!-- 系统公告 -->
        <div class="acBox">
            <ul>
                <li style="left:72px;">联系电话：0755-26503033 微信公众号：好邻邦云社区&nbsp;&nbsp;</li>
            </ul>
            <i class="close"></i>
        </div>
    </div>

    <div class="heading">房屋租售-发布</div>
    <div class="memberInfo">
    	<div class="baseInfo">
    		<div class="info">客户名称：${member.memberName}</div>
    		<div class="info">手机号码：${member.mobile}</div>
    		<div class="info">详细地址：${room.WYName}${room.LYName}${room.roomNo}</div>
    	</div>
    	<div class="avatar"><img src="${ctxStatic}/image/avatar.png" style="width: 100%; height: 70px"></div>
    </div>
	<form id="houseRentForm">
		<div class="roomtype">
			<div class="fwczbt">
				<input type="radio" name="rental_type" id="rent" value="0" checked="checked"/><label for="rent">房屋出租</label>
			</div>
			<div class="fwcsbt">
				<input type="radio" name="rental_type" id="sale" value="1"/><label for="sale">房屋出售</label>
			</div>
	    </div>
	    <div class="info">发布标题<input class="info1 inputstyle" name="title"></input></div>
	    <div class="info">房屋描述<textarea id="house_desc_textarea" class="info1 textareastyle" rows="6" cols="3" name="house_desc" maxlength="300"></textarea></div>
	    <div class="info rent" style="display:block;">月租价格<input id="rent1" class="info2 inputstyle" name="monthly_rent" onkeyup="clearNoNum(this, 'rent1')" type='number' step='0.01'></input> 元</div>
	    <div class="info sale">出售价格<input id="rent2" class="info2 inputstyle" name="monthly_rent" onkeyup="clearNoNum(this, 'rent2')"></input> 元</div>
	    <div class="info" style="margin-top: 15px; display: table;">
	    	<div class="showphoto">
	    		<%-- <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/index-bar.png.jpg" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/house.png" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/2.png" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/house.png" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/avatar.png" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/index-bar.png.jpg" class="imgurl">
			    </div>
			    <div class="photo">
		    		<div class="deldiv" >
		    			<img src="${ctxStatic}/image/删除图片.png" class="del">
			    	</div>
			    	<img src="${ctxStatic}/image/1.png" class="imgurl">
			    </div> --%>
	    	</div>
		    <div class="camerabg" onclick="startCamera()">
	    		<img src="${ctxStatic}/image/相机.png" class="imgurl">
	    	</div>
	    </div>
	    <div class="location_address"></div>
	    <input name="longitude" type="hidden"/>
	    <input name="latitude" type="hidden"/>
	</form>

	<div class="submit">
    	<!-- <button class="submit1" onclick="submit()">提交</button>
        <button class="submit2" onclick="javascript:history.go(-1)">返回</button> -->
        <div class="submit1">
            <a onclick="formSubmit()" class="weui_btn weui_btn_mini bg-blue f20">提交</a>
        </div> 
        <div class="submit2">
            <a onclick="javascript:history.go(-1)" class="weui_btn weui_btn_mini weui_btn_plain_primary f20">返回</a>  
        </div>     
    </div>
</body>
<script src="${ctxStatic}/js/layer.js"></script>
<script src="${ctxStatic}/js/ads.js"></script>
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
    var width = document.body.offsetWidth;
    $(".heading").css("margin-top", (width*45/100+10));
</script>
</html>