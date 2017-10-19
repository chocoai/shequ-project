<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程实例对应的关系表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyActRelation/">流程实例对应的关系表列表</a></li>
		<shiro:hasPermission name="sns:wyActRelation:edit"><li><a href="${ctx}/sns/wyActRelation/form">流程实例对应的关系表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyActRelation" action="${ctx}/sns/wyActRelation/" method="post" class="breadcrumb form-search">
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
				<th>更新时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyActRelation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyActRelation">
			<tr>
				<td></td>
				<td></td>
				<td><a href="${ctx}/sns/wyActRelation/form?id=${wyActRelation.id}">
					<fmt:formatDate value="${wyActRelation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wyActRelation.remarks}
				</td>
				<shiro:hasPermission name="sns:wyActRelation:edit"><td>
    				<a href="${ctx}/sns/wyActRelation/form?id=${wyActRelation.id}">修改</a>
					<a href="${ctx}/sns/wyActRelation/delete?id=${wyActRelation.id}" onclick="return confirmx('确认要删除该流程实例对应的关系表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>