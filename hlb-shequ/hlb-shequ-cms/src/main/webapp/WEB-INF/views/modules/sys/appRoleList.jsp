<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#sysType").change(function(){				
				
				$("#searchForm").submit();
			});
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
		<li class="active"><a href="${ctx}/sys/appRole?sysType=${appRole.sysType}">角色模板列表</a></li>
		<shiro:hasPermission name="sys:appRole:edit"><li><a href="${ctx}/sys/appRole/form?sysType=${appRole.sysType}">角色模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appRole" action="${ctx}/sys/appRole/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>系统名称：</label>
				<form:select path="sysType" class="input-medium">
					<form:options items="${fns:getDictList('sys_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>角色名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>角色名称</th>
				<th>英文名称</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sys:appRole:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appRole">
			<tr>
				<td><a href="${ctx}/sys/appRole/form?id=${appRole.id}&sysType=${appRole.sysType}">
					${appRole.name}
				</a></td>
				<td>${appRole.enname}</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${appRole.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appRole.remarks}
				</td>
				<shiro:hasPermission name="sys:appRole:edit"><td style="text-align: center;">
    				<a href="${ctx}/sys/appRole/form?id=${appRole.id}&sysType=${appRole.sysType}">修改</a>
					<a href="${ctx}/sys/appRole/delete?id=${appRole.id}" onclick="return confirmx('确认要删除该角色模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>