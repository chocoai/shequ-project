<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页菜单管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyMenus/">首页菜单列表</a></li>
		<shiro:hasPermission name="sns:wyMenus:edit"><li><a href="${ctx}/sns/wyMenus/form">首页菜单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyMenus" action="${ctx}/sns/wyMenus/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>父级编号</th>
				<th>类型</th>
				<th>菜单名称</th>
				<th>菜单链接</th>
				<th>菜单图标</th>
				<th>是否显示</th>
				<th>背景颜色</th>
				<shiro:hasPermission name="sns:wyMenus:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyMenus">
			<tr>
				<td><a>${wyMenus.sort}</a></td>
				<td><a>${wyMenus.parentids}</a></td>
				<c:if test="${wyMenus.type == 0}">
				<td><a>首页菜单</a></td>
				</c:if>
				<c:if test="${wyMenus.type == 1}">
				<td><a>个人中心菜单</a></td>
				</c:if>
				<td><a>${wyMenus.name}</a></td>
				<%-- <td><a>${wyMenus.href}</a></td> --%>
				<td><a>${wyMenus.href}</a></td>
				<td><img src="${wyMenus.icon}" style="width:39px"></td>			
				
				<c:if test="${wyMenus.isshow == 1}">
				<td><a>是</a></td>
				</c:if>
				<c:if test="${wyMenus.isshow == 0}">
				<td><a>否</a></td>
				</c:if>
				<td><a style="background-color: '#${wyMenus.backgroundColor}'"></a></td>
				<shiro:hasPermission name="sns:wyMenus:edit"><td>
    				<a href="${ctx}/sns/wyMenus/form?id=${wyMenus.id}">修改</a>
					<a href="${ctx}/sns/wyMenus/delete?id=${wyMenus.id}" onclick="return confirmx('确认要删除该首页菜单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>