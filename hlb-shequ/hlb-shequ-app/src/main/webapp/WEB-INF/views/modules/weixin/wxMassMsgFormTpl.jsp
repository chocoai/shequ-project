<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发消息记录表管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/layer/layer-v1.8.5/layui.css"  media="all">
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxMassMsg/tpl">模板群发列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/formTpl?id=${wxMassMsg.id}">模板群发<shiro:hasPermission name="weixin:wxMassMsg:edit">${not empty wxMassMsg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMassMsg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/saveTpl" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">发送类型:</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('wx_mass_group_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="wyBuilding" class="control-group">
			<label class="control-label">关联物业：</label>
			<div class="controls">
				<sys:selectDiv url="${ctx}/sns/wyBuilding/getBuildsBySource?source=${wxAccount.source}" id="wyBuilding" name="wyid" value="${wxAccount.wyid}" labelName="wymc" labelValue="${wxAccount.wymc}" title="物业" allowClear="true"></sys:selectDiv>
				<span class="help-inline">(为空,表示全部)</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择消息模板：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div id="" class="control-group">
			<label class="control-label">选择人员：</label>			
			<div class="controls">
				<sys:selectDiv url="${ctx}/weixin/wxAccount/select?source=${wxMsgTpl.source}" id="member" name="account.id" value="${wxMsgTpl.account.id}" labelName="account.wxname" labelValue="${wxMsgTpl.account.wxname}" title="微信账号" allowClear="true" cssClass="required"></sys:selectDiv>
				<span class="help-inline"><font color="red">*</font> </span>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">群发名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">群发用户：</label>
			<div class="controls">				
				<input id="btnCancel" class="btn required" type="button" value="选择用户" onclick="showSelectUsers();"/>
				<span id="selectOrgIdsOrCandidateIds"></span>
				<input id="orgIds" type="hidden"/>
				<input id="candidateIds" type="hidden"/>
				<form:hidden path="toUsers" htmlEscape="false" maxlength="1000"/>				
				<span class="help-inline"><font color="red">*</font></span>				
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMassMsg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
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
		
		//弹出选择用户框
		function showSelectUsers(){
			var url="${ctx}/sns/wyActCandidate/selectOrgUser2";
			var title="选择用户";
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: title,
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.6 , document.documentElement.clientHeight * 0.6],
			  close: function(index){
			   	 layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});			
		}
		
		function selectNews(){
			var url="${ctx}/weixin/wxMassMsg/showSelectNews2";
			var title="选择图文消息";
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: title,
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.6 , document.documentElement.clientHeight * 0.6]			  
			});
		}
		
	</script>
	
	
</body>
</html>