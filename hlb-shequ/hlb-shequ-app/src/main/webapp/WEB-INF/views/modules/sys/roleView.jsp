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
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="##">查看角色</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="role" method="post" class="form-horizontal">
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
			<div class="controls">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span8">
						<div class="row-fluid">
							<div class="span12">
								<table id="treeTable" data-toggle="table1" data-height="668" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th><div>菜单名称</div></th>											
											<th><div>查看</div></th>
											<th><div>新增</div></th>
											<th><div>修改</div></th>
											<th><div>删除</div></th>
											<th><div>执行</div></th>
											<th><div>审核</div></th>
											<th><div>打印</div></th>
											<th><div>导出</div></th>
											<th><div>导入</div></th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${volist}" var="menu" varStatus="status">
										<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
											<td nowrap>${menu.name} <c:if test="${not empty menu.href}"></c:if></td>		
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.view}"><input name="permission" type="checkbox" value="${menu.view}" ${menu.viewChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.add}"><input name="permission"  type="checkbox" value="${menu.add}" ${menu.addChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.edit}"><input name="permission" type="checkbox" value="${menu.edit}" ${menu.editChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.del}"><input name="permission" type="checkbox" value="${menu.del}" ${menu.delChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.exec}"><input name="permission" type="checkbox" value="${menu.exec}" ${menu.execChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.audit}"><input name="permission" type="checkbox" value="${menu.audit}" ${menu.auditChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.print}"><input name="permission" type="checkbox" value="${menu.print}" ${menu.printChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.export}"><input name="permission" type="checkbox" value="${menu.export}" ${menu.exportChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.daoru}"><input name="permission" type="checkbox" value="${menu.daoru}" ${menu.daoruChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>							
						</div>
					</div>
					<div class="span4">
					</div>
				</div>
			</div>
			</div>
		</div>
	</form:form>
</body>
</html>