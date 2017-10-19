<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/appLog/">日志表列表</a></li>
		<shiro:hasPermission name="sys:appLog:edit"><li><a href="${ctx}/sys/appLog/form">日志表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appLog" action="${ctx}/sys/appLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日志类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>操作菜单：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>操作用户：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>操作时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>请求URI：</label>
				<form:input path="requestUri" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作菜单</th>
				<th>操作用户</th>
				<th>操作时间</th>
				<th>操作者IP</th>
				<th>请求URI</th>
				<th>提交方式</th>
				<shiro:hasPermission name="sys:appLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appLog">
			<tr>
				<td><a href="${ctx}/sys/appLog/form?id=${appLog.id}">
					${appLog.title}
				</a></td>
				<td>
					${appLog.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${appLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appLog.remoteAddr}
				</td>
				<td>
					${appLog.requestUri}
				</td>
				<td>
					${appLog.method}
				</td>
				<shiro:hasPermission name="sys:appLog:edit"><td>
    				<a href="${ctx}/sys/appLog/form?id=${appLog.id}">修改</a>
					<a href="${ctx}/sys/appLog/delete?id=${appLog.id}" onclick="return confirmx('确认要删除该日志表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>