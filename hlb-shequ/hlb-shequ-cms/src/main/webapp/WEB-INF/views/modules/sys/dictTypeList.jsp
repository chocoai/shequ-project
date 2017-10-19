<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("td").css("background-color","#FFF");
        	var firstTr;
        	var allTrs=$("#contentTable").find("tbody tr");        	
        	$.each(allTrs,function(index,item){        	        	
        		if(index==0){
        			firstTr=this;
        		}
        	});
        	//初始化第一个分类
        	var type=$(firstTr).attr("data-type");        	
        	window.parent.frames["dict"].location.href = "${ctx}/sys/dict?type="+type; 
        	$(firstTr).find("td").css("background-color","#CCC");        	
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function selectRow(type,dom){
        	//先重置所有颜色
        	$(dom).parent().find("td").css("background-color","#FFF");        	
        	//在赋值每一行
        	$(dom).find("td").css("background-color","#CCC");
        	window.parent.frames["dict"].location.href = "${ctx}/sys/dict?type="+type; 
        }
        
        
	</script>
	<style>
		.color-select{
			background-color:red;
			
		}
		.table th, .table td{
			padding: 4px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dictType/">字典分类列表</a></li>
		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dictType/form">字典分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dictType" action="${ctx}/sys/dictType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered">
		<thead>
			<tr>
			 	<th>分类编码</th>
				<th>分类名称</th>				
				<shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dictType">
			<tr data-id="${dictType.id}" data-type="${dictType.value}" onclick="selectRow('${dictType.value}',this)">
				<td>${dictType.code}</td>
				<td>
					${dictType.name}
				</td>				
				<shiro:hasPermission name="sys:dict:edit"><td style="text-align: center;">
    				<a href="${ctx}/sys/dictType/form?id=${dictType.id}">修改</a>
					<%-- <a href="${ctx}/sys/dictType/delete?id=${dictType.id}" onclick="return confirmx('确认要删除该字典分类吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>