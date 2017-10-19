<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业报修管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/blueprint/screen.css" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="${ctxStatic}/common/blueprint/print.css" type="text/css" media="print">
	<link href="${ctxStatic}/jui/themes/redmond/jquery-ui-1.9.2.custom.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/style.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctxStatic}/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctxStatic}/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/activiti/workflow-bizHandler.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/activiti/workflow-graphTrace.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/activiti/workflow-actForm.js" type="text/javascript"></script>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var ctxRoot = "${ctxRoot}";
		$(document).ready(function() {
			
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a style="text-decoration:none" href="${ctx}/workflow/processinstance/queryProcessDefinition">流程列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/workflow/processinstance/queryProcessDefinition" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>流程名称：</label>
				<form:input path="procDefName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>流程key：</label>
				<form:input path="procDefKey" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>流程定义ID：</label>
				<form:input path="procDefId" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程名称</th>			
				<th>流程key</th>
				<th>流程版本</th>			
				<th>流程定义id</th>
				<th>流程部署id</th>
				<th>操作</th>	
			</tr>			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pd">
			<tr>
				<td>${pd.name}</td>			
				<td>${pd.key}</td>	
				<td>${pd.version}</td>			
				<td>${pd.id}</td>
				<td>${pd.deploymentId}</td>
				<shiro:hasPermission name="act:process:edit">
				<td>
    				<a onclick="setAssign('${pd.id}','设置待办人员')" href='#' pid="${pd.id}" title="点击设置待办人员">设置待办人员</a>&nbsp;&nbsp;
    				<a onclick="setHandler('${pd.id}','设置处理类')" href='#' pid="${pd.id}" title="设置处理类">设置处理类</a>&nbsp;&nbsp;
    				<a onclick="setActForm('${pd.id}','设置表单')" href='#' pid="${pd.id}" title="设置表单">设置表单</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>
	<!-- 选择处理人 -->
	<div id="selectHandler" class="template"></div>
	<script type="text/javascript">
		
	function setAssign(procDefinitionId,titleName){	
 		var src = "${ctx}/sns/wyActCandidate/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
		$.layer({
		  type: 2,
		  shade: [0.8, '#393D49'],
		  fix: false,
		  title: titleName,
		  maxmin: true,
		  iframe: {src : src},
		  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
		  close: function(index){
		   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
	
	function setHandler(procDefinitionId,titleName){
 		var src = "${ctx}/sns/wyActHandler/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
		$.layer({
		  type: 2,
		  shade: [0.8, '#393D49'],
		  fix: false,
		  title: titleName,
		  maxmin: true,
		  iframe: {src : src},
		  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
		  close: function(index){
		   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
	
	function setActForm(procDefinitionId,titleName){	
 		var src = "${ctx}/sns/wyActForm/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
		$.layer({
		  type: 2,
		  shade: [0.8, '#393D49'],
		  fix: false,
		  title: titleName,
		  maxmin: true,
		  iframe: {src : src},
		  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
		  close: function(index){
		   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
		
	</script>
	
	
</body>
</html>