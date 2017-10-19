<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板消息通用模板定义管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/js/jscolor.js"></script>
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
		function clickSubmit(){
			var firstName = $("#firstName").val();
			var firstField = $("#firstField").val();
			var firstColor = $("#firstColor").val();
			
			var remarkName = $("#remarkName").val();
			var remarkField = $("#remarkField").val();
			var remarkColor = $("#remarkColor").val();
			
			if((firstName==undefined||firstName=='')||(firstField==undefined||firstField=='')||(firstColor==undefined||firstColor=='')){
				alert("标题的三个字段不能为空");
				return false;
			}						
			if((remarkName==undefined||remarkName=='')||(remarkField==undefined||remarkField=='')||(remarkColor==undefined||remarkColor=='')){
				alert("备注的三个字段不能为空");
				return false;
			}		
		}		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxMsgTplCommon/">通用模板列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMsgTplCommon/form?id=${wxMsgTplCommon.id}">通用模板<shiro:hasPermission name="weixin:wxMsgTplCommon:edit">${not empty wxMsgTplCommon.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMsgTplCommon:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMsgTplCommon" action="${ctx}/weixin/wxMsgTplCommon/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板编号：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">字段说明：</label>
			<div class="controls">
				<span style="padding:22px;font-size:15px;">字段名称</span><span style="padding:22px;font-size:15px;">对应的字段</span><span style="padding:22px;font-size:15px;">颜色设置</span>
				<span class="help-inline"><font color="red">说明:有几个字段就填写几个字段,标题和备注信息字段必须有</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="firstName" htmlEscape="false" maxlength="100" class="input-medium required" value="标题"/>
				<form:input path="firstField" htmlEscape="false" maxlength="100" class="input-mini required" value="first"/>
				<form:input path="firstColor" htmlEscape="false" maxlength="100" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">字段1：</label>
			<div class="controls">
				<form:input path="keyword1Name" htmlEscape="false" maxlength="100" class="input-medium "/>
				<form:input path="keyword1Field" htmlEscape="false" maxlength="100" class="input-mini "/>
				<form:input path="keyword1Color" htmlEscape="false" maxlength="100" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">字段2：</label>
			<div class="controls">
				<form:input path="keyword2Name" htmlEscape="false" maxlength="100" class="input-medium "/>
				<form:input path="keyword2Field" htmlEscape="false" maxlength="20" class="input-mini "/>
				<form:input path="keyword2Color" htmlEscape="false" maxlength="20" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">字段3：</label>
			<div class="controls">
				<form:input path="keyword3Name" htmlEscape="false" maxlength="100" class="input-medium "/>
				<form:input path="keyword3Field" htmlEscape="false" maxlength="20" class="input-mini "/>
				<form:input path="keyword3Color" htmlEscape="false" maxlength="20" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">字段4：</label>
			<div class="controls">
				<form:input path="keyword4Name" htmlEscape="false" maxlength="100" class="input-medium "/>
				<form:input path="keyword4Field" htmlEscape="false" maxlength="20" class="input-mini "/>
				<form:input path="keyword4Color" htmlEscape="false" maxlength="20" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段5：</label>
			<div class="controls">
				<form:input path="keyword5Name" htmlEscape="false" maxlength="100" class="input-medium "/>
				<form:input path="keyword5Field" htmlEscape="false" maxlength="20" class="input-mini "/>
				<form:input path="keyword5Color" htmlEscape="false" maxlength="20" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注名称：</label>
			<div class="controls">
				<form:input path="remarkName" htmlEscape="false" maxlength="100" class="input-medium required" value="备注"/>
				<form:input path="remarkField" htmlEscape="false" maxlength="20" class="input-mini required" value="remark"/>
				<form:input path="remarkColor" htmlEscape="false" maxlength="20" class="input-mini jscolor"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMsgTplCommon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return clickSubmit();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>