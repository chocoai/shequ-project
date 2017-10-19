<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<title>修改地址</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
		<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
		<script type="text/javascript" src="${ctxStatic}/js/city-picker.js"></script>
		<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
		<style type="text/css">
			body{margin: 0 auto;background-color: #f8f8f8;}
		</style>
	</head>
	<body>
		<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
			<a class="weui-cell weui-cell_access" href="javascript:;">
				<div class="weui-cell__ft1">
				</div>
				<div class="weui-cell__bd">
					<p>修改地址</p>
				</div>
				<!-- <div class="weui-cell__hd" onclick="window.location.href='${ctx}/houseRent/publish'"><img src="${ctxStatic}/image/保存.png"></div> -->
			</a>
		</div>

		<div class="weui-cells weui-cells_form" style="margin:0 0 10px 0">
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">收货人</label></div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="rname" name="rname" value="吴欣超">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">联系电话</label></div>
				<div class="weui-cell__bd">
					<input class="weui-input" type="tel" id="rphone" name="rphone" value="13580750428">
				</div>
			</div>
			<div class="weui-cell">
	        	<div class="weui-cell__hd">
	        		<label for="name" class="weui-label">所在地区</label>
	        	</div>
	        	<div class="weui-cell__bd">
	          		<input class="weui-input" id="city-picker" type="text" value="广东省 深圳市 宝安区">
	        	</div>
     	 	</div>
     	 	<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" placeholder="详细地址" rows="3" name="rdetail" id="rdetail">深圳市宝安区西乡流塘新村三巷10号1106</textarea>
				</div>
			</div>
		</div>

		<div class="weui-cells" style="color: red">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p>删除地址</p>
				</div>
			</div>
		</div>

		<div class="bottom-button" onclick="window.location.href='${ctx}/mytest/myd'">保存</div>

		<script type="text/javascript">
			$("#city-picker").cityPicker({
		    	title: "请选择收货地址"
		  	});
		</script>
	</body>
</html>