<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>表单详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/repairForm.css">
	<script src="${ctxStatic}/js/repairForm.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	<script src="${ctxStatic}/js/jquery.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
	</script>
</head>
<body>
	<div class="title">表单详情</div>	
	<input id="repairId" type="hidden" name="" value="${repairId}"/>
	<div class="content">
		<span class="name">物料编号</span>
		<input class="val val1" value="" placeholder="请输入内容">
	</div>
	<div class="content">
		<span class="name">物料名称</span>
		<input class="val val2" value="" placeholder="请输入内容">
	</div>
	<div class="content">
		<span class="name">规格型号</span>
		<input class="val val3" value="" placeholder="请输入内容">
	</div>
	<div class="content">
		<span class="name">价格</span>
		<input class="val val4" value="" placeholder="请输入内容" onblur="count()">
	</div>
	<div class="content">
		<span class="name">数量</span>
		<input class="val val5" value="" placeholder="请输入内容" onblur="count()">
	</div>
	<div class="content">
		<span class="name">合计</span>
		<span class="val val6"></span>
		<!-- <input class="val val6" value="" disabled="true"> -->
	</div>
	<div class="sure" onclick="submit()">确定</div>
</body>