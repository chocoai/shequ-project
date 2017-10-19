<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程处理后调用方法管理</title>
	<meta name="decorator" content="default"/>

</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="tabbable" id="tabs-59696">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-856083" data-toggle="tab">任务创建</a>
					</li>
					<li>
						<a href="#panel-320647" data-toggle="tab">定时任务</a>
					</li>
					<li>
						<a href="#panel-123456" data-toggle="tab">任务完成</a>
					</li>					
				</ul>
				<form id="wyBizHandlerFrom" name="wyBizHandlerFrom" action="">
				<input type="hidden" name="activitiId" value="${activitiId}">
				<input type="hidden" name="procDefinitionId" value="${procDefinitionId}">
				<div class="tab-content">
					<div class="tab-pane active" id="panel-856083">
						<div class="control-group">
							<label class="control-label">选择处理器:</label><br/>		
							<c:forEach items="${createlist}" var="wyBizHandler" varStatus="status">			
							<div class="controls">
								<input id="roleIdList_${status.index}" name="createHandler" class="required" type="checkbox" value="${wyBizHandler.handlerInstance}" ${wyBizHandler.checked?'checked':''}><label for="roleIdList_${status.index}">${wyBizHandler.handlerName}</label>
							</div>
							</c:forEach>
						</div>	
					</div>
					<div class="tab-pane" id="panel-320647">
						<div class="control-group">
							<label class="control-label">设置定时时间:</label>
							<div class="controls">
								<select name="jobDay" class="input-mini">
									<c:forEach var="i" begin="0" end="30" varStatus="status">
										<option value="${status.index}" ${(status.index==wyActJob.jobDay)?'selected':''}>${status.index}</option>					       
									</c:forEach>					
								</select> 天&nbsp;&nbsp;&nbsp;&nbsp;
								<select name="jobHour" class="input-mini">
									<c:forEach var="i" begin="0" end="23" varStatus="status">
										<option value="${status.index}" ${(status.index==wyActJob.jobHour)?'selected':''}>${status.index}</option>					       
									</c:forEach>
								</select> 时&nbsp;&nbsp;&nbsp;&nbsp;
								<select name="jobMinute" class="input-mini">
									<c:forEach var="i" begin="0" end="59" varStatus="status">
										<option value="${status.index}" ${(status.index==wyActJob.jobMinute)?'selected':''}>${status.index}</option>					       
									</c:forEach>
								</select> 分				
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">定时执行的任务:</label><br/>		
							<c:forEach items="${createJoblist}" var="wyBizHandler" varStatus="status">			
							<div class="controls">
								<input id="jobHandler_${status.index}" name="jobHandler" class="required" type="checkbox" value="${wyBizHandler.handlerInstance}" ${wyBizHandler.checked?'checked':''}><label for="jobHandler_${status.index}">${wyBizHandler.handlerName}</label>
							</div>
							</c:forEach>
						</div>					
					</div>
					<div class="tab-pane" id="panel-123456">
						<div class="control-group">
							<label class="control-label">选择处理器:</label><br/>		
							<c:forEach items="${completelist}" var="wyBizHandler" varStatus="status">			
							<div class="controls">
								<input id="completeHandler_${status.index}" name="completeHandler" class="required" type="checkbox" value="${wyBizHandler.handlerInstance}" ${wyBizHandler.checked?'checked':''}><label for="completeHandler_${status.index}">${wyBizHandler.handlerName}</label>
							</div>
							</c:forEach>
						</div>											
					</div>					
				</div>
				</form>
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
		var formdata = $("#wyBizHandlerFrom").serialize();		
		
		$.ajax({
            url: '${ctx}/sns/wyActHandler/saveHandler',
            data: formdata,
            type: 'post',
            async: false,
            success: function(data) {            	       
            	if(data.code==200){
            		closeLayer();
            	}else{
            		
            	}            
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