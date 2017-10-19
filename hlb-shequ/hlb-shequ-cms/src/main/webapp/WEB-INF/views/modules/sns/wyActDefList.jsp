<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作流程定义表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyActDef/">工作流程定义表列表</a></li>
		<shiro:hasPermission name="sns:wyActDef:edit"><li><a href="${ctx}/sns/wyActDef/form">工作流程定义表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyActDef" action="${ctx}/sns/wyActDef/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>流程名称：</label>
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
				<th>流程名称</th>
				<th>流程key</th>
				<th>流程定义id</th>
				<th>使用机构</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyActDef:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyActDef">
			<tr>
				<td><a href="${ctx}/sns/wyActDef/form?id=${wyActDef.id}">
					${wyActDef.name}
				</a></td>
				<td>${wyActDef.key}</td>
				<td>${wyActDef.procDefId}</td>
				<td>${wyActDef.groupId}</td>
				<td>
					<fmt:formatDate value="${wyActDef.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wyActDef.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wyActDef.remarks}
				</td>
				<shiro:hasPermission name="sns:wyActDef:edit"><td>
    				<a href="${ctx}/sns/wyActDef/form?id=${wyActDef.id}">修改</a>
					<a href="${ctx}/sns/wyActDef/delete?id=${wyActDef.id}" onclick="return confirmx('确认要删除该工作流程定义表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>