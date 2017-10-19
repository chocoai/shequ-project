<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>好邻邦</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/shequ-server.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min1.css">
		<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/selectSource.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
		</script>

		<style>
			body{margin:0;overflow-x:hidden;padding:0;}
		</style>
	</head>
	<body>
		<div class="shequ-title_top">
			<div class="shequ-title_left add">
				<img src="${ctxStatic}/image/添加1.png">
				<span>添加</span>
			</div>
			<div class="shequ-title_middle">物业名称</div>
			<!-- <div class="shequ-title_right" onclick="window.open('${ctx}/index','_self')">
				<img src="${ctxStatic}/image/跳过.png">
				<span>跳过</span>
			</div> -->
		</div>

		<form class="building-info">
			<div class="shequ-content">
			<c:forEach items="${urlmapList}" var="list">
				<div class="shequ-content_list">
					<div class="shequ-content_list_decorate"></div>
					<input type='hidden' value='${list.id}' name='id'></input>
					${list.urlname}
					<img src="${ctxStatic}/image/房屋.png">
				</div>
			</c:forEach>
				<!-- <div class="shequ-content_list">
					<div class="shequ-content_list_decorate"></div>
					<input type='hidden' value='' name='buildingId'></input>
					凭证导入范例物业
					<img src="${ctxStatic}/image/房屋.png">
				</div> -->
			</div>
		</form>

		<div class="shequ-bottom_submit submit" style="margin-left: 37.5%;margin-top: 35px;">
			查询更新
		</div>

		<div class="shequ-popup" style="display: block;">
			<div class="weui-search-bar" id="searchBar" style="height: 44px;position: fixed; width: 100%; z-index: 999;">
				<div class="weui-search-bar__form" style="margin: 0;">
					<div class="weui-search-bar__box">
						<i class="weui-icon-search"></i>
						<input type="search" class="weui-search-bar__input" id="keyword" placeholder="物业公司搜索" required="">
						<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
					</div>
					<label class="weui-search-bar__label" id="searchText">
						<i class="weui-icon-search"></i>
						<span style="line-height: 28px;">物业公司搜索</span>
					</label>
				</div>
				<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
			</div>
			<div class="show-building" style="margin-top: 50px; width: 100%">
					

			</div>
		</div>
		<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
    	<script src="${ctxStatic}/js/jquery.min.js"></script>
	</body>
</html>