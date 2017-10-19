<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("td").css("background-color","#FFF");
        	var firstTr;
        	var allTrs=$("#contentTable").find("tbody tr");        	
        	$.each(allTrs,function(index,item){        	        	
        		if(index==0){
        			firstTr=this;
        		}
        	});
        	//初始化第一个分类
        	var accountId = $(firstTr).attr("data-accountId");  
        	var source = $(firstTr).attr("data-source");       	
        	window.parent.frames["msgTpl"].location.href = "${ctx}/weixin/wxMsgTpl/list?accountId="+accountId+"&source="+source; 
        	$(firstTr).find("td").css("background-color","#CCC");
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
		
		function selectRow(source,accountId,dom){
        	//先重置所有颜色
        	$(dom).parent().find("td").css("background-color","#FFF");        	
        	//在赋值每一行
        	$(dom).find("td").css("background-color","#CCC");
        	
        	console.info(window.parent.frames["msgTpl"]);
        	window.parent.frames["msgTpl"].location.href = "${ctx}/weixin/wxMsgTpl/list?accountId="+accountId+"&source="+source;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxAccount/listSelect?source=${wxAccount.source}">公众号列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxAccount" action="${ctx}/weixin/wxAccount/listSelect" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>source：</label>
				<form:input path="source" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>公众号名称：</label>
				<form:input path="wxname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>			
			<li class="clearfix"></li>
		</ul><br/>	
	<sys:message content="${message}"/>
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
			<tr data-accountId="${wxAccount.id}" data-source="${wxAccount.source}" onclick="selectRow('${wxAccount.source}','${wxAccount.id}',this)">
				<td>${wxAccount.group.name}</td>				
				<td>${wxAccount.wxname}</td>
				<td>${wxAccount.wxnum}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>