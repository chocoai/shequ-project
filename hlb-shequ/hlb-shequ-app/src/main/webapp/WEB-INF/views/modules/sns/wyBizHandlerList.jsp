<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务处理器管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyBizHandler/">业务处理器列表</a></li>
		<shiro:hasPermission name="sns:wyBizHandler:edit"><li><a href="${ctx}/sns/wyBizHandler/form">业务处理器添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyBizHandler" action="${ctx}/sns/wyBizHandler/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>处理器名称：</label>
				<form:input path="handlerName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>处理器名称</th>
				<th>实例名</th>
				<th>处理类的包名路径</th>
				<th>处理方法</th>
				<th>类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sns:wyBizHandler:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBizHandler">
			<tr>
				<td>
					<a href="${ctx}/sns/wyBizHandler/form?id=${wyBizHandler.id}">
						${wyBizHandler.handlerName}
					</a>
				</td>
				<td>
					${wyBizHandler.handlerInstance}
				</td>
				<td>
					${wyBizHandler.handlerClass}
				</td>
				<td>
					${wyBizHandler.handlerMethod}
				</td>
				<td>
					${fns:getDictLabel(wyBizHandler.handlerType, 'task_biz_handler', '')}					
				</td>
				<td><a href="${ctx}/sns/wyBizHandler/form?id=${wyBizHandler.id}">
					<fmt:formatDate value="${wyBizHandler.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wyBizHandler.remarks}
				</td>
				<shiro:hasPermission name="sns:wyBizHandler:edit"><td>
    				<a href="${ctx}/sns/wyBizHandler/form?id=${wyBizHandler.id}">修改</a>
					<a href="${ctx}/sns/wyBizHandler/delete?id=${wyBizHandler.id}" onclick="return confirmx('确认要删除该业务处理器吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>