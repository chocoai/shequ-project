<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
		});
		
        function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
		function updateCategory(id, category){
			$.jBox($("#categoryBox").html(), {title:"设置业务分类", buttons:{"关闭":true}, submit: function(){}});
			$("#categoryBoxId").val(id);
			$("#categoryBoxCategory").val(category);
		}
		
		function setAssign(procDefinitionId,titleName){	
	 		var src = "${ctx}/sns/wyActCandidate/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
			$.layer({
			  type: 2,
			  shade: [0.8, '#393D49'],
			  fix: false,
			  title: titleName,
			  maxmin: true,
			  iframe: {src : src},
			  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
			  close: function(index){
			   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});
		}
	
	function setHandler(procDefinitionId,titleName){
 		var src = "${ctx}/sns/wyActHandler/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
		$.layer({
		  type: 2,
		  shade: [0.8, '#393D49'],
		  fix: false,
		  title: titleName,
		  maxmin: true,
		  iframe: {src : src},
		  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
		  close: function(index){
		   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
	
	function setActForm(procDefinitionId,titleName){	
 		var src = "${ctx}/sns/wyActForm/getGraphTrace?processDefinitionId=" + procDefinitionId + "&titleName=" + titleName;
		$.layer({
		  type: 2,
		  shade: [0.8, '#393D49'],
		  fix: false,
		  title: titleName,
		  maxmin: true,
		  iframe: {src : src},
		  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
		  close: function(index){
		   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
	
	//更新流程别名
	function updateAliasName(procDefId){
		$.jBox($("#setAliasBox").html(), {width:450,height:350,title:"设置流程别名", buttons:{"关闭":true}, submit: function(){}});	
		$("#setAliasProcDefId").val(procDefId);
	}
	
	//显示备注信息
	function viewDetail(id){
		var href = "${ctx}/act/process/getActInfo?procDefId="+id;
		$.ajax({
            type:"POST",
            url : href,
            datatype : "json",
            success : function(result){
           		if(result.code==200){    
           			var data = result.data; 
           			
           			$.jBox($("#viewDetail").html(), {width:450,height:350,title:"查看流程详情", buttons:{"关闭":true}, submit: function(){}});	     			
           			$("#viewDetail-actAliasName").val(data.alias);	
           			$("#viewDetail-description").val(data.remarks);	
           			          			          			
           		}      
            }        
	  	});			
	}
		
</script>
	<script type="text/template" id="categoryBox">
		<form id="categoryForm" action="${ctx}/act/process/updateCategory" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在设置，请稍等...');"><br/>
			<input id="categoryBoxId" type="hidden" name="procDefId" value="" />
			<select id="categoryBoxCategory" name="category">
				<c:forEach items="${wyBizDefList}" var="def">
					<option value="${def.key}">${def.name}</option>
				</c:forEach>
			</select>
			<br/><br/>　　
			<input id="categorySubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
		</form>
	</script>
	<script type="text/template" id="setAliasBox">
		<form id="setAliasForm" action="${ctx}/act/process/updateInfo" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在设置，请稍等...');"><br/>
			<input id="setAliasProcDefId" type="hidden" name="procDefId" value="" />
			流程名称：<input id="actAliasName" type="text" name="actAliasName" value="" class="input-xlarge"/><br/><br/>
			备注说明：<textarea id="description" name="description" rows="6" class="input-xlarge"></textarea>			
			<br/><br/>　　
			<input id="actAliasNameSubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
		</form>
	</script>
	<script type="text/template" id="viewDetail">
		<form id="viewDetail" action="" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在设置，请稍等...');"><br/>
			流程名称：<input id="viewDetail-actAliasName" type="text" name="actAliasName" value="" disabled="disabled" class="input-xlarge"/><br/><br/>
			备注说明：<textarea id="viewDetail-description" name="description" rows="6" class="input-xlarge" disabled="disabled"></textarea>			
			<br/><br/>　　
		</form>
	</script>
</head>
<body>
	<c:set var="user" value="${fns:getUser()}"/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/act/process/">流程管理</a></li>		
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/process/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label for="category">业务分类：</label>
				<form:select path="category" class="input-medium">
					<form:option value="" label="所有分类"/>
					<c:forEach items="${wyBizDefList}" var="def">
						<option value="${def.key}" ${def.key eq act.category?'selected':''}>${def.name}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li><label for="procDefName">流程名称：</label>
				<form:input path="procDefName" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<c:if test="${user.isSuperAdmin}">
			<li><label for="procDefKey">流程key：</label>
				<form:input path="procDefKey" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li><label for="procDefId">流程定义ID：</label>
				<form:input path="procDefId" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			</c:if>			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>	
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程版本</th>
				<th>流程名称</th>
				<!-- <th>流程别名</th> -->
				<th>业务分类</th>
				<c:if test="${user.isSuperAdmin}">
				<th>流程图片</th>
				<th>部署时间</th>
				<!-- <th>流程标识</th>	 -->							
				<th>流程定义ID</th>				
				<th>流程XML</th>
				</c:if>			
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="actDef">				
				<tr>
					<td><b title='流程版本号'>V: ${actDef.version}</b></td>
					<%-- <td>${actDef.name}</td> --%>
					<td><a href="javascript:updateAliasName('${actDef.procDefId}')" title="点击设置流程别名">
						<c:choose>
						<c:when test="${not empty actDef.alias}">
							${actDef.alias}
						</c:when>
						<c:otherwise>
							设置流程别名
						</c:otherwise>					
						</c:choose>
						</a>					
					</td> 
					<td><%-- <a href="javascript:updateCategory('${actDef.procDefId}', '${actDef.category}')" title="点击设置分类">	 --%>
					<c:set var="category2" value="无分类"/>				
					<c:forEach items="${wyBizDefList}" var="def" varStatus="status">
						<c:if test="${def.key eq actDef.category}">
							<c:set var="category2" value="${def.name}"/>
						</c:if>						
					</c:forEach>
					${category2}
					<!-- </a> --></td>
					<c:if test="${user.isSuperAdmin}">
					<td style="text-align: center;"><a target="_blank" href="${ctx}/act/process/resource/read?procDefId=${actDef.procDefId}&resType=image"><%-- ${actDef.diagramResourceName} --%>流程图</a></td>
					<td style="text-align: center;"><fmt:formatDate value="${actDef.deploymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<%-- <td>${actDef.key}</td>	 --%>				
					<td>${actDef.procDefId}</td>
					<td style="text-align: center;"><a target="_blank" href="${ctx}/act/process/resource/read?procDefId=${actDef.procDefId}&resType=xml"><%-- ${actDef.resourceName} --%>流程XML</a></td>
					</c:if>
					<td style="text-align: center;">
						<c:if test="${actDef.suspended}">
							<a href="${ctx}/act/process/update/active?procDefId=${actDef.procDefId}" onclick="return confirmx('确认要激活吗？', this.href)">启用</a>
						</c:if>
						<c:if test="${!actDef.suspended}">
							<a href="${ctx}/act/process/update/suspend?procDefId=${actDef.procDefId}" onclick="return confirmx('确认挂起除吗？', this.href)">暂停</a>
						</c:if>
						<a href='${ctx}/act/process/delete?deploymentId=${actDef.deploymentId}' onclick="return confirmx('确认要删除该流程吗？', this.href)">删除</a>                        
    					<a onclick="setHandler('${actDef.procDefId}','任务设置')" href='#' title="任务设置">任务设置</a>
    					<a onclick="setActForm('${actDef.procDefId}','设置表单')" href='#' title="设置表单">设置表单</a>
    					<a onclick="viewDetail('${actDef.procDefId}','查看详情')" href='#' title="查看详情">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
