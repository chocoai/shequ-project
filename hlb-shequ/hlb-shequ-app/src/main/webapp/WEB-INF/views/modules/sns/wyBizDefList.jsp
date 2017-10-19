<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务功能定义管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyBizDef/">业务功能定义列表</a></li>
		<shiro:hasPermission name="sns:wyBizDef:edit"><li><a href="${ctx}/sns/wyBizDef/form">业务功能定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyBizDef" action="${ctx}/sns/wyBizDef/" method="post" class="breadcrumb form-search">
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
				<th>业务key</th>
				<th>使用机构</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyBizDef:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBizDef">
			<tr>
				<td><a href="${ctx}/sns/wyBizDef/form?id=${wyBizDef.id}">
					${wyBizDef.name}
				</a></td>
				<td>${wyBizDef.key}</td>
				<td>${wyBizDef.groupId}</td>
				<td>
					<fmt:formatDate value="${wyBizDef.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wyBizDef.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wyBizDef.remarks}
				</td>
				<shiro:hasPermission name="sns:wyBizDef:edit">
				<td>
    				<a href="${ctx}/sns/wyBizDef/form?id=${wyBizDef.id}">修改</a>
					<a href="${ctx}/sns/wyBizDef/delete?id=${wyBizDef.id}" onclick="return confirmx('确认要删除该业务功能定义吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>