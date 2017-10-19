<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程引用设置</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function showActPic(id,name,version){
			var url = "${ctx}/act/process/resource/read?resType=image&procDefId="+id;
			$.layer({
			  type: 2,
			  shade: [0.8, '#393D49'],
			  fix: false,
			  title: "查看流程图 <font color='red'>"+name+"（版本V："+ version +"）</font>",
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8]
			});
		}
		
		//保存选中的流程
		function saveSelectAct(){
			var selectedId = $("#contentTable").find("input[name='selectedId']:checked").val();
			var category = $("#category").val();
			var formdata = {
				actId : selectedId,
				category : category				
			};
			if(selectedId){
				$.ajax({
		            type:"POST",
		            url : "${ctx}/sns/wyReBizAct/save",
		            data : formdata,
		            datatype : "json",
		            success : function(result){
		            	console.info(result);
		           		 if(result.code==200){
		           		 	var data = result.data;		           		 	
		           		 	//重新刷新页面
		           		 	// $("#searchForm").submit();
		           		 	 showTip("引用流程<font color='red'>"+ data.wyActDef.name +"（版本V："+ data.wyActDef.version +"）"+"</font>成功");	
		           		 }     
		            }        
			  	});			
			}			
			return false;
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
			   		
			  }
			});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyReBizAct/setting">流程设置</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wyReBizAct" action="${ctx}/sns/wyReBizAct/setting" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>业务分类：</label>
				<form:select path="category" class="input-medium required">					
					<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
			</li>			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>			
			<tr>
				<th>流程版本</th>
				<th>流程名称</th>
				<th>流程别名</th>
				<th>业务分类</th>				
				<th>部署时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="act">
			<tr>
				<td><b>V：${act.wyActDef.version}</b></td>
				<td>${act.wyActDef.name}</td>
				<td>${act.wyActDef.alias}</td>
				<td>${act.category}</td>				
				<td><fmt:formatDate value="${act.wyActDef.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><%-- <c:if test="${act.editable}"> --%><a onclick="setAssign('${act.wyActDef.procDefId}','设置待办人员')" href='javascript:;' title="点击设置待办人员">设置待办人员</a><%-- </c:if> --%></td>				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>