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
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/houseRent.js"></script>
		<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
		<script src="${ctxStatic}/js/ad_translation.js"></script>		<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
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
	<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
        <a class="weui-cell weui-cell_access" href="javascript:;">
            <div class="weui-cell__ft1">
            </div>
            <div class="weui-cell__bd">
                <p>房屋租售-发布</p>
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
    <div class="weui-cells weui-cells_form weui-cells_radio"  id="houseRentForm">
    	<label class="weui-cell weui-check__label" for="rent">
			<div class="weui-cell__bd">
				<p>房屋出租</p>
			</div>
			<div class="weui-cell__ft">
				<input type="radio" class="weui-check" name="rental_type" id="rent" checked="checked" value="0">
				<span class="weui-icon-checked"></span>
			</div>
		</label>
		<label class="weui-cell weui-check__label" for="sale">
			<div class="weui-cell__bd">
				<p>房屋出售</p>
			</div>
			<div class="weui-cell__ft">
				<input type="radio" class="weui-check" name="rental_type" id="sale" value="1">
				<span class="weui-icon-checked"></span>
			</div>
		</label>
    	<div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">发布标题</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" placeholder="请输入商品名" id="title" name="title">
            </div>
        </div>
        <div class="weui-cell rents">
            <div class="weui-cell__hd"><label class="weui-label">月租价格</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="rent1" name="monthly_rent" onkeyup="clearNoNum(this, 'rent1')" type='number' step='0.01' placeholder="请输入金额" style="width: 60%">元
            </div>
        </div>
        <div class="weui-cell sales" style="display: none;">
            <div class="weui-cell__hd"><label class="weui-label">出售价格</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="rent2" name="monthly_rent" onkeyup="clearNoNum(this, 'rent2')" type='number' step='0.01' placeholder="请输入金额" style="width: 60%">元
            </div>
        </div>
        <div class="weui-cell" style="border-bottom: 1px solid #e5e5e5;"s>
            <div class="weui-cell__bd">
                <textarea class="weui-textarea" placeholder="房屋描述" rows="3" name="house_desc" id="house_desc_textarea"></textarea>
            </div>
        </div>
        <form>
            <div class="info" style="margin-top: 15px; display: table;">
                <div class="showphoto">
                    
                </div>
                <div class="weui-uploader__input-box" onclick="startCamera()">
                    <input id="uploaderInput" class="weui-uploader__input imgurl" readonly="true" accept="image/*" multiple="">
                </div>
            </div>
            <div class="location_address"></div>
            <input name="longitude" type="hidden"/>
            <input name="latitude" type="hidden"/>
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
<script src="${ctxStatic}/js/layer.js"></script>
</html>