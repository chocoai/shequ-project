<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
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
	    
	    function saveSelectOffice(){
	    	var checkboxs = $("#treeTableList").find("input[name='officeId']:checked");	    	
	    	
	    	var officeIds="";
	    	$.each(checkboxs,function(i,v){
	    		officeIds += "," + $(this).val();
	    	});
	    	if(officeIds.length>1){
	    		officeIds = officeIds.substring(1);	    		
	    	}
	    	$("#assignee").val(officeIds);
	    	
	    	//保存群组信息
	    	$("#group").val("");
	    	
	    	
	    	console.info(officeIds);
	    		    	
	    	return true;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="#" onclick="chooseUser();">选择用户</a></li>
		<li class="active"><a href="#" onclick="chooseOffice();">选择部门</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/act/process/saveSelectUser" method="post" class="breadcrumb form-search ">
		<input id="officeIds" name="officeIds" type="hidden"/>
		<ul class="ul-form">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" onclick="return saveSelectOffice();"/>				
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>			
				<th>机构名称</th>
				<th>归属区域</th>
				<th>机构编码</th>
				<th>机构类型</th>
				<th>备注</th>
				<th><span onclick="userSelectAll();"><input id="user_select_all" class="required" type="checkbox" value=""><label for="user_select_all">全选</label></span></th>			
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">			
			<td>{{row.name}}</td>
			<td>{{row.area.name}}</td>
			<td>{{row.code}}</td>
			<td>{{dict.type}}</td>
			<td>{{row.remarks}}</td>
			<td><input id="user_{{row.id}}" name="officeId" class="required" type="checkbox" value="{{row.id}}">
				<label for="user_{{row.id}}">选择</label>
			</td>
		</tr>
	</script>
</body>
</html>