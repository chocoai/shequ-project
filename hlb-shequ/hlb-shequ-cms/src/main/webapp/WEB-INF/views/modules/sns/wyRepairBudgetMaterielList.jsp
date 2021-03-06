<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业维修预算物料明细管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyRepairBudgetMateriel/">物业维修预算物料明细列表</a></li>
		<shiro:hasPermission name="sns:wyRepairBudgetMateriel:edit"><li><a href="${ctx}/sns/wyRepairBudgetMateriel/form">物业维修预算物料明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyRepairBudgetMateriel" action="${ctx}/sns/wyRepairBudgetMateriel/" method="post" class="breadcrumb form-search">
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
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyRepairBudgetMateriel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyRepairBudgetMateriel">
			<tr>
				<td><a href="${ctx}/sns/wyRepairBudgetMateriel/form?id=${wyRepairBudgetMateriel.id}">
					<fmt:formatDate value="${wyRepairBudgetMateriel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="sns:wyRepairBudgetMateriel:edit"><td>
    				<a href="${ctx}/sns/wyRepairBudgetMateriel/form?id=${wyRepairBudgetMateriel.id}">修改</a>
					<a href="${ctx}/sns/wyRepairBudgetMateriel/delete?id=${wyRepairBudgetMateriel.id}" onclick="return confirmx('确认要删除该物业维修预算物料明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>