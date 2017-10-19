<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新建流程 - 流程设计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
					setTimeout(function(){location='${ctx}/act/model/'}, 1000);
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
		function page(n,s){
        	location = '${ctx}/act/model/?pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/act/model/">流程图列表</a></li>
		<li class="active"><a href="${ctx}/act/model/update?id=${modelData.id}">新建流程图</a></li>
	</ul><br/>
	<sys:message content="${message}"/>
	<form id="inputForm" action="${ctx}/act/model/update"method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${modelData.id}">
		<div class="control-group">
			<label class="control-label">业务分类：</label>
			<div class="controls">
				<select id="category" name="category" class="required input-xlarge">
					<c:forEach items="${wyBizDefList}" var="def">
						<option value="${def.key}" ${(def.key eq modelData.category)?'selected="selected"':''}>${def.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程名称：</label>
			<div class="controls">
				<input id="name" name="name" type="text" class="input-xlarge required" value="${modelData.name}"/>
				<span class="help-inline"></span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">流程标识：</label>
			<div class="controls">
				<input id="key" name="key" type="text" class="input-xlarge required" value="${modelData.key}" ${not empty modelData.id?'disabled="disabled"':''}/>
				<span class="help-inline"><font color="red">*</font>流程唯一标识</span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">流程描述：</label>
			<div class="controls">
				<textarea id="description" name="description" rows="4" class="input-xxlarge required" >${description}</textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
