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
					<li class="active">
						<a href="#panel-856083" data-toggle="tab">任务表单</a>
					</li>					
				</ul>
			<form:form id="wyActShowFrom" modelAttribute="wyActFormDto" method="post" class="form-horizontal">
				<input type="hidden" name="activitiId" value="${wyActFormDto.activitiId}">
				<input type="hidden" name="procDefinitionId" value="${wyActFormDto.procDefinitionId}">
				<div class="tab-content">
					<div class="tab-pane active" id="panel-856083">						
					   <div class="control-group">
							<label class="control-label">请选择任务表单:</label>
							<div class="controls">								
								<form:select path="formId" class="input-xlarge">
									<form:option value="" label="请选择"/>
									<form:options items="${wyBizFormList}" itemLabel="formName" itemValue="id" htmlEscape="false"/>
								</form:select>
							</div>
						</div>						
					</div>														
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
		var formdata = $("#wyActShowFrom").serialize();	
		$.ajax({
            url: '${ctx}/sns/wyActForm/saveForm',
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