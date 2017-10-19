<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>具体人员明细表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyActCandidateDetail/">具体人员明细表列表</a></li>
		<shiro:hasPermission name="sns:wyActCandidateDetail:edit"><li><a href="${ctx}/sns/wyActCandidateDetail/form">具体人员明细表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyActCandidateDetail" action="${ctx}/sns/wyActCandidateDetail/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="sns:wyActCandidateDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyActCandidateDetail">
			<tr>
				<td><a href="${ctx}/sns/wyActCandidateDetail/form?id=${wyActCandidateDetail.id}">
					<fmt:formatDate value="${wyActCandidateDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="sns:wyActCandidateDetail:edit"><td>
    				<a href="${ctx}/sns/wyActCandidateDetail/form?id=${wyActCandidateDetail.id}">修改</a>
					<a href="${ctx}/sns/wyActCandidateDetail/delete?id=${wyActCandidateDetail.id}" onclick="return confirmx('确认要删除该具体人员明细表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>