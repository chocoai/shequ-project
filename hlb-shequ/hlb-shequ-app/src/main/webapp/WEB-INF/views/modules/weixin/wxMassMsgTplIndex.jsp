<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用实例消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#contentTable").find("td").css("background-color","#FFF");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function selectRow(msgId,dom){
        	//先重置所有颜色
        	$(dom).parent().find("td").css("background-color","#FFF");        	
        	//在赋值每一行
        	$(dom).find("td").css("background-color","#CCC");
        	window.parent.frames["tplSelectAccounut"].location.href = "${ctx}/weixin/wxMassMsg/tplSelectAccounut?msgId="+msgId; 
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/tplIndex">消息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMassMsgCommon" action="${ctx}/weixin/wxMassMsg/tplIndex" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息名称：</label>
				<form:input path="firstValue" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>更新时间</th>
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMassMsgCommon">
			<tr>
				<td>
					${wxMassMsgCommon.firstValue}
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${wxMassMsgCommon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxMassMsgCommon.remarks}
				</td>	
				<td style="text-align: center;">
					<a href="${ctx}/weixin/wxMassMsg/tplSendMsg?msgId=${wxMassMsgCommon.id}">发送消息</a>
					<a href="${ctx}/weixin/wxMassMsg/tplSendHistory?msgId=${wxMassMsgCommon.id}">发送详情</a>					
				</td>			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>