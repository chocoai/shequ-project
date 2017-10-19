<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看公众号API</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxAccount/">微信管理列表</a></li>
		<li class="active"><a>查看公众号API</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxAccount" action="" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">公众号logo:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="logo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="wxname" htmlEscape="false" maxlength="60" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="wxnum" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">接口URL地址：</label>
			<div class="controls">				
				<input id="url" name="url" class="input-xlarge required" type="text" value="${url}" maxlength="20">
				<span class="help-inline"><font color="red">*</font> </span><a href="${url}" target="_blank"> 在浏览器中打开>></a>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">你的Token：</label>
			<div class="controls">
				<form:input path="token" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">EncodingAESKey：</label>
			<div class="controls">
				<form:input path="encodingAESKey" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*消息加解密密钥</font> </span>
			</div>
		</div>				
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>	
</body>
</html>