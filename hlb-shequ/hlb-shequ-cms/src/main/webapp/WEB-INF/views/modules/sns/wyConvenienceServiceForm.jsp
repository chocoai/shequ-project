<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>便民服务管理</title>
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
	<!-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyConvenienceService/">便民服务列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyConvenienceService/form?id=${wyConvenienceService.id}">便民服务<shiro:hasPermission name="sns:wyConvenienceService:edit">${not empty wyConvenienceService.id?'修改':'创建'}</shiro:hasPermission><shiro:lacksPermission name="sns:wyConvenienceService:edit">查看</shiro:lacksPermission></a></li>
	</ul> -->
	<br/>
	<form:form id="inputForm" modelAttribute="wyConvenienceService" action="${ctx}/sns/wyConvenienceService/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="display: none">
			<label class="control-label">便民服务id：</label>
			<div class="controls">
				<form:input path="serviceId" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">内容<font color="red">*</font>：</label>
			<div class="controls">
				<form:textarea id="content" path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/sns/wyConvenienceService/images" />
			</div>
		</div>		
		<div class="control-group" style="display: none">
			<label class="control-label">create_time：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${wyConvenienceService.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">source：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">group_id：</label>
			<div class="controls">
				<form:input path="groupId" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sns:wyConvenienceService:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>