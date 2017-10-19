<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业投诉表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyComplain/">物业投诉表列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyComplain:edit"><li><a href="${ctx}/sns/wyComplain/form">物业投诉表添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyComplain" action="${ctx}/sns/wyComplain/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>投诉姓名：</label>
				<form:input path="applyname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>投诉内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>投诉姓名</th>
				<th>联系电话</th>
				<th>投诉内容</th>
				<th>详细描述</th>
				<th>客户名称</th>
				<th>手机号码</th>
				<shiro:hasPermission name="sns:wyComplain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyComplain">
			<tr>
				<td>${wyComplain.applyname}</td>
				<td>${wyComplain.phone}</td>
				<td>${wyComplain.content}</td>
				<td>${wyComplain.contentdetail}</td>
				<td>${wyComplain.member.memberName}</td>
				<td>${wyComplain.member.mobile}</td>
				<shiro:hasPermission name="sns:wyComplain:edit"><td>
					<a href="${ctx}/sns/wyComplain/delete?id=${wyComplain.id}" onclick="return confirmx('确认要删除该物业投诉表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>