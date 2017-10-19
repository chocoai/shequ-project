<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信文本管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxText/">微信文本列表</a></li>
		<shiro:hasPermission name="weixin:wxText:edit"><li><a href="${ctx}/weixin/wxText/form">微信文本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxText" action="${ctx}/weixin/wxText/" method="post" class="breadcrumb form-search">
		<span>当前微信公众号名称为：<font color="red"><b>${wxText.account.wxname}</b></font>，微信号为：${wxText.account.wxnum}</span>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>关键字：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>回复内容</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="weixin:wxText:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxText">
			<tr>
				<td nowrap="nowrap"><a href="${ctx}/weixin/wxText/form?id=${wxText.id}">
					${wxText.keyword}
				</a></td>
				<td nowrap="nowrap">
					${wxText.content}
				</td>
				<td>
					<fmt:formatDate value="${wxText.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxText.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxText:edit"><td>
    				<a href="${ctx}/weixin/wxText/form?id=${wxText.id}">修改</a>
					<a href="${ctx}/weixin/wxText/delete?id=${wxText.id}" onclick="return confirmx('确认要删除该微信文本吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>