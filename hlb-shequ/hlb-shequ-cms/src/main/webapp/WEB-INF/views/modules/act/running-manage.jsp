<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<title>管理运行中流程</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
    <link rel="stylesheet" href="${ctxStatic}/common/blueprint/screen.css" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="${ctxStatic}/common/blueprint/print.css" type="text/css" media="print"> 
	<!--[if lt IE 8]>
		<link rel="stylesheet" href="${ctxStatic}/common/blueprint/ie.css" type="text/css" media="screen, projection">
	<![endif]-->
    <link href="${ctxStatic}/jui/themes/redmond/jquery-ui-1.9.2.custom.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/style.css" type="text/css" rel="stylesheet"/>
	<!--[if IE]>
	<link rel="stylesheet" href="${ctxStatic}/common/style-ie.css" type="text/css" media="screen"/>
	<![endif]-->

    <script src="${ctxStatic}/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctxStatic}/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctxStatic}/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/activiti/workflow.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			// 跟踪
		    $('.trace').click(graphTrace);
		});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
	<div class="ui-widget">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				<strong>提示：</strong>${message}</p>
			</div>
		</div>
	</c:if>
	<table>
		<tr>
			<th>执行IDssss</th>
			<th>流程实例ID</th>
			<th>流程定义ID</th>
			<th>当前节点</th>
			<th>是否挂起</th>
			<th>操作</th>
		</tr>

		<c:forEach items="${page.list}" var="p">
		<c:set var="pdid" value="${p.processDefinitionId}" />
		<c:set var="activityId" value="${p.activityId}" />
		<tr>
			<td>${p.id }</td>
			<td>${p.processInstanceId}</td>
			<td>${p.processDefinitionId}</td>
			<td><a class="trace" href='#' pid="${p.id}" title="点击查看流程图">${p.id}</a></td>
			<td>${p.suspended}</td>
			<td>
				<c:if test="${p.suspended}">
					<a href="update/active/${p.processInstanceId}">激活</a>
				</c:if>
				<c:if test="${!p.suspended}">
					<a href="update/suspend/${p.processInstanceId}">挂起</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>
	<!-- 选择处理人 -->
	<div id="selectHandler" class="template"></div>
	

</body>
</html>
