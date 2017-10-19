<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			

		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
	    //选中所有	    
	    function userSelectAll(){
	    	var checkboxs = $("#user_tbody").find(".required");
	    	$.each(checkboxs,function(i,v){
	    		console.info(v);	    		
	    		$(this).attr("checked");
	    		//alert("i="+i+";v="+$(v).val());	    		
	    	});
	    }
	    function saveSelectUser(){
	    	var checkboxs = $("#user_tbody").find("input[name='userId']:checked");	    	
	    	
	    	var userids="";
	    	$.each(checkboxs,function(i,v){
	    		userids += "," + $(this).val();
	    	});
	    	
	    	if(userids.length >1){
	    		userids = userids.substring(1);	    		
	    	}
	    	$("#assignee").val(userids);
	    	
	    	//保存群组信息
	    	$("#group").val("");
	    	
	    	var group="";
	    		    	
	    	return true;
	    }
	    
	    
	    //选择部门
	    function chooseOffice(){	    
	    	parent.setting.callback.onClick=parent.officeOnclick;	    	
	    	parent.refreshTree();
	    	window.location.href="${ctx}/sys/office/selectHandlerOfficeList?id=&parentIds=";
	    }
	    
	    
	    //选择用户
	    function chooseUser(){
	    	parent.setting.callback.onClick=parent.userOnClick;
	    	parent.refreshTree();
	    	window.location.href="${ctx}/sys/user/selectHandlerList?activitiId=${activitiId}&procDefinitionId=${procDefinitionId}";
	    }
	   	
	</script>
</head>
<body>	
	<ul class="nav nav-tabs">
		<li class="active"><a href="#" onclick="chooseUser();">选择用户</a></li>
		<li><a href="#" onclick="chooseOffice();">选择部门</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/act/process/saveSelectUser" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="taskId" name="taskId" type="hidden" value="${activitiId}"/>
		<input id="procDefId" name="procDefId" type="hidden" value="${procDefinitionId}"/>
		<input id="group" name="group" type="hidden" value="${group}"/>
		<input id="assignee" name="assignee" type="hidden" value="${assignee}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" onclick="return saveSelectUser();"/>				
			</li>
			<li class="clearfix"></li>
		</ul>		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>登录名</th>
				<th>归属公司</th>
				<th>归属部门</th>
				<th>电话</th>
				<th>手机</th>
				<%--<th>角色</th> --%>	
				<th><span onclick="userSelectAll()"><input id="user_select_all" class="required" type="checkbox" value=""><label for="user_select_all">全选</label></span></th>
			</tr>
		</thead>
		<tbody id="user_tbody">		
		<c:forEach items="${page.list}" var="user" varStatus="status">
			<tr>				
				<td>${user.name}</td>
				<td>${user.loginName}</td>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>	
				<td>
					<span>
						<input id="user_${status.index}" name="userId" class="required" type="checkbox" value="${user.id}">
						<label for="user_${status.index}">选择</label>
					</span>
				</td>			
			</tr>
		</c:forEach>
		</form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>