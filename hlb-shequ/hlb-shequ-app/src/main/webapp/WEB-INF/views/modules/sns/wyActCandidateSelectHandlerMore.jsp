<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body> 
<div class="container-fluid">
	<div class="row-fluid">
		<br/>
		<c:forEach items="${pvmTransitionDtoList}" var="pvm" varStatus="status">
		<input class="btn" type="button" value="从【${pvm.source}】到【${pvm.destination}】" onclick="setHandler('${wyActCandidate.procDefId}','${wyActCandidate.taskId}','${pvm.sourceId}')"><br/><br/>
		</c:forEach>
	</div>
</div>       
<div class="form-actions">
 	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeLayer()">
</div>
<script type="text/javascript">
	function setHandler(procDefId,taskId,source){
		var url= "${ctx}/sns/wyActCandidate/selectHandler?procDefId=" + procDefId + "&taskId=" + taskId + "&source=" + source+"&more=true";
		console.info("url="+url);
		window.location.href = url;
	}
	
	function closeLayer(){	  		
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		 parent.layer.close(index);
	}
</script>
</body>
</html>