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
	        location.href="${ctx}/weixin/wxAccount/form?typeId=" + typeId; 
	    }
		function setDefaultAccount(id){
			$.get("${ctx}/weixin/wxAccount/setDefaultAccount" ,"id=" + id, function(data) {
				if(data=="true"){
					location.href="${ctx}/weixin/wxAccount/"; 
				}
		    });
		}		
		function wxApiView(id){
			location.href="${ctx}/weixin/wxAccount/wxApiView?id=" + id; 
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加服务号" onclick="addWeixin(2)"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加企业号" onclick="addWeixin(3)"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加订阅号" onclick="addWeixin(1)"/></li>
			<li class="clearfix"></li>
		</ul><br/>	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公众号名称</th>
				<th>微信号</th>
				<th>类型</th>
				<!-- <th>appid</th> -->
				<th>公众号原始ID</th>
				<shiro:hasPermission name="weixin:wxAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAccount" varStatus="status">		
			<tr>				
				<td><a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">
					${wxAccount.wxname}
				</a></td>
				<td>${wxAccount.wxnum}</td>
				<td>${fns:getDictLabel(wxAccount.typeId, 'weixin_account_type', '')}</td>
				<%-- <td>${wxAccount.appid}</td> --%>
				<td>${wxAccount.originId}</td>
				<shiro:hasPermission name="weixin:wxAccount:edit"><td>
    				<ul class="ul-form">
	    				<a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">修改</a>
						<a href="${ctx}/weixin/wxAccount/delete?id=${wxAccount.id}" onclick="return confirmx('确认要删除该微信管理吗？', this.href)">删除</a>&nbsp;
						<a id="btnSubmit" href="javascript:;" onclick="wxApiView('${wxAccount.id}');">API接口</a>
						<a id="btnSubmit" href="javascript:;" onclick="wxApiView('${wxAccount.id}');">关联组织机构</a>
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