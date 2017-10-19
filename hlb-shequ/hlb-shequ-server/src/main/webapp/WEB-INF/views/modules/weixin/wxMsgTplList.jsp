<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>具体消息模板管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMsgTpl/">具体消息模板列表</a></li>
		<shiro:hasPermission name="weixin:wxMsgTpl:edit"><li><a href="${ctx}/weixin/wxMsgTpl/form">具体消息模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMsgTpl" action="${ctx}/weixin/wxMsgTpl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="weixin:wxMsgTpl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMsgTpl">
			<tr>
				<td><a href="${ctx}/weixin/wxMsgTpl/form?id=${wxMsgTpl.id}">
					${wxMsgTpl.name}
				</a></td>
				<td>
					<fmt:formatDate value="${wxMsgTpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxMsgTpl.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxMsgTpl:edit"><td>
    				<a href="${ctx}/weixin/wxMsgTpl/form?id=${wxMsgTpl.id}">修改</a>
					<a href="${ctx}/weixin/wxMsgTpl/delete?id=${wxMsgTpl.id}" onclick="return confirmx('确认要删除该具体消息模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>