<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>候选人表或者群组表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyActCandidate/">候选人表或者群组表列表</a></li>
		<shiro:hasPermission name="sns:wyActCandidate:edit"><li><a href="${ctx}/sns/wyActCandidate/form">候选人表或者群组表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyActCandidate" action="${ctx}/sns/wyActCandidate/" method="post" class="breadcrumb form-search">
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
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyActCandidate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyActCandidate">
			<tr>
				<td><a href="${ctx}/sns/wyActCandidate/form?id=${wyActCandidate.id}">
					<fmt:formatDate value="${wyActCandidate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wyActCandidate.remarks}
				</td>
				<shiro:hasPermission name="sns:wyActCandidate:edit"><td>
    				<a href="${ctx}/sns/wyActCandidate/form?id=${wyActCandidate.id}">修改</a>
					<a href="${ctx}/sns/wyActCandidate/delete?id=${wyActCandidate.id}" onclick="return confirmx('确认要删除该候选人表或者群组表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>