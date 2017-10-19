<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公众号添加</title>
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
		<li><a href="${ctx}/weixin/wxAccount/list?gid=${wxAccount.gid}">微信管理列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">公众号<shiro:hasPermission name="weixin:wxAccount:edit">${not empty wxAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxAccount" action="${ctx}/weixin/wxAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="typeId"/>
		<form:hidden path="source"/>
		<form:hidden path="groupId"/>
		<form:hidden path="gid"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">公众号类型：</label>
			<div class="controls">
				<span class="help-inline"><font color="red" size="5px">${fns:getDictLabel(wxAccount.typeId, 'weixin_account_type', '')}</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="wxname" htmlEscape="false" maxlength="60" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="wxnum" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">公众号原始ID：</label>
			<div class="controls">
				<form:input path="originId" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*请认真填写，错了不能修改。</font>比如：gh_b5c1775e46de</span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">appid：</label>
			<div class="controls">
				<form:input path="appid" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">appsecret：</label>
			<div class="controls">
				<form:input path="appsecret" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>	
</body>
</html>