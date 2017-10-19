<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程设计</title>
	<meta name="decorator" content="default"/>
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
		//部署 
		function deploy(modelId){
			top.$.jBox.confirm("确认要部署该流程吗？",'系统提示',function(v,h,f){
				if(v=='ok'){
					var href="${ctx}/act/model/deploy?id="+modelId;
					$.ajax({
			            type:"POST",
			            url : href,
			            datatype : "json",
			            success : function(result){
			           		if(result.code==200){
			           			var data=result.data;	
			           			showTip("流程<font color='red'>"+ data.name +"（版本V："+ data.version +"）"+"</font>部署成功");		           			
			           			//打开新的页签
			           			addTabPage("流程管理","${ctx}/act/process");				           			          			
			           		}      
			            }        
				  	});
				}
			},{buttonsFocus:1, closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			return false;
		}
		
		//查看详情
		function showDetail(id){
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
		<form id="categoryForm" action="${ctx}/act/model/updateCategory" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在分类，请稍等...');"><br/>
			<input id="categoryBoxId" type="hidden" name="id" value="" />
			<select id="categoryBoxCategory" name="category">
				<c:forEach items="${wyBizDefList}" var="def">
					<option value="${def.key}">${def.name}</option>
				</c:forEach>
			</select>
			<br/><br/>　　
			<input id="categorySubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
		</form>
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/act/model/">流程图列表</a></li>
		<li><a href="${ctx}/act/model/create">新建流程图</a></li>
	</ul>	
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/model/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label for="roomno">业务分类：</label>
				<form:select path="category" class="input-medium">
					<form:option value="" label="所有分类"/>
					<c:forEach items="${wyBizDefList}" var="def">
						<option value="${def.key}" ${(def.key eq act.category)?'selected="selected"':''}>${def.name}</option>
					</c:forEach>				
				</form:select>				
			</li>			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<c:set var="user" value="${fns:getUser()}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程版本号</th>				
				<th>流程名称</th>
				<th>业务分类</th>
				<c:if test="${user.isSuperAdmin}">
				<!-- <th>流程标识</th>	 -->			
				<th>流程ID</th>
				</c:if>
				<th>创建时间</th>
				<th>最后更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model">
				<tr>
					<td><b title='流程版本号'>V: ${model.version}</b></td>
					<td>${model.name}</td>
					<td><a href="#" onclick="updateCategory('${model.id}', '${model.category}')" title="点击设置分类">
					<c:forEach items="${wyBizDefList}" var="def">
						<c:if test="${def.key eq model.category}">${def.name}</c:if>
					</c:forEach>					
					</a></td>
					<c:if test="${user.isSuperAdmin}">
					<%-- <td>${model.key}</td>	 --%>				
					<td>${model.id}</td>
					</c:if>
					<td style="text-align: center;"><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td style="text-align: center;"><fmt:formatDate value="${model.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td style="text-align: center;">
						<a href="${ctx}/act/model/update?id=${model.id}">编辑</a>
						<a href="javascript:;" onclick="return deploy('${model.id}')">部署</a>
						<a href="${ctx}/act/model/export?id=${model.id}" target="_blank">导出</a>
	                    <a href="${ctx}/act/model/delete?id=${model.id}" onclick="return confirmx('确认要删除该流程吗？', this.href)">删除</a>	                    
	                    <a href="javascript:;" onclick="return showDetail('${model.id}')">部署</a>
	                    <a href="${pageContext.request.contextPath}/act/rest/service/editor?id=${model.id}" target="_blank">流程图</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>