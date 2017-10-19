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
		<div class="span12">
			<div class="tabbable" id="tabs-59696">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-856083" data-toggle="tab">选择待办人</a>
					</li>
					<li>
						<a href="#panel-320647" data-toggle="tab">指定处理人</a>
					</li>					
				</ul>
				 <form class="form-horizontal" action="${ctx}/sns/wyActCandidate/saveWyActCandidate">	
				 <form:form id="wyActShowFrom" modelAttribute="wyActFormDto" action="${ctx}/sns/wyActCandidate/saveWyActCandidate" method="post" class="form-horizontal">	
				<div class="tab-content">
					<div class="tab-pane active" id="panel-856083">						 
						  	   <input type="hidden" name="taskId" value="${activitiId}"/>
						  	   <input type="hidden" name="procDefId" value="${procDefinitionId}"/>
					           <div class="control-group">
					           
					             <label class="control-label" for="assignees1">待办人员1</label>
					             <div class="controls">
					             	
					             						             	
					               	<input id="assignees1" type="text" name="assignees" value="${assignees[0]}">
					             </div>
					           </div>
					           <div class="control-group">
					             <label class="control-label" for="assignees2">待办人员2</label>
					             <div class="controls">
					               <input id="assignees2" type="text" name="assignees" value="${assignees[1]}">
					             </div>
					           </div>
					           <div class="control-group">
					             <label class="control-label" for="assignees3">待办人员3</label>
					             <div class="controls">
					               <input id="assignees3" type="text" name="assignees" value="${assignees[2]}">
					             </div>
					           </div>
					</div>
					<div class="tab-pane" id="panel-320647">
						和某个节点相同的处理人：
			           <div class="controls">
			           		<c:forEach items="${actList}" var="act" varStatus="status">
							<span><input id="actId${status.index}" name="actId" class="required" type="radio" value="${act.taskDefKey}"><label for="actId${status.index}">${act.taskName}</label></span><br/>
							</c:forEach>
						</div>	
						和发起人相同的处理人：
						<div class="controls">
							<span><input id="actId" name="actId" class="required" type="radio" value="2"><label for="actId">指定为发起人</label></span><br/>
						</div>		
					</div>				
			</div>				
		</div>	
			
		</div>
	</div>
</div>
<div class="form-actions">
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存">&nbsp;
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)">
</div>       
</form:form>          
</body>
</html>