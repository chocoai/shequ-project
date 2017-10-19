<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用实例消息管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMassMsgCommon/">通用消息列表</a></li>
		<shiro:hasPermission name="weixin:wxMassMsgCommon:edit"><li><a href="${ctx}/weixin/wxMassMsgCommon/form">通用消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassMsgCommon" action="${ctx}/weixin/wxMassMsgCommon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息标题：</label>
				<form:input path="firstValue" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>消息标题</th>
				<th>消息备注</th>
				<th>更新时间</th>				
				<shiro:hasPermission name="weixin:wxMassMsgCommon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMassMsgCommon">
			<tr>
				<td><a href="${ctx}/weixin/wxMassMsgCommon/form?id=${wxMassMsgCommon.id}&step=2">
					${wxMassMsgCommon.firstValue}
				</a></td>
				<td>${wxMassMsgCommon.remarkValue}</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${wxMassMsgCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="weixin:wxMassMsgCommon:edit"><td style="text-align: center;">
    				<a href="${ctx}/weixin/wxMassMsgCommon/form?id=${wxMassMsgCommon.id}&step=2">修改</a>
					<a href="${ctx}/weixin/wxMassMsgCommon/delete?id=${wxMassMsgCommon.id}" onclick="return confirmx('确认要删除该通用实例消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>