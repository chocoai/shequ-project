<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信多图文管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxNewsMulti/">微信多图文列表</a></li>
		<shiro:hasPermission name="weixin:wxNewsMulti:edit"><li><a href="${ctx}/weixin/wxNewsMulti/form">微信多图文添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxNewsMulti" action="${ctx}/weixin/wxNewsMulti/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号：</label>
				<form:select path="account.id" class="input-medium" onchange="$('#searchForm').submit();">
					<option value="">请选择</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>关键字：</label>
				<form:input path="keywords" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>关键字</th>
				<th>图文标题</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="weixin:wxNewsMulti:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxNewsMulti">
			<tr>
				<td><a href="${ctx}/weixin/wxNewsMulti/form?id=${wxNewsMulti.id}">${wxNewsMulti.keywords}</a></td>
				<td>${wxNewsMulti.news}</td>
				<td style="text-align: center;"><a href="${ctx}/weixin/wxNewsMulti/form?id=${wxNewsMulti.id}">
					<fmt:formatDate value="${wxNewsMulti.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wxNewsMulti.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxNewsMulti:edit"><td style="text-align: center;">
    				<a href="${ctx}/weixin/wxNewsMulti/form?id=${wxNewsMulti.id}">修改</a>
					<a href="${ctx}/weixin/wxNewsMulti/delete?id=${wxNewsMulti.id}" onclick="return confirmx('确认要删除该微信多图文吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>