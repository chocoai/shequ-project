<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息模板管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
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
		<li class="active"><a href="${ctx}/weixin/wxMsgTpl/list?accountId=${wxMsgTpl.accountId}">消息模板列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMsgTpl" action="${ctx}/weixin/wxMsgTpl/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="accountId" name="accountId" type="hidden" value="${wxMsgTpl.accountId}"/>
		<ul class="ul-form">
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr height="30px">	
				<th>模板编号</th>			
				<th>模板名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th width="120">消息模板ID</th>
				<shiro:hasPermission name="weixin:wxMsgTpl:edit"><th style="text-align:center;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMsgTpl" varStatus="status">
			<tr>				
				<td>
					${wxMsgTpl.commonTpl.code}
				</td>
				<td>
					${wxMsgTpl.name}
				</td>
				<td>
					<fmt:formatDate value="${wxMsgTpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxMsgTpl.remarks}
				</td>
				<td width="120"><input id="tplId-${status.index}" type="text" name="tplId" value="${wxMsgTpl.tplId}" readonly="readonly" style="margin:0;text-align:center;height: 17px;padding: 0px;border-color: #FFF;"/></td>
				<shiro:hasPermission name="weixin:wxMsgTpl:edit"><td style="text-align:center;">
    				<a class="edit" href="javascript:;">修改</a>
    				<a class="save" href="javascript:;" onclick="edit('${wxMsgTpl.id}','tplId-${status.index}','${wxMsgTpl.accountId}')" style="display: none">保存</a>
					<a href="${ctx}/weixin/wxMsgTpl/delete?id=${wxMsgTpl.id}" onclick="return confirmx('确认要删除该消息模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(".edit").click(function(){
			var edit = $(this).parent().parent().find("input");
			edit.removeAttr("readonly");
			edit.focus();
			$(this).css("display", "none");
			$(this).parent().find(".save").css("display","unset");
		});

		//更新模板id
        function edit(id,domid,accountId){
        	console.info("domid="+domid);
        
        	var tplid=$("#"+domid).val();	
        	console.info("id="+id+";tplid="+tplid+";accountId="+accountId);	
        	
        		
			window.location.href="${ctx}/weixin/wxMsgTpl/updateTplId?id="+ id +"&tplId="+ tplid +"&accountId="+ accountId; 
        }
	</script>
</body>
</html>