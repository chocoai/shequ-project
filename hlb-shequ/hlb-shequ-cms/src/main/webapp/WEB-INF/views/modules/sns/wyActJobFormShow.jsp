<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务设置</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
		<form id="settingTimeForm" name="settingTimeForm">
		<input type="hidden" name="procDefId" value="${procDefinitionId}"/>
		<input type="hidden" name="taskId" value="${activitiId}"/>
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
		</form>	
		</div>
	</div>
</div>
<div class="form-actions">
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="saveSettingTime()">&nbsp;
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeLayer()">
</div>

<script type="text/javascript">
	function saveSettingTime(){
		//获取form表单数据
		var formdata = $("#settingTimeForm").serialize();	
		$.ajax({
            url: '${ctx}/sns/wyActJob/saveSettingTime',
            data: formdata,
            type: 'post',
            async: false,
            success: function(data) {       	       
            	if(data.code==200){
            	     closeLayer();
            	}else{
            		alert(data.msg);
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