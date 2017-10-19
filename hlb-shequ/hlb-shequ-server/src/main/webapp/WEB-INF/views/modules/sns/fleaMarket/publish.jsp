<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>跳蚤市场-发布</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/fleamarketPublish.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
        <script src="${ctxStatic}/js/jquery.js"></script>
        <script src="${ctxStatic}/js/ad_translation.js"></script>		
		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}"
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;background-color: #f8f8f8;}
            .weui-uploader__input-box{
                width: 20%;
                margin-right: 4%;
                float: left;
                margin-bottom: 10px;
            }
            .weui-cell__ft {
                color: rgba(0, 0, 0, 0.6);
            }
		</style>
</head>
<body>
	<!-- 广告 -->
	<!-- <div class="focus" id="focus">
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
        </div> -->
        <!-- 系统公告 -->
        <!-- <div class="acBox">
            <ul>
                <li style="left:72px;">联系电话：0755-26503033 微信公众号：好邻邦云社区&nbsp;&nbsp;</li>
            </ul>
            <i class="close"></i>
        </div>
    </div> -->
   <div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
        <a class="weui-cell weui-cell_access" href="javascript:;">
            <div class="weui-cell__ft1">
            </div>
            <div class="weui-cell__bd">
                <p>跳蚤市场-发布</p>
            </div>
        </a>
    </div>
    <div class="weui-cells usershow">
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="${ctxStatic}/image/客户名称.png"></div>
                <div class="weui-label">
                    <p>客户名称</p>
                </div>
            <div class="weui-cell__ft">${member.memberName}</div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="${ctxStatic}/image/手机号码.png"></div>
                <div class="weui-label">
                    <p>手机号码</p>
                </div>
            <div class="weui-cell__ft">${member.mobile}</div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="${ctxStatic}/image/详细地址.png"></div>
                <div class="weui-label">
                    <p>详细地址</p>
                </div>
            <div class="weui-cell__ft">${room.WYName}${room.LYName}${room.roomNo}</div>
        </div>
    </div>
    <div class="weui-cells weui-cells_form" id="houseRentForm">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">物品名称</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" placeholder="请输入商品名" id="goodsName" name="goodsName">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">转让价格</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="price" name="price" onkeyup="clearNoNum(this, 'price')" type='number' step='0.01' placeholder="请输入金额" style="width: 60%">元
            </div>
        </div>
        <div class="weui-cell" style="border-bottom: 1px solid #e5e5e5;">
            <div class="weui-cell__bd">
                <textarea class="weui-textarea" placeholder="物品描述" rows="3" name="goodsDesc" id="goodsDesc"></textarea>
            </div>
        </div>
        <form id="houseRentForm">
            <div class="info" style="margin-top: 15px; display: table;">
                <div class="showphoto">
                    
                </div>
                <div class="weui-uploader__input-box" onclick="startCamera()">
                    <input id="uploaderInput" class="weui-uploader__input imgurl" readonly="true" accept="image/*" multiple="">
                </div>
            </div>
            <div class="location_address"></div>
            <input name="longitude" type="hidden"></input>
            <input name="latitude" type="hidden"></input>
        </form>
    </div>

    <div class="division"></div>

    <div class="weui-cells" style="margin-top: 0px;">
        <div class="weui-cell">
            <a onclick="formSubmit()" class="weui-btn weui-btn_primary but_style1">提交</a>
            <a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
        </div>
    </div>

    <div class="division1"></div>
</body>
<script src="${ctxStatic}/js/myweixin.js"></script>
<script src="${ctxStatic}/js/base.js"></script>
<script src="${ctxStatic}/js/roll.js"></script>
<script src="${ctxStatic}/js/fleaMarket2.js"></script>
<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
<script src="${ctxStatic}/js/layer.js"></script>
<script src="${ctxStatic}/js/ads.js"></script>
</html>