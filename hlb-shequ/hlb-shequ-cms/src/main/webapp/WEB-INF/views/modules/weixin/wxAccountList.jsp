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
        
		function addWeixin(typeId){
	        location.href="${ctx}/weixin/wxAccount/form?gid=${wxAccount.gid}&typeId=" + typeId; 
	    }
				
		function wxApiView(id){
			location.href="${ctx}/weixin/wxAccount/wxApiView?id=" + id; 
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxAccount/list?gid=${wxAccount.gid}">微信管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxAccount" action="${ctx}/weixin/wxAccount/list?gid=${wxAccount.gid}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号名称：</label>
				<form:input path="wxname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${type == 'g'}">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加服务号" onclick="addWeixin(2)"/></li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加企业号" onclick="addWeixin(3)"/></li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加订阅号" onclick="addWeixin(1)"/></li>
				<li class="clearfix"></li>
			</c:if>
		</ul><br/>	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构名称</th>
				<th>公众号名称</th>
				<th>微信号</th>
				<th>类型</th>
				<th>appid</th>
				<th>公众号原始ID</th>
				<shiro:hasPermission name="weixin:wxAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAccount" varStatus="status">		
			<tr>
				<td>${wxAccount.group.name}</td>				
				<td><a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">
					${wxAccount.wxname}
				</a></td>
				<td>${wxAccount.wxnum}</td>
				<td style="text-align:center;">${fns:getDictLabel(wxAccount.typeId, 'weixin_account_type', '')}</td>
				<td>${wxAccount.appid}</td>
				<td>${wxAccount.originId}</td>
				<shiro:hasPermission name="weixin:wxAccount:edit"><td style="text-align:center;">
    				<ul class="ul-form">
	    				<a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">修改</a>
						<a href="${ctx}/weixin/wxAccount/delete?id=${wxAccount.id}" onclick="return confirmx('确认要删除该微信管理吗？', this.href)">删除</a>&nbsp;
						<a href="javascript:;" onclick="wxApiView('${wxAccount.id}');">API接口</a>
					</ul>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>