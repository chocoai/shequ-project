<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板群发消息记录表管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("td").css("background-color","#FFF");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function selectRow(id,dom){
        	//先重置所有颜色
        	$(dom).parent().find("td").css("background-color","#FFF");        	
        	//在赋值每一行
        	$(dom).find("td").css("background-color","#CCC");
        	
        	$("#accountId").val(id);
        //	window.parent.frames["tplSelectAccounut"].location.href = "/hlb-shequ-app/admin/weixin/wxMassMsg/tplSelectAccounut?id="+id; 
        }
        //下一步
        function nextStep(){
        	var accountId = $("#accountId").val();
        	var msgId=$("#msgId").val();
        	window.location.href="${ctx}/weixin/wxMassMsg/tplSelectSendObj?accountId="+accountId+"&msgId="+msgId;        	
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/tplSelectAccounut">选择公众号</a></li>
		<li><a href="${ctx}/weixin/wxMassMsg/tplSelectSendObj">选择发送对象</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/tpl" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="source"/>
		<ul class="ul-form">
			<li><label>公众号名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<input id="accountId" type="hidden" />
	
	<input id="msgId" type="hidden" value="${wxMassMsg.msgId}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构名称</th>
				<th>公众号名称</th>
				<th>微信号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAccount" varStatus="status">		
			<tr data-accountId="${wxAccount.id}" onclick="selectRow('${wxAccount.id}',this)">
				<td>${wxAccount.group.name}</td>				
				<td>${wxAccount.wxname}</td>
				<td>${wxAccount.wxnum}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>	
	<div class="form-actions" style="text-align:center;">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步" onclick="nextStep();"/>&nbsp;
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>