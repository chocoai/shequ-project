<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用实例消息管理</title>
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
		<li><a href="${ctx}/weixin/wxMassMsgCommon/">通用消息列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsgCommon/form?id=${wxMassMsgCommon.id}&step=2&tplId=${wxMassMsgCommon.tplId}">通用消息<shiro:hasPermission name="weixin:wxMassMsgCommon:edit">${not empty wxMassMsgCommon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMassMsgCommon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsgCommon" action="${ctx}/weixin/wxMassMsgCommon/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="tplId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">				
				<span style="font-size:13px;">${wxMsgTplCommon.name}（${wxMsgTplCommon.code}）</span>
			</div>
		</div>		
		<c:if test="${not empty wxMsgTplCommon.firstName}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.firstName}：</label>
			<div class="controls">
				<form:input path="firstValue" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.firstColor};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>		
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.keyword1Name}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.keyword1Name}：</label>
			<div class="controls">
				<form:input path="keyword1Value" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.keyword1Color};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.keyword2Name}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.keyword2Name}：</label>
			<div class="controls">
				<form:input path="keyword2Value" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.keyword2Color};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.keyword3Name}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.keyword3Name}：</label>
			<div class="controls">
				<form:input path="keyword3Value" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.keyword3Color};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.keyword4Name}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.keyword4Name}：</label>
			<div class="controls">
				<form:input path="keyword4Value" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.keyword4Color};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.keyword5Name}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.keyword5Name}：</label>
			<div class="controls">
				<form:input path="keyword5Value" htmlEscape="false" maxlength="255" class="input-xlarge required" style="color:#${wxMsgTplCommon.keyword5Color};"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		<c:if test="${not empty wxMsgTplCommon.remarkName}">
		<div class="control-group">
			<label class="control-label">${wxMsgTplCommon.remarkName}：</label>
			<div class="controls">
				<form:textarea path="remarkValue" htmlEscape="false" maxlength="255" rows="4" class="input-xlarge required" style="color:#${wxMsgTplCommon.remarkColor};"/>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">消息详情：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				<sys:ckeditor replace="content" uploadPath="/sns/wyConvenienceService/images" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMassMsgCommon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>