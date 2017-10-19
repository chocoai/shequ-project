<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>楼盘表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyBuilding/">楼盘表</a></li>
		<!-- <shiro:hasPermission name="sns:wyBuilding:edit"><li><a href="${ctx}/sns/wyBuilding/form">楼盘表添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="wyBuilding" action="${ctx}/sns/wyBuilding/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>来源：</label>
				<form:input path="source" id="source" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>物业ID：</label>
				<form:input path="wyid" id="wyid" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns">
				<shiro:hasPermission name="sns:wyBuilding:edit">
					<input class="btn btn-primary" type="button" value="添加" onclick="add()"/>
					<input class="btn btn-primary" type="button" value="更新全部" onclick="updateAll()"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th><!-- 楼盘id -->
				<th>来源</th>
				<th>物业id</th>
				<th>省份</th>
				<th>城市</th>
				<th>区县</th>
				<th>详细地址</th>
				<th>物业编号</th>
				<th>物业名称</th>
				<shiro:hasPermission name="sns:wyBuilding:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBuilding">
			<tr>				
				<td><a>${wyBuilding.buildingId}</a></td>
				<td><a>${wyBuilding.source}</a></td>
				<td><a>${wyBuilding.wyid}</a></td>
				<td><a>${wyBuilding.province}</a></td>
				<td><a>${wyBuilding.city}</a></td>
				<td><a>${wyBuilding.area}</a></td>
				<td><a>${wyBuilding.address}</a></td>
				<td><a>${wyBuilding.wybh}</a></td>
				<td><a>${wyBuilding.wymc}</a></td>
				<shiro:hasPermission name="sns:wyBuilding:edit"><td>
    				<!-- <a href="${ctx}/sns/wyBuilding/form?id=${wyBuilding.id}">修改</a> -->
					<a href="${ctx}/sns/wyBuilding/delete?id=${wyBuilding.id}" onclick="return confirmx('确认要删除该楼盘表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>