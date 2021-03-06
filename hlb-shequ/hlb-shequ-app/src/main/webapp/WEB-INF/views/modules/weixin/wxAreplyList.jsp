<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动回复管理</title>
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
		function setDefaultAreply(id){
			$.get("${ctx}/weixin/wxAreply/setDefaultAreply" ,"id=" + id, function(data) {
				if(data=="true"){
					location.href="${ctx}/weixin/wxAreply/"; 
				}
		    });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxAreply/">自动回复列表</a></li>
		<shiro:hasPermission name="weixin:wxAreply:edit"><li><a href="${ctx}/weixin/wxAreply/form">自动回复添加</a></li></shiro:hasPermission>
	</ul>	
	<form:form id="searchForm" modelAttribute="wxAreply" action="${ctx}/weixin/wxAreply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号：</label>
				<form:select path="account.id" class="input-medium" onchange="$('#searchForm').submit();">
					<option value="">所有公众号</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
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
				<th>内容</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="weixin:wxAreply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAreply" varStatus="status">
			<tr>				
				<td nowrap="nowrap"><a href="${ctx}/weixin/wxAreply/form?id=${wxAreply.id}">
					${wxAreply.keyword}
				</a></td>
				<td nowrap="nowrap">
					${wxAreply.content}
				</td>
				<td>
					<fmt:formatDate value="${wxAreply.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td nowrap="nowrap">
					${wxAreply.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxAreply:edit"><td>
    				<a href="${ctx}/weixin/wxAreply/form?id=${wxAreply.id}">修改</a>
					<a href="${ctx}/weixin/wxAreply/delete?id=${wxAreply.id}" onclick="return confirmx('确认要删除该自动回复吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>