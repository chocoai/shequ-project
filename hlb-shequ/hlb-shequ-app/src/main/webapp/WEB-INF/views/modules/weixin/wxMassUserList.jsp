<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发消息用户表管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMassUser/">群发消息用户表列表</a></li>
		<shiro:hasPermission name="weixin:wxMassUser:edit"><li><a href="${ctx}/weixin/wxMassUser/form">群发消息用户表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassUser" action="${ctx}/weixin/wxMassUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送给用户的openid：</label>
				<form:input path="openid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>发送的消息关联表：</label>
				<form:input path="massMsgId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="weixin:wxMassUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMassUser">
			<tr>
				<shiro:hasPermission name="weixin:wxMassUser:edit"><td>
    				<a href="${ctx}/weixin/wxMassUser/form?id=${wxMassUser.id}">修改</a>
					<a href="${ctx}/weixin/wxMassUser/delete?id=${wxMassUser.id}" onclick="return confirmx('确认要删除该群发消息用户表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>