<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程引用管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyReBizAct/">流程引用列表</a></li>
		<shiro:hasPermission name="sns:wyReBizAct:edit"><li><a href="${ctx}/sns/wyReBizAct/form">流程引用添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyReBizAct" action="${ctx}/sns/wyReBizAct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">			
			<li>
				<label>流程名称：</label>
				<form:input path="wyActDef.name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li>
				<label>业务名称：</label>
				<form:input path="wyBizDef.name" htmlEscape="false" maxlength="32" class="input-medium"/>
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
				<th>流程名称</th>
				<th>流程key</th>
				<c:if test="${user.isSuperAdmin}">
				<th>流程定义ID</th>	
				</c:if>			
				<shiro:hasPermission name="sns:wyReBizAct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="wyReBizAct">
			<tr>
				<td><a href="${ctx}/sns/wyReBizAct/form?id=${wyReBizAct.id}">${wyReBizAct.wyBizDef.name}</a></td>
				<td>${wyReBizAct.wyBizDef.key}</td>	
				<td>${wyReBizAct.wyActDef.name}</td>
				<td>${wyReBizAct.wyActDef.key}</td>
				<c:if test="${user.isSuperAdmin}">
				<td>${wyReBizAct.wyActDef.procDefId}</td>
				</c:if>							
				<shiro:hasPermission name="sns:wyReBizAct:edit">
				<td>
    				<a href="${ctx}/sns/wyReBizAct/form?id=${wyReBizAct.id}">修改</a>
					<a href="${ctx}/sns/wyReBizAct/delete?id=${wyReBizAct.id}" onclick="return confirmx('确认要删除该流程引用吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>