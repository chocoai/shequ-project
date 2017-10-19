<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html >
<html>
<head>
	<title>在线表单列表</title>
<%@include file="/common/base.jsp" %>
<script type="text/javascript">
$(function() {
	query();
})
function query(){
	$('#dg').datagrid('options').queryParams = parseParams("toolbar");
	$("#dg").datagrid('reload');
	
	//$("#dg").datagrid('load',parseParams("toolbar"));
}
function formatterName(value, row, index){ 
	return row.addUser.name;
}  
function formatOp(value, row, index){ 
	var html = "";
	html += "<a href='javascript:openView(null,null,\""+value+"\")'>编辑</a>  ";
	html += "<a href='javascript:openDesign(\""+value+"\")'>设计</a>  ";
	html += "<a href='javascript:del(\""+value+"\")'>删除</a>";
	return html;
}  
function openView(rowIndex,rowData,bizId){
	bizId = bizId || rowData.id;
	$('#win').window({
			title: '修改在线表单',
		    width:450,
		    height:350,
		    modal:true
	});
	$('#subWin').attr("src",'page/online-form/add.jsp?bizId='+bizId) 
	$('#win').window('open');
}
function openDesign(id){
	$('#win').window({
			title: '设计在线表单',
		    width:1000,
		    height:680,
		    modal:true
	});
	$('#subWin').attr("src",'page/online-form/design.jsp?bizId='+id) 
	$('#win').window('open');
}

function add(){
	$('#win').window({
			title: '新增在线表单',
		    width:450,
		    height:350,
		    modal:true
	});
	$('#subWin').attr("src",'page/online-form/add.jsp?bizId=') 
	$('#win').window('open');
}
function del(id){
	$('#frm').mySubmit({
		url : 'OnlineFormController/delete?id='+id
	});
}
</script>
<style type="text/css">
</style>
</head>

<body>
	<table id="dg" class="easyui-datagrid" title="内容管理" style="width:100%;height:auto;margin:0 auto;"
			data-options="singleSelect:true,collapsible:true,onDblClickRow:openView" 
			url="OnlineFormController/list"
			rownumbers="true" toolbar="#toolbar">
		<thead>
			<tr>
				<th align="center" data-options="field:'key',width:200,align:'center'">表单名称</th>
				<th align="center" data-options="field:'descr',width:600,align:'center'">描述</th>
				<th align="center" formatter="formatterName" data-options="field:'addUserId',width:240,align:'center'">设计者</th>
				<th align="center" formatter="formatDate" data-options="field:'addtime',width:150,align:'center'">日期</th>
				<th align="center" data-options="field:'enabled',width:100,align:'center'">是否启用</th>
				<th align="center" formatter="formatOp" data-options="field:'id',width:350,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
<div id="toolbar">
    	<span>设计者:</span> <input class="easyui-validatebox"  name="addUser.name" >
		   <a href="javascript:query()" class="easyui-linkbutton"
			data-options="iconCls:'icon-search'" style="width: 80px">查询</a>
		   <a href="javascript:add()" class="easyui-linkbutton"
			data-options="iconCls:'icon-add'" style="width: 80px">新增</a>
</div>
<div id="win">
	<iframe id="subWin" src=''width= "100%" height= "99%" marginheight= "0" marginwidth= "0" hspeace= "0" vspace= "0" frameborder= "0"  ></iframe>
</div>
<form id="frm" method="post" style="display:none;">
</form>
</body>
</html>