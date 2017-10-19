<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发消息记录表管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/news">群发列表</a></li>
		<shiro:hasPermission name="weixin:wxMassMsg:edit"><li><a href="${ctx}/weixin/wxMassMsg/form">群发添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/news" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号：</label>
				<form:select path="accountId" class="input-medium" onchange="$('#searchForm').submit();">
					<option value="">请选择</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>群发名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>群发名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="weixin:wxMassMsg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMassMsg">
			<tr>
				<td><a href="${ctx}/weixin/wxMassMsg/form?id=${wxMassMsg.id}">
					${wxMassMsg.name}
				</a></td>
				<td style="text-align: center;">
					<fmt:formatDate value="${wxMassMsg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxMassMsg.remarks}
				</td>
				<shiro:hasPermission name="weixin:wxMassMsg:edit"><td style="text-align: center;">
					<!-- <a href="javascript:send()" onclick="return confirmx('确认要发送【<font color=\'red\'>${wxMassMsg.name}</font>】吗？', this.href)">发送</a> -->
					<a href="${ctx}/weixin/wxMassMsg/sendNewsMsg?id=${wxMassMsg.id}" onclick="return confirmx('确认要发送【<font color=\'red\'>${wxMassMsg.name}</font>】吗？', this.href)">发送</a>
    				<a href="${ctx}/weixin/wxMassMsg/form?id=${wxMassMsg.id}">修改</a>
					<a href="${ctx}/weixin/wxMassMsg/delete?id=${wxMassMsg.id}" onclick="return confirmx('确认要删除【<font color=\'red\'>${wxMassMsg.name}</font>】吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		/*function send(){
			$.ajax({
				type: "POST",
				url : "${ctx}/weixin/wxMassMsg/sendNewsMsg?id=${wxMassMsg.id}",
				datatype : "json",
				success : function(result){
					if(result.code==200){

					}
				}
			});
		}*/
	</script>
</body>
</html>