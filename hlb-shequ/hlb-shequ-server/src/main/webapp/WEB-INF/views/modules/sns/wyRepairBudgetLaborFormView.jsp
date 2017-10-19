<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>表单详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
<link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
</head>
<body ontouchstart class="page-bg">
	<div class="page-hd">
		<h1 class="page-hd-title">人力详情</h1>
		<p class="page-hd-desc">人力详情说明</p>
	</div>
	<div class="weui-form-preview">
		<div class="weui-form-preview-hd">
			<label class="weui-form-preview-label">工时费</label> 
			<em	class="weui-form-preview-value">¥ ${wyRepairBudgetLabor.count}</em>
		</div>
		<div class="weui-form-preview-bd">
			<p>
				<label class="weui-form-preview-label">姓名</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetLabor.name}</span>
			</p>
			<%-- <p>
				<label class="weui-form-preview-label">每小时单价</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetLabor.price}</span>
			</p>
			<p>
				<label class="weui-form-preview-label">花费小时数</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetLabor.spentHour}</span>
			</p> --%>
		</div>
		<div class="weui-form-preview-ft">
			<a class="weui-form-preview-btn weui-form-preview-btn-primary"	href="javascript:" onclick="javascript:history.go(-1)">返回</a>
		</div>
	</div>
</body>
</html>