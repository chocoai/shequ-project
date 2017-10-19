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
			
			$("#sysType").change(function(){
				var type=$("#sysType").val();
				
				$("#searchForm").submit();
			});
			
			$("#treeTable").freezeHeader({ 'height': '600px' });
					
		});
		
		function toSelect(jq){
        	var cas = $(jq).attr("class");
        	if($(jq).attr("checked")){
        		$("."+cas).attr("checked", true);
        		$(".column1").attr("checked", true);
        	}else{
        		$("."+cas).attr("checked", false);
        	}
        }
		function check(row){
        	$("."+row+".column1").attr("checked", true);
        }
        function cancel(row){
        	if($("."+row+".column1").attr("checked", false)){
        		$("."+row).attr("checked", false);
        	}
        }
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="##">应用平台角色列表</a></li>
		<%-- <li class="active"><a href="${ctx}/sys/appRole/form?id=${appRole.id}&sysType=${appRole.sysType}">应用平台角色<shiro:hasPermission name="sys:appRole:edit">${not empty appRole.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:appRole:edit">查看</shiro:lacksPermission></a></li> --%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appRole" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="sysType"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">子系统名称：</label>
			<div class="controls">
				<span style="color:red;font-size:16px;"><b>${fns:getDictLabel(appRole.sysType,'sys_type','')}</b></span>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required" disabled="disabled"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">英文名称：</label>
			<div class="controls">
				<form:input path="enname" htmlEscape="false" maxlength="255" class="input-xlarge " disabled="disabled"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<form:select path="useable" disabled="disabled">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " disabled="disabled"/>
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
								<table id="treeTable" data-toggle="table1" data-height="668" class="table table-striped table-bordered table-condensed hide">
									<thead>
										<tr>
											<th>菜单名称</th>											
											<th>查看</th>
											<th>新增</th>
											<th>修改</th>
											<th>删除</th>
											<th>执行</th>
											<th>审核</th>
											<th>打印</th>
											<th>导出</th>
											<th>导入</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${volist}" var="menu" varStatus="status">
										<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
											<td nowrap>${menu.name} <c:if test="${not empty menu.href}"></c:if></td>								
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.view}"><input type="checkbox" value="${menu.view}" ${menu.viewChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.add}"><input type="checkbox" value="${menu.add}" ${menu.addChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.edit}"><input type="checkbox" value="${menu.edit}" ${menu.editChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.del}"><input type="checkbox" value="${menu.del}" ${menu.delChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.exec}"><input type="checkbox" value="${menu.exec}" ${menu.execChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.audit}"><input type="checkbox" value="${menu.audit}" ${menu.auditChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.print}"><input type="checkbox" value="${menu.print}" ${menu.printChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.export}"><input type="checkbox" value="${menu.export}" ${menu.exportChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
											<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.daoru}"><input type="checkbox" value="${menu.daoru}" ${menu.daoruChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
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