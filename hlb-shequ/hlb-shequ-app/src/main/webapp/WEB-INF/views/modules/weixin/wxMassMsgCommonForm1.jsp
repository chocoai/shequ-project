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
		<li class="active"><a href="${ctx}/weixin/wxMassMsgCommon/form?id=${wxMassMsgCommon.id}">通用消息<shiro:hasPermission name="weixin:wxMassMsgCommon:edit">${not empty wxMassMsgCommon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMassMsgCommon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsgCommon" action="${ctx}/weixin/wxMassMsgCommon/form?step=2" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">选择消息模板：</label>
			<div class="controls">
				<form:select path="tplId" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${wxMsgTplCommonList}" var="tpl">
						<option value="${tpl.id}">${tpl.name}（${tpl.code}）</option>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMassMsgCommon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>