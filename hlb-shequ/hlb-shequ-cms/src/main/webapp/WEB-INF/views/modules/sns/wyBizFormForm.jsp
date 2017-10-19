<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程表单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyBizForm/">流程表单列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyBizForm/form?id=${wyBizForm.id}">流程表单<shiro:hasPermission name="sns:wyBizForm:edit">${not empty wyBizForm.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sns:wyBizForm:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wyBizForm" action="${ctx}/sns/wyBizForm/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">业务类型:</label>
			<div class="controls">
				<form:select path="formType" class="input-xlarge required">
					<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单名称：</label>
			<div class="controls">				
				<form:input path="formName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">编码：</label>
			<div class="controls">				
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">formid自定义key：</label>
			<div class="controls">
				<form:input path="formId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">跳转URL地址：</label>
			<div class="controls">
				<form:input path="formUrl" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">HTML样式:</label>
			<div class="controls">
				<form:textarea id="formHtml" htmlEscape="true" path="formHtml" rows="5" maxlength="5000" class="input-xxlarge"/>
				<sys:ckeditor replace="formHtml" uploadPath="/form/images" />
			</div>
		</div> --%>
		
		<div class="form-actions">
			<shiro:hasPermission name="sns:wyBizForm:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
	</form:form>
</body>
</html>