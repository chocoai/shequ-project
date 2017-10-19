<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信公众号粉丝分组管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMemberGroup/">微信公众号粉丝分组列表</a></li>
		<shiro:hasPermission name="weixin:wxMemberGroup:edit"><li><a href="${ctx}/weixin/wxMemberGroup/form">微信公众号粉丝分组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMemberGroup" action="${ctx}/weixin/wxMemberGroup/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="weixin:wxMemberGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMemberGroup">
			<tr>
				<td><a href="${ctx}/weixin/wxMemberGroup/form?id=${wxMemberGroup.id}">
					<fmt:formatDate value="${wxMemberGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="weixin:wxMemberGroup:edit"><td>
    				<a href="${ctx}/weixin/wxMemberGroup/form?id=${wxMemberGroup.id}">修改</a>
					<a href="${ctx}/weixin/wxMemberGroup/delete?id=${wxMemberGroup.id}" onclick="return confirmx('确认要删除该微信公众号粉丝分组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>