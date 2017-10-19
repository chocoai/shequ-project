<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务处理后url设置</title>
	<meta name="decorator" content="default"/>

</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="tabbable" id="tabs-59696">
				<ul class="nav nav-tabs">
					<c:if test="${activitiType eq 'startEvent'}">
					<li class="active">
						<a href="#panel-856083" data-toggle="tab">设置流程开始显示页面</a>
					</li>
					</c:if>
					<c:if test="${activitiType eq 'endEvent'}">
					<li ${(activitiType eq 'endEvent')?'class="active"':''}>
						<a href="#panel-320647" data-toggle="tab">流程结束后显示页面</a>
					</li>
					</c:if>					
				</ul>
				<form:form id="wyActShowForm2" modelAttribute="wyActFormDto" method="post" class="form-horizontal">
					<input type="hidden" name="activitiId" value="${activitiId}">
					<input type="hidden" name="procDefinitionId" value="${procDefinitionId}">
					<div class="tab-content">
						<c:if test="${activitiType eq 'startEvent'}">
						<div class="tab-pane active" id="panel-856083">
							<div class="control-group">
								<label class="control-label">设置流程开始显示页面:</label>
								<div class="controls">								
									<form:select path="startUrlFormid" class="input-xlarge">
										<form:option value="" label="请选择"/>
										<form:options items="${wyBizFormList}" itemLabel="formName" itemValue="id" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
						</c:if>
						<c:if test="${activitiType eq 'endEvent'}">
						<div class="tab-pane ${(activitiType eq 'endEvent')?'active':''}" id="panel-320647">
							<div class="control-group">
								<label class="control-label">流程结束后显示页面:</label>
								<div class="controls">								
									<form:select path="endUrlFormid" class="input-xlarge">
										<form:option value="" label="请选择"/>
										<form:options items="${wyBizFormList}" itemLabel="formName" itemValue="id" htmlEscape="false"/>
									</form:select>
								</div>
							</div>					
						</div>	
						</c:if>				
					</div>
				</form:form>
			</div>			
		</div>
	</div>
</div>
<div class="form-actions">
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="saveHandler()">&nbsp;
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeLayer()">
</div>
<script type="text/javascript">
	function saveHandler(){
		//获取form表单数据
		var formdata = $("#wyActShowForm2").serialize();	
		$.ajax({
            url: '${ctx}/sns/wyActForm/saveForm2',
            data: formdata,
            type: 'post',
            async: false,
            success: function(data) {             	       
            	if(data.code==200){
            		closeLayer();
            	}else{
            		alert(data.msg);
            	}            	            
                console.info(data);
            }
        });		
	}
	
	function closeLayer(){	  		
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		 parent.layer.close(index);
	}
</script>
</body>

</html>