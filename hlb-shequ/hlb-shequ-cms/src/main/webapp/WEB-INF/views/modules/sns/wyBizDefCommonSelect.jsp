<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用业务功能定义管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyBizDefCommon/">通用业务功能定义列表</a></li>
		<shiro:hasPermission name="sns:wyBizDefCommon:edit"><li><a href="${ctx}/sns/wyBizDefCommon/form">通用业务功能定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyBizDefCommon" action="${ctx}/sns/wyBizDefCommon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyBizDefCommon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBizDefCommon">
			<tr>
				<td><a href="${ctx}/sns/wyBizDefCommon/form?id=${wyBizDefCommon.id}">
					${wyBizDefCommon.name}
				</a></td>
				<td>
					<fmt:formatDate value="${wyBizDefCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wyBizDefCommon.remarks}
				</td>
				<shiro:hasPermission name="sns:wyBizDefCommon:edit"><td>
    				<a href="${ctx}/sns/wyBizDefCommon/form?id=${wyBizDefCommon.id}">修改</a>
					<a href="${ctx}/sns/wyBizDefCommon/delete?id=${wyBizDefCommon.id}" onclick="return confirmx('确认要删除该通用业务功能定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>