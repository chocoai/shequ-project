<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公众号添加</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
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
			$("#wxAccount_povince").change(function(){
				var id = $(this).val();
				var jq = $("#wxAccount_city");
				var url="${ctx}/sys/area/findCities";
				getAreaOption(url,id,jq);
			});
			$("#wxAccount_city").change(function(){
				var id = $(this).val();
				var jq = $("#wxAccount_qu");
				var url="${ctx}/sys/area/findQus";
				getAreaOption(url,id,jq);
			});
			$("#wxAccount_qu").change(function(){
				var url="${ctx}/sys/area/findCities";
			});	
			
			function getAreaOption(url,id,jq){
				$.get(url ,"id=" + id, function(data) {
					var selectedStr ="";
					jq.find("option:first").attr("selected","selected");
					jq.find("option").each(function(){
						var val=$(this).val();
						if(val !=''){
							$(this).remove();
						}
					});					
					 $.each(data, function(){  
						 selectedStr +="<option value='"+ this.id +"'>"+ this.name +"</option>";
					 });
					 jq.append(selectedStr);
			    });	
			}
		});
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxAccount/">微信管理列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxAccount/form?id=${wxAccount.id}">公众号<shiro:hasPermission name="weixin:wxAccount:edit">${not empty wxAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxAccount" action="${ctx}/weixin/wxAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="typeId"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">公众号logo:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="logo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联物业：</label>
			<div class="controls">
				<sys:selectDiv url="${ctx}/sns/wyBuilding/getBuildsBySource?source=${wxAccount.source}" id="wyBuilding" name="wyid" value="${wxAccount.wyid}" labelName="wymc" labelValue="${wxAccount.wymc}" title="物业" allowClear="true"></sys:selectDiv>
				<span class="help-inline">(为空,表示全部)</span>
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
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="25" class="input-xlarge required"/>
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