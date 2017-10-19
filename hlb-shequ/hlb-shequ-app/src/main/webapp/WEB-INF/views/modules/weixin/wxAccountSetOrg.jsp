<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信管理管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxAccount/">微信管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxAccount" action="${ctx}/weixin/wxAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号名称：</label>
				<form:input path="wxname" id="wxname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>			
			<li class="clearfix"></li>
		</ul><br/>	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>公众号名称</th>
				<th>微信号</th>
				<th>类型</th>
				<th>公众号原始ID</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAccount" varStatus="status">		
			<tr>
				<td>
					<input type="radio" name="selectId" value="${wxAccount.id}" data-value-name="${wxAccount.wxname}"/>
				</td>
				<td>
					${wxAccount.wxname}
				</td>
				<td>${wxAccount.wxnum}</td>
				<td>${fns:getDictLabel(wxAccount.typeId, 'weixin_account_type', '')}</td>
				<td>${wxAccount.originId}</td>				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>