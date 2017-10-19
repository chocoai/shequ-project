<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信图文管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxNews/">微信图文列表</a></li>
		<shiro:hasPermission name="weixin:wxNews:edit"><li><a href="${ctx}/weixin/wxNews/form">微信图文添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxNews" action="${ctx}/weixin/wxNews/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号：</label>
				<form:select path="account.id" class="input-medium" onchange="$('#searchForm').submit();">
					<option value="">请选择</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>图文标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="60" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>关键字</th>
				<th>文章简介</th>
				<th>点击数量</th>
				<th>作者</th>				
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="weixin:wxNews:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxNews">
			<tr>
				<td><a href="${ctx}/weixin/wxNews/form?id=${wxNews.id}">
					${wxNews.title}
				</a></td>
				<td>
					${wxNews.keyword}
				</td>
				<td>
					${wxNews.description}
				</td>
				<td style="text-align: right;">
					${wxNews.click}
				</td>
				<td>
					${wxNews.writer}
				</td>				
				<td style="text-align: center;">
					<fmt:formatDate value="${wxNews.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxNews.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxNews:edit"><td style="text-align: center;">
    				<a href="${ctx}/weixin/wxNews/form?id=${wxNews.id}">修改</a>
					<a href="${ctx}/weixin/wxNews/delete?id=${wxNews.id}" onclick="return confirmx('确认要删除该微信图文吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>