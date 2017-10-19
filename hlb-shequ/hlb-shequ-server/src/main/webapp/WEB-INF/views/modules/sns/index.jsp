<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>${title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<!-- <link rel="stylesheet" href="${ctxStatic}/css/base.css"> -->
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/layer.css" id="layui_layer_skinlayercss">
        <link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
        <link rel="stylesheet" href="${ctxStatic}/css/example.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/public.js"></script>
		<script src="${ctxStatic}/js/mobile_common.js"></script>
		<script src="${ctxStatic}/js/iscroll-probe.js"></script>
        <script src="${ctxStatic}/js/getscript.js"></script>
		<script src="${ctxStatic}/js/convertor.js"></script>
		<script src="${ctxStatic}/js/jquery.flexslider-min.js"></script>
		<script src="${ctxStatic}/js/swiper.min.js"></script>
        <script src="${ctxStatic}/js/index.js"></script>
        <script src="${ctxStatic}/js/zepto.min.js"></script>
        <script src="${ctxStatic}/js/ad_translation.js"></script>
        <script type="text/javascript">var ctx = "${ctx}";</script>
		
	<style>
		body{margin:0;overflow-x:hidden;padding:0;}
        .bg-color{
            border-radius: 60px;
        }
        html, body {
          height: 100%;
          margin: 0;
        }
        .bg-color1{
            background-color: aqua;
            padding: 14;
            border-radius: 10px;
        }
        .bg-color2{
            background-color: aqua;
            padding: 14;
            border-radius: 50px;
        }
	</style>
</head>

<script type="text/javascript">
$(function() {
    var narrow = $(".bg-colors").css("padding").substring(0,2);
    $(".bga").height($(".bga").height()-narrow*2);
    $(".bg-colors").parent().css("margin-top",-narrow);
});
</script>


<body>
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
        <div class="bd" style="background-size:cover">
            <div class="tempWrap" style="overflow:hidden; position:relative;">
                <ul style="width: 640px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translate(0px, 0px) translateZ(0px);">
                    <c:forEach items="${ads}" var="ads">
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
    <c:if test="${membertype == 1}">
    <div style="z-index: 200;position: relative;">
        <a class="position1"> 
            <span class="glyphicon" onclick="changeRoom(${room.roomId})">${room.WYName}${room.LYName}${room.roomNo}</span>
        </a>  
        <!-- <a class="position" onclick="changeRoom(${room.roomId})"> 
            <span class="glyphicon">更改房号</span>
        </a> -->
    </div>
    </c:if>
    <c:if test="${membertype == 9}">
    <div style="z-index: 200;position: relative;"> 
        <c:if test="${admintype != 1}">
            <a class="position1"> 
                <span class="glyphicon">游客</span>
            </a>
        </c:if>
        <c:if test="${admintype == 1}">
            <a class="position1"> 
                <span class="glyphicon">管理员</span>
            </a>
        </c:if>
       <!--  <a class="position" onclick="changeInfo()"> 
            <span class="glyphicon">更新信息</span>
        </a> -->
    </div>
    </c:if>
    <!--分类开始-->
    <!-- <div class="page-center-box" id="index">
        <div class="banner mb10">
            <div class="flexslider_cate">
                <ul class="slides">
                    <li class="list flex-active-slide" style="width: 100%; float: left; margin-right: -100%; position: relative; display: block; transition: opacity 0.6s ease; opacity: 1; z-index: 2;">
                        <ul class="cate">
                        <c:forEach items="${menuList}" var="menu">
                            <li>
                                <a href="${ctx}${menu.href}">
                                    <div class="icon">
                                        <img src="${ctxStatic}/${menu.icon}">
                                    </div>
                                    <p>
                                        ${menu.name}
                                    </p>
                                </a>
                            </li>
                        </c:forEach>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div> -->

    <div class="weui_grids">
        <c:forEach items="${menuList}" var="menu">
       <!--  <a href="${ctx}${menu.href}" class="weui_grid js_grid"> -->
        <a onclick="jump('${ctx}${menu.href}')" class="weui_grid js_grid bga">
            <!-- <div class="bg-color" style="background-color: ${menu.backgroundColor}"> -->
            <div >
                <div class="weui_grid_icon bg-colors bg-color${menu.backgroundType}" style="background-color: ${menu.backgroundColor}">
                    <!-- <img src="${menu.icon}" alt="" style="width: 28px; height: 28px;"> -->
                    <img src="${menu.icon}" alt="">
                </div>
                <p class="weui_grid_label">
                    ${menu.name}
                </p>
            </div>
        </a>
        </c:forEach>
    </div>

    <!-- weui1.0版本 -->
    <!-- <div class="weui-tabbar">
        <a href="javascript:;" class="weui-tabbar__item">
            <span style="display: inline-block;position: relative;">
                <img src="${ctxStatic}/image/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">8</span>
            </span>
            <p class="weui-tabbar__label">首页</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item">
            <img src="${ctxStatic}/image/icon_tabbar.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">报修</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item">
            <span style="display: inline-block;position: relative;">
                <img src="${ctxStatic}/image/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <span class="weui-badge weui-badge_dot" style="position: absolute;top: 0;right: -6px;"></span>
            </span>
            <p class="weui-tabbar__label">我的报修</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on">
            <img src="${ctxStatic}/image/icon_tabbar.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">我的</p>
        </a>
    </div> -->
    <div class="weui_tab1" style="padding-top: 100px">
        <div class="weui_tabbar" style="position: fixed;">
            <a href="##" onclick="jump('${ctx}/index')" class="weui_tabbar_item weui_bar_item_on">
                <div class="weui_tabbar_icon">
                    <img src="${ctxStatic}/image/首页-首页.png" alt="">
                </div>
                <p class="weui_tabbar_label">首页</p>
            </a>
            <a href="##" onclick="jump('null')" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="${ctxStatic}/image/邻里管理.png" alt="">
                </div>
                <p class="weui_tabbar_label">邻里圈</p>
            </a>
            <a href="##" onclick="jump('null')" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="${ctxStatic}/image/生活圈.png" alt="">
                </div>
                <p class="weui_tabbar_label">好生活</p>
            </a>
            <a href="${ctx}/personcenter/index" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="${ctxStatic}/image/个人中心 .png" alt="">
                </div>
                <p class="weui_tabbar_label">个人中心</p>
            </a>             
        </div>
    </div>
</body>

<script type="text/javascript">
    var width = document.body.offsetWidth;
    $(".weui_grids").css("margin-top", (width*45/100+10));
    $(".weui_grids").css("z-index", "98");

    $('.weui_tab1').tab({
        defaultIndex: 0,
        activeClass:'weui_bar_item_on',
        onToggle:function(index){
        /*if(index>0){
            alert(index);
        }  */ 
        }
    });

    function jump(url){
        var memberType = "${membertype}";
        var adminType = "${admintype}";
        if(url=="/hlb-shequ-server/personcenter/index" || url=="/hlb-shequ-server/wyConvenienceService/index2" || url=="/hlb-shequ-server/wyConvenienceService/index" || url=="/hlb-shequ-server/fleaMarket/list" || url=="/hlb-shequ-server/houseRent/list"){
            window.location.href = url;
            return;
        }
        if(url.indexOf("null")!=-1){
            layer.open({
                content: "暂未开放",
                skin: 'msg',
                time: 2
            });
            return;
        }
        if(memberType != '1'){
            layer.open({
                //content: "您是游客身份，无权查看",
                content: "该模块仅对会员开放",
                skin: 'msg',
                time: 2
            });
            return;
        }
       /* if(adminType == '1'){
            layer.open({
                content: "您是游客身份，无权查看",
                skin: 'msg',
                time: 2
            });
            return;
        }*/
        window.location.href = url;
    } 
</script>
</html>