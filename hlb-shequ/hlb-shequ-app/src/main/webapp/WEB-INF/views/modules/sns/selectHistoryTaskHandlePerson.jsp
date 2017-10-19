<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">		
		
		
	</script>
</head>
<body>	
<br/>
	<form:form id="inputForm" modelAttribute="wyActCandidate" action="" method="post" class="form-horizontal">
		<form:hidden path="procDefId"/>
		<form:hidden path="taskId"/>
		<form:hidden path="source"/>
		<div class="control-group">
			<label class="control-label">选择历史节点:</label>
			<div class="controls">
				<c:forEach items="${actList}" var="act" varStatus="status">
					<span>
						<input id="historyActivitiId${status.index}" name="historyActivitiId" class="required" type="radio" value="${act.taskDefKey}" ${(act.taskDefKey eq wyActCandidate.sameTaskId)?'checked':''}>
						<label for="historyActivitiId${status.index}"><h4>${act.taskName}</h4></label>
					</span><br/>
				</c:forEach>				
			</div>
		</div>	
	</form:form>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="saveHistoryHandler();">&nbsp;&nbsp;
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeLayer()">
	</div>	
	<script type="text/javascript">
		function saveHistoryHandler(){			
			var formdata=$("#inputForm").serialize();
			$.ajax({
	            url: '${ctx}/sns/wyActCandidate/selectHistoryTaskHandlePersonSave',
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
			
			//保存处理类的请求		
						
		}
		
		function closeLayer(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引			
			parent.layer.close(index);
		}
	</script>	
</body>
</html>