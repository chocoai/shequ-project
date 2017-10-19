<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<title>管理收货地址</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<link rel="stylesheet" href="${ctxStatic}/css/deliveryAddress.css">
		<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
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
					<p>管理收货地址</p>
				</div>
			</a>
		</div>

		<div class="address-list">
			<div class="address-content">
				<div class="address-memberinfo">
					<div class="name">吴欣超</div>
					<div class="mobile">13580750428</div>
				</div>
				<div class="detail-address">
					广东省深圳市宝安区西乡街道深圳市宝安区西乡流塘新村三巷10号1106
				</div>
				<div class="address-operate">
					<div class="default-address">
						<div class="img">
							<img src="${ctxStatic}/image/默认地址选中-01.png">
						</div>
						<div class="words choose">默认地址</div>
					</div>
					<div class="del-address">
						<div class="img">
							<img src="${ctxStatic}/image/删除.png">
						</div>
						<div class="words">删除</div>
					</div>
					<div class="edit-address" onclick="window.location.href='${ctx}/mytest/editd'">
						<div class="img">
							<img src="${ctxStatic}/image/编辑.png">
						</div>
						<div class="words">编辑</div>
					</div>
				</div>
			</div>

			<div class="address-content">
				<div class="address-memberinfo">
					<div class="name">吴欣超</div>
					<div class="mobile">13580750428</div>
				</div>
				<div class="detail-address">
					广东省深圳市宝安区西乡街道深圳市宝安区西乡流塘新村三巷10号1106
				</div>
				<div class="address-operate">
					<div class="default-address">
						<div class="img">
							<img src="${ctxStatic}/image/默认地址-没有选中.png">
						</div>
						<div class="words">默认地址</div>
					</div>
					<div class="del-address">
						<div class="img">
							<img src="${ctxStatic}/image/删除.png">
						</div>
						<div class="words">删除</div>
					</div>
					<div class="edit-address" onclick="window.location.href='${ctx}/mytest/editd'">
						<div class="img">
							<img src="${ctxStatic}/image/编辑.png">
						</div>
						<div class="words">编辑</div>
					</div>
				</div>
			</div>
		</div>

		<div class="add-button" onclick="window.location.href='${ctx}/mytest/addd'">添加新地址</div>
	</body>
</html>