<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板消息通用模板定义管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMsgTplCommon/">通用模板列表</a></li>
		<shiro:hasPermission name="weixin:wxMsgTplCommon:edit"><li><a href="${ctx}/weixin/wxMsgTplCommon/form">通用模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMsgTplCommon" action="${ctx}/weixin/wxMsgTplCommon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板编码</th>
				<th>模板名称</th>
				<th>标题字段</th>
				<th>字段1</th>
				<th>字段2</th>
				<th>字段3</th>
				<th>字段4</th>
				<th>字段5</th>
				<th>备注字段</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="weixin:wxMsgTplCommon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMsgTplCommon">
			<tr>
				<td><a href="${ctx}/weixin/wxMsgTplCommon/form?id=${wxMsgTplCommon.id}">${wxMsgTplCommon.code}</a></td>
				<td>${wxMsgTplCommon.name}</td>				
				<td><font color="#${wxMsgTplCommon.firstColor}">${wxMsgTplCommon.firstName}<c:if test="${not empty wxMsgTplCommon.firstField}">(${wxMsgTplCommon.firstField})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.keyword1Color}">${wxMsgTplCommon.keyword1Name}<c:if test="${not empty wxMsgTplCommon.keyword1Field}">(${wxMsgTplCommon.keyword1Field})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.keyword2Color}">${wxMsgTplCommon.keyword2Name}<c:if test="${not empty wxMsgTplCommon.keyword2Field}">(${wxMsgTplCommon.keyword2Field})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.keyword3Color}">${wxMsgTplCommon.keyword3Name}<c:if test="${not empty wxMsgTplCommon.keyword3Field}">(${wxMsgTplCommon.keyword3Field})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.keyword4Color}">${wxMsgTplCommon.keyword4Name}<c:if test="${not empty wxMsgTplCommon.keyword4Field}">(${wxMsgTplCommon.keyword4Field})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.keyword5Color}">${wxMsgTplCommon.keyword5Name}<c:if test="${not empty wxMsgTplCommon.keyword5Field}">(${wxMsgTplCommon.keyword5Field})</c:if></font></td>
				<td><font color="#${wxMsgTplCommon.remarkColor}">${wxMsgTplCommon.remarkName}<c:if test="${not empty wxMsgTplCommon.remarkField}">(${wxMsgTplCommon.remarkField})</c:if></font></td>
				<td style="text-align:center;">
					<fmt:formatDate value="${wxMsgTplCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxMsgTplCommon.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxMsgTplCommon:edit"><td style="text-align:center;">
    				<a href="${ctx}/weixin/wxMsgTplCommon/form?id=${wxMsgTplCommon.id}">修改</a>
					<a href="${ctx}/weixin/wxMsgTplCommon/delete?id=${wxMsgTplCommon.id}" onclick="return confirmx('确认要删除该模板消息通用模板定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>