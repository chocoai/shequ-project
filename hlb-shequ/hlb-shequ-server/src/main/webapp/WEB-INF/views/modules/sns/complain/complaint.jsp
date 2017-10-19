<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>物业报修</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/base.css">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/layer.css" id="layui_layer_skinlayercss">
		<link rel="stylesheet" href="${ctxStatic}/css/repairs.css">
		<link rel="stylesheet" href="${ctxStatic}/LCalendar/LCalendar.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/public.js"></script>
		<script src="${ctxStatic}/js/mobile_common.js"></script>
		<script src="${ctxStatic}/js/iscroll-probe.js"></script>
        <script src="${ctxStatic}/js/api.js"></script>
        <script src="${ctxStatic}/js/getscript.js"></script>
		<script src="${ctxStatic}/js/convertor.js"></script>
		<script src="${ctxStatic}/js/jquery.flexslider-min.js"></script>
		<script src="${ctxStatic}/js/swiper.min.js"></script>
		<script src="${ctxStatic}/js/repairs.js"></script>
		<script src="${ctxStatic}/LCalendar/LCalendar.min.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}"
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
                    <c:forEach items="${ads}" var="ads">
                    <li style="display: table-cell; vertical-align: top; width: 320px;">
                        <a>
                            <img src="${ads.imgSrc}" />
                        </a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

    <!-- 用户信息 -->
    <div class="info">
    	<div class="title">
    		<span class="titlename">物业投诉</span>
    	</div>
    	<div class="username">
    		<span class="infolist">客户名称 : 张三</span>
    	</div>
    	<div class="mobile">
    		<span class="infolist">手机号码 : 13800138000</span>
    	</div>
    	<div class="address">
    		<span class="infolist">详细地址 : 豪龙大厦A座16C</span>
    	</div>
    </div>

    <div class="border"></div>

    <!-- 报修内容 -->
    <div class="content">
    	<form method="post">
    		<div class="area">
    			<input class="private" type="radio" onclick="changePrivate()">
    			<span>私人区域</span>
    			<input class="public" type="radio" onclick="changePublic()">
    			<span>公共区域</span>
    		</div>
    		<div class="repairsname">
    			<div >报修姓名</div>
    			<input></input>
    		</div>
    		<div class="phone">
    			<div>联系电话</div>
    			<input></input>
    		</div>
    		<div class="repairscontent">
    			<div>报修内容</div>
    			<input></input>
    		</div>
    		<div class="detail">
    			<div>详细描述</div>
    			<textarea rows="50" cols="3"></textarea>
    		</div>
    		<div class="photo">
    			<div class="camera">
    				<image class="img img0" src="${ctxStatic}/image/timg.jpg"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture1"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture1"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture1"></image>
    			</div>
    		</div>
    		<div class="ordertime">
    			<div>预约时间</div>
    			<!-- <input type="datetime-local"></input> -->
    			<input id="beginTime" type="text" name="beginTime" value="<fmt:formatDate value="${questionVo.beginTime}" pattern="yyyy-MM-dd HH:mm"/>" class="text" readonly/>
    		</div>
    	</form>
    </div>

    <div class="border"></div>

    <!-- 提交按钮 -->
    <div class="submit">
    	<button class="submit1" onclick="submit()">提交</button>
    </div>

</body>

<script type="text/javascript">
	//轮播图js
	$(function() {
	    $("#search-btn").click(function() {
	        if ($(".top-search").css("display") == 'block') {
	            $(".top-search").hide();
	            $(".top-title").show(200);
	        } else {
	            $(".top-search").show();
	            $(".top-title").hide(200);
	        }
	    });
	    $("#search-bar li").each(function(e) {
	        $(this).click(function() {
	            if ($(this).hasClass("on")) {
	                $(this).parent().find("li").removeClass("on");
	                $(this).removeClass("on");
	                $(".serch-bar-mask").hide();
	            } else {
	                $(this).parent().find("li").removeClass("on");
	                $(this).addClass("on");
	                $(".serch-bar-mask").show();
	            }
	            $(".serch-bar-mask .serch-bar-mask-list").each(function(i) {
	                if (e == i) {
	                    $(this).parent().find(".serch-bar-mask-list").hide();
	                    $(this).show();
	                } else {
	                    $(this).hide();
	                }
	                $(this).find("li").click(function() {
	                    $(this).parent().find("li").removeClass("on");
	                    $(this).addClass("on");
	                });
	            });
	        });
	    });
	});

	TouchSlide({
        slideCell: "#focus",
        titCell: ".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
        mainCell: ".bd ul",
        effect: "left",
        autoPlay: true, //自动播放
        autoPage: true, //自动分页
        switchLoad: "_src", //切换加载，真实图片路径为"_src", 
    });
</script>
</html>