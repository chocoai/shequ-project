<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>便民服务管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyConvenienceService/">便民服务列表</a></li>
		<shiro:hasPermission name="sns:wyConvenienceService:edit"><li><a href="${ctx}/sns/wyConvenienceService/form">便民服务创建</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyConvenienceService" action="${ctx}/sns/wyConvenienceService/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>编号</td>
				<td>创建时间</td>
				<td>来源</td>
				<td>组织机构</td>
				<shiro:hasPermission name="sns:wyConvenienceService:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyConvenienceService">
			<tr>
				<td><a>${wyConvenienceService.serviceId}</a></td>
				<td><fmt:formatDate value="${wyConvenienceService.createTime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td><a>${wyConvenienceService.source}</a></td>
				<td><a>${wyConvenienceService.groupId}</a></td>
				<shiro:hasPermission name="sns:wyConvenienceService:edit"><td>
    				<a href="${ctx}/sns/wyConvenienceService/form?serviceId=${wyConvenienceService.serviceId}">修改</a>
					<a href="${ctx}/sns/wyConvenienceService/delete?serviceId=${wyConvenienceService.serviceId}" onclick="return confirmx('确认要删除该便民服务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>