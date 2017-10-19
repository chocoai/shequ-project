<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
				
		function selectRow(source,dom){
        	//先重置所有颜色
        	$(dom).parent().find("td").css("background-color","#FFF");        	
        	//在赋值每一行
        	$(dom).find("td").css("background-color","#CCC");
        	$("#selectId").val(source);
        }        
		
	</script>
</head>
<body>	
	<form:form id="searchForm" modelAttribute="urlmap" action="${ctx}/sys/office/searchOrg" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查询内容：</label>
				<form:input path="urlname" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>			
			<li class="clearfix"></li>
		</ul>
	</form:form>	
	<sys:message content="${message}"/>
	<input id="selectId" name="selectId" type="hidden"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>				
				<th>公司编码</th>
				<th>公司名称</th>				
			</tr>
		</thead>
		<tbody id="treeTableList">
		<c:forEach items="${page.list}" var="urlmap">
			<tr onclick="selectRow('${urlmap.urlkey}',this)">
				<td>${urlmap.urlkey}</td>
				<td>${urlmap.urlname}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>