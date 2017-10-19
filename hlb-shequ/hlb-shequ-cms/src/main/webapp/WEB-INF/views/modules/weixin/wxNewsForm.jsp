<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信图文管理</title>
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
		<li><a href="${ctx}/weixin/wxNews/">微信图文列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxNews/form?id=${wxNews.id}">微信图文<shiro:hasPermission name="weixin:wxNews:edit">${not empty wxNews.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxNews:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxNews" action="${ctx}/weixin/wxNews/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">关键字<font color="red">*</font>：</label>
			<div class="controls">
				<form:input path="keyword" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline">用于匹配用户输入的关键字,多个关键字，用空格分隔 </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">标题<font color="red">*</font>：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="60" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">封面图片<font color="red">*</font>：</label>
			<div class="controls">
				<form:hidden id="picurl" path="picurl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="picurl" type="images" uploadPath="/weixin/images" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文章简介<font color="red">*</font>：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容<font color="red">*</font>：</label>
			<div class="controls">
				<form:textarea id="content" path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/weixin/images" />
			</div>
		</div>				
		<div class="control-group">
			<label class="control-label">图文外链地址：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<span class="help-inline">默认由系统自动生成外链地址 </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">作者<font color="red">*</font>：</label>
			<div class="controls">
				<form:input path="writer" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxNews:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>