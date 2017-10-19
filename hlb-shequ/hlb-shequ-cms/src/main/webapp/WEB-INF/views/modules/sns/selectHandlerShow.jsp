<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js"
	type="text/javascript"></script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="wyRelationCandidate" action="" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="tabbable" id="tabs-270499">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#panel-573269" data-toggle="tab">选择部门</a>
							</li>							
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="panel-573269">
								<div class="control-group">
									<c:forEach items="${wyBizHandlerList}" var="handler">
										
										
										
									</c:forEach>									
								</div>
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit"	value="保 存" onclick="saveCandidateSelectHandler();">&nbsp; 
		<input id="btnCancel" class="btn" type="button" value="返 回"	onclick="closeLayer()">
	</div>
	<script type="text/javascript">
		function closeLayer(){	  		
			 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			 parent.layer.close(index);
		}
		
		
	</script>
</body>	
</html>