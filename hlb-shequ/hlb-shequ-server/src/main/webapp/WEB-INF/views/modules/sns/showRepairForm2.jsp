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
<script src="${ctxStatic}/js/repairForm.js"></script>
<script src="${ctxStatic}/js/layer.js"></script>
<script src="${ctxStatic}/js/jquery.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}/";
</script>
</head>
<body ontouchstart class="page-bg">
	<div class="page-hd">
		<h1 class="page-hd-title">物料详情</h1>
		<p class="page-hd-desc">物料详情列表</p>
	</div>
	<div class="weui-form-preview">
		<div class="weui-form-preview-hd">
			<label class="weui-form-preview-label">合价金额</label> 
			<em	class="weui-form-preview-value">¥ ${wyRepairBudgetDetail.count}</em>
		</div>
		<div class="weui-form-preview-bd">
			<p>
				<label class="weui-form-preview-label">物料编号</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetDetail.materialNo}</span>
			</p>
			<p>
				<label class="weui-form-preview-label">物料名称</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetDetail.materialName}</span>
			</p>
			<p>
				<label class="weui-form-preview-label">规格型号</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetDetail.specificationModel}</span>
			</p>
			<p>
				<label class="weui-form-preview-label">价格</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetDetail.price} 元</span>
			</p>
			<p>
				<label class="weui-form-preview-label">数量</label> 
				<span class="weui-form-preview-value">${wyRepairBudgetDetail.num} 个</span>
			</p>
		</div>
		<div class="weui-form-preview-ft">
			<a class="weui-form-preview-btn weui-form-preview-btn-primary"	href="javascript:" onclick="javascript:history.go(-1)">返回</a>
		</div>
	</div>
</body>
</html>