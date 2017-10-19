<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业维修预算工时明细管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyRepairBudgetLabor/">物业维修预算工时明细列表</a></li>
		<shiro:hasPermission name="sns:wyRepairBudgetLabor:edit"><li><a href="${ctx}/sns/wyRepairBudgetLabor/form">物业维修预算工时明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyRepairBudgetLabor" action="${ctx}/sns/wyRepairBudgetLabor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>姓名</th>
				<shiro:hasPermission name="sns:wyRepairBudgetLabor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyRepairBudgetLabor">
			<tr>
				<td><a href="${ctx}/sns/wyRepairBudgetLabor/form?id=${wyRepairBudgetLabor.id}">
					<fmt:formatDate value="${wyRepairBudgetLabor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wyRepairBudgetLabor.name}
				</td>
				<shiro:hasPermission name="sns:wyRepairBudgetLabor:edit"><td>
    				<a href="${ctx}/sns/wyRepairBudgetLabor/form?id=${wyRepairBudgetLabor.id}">修改</a>
					<a href="${ctx}/sns/wyRepairBudgetLabor/delete?id=${wyRepairBudgetLabor.id}" onclick="return confirmx('确认要删除该物业维修预算工时明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>