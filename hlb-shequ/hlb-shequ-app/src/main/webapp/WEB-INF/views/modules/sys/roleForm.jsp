<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用平台角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script src="${ctxStatic}/js/jquery.freezeheader.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
			$("#treeTable").treeTable({expandLevel : 3}).show();
			$("#treeTable").freezeHeader({ 'height': '600px' });
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function toSelect(jq, rc){
        	var cas = $(jq).attr("class");
        	if($(jq).attr("checked")){
        		var clas = new Array();
        		$("."+cas).each(function(){
        			$(this).attr("checked", true);
        			clas = $(this).attr("class").split(" ");
        			$(".column1."+clas[0]).attr("checked", true);
        		});
        	}else{
        		$("."+cas).attr("checked", false);
        		if(cas == "column1"){
        			 $("table :checkbox").attr("checked",false);
        		}
        	}
        }
        function check(row){
        	$("."+row+".column1").attr("checked", true);
        }
        function cancel(row){
        	if(!$("."+row+".column1").attr("checked")){
        		$("."+row).attr("checked", false);
        	}
        }
	</script>
	<style type="text/css">
		.fix1{
			position: fixed;
		    width: 100%;
		    background-color: #FFF;
		}
		.distance_fix1{
			margin-top: 40px;
		}
		.thead{

		}
		th {
			padding: 4px !important;
		}
		td {
			padding: 3.5px !important;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<li class="active"><a href="${ctx}/sys/role/form?id=${appRole.id}">角色<shiro:hasPermission name="sys:role:edit">${not empty appRole.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">角色名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">英文名称：</label>
			<div class="controls">
				<form:input path="enname" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色授权:</label>
			<div class="controls" style="width: 1000px;">
			<div class="container-fluid">
				<div class="row-fluid">
					<table id="treeTable" data-toggle="table1" data-height="668" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th><div>菜单名称</div></th>											
											<th><div>查看<input class="column1" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>新增<input class="column2" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>修改<input class="column3" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>删除<input class="column4" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>执行<input class="column5" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>审核<input class="column6" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>打印<input class="column7" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>导出<input class="column8" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
											<th><div>导入<input class="column9" type="checkbox" onclick="toSelect(this, 1)"/></div></th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${volist}" var="menu" varStatus="status">
										<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
											<td nowrap>${menu.name} <c:if test="${not empty menu.href}"><input class="row${status.index}" type="checkbox" onclick="toSelect(this, 2)"/></c:if></td>		
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.view}"><input class="row${status.index} column1" onclick="cancel('row${status.index}')" name="permission" type="checkbox" value="${menu.view}" ${menu.viewChecked?'checked="checked"':''} /></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.add}"><input class="row${status.index} column2" onclick="check('row${status.index}')" name="permission"  type="checkbox" value="${menu.add}" ${menu.addChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.edit}"><input class="row${status.index} column3" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.edit}" ${menu.editChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.del}"><input class="row${status.index} column4" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.del}" ${menu.delChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.exec}"><input class="row${status.index} column5" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.exec}" ${menu.execChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.audit}"><input class="row${status.index} column6" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.audit}" ${menu.auditChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.print}"><input class="row${status.index} column7" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.print}" ${menu.printChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.export}"><input class="row${status.index} column8" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.export}" ${menu.exportChecked?'checked="checked"':''}/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.daoru}"><input class="row${status.index} column9" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.daoru}" ${menu.daoruChecked?'checked="checked"':''}/></c:if></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
					<!-- <div class="span4">
					</div> -->
				</div>
			</div>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>