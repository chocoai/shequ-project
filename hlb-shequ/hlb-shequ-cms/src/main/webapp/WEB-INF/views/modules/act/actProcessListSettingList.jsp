<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程设置</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
		});
		function page(n,s){
        	location = '${ctx}/act/process/settingList?pageNo='+n+'&pageSize='+s;
        }
		function updateCategory(id, category){
			$.jBox($("#categoryBox").html(), {title:"设置分类", buttons:{"关闭":true}, submit: function(){}});
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
		
	</script>
	<script type="text/template" id="categoryBox">
		<form id="categoryForm" action="${ctx}/act/process/updateCategory" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在设置，请稍等...');"><br/>
			<input id="categoryBoxId" type="hidden" name="procDefId" value="" />
			<select id="categoryBoxCategory" name="category">
				<c:forEach items="${wyBizDefList}" var="def">
					<option value="${def.key}" ${def.key==category?'selected':''}>${def.name}</option>
				</c:forEach>
			</select>
			<br/><br/>　　
			<input id="categorySubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
		</form>
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/act/process/settingList">流程设置</a></li>		
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/process/settingList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label for="category">分类查找：</label>
				<form:select path="category" class="input-medium">
					<option value="">全部分类</option>
					<c:forEach items="${wyBizDefList}" var="def">
						<option value="${def.key}" ${def.key eq act.category?'selected':''}>${def.name}</option>
					</c:forEach>
				</form:select>
			</li>
			<li><label for="procDefName">流程名称：</label>
				<form:input path="procDefName" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li><label for="procDefKey">流程key：</label>
				<form:input path="procDefKey" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li><label for="procDefId">流程定义ID：</label>
				<form:input path="procDefId" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程分类</th>
				<th>流程名称</th>
				<th>流程图片</th>
				<th>部署时间</th>
				<th>流程标识</th>
				<th>流程版本</th>				
				<th>流程定义ID</th>				
				<th>流程XML</th>				
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="object">
				<c:set var="process" value="${object[0]}" />
				<c:set var="deployment" value="${object[1]}" />
				<tr>
					<td><a href="javascript:updateCategory('${process.id}', '${process.category}')" title="点击设置分类">					
					<c:set var="category2" value="无分类"/>				
					<c:forEach items="${wyBizDefList}" var="def" varStatus="status">
						<c:if test="${def.key eq process.category}">
							<c:set var="category2" value="${def.name}"/>
						</c:if>						
					</c:forEach>
					${category2}
					</a></td>
					<td>${process.name}</td>
					<td><a target="_blank" href="${ctx}/act/process/resource/read?procDefId=${process.id}&resType=image">${process.diagramResourceName}</a></td>
					<td><fmt:formatDate value="${deployment.deploymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${process.key}</td>
					<td><b title='流程版本号'>V: ${process.version}</b></td>
					<td>${process.id}</td>
					<td><a target="_blank" href="${ctx}/act/process/resource/read?procDefId=${process.id}&resType=xml">${process.resourceName}</a></td>
					<td>
						<a onclick="setAssign('${process.id}','设置待办人员')" href='#' title="点击设置待办人员">设置待办人员</a>    					
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
