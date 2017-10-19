<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房屋租售管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyHouseRent/">房屋租售列表</a></li>
		<shiro:hasPermission name="sns:wyHouseRent:edit"><li><a href="${ctx}/sns/wyHouseRent/form">房屋租售添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyHouseRent" action="${ctx}/sns/wyHouseRent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发布标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发布标题</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyHouseRent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyHouseRent">
			<tr>
				<td><a href="${ctx}/sns/wyHouseRent/form?id=${wyHouseRent.id}">
					${wyHouseRent.title}
				</a></td>
				<td>
					<fmt:formatDate value="${wyHouseRent.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyHouseRent:edit"><td>
    				<a href="${ctx}/sns/wyHouseRent/form?id=${wyHouseRent.id}">修改</a>
					<a href="${ctx}/sns/wyHouseRent/delete?id=${wyHouseRent.id}" onclick="return confirmx('确认要删除该房屋租售吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>