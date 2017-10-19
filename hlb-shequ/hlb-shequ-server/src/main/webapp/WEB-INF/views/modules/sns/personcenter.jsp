<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>个人中心</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
        <script src="${ctxStatic}/js/layer.js"></script>
        <script src="${ctxStatic}/js/zepto.min.js"></script>
        <script src="${ctxStatic}/js/ad_translation.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}"
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;}
			.title{
				height: 30px;
			    line-height: 30px;
			    text-align: center;
			    font-size: 24px;
			    color: #FF9900;
			    margin: 12px 0;
			}
            .bg-color{
                border-radius: 60px;
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
<body onload="load()">
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

    <!-- 其他内容 -->
    <div class="title" style="display: none">个人信息中心</div>
    <div class="weui_grids">
        <c:forEach items="${menuList}" var="menu">
       <!--  <a href="${ctx}${menu.href}" class="weui_grid js_grid"> -->
        <a href="#" onclick="jump('${ctx}${menu.href}')" class="weui_grid js_grid bga">
            <!-- <div class="bg-color" style="background-color: ${menu.backgroundColor}">
                <div class="weui_grid_icon">
                    <img src="${menu.icon}" alt="" style="width: 28px; height: 28px;">
                </div>
                <p class="weui_grid_label">
                    ${menu.name}
                </p>
            </div> -->
            <div>
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
    
    <div class="weui_tab1" style="padding-top: 100px">
        <div class="weui_tabbar" style="position: fixed;">
            <a href="${ctx}/index" class="weui_tabbar_item">
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
            <a href="##" class="weui_tabbar_item weui_bar_item_on">
                <div class="weui_tabbar_icon">
                    <img src="${ctxStatic}/image/个人中心 .png" alt="">
                </div>
                <p class="weui_tabbar_label">个人中心</p>
            </a>             
        </div>
    </div>
</body>

<script type="text/javascript">
   function load(){
        $('.weui_tab1').tab({
            defaultIndex: 0,
            activeClass:'weui_bar_item_on',
            onToggle:function(index){
            /*if(index>0){
                alert(index);
            }  */ 
            }
        });

        var narrow = $(".bg-colors").css("padding").substring(0,2);
        $(".bga").height($(".bga").height()-narrow*2);
        $(".bg-colors").parent().css("margin-top",-narrow);
        var width = document.body.offsetWidth;
        $(".weui_grids").css("margin-top", (width*45/100+10));
        $(".weui_grids").css("z-index", "98");
   }
	//跳转内容
    function jump(url){
        var memberType = "${membertype}";
        var adminType = "${admintype}";
        if(url=="/hlb-shequ-server/personcenter/myRegister" || url=="/hlb-shequ-server/wuye/myAffairs"){
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
        window.location.href = url;
    }
</script>
</html>