<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作流审批记录表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyApprove/">工作流审批记录表列表</a></li>
		<shiro:hasPermission name="sns:wyApprove:edit"><li><a href="${ctx}/sns/wyApprove/form">工作流审批记录表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyApprove" action="${ctx}/sns/wyApprove/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>审批名称：</label>
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
				<th>审批名称</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyApprove:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyApprove">
			<tr>
				<td><a href="${ctx}/sns/wyApprove/form?id=${wyApprove.id}">
					${wyApprove.name}
				</a></td>
				<td>
					<fmt:formatDate value="${wyApprove.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyApprove:edit"><td>
    				<a href="${ctx}/sns/wyApprove/form?id=${wyApprove.id}">修改</a>
					<a href="${ctx}/sns/wyApprove/delete?id=${wyApprove.id}" onclick="return confirmx('确认要删除该工作流审批记录表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>