<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>楼盘表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("tr").click(function(){
			
				
			});
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function add(){
        	var source = $("#source").val();
        	var wyid = $("#wyid").val();
        	if(source == null || source == ""){
        		alert("来源不能为空");
        		return;
        	}
        	window.location.href = "${ctx}/sns/wyBuilding/update?source="+source+"&wyid="+wyid;
        }
        function updateAll(){
        	var href = "${ctx}/sns/wyBuilding/update";
        	return confirmx('确认要全部更新吗？该操作将会清除原有数据！', href);
        }
        
        
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="wyBuilding" action="${ctx}/sns/wyBuilding/getBuildsBySource" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="source" type="hidden" value="${wyBuilding.source}"/>
		<ul class="ul-form">			
			<li><label>物业名称：</label>
				<form:input path="wymc" id="wymc" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns">				
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>物业编号</th>
				<th>物业名称</th>
				<th>省份</th>
				<th>城市</th>
				<th>区县</th>
				<th>详细地址</th>				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBuilding">
			<tr>
				<td>
					<input type="checkbox" name="selectId" value="${wyBuilding.buildingId}" data-value-name="${wyBuilding.wymc}"/>
				</td>
				<td><a>${wyBuilding.wybh}</a></td>
				<td><a>${wyBuilding.wymc}</a></td>
				<td><a>${wyBuilding.province}</a></td>
				<td><a>${wyBuilding.city}</a></td>
				<td><a>${wyBuilding.area}</a></td>
				<td><a>${wyBuilding.address}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>