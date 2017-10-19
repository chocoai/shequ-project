<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>注册表管理</title>
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
		<li class="active"><a href="${ctx}/sys/hlbregister/">注册表列表</a></li>
		<shiro:hasPermission name="sys:hlbregister:edit"><li><a href="${ctx}/sys/hlbregister/form">注册表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hlbregister" action="${ctx}/sys/hlbregister/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="sys:hlbregister:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hlbregister">
			<tr>
				<shiro:hasPermission name="sys:hlbregister:edit"><td>
    				<a href="${ctx}/sys/hlbregister/form?id=${hlbregister.id}">修改</a>
					<a href="${ctx}/sys/hlbregister/delete?id=${hlbregister.id}" onclick="return confirmx('确认要删除该注册表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>