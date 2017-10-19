<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用平台用户管理</title>
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
		<li class="active"><a href="${ctx}/sys/appUser/">应用平台用户列表</a></li>
		<shiro:hasPermission name="sys:appUser:edit"><li><a href="${ctx}/sys/appUser/form">应用平台用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appUser" action="${ctx}/sys/appUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名,姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名,姓名</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:appUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appUser">
			<tr>
				<td><a href="${ctx}/sys/appUser/form?id=${appUser.id}">
					${appUser.name}
				</a></td>
				<td style="text-align: center;">
					<fmt:formatDate value="${appUser.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appUser.remarks}
				</td>
				<shiro:hasPermission name="sys:appUser:edit"><td style="text-align: center;">
    				<a href="${ctx}/sys/appUser/form?id=${appUser.id}">修改</a>
					<a href="${ctx}/sys/appUser/delete?id=${appUser.id}" onclick="return confirmx('确认要删除该应用平台用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>