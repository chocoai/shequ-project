<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板群发消息记录表管理</title>
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
		<li><a href="${ctx}/weixin/wxMassMsg/tplIndex">消息列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/tplSendHistory?msgId=${wxMassMsg.msgId}">已发送消息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/tplSendHistory" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="msgId"/>
		<ul class="ul-form">
			<li><label>消息名称：</label>
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
				<th>消息名称</th>				
				<th>消息标题</th>
				<th>发送类型</th>
				<th>发送对象</th>	
				<th>发送公众号</th>		
				<th>发送时间</th>
				<th>总发送条数</th>
				<th>已发送</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMassMsg">
			<tr>
				<td>
					${wxMassMsg.name}
				</td>
				<td>${wxMassMsgCommon.firstValue}</td>
				<td>${fns:getDictLabel(wxMassMsg.type,'tpl_msg_mass_type','')}</td>
				<td>${wxMassMsg.toUsers}</td>
				<td>${wxMassMsg.wxAccount.wxname}</td>
				<td>
					<fmt:formatDate value="${wxMassMsg.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>				
				<td>${wxMassMsg.needSendNum}</td>
				<td>${wxMassMsg.currSendNum}</td>			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>