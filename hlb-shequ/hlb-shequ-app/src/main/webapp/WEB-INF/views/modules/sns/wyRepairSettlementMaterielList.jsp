<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业维修核算物料明细管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyRepairSettlementMateriel/">物业维修核算物料明细列表</a></li>
		<shiro:hasPermission name="sns:wyRepairSettlementMateriel:edit"><li><a href="${ctx}/sns/wyRepairSettlementMateriel/form">物业维修核算物料明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyRepairSettlementMateriel" action="${ctx}/sns/wyRepairSettlementMateriel/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="sns:wyRepairSettlementMateriel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyRepairSettlementMateriel">
			<tr>
				<td><a href="${ctx}/sns/wyRepairSettlementMateriel/form?id=${wyRepairSettlementMateriel.id}">
					<fmt:formatDate value="${wyRepairSettlementMateriel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="sns:wyRepairSettlementMateriel:edit"><td>
    				<a href="${ctx}/sns/wyRepairSettlementMateriel/form?id=${wyRepairSettlementMateriel.id}">修改</a>
					<a href="${ctx}/sns/wyRepairSettlementMateriel/delete?id=${wyRepairSettlementMateriel.id}" onclick="return confirmx('确认要删除该物业维修核算物料明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>