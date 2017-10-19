<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>社区公告</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
	<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
	<style type="text/css">
		.weui-cell__ft{
		    font-size: 12px;
		}
	</style>
</head>
<body>
	<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__ft1">
			</div>
			<div class="weui-cell__bd">
				<p>社区公告</p>
			</div>
		</a>	
	</div>

	<div class="weui-cells">
		<c:forEach items="${wmms}" var="wmms">
			<a class="weui-cell weui-cell_access" href="${wmms.url}">
				<div class="weui-cell__bd">
					<p>${wmms.title}</p>
				</div>
				<div class="weui-cell__ft">${fns:getDate1(wmms.createDate)}</div>
			</a>
		</c:forEach>
	</div>
</body>
</html>