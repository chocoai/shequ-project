<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业评论表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyEvaluate/">物业评论表列表</a></li>
		<shiro:hasPermission name="sns:wyEvaluate:edit"><li><a href="${ctx}/sns/wyEvaluate/form">物业评论表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyEvaluate" action="${ctx}/sns/wyEvaluate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>评价标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>评价标题</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyEvaluate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyEvaluate">
			<tr>
				<td><a href="${ctx}/sns/wyEvaluate/form?id=${wyEvaluate.id}">
					${wyEvaluate.title}
				</a></td>
				<td>
					<fmt:formatDate value="${wyEvaluate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyEvaluate:edit"><td>
    				<a href="${ctx}/sns/wyEvaluate/form?id=${wyEvaluate.id}">修改</a>
					<a href="${ctx}/sns/wyEvaluate/delete?id=${wyEvaluate.id}" onclick="return confirmx('确认要删除该物业评论表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>