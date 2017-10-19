<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物业报修管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var ctx = "${ctx}";
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        $(function() {
			// 跟踪
		    $('.trace').click(graphTrace);
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a style="text-decoration:none" href="${ctx}/sns/wyRepair/">物业报修列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyRepair:edit"><li><a style="text-decoration:none" href="${ctx}/sns/wyRepair/form">物业报修添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyRepair" action="${ctx}/sns/wyRepair/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>物业小区：</label>
				<form:select path="wymc" class="input-medium">
					<option value="">所有小区</option>
					<c:forEach items="${list}" var="list">
						<option value="${list.source}_${list.groupId}" ${((list.source eq source)&&(list.groupId eq wyid))?'selected="selected"':''}>${list.wymc}</option>
					</c:forEach>
				</form:select>
			</li>
			<li>
				<label for="repairtype">报修类型：</label>
				<form:select path="repairtype" class="input-medium">
					<form:option value="" label="所有类型"/>
					<c:forEach items="${fns:getDictList('repair_type')}" var="dict">
						<option value="${dict.value}" ${(dict.value eq wyRepair.repairtype)?'selected="selected"':''}>${dict.label}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li><label>报修人：</label>
				<form:input path="applyname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>报修内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报修类型</th>			
				<th>报修人</th>			
				<th>联系电话</th>			
				<th>会员名称</th>
				<th>详细地址</th>		
				<!-- <th>流程id</th> -->		
				<!-- <th>报修状态</th>	 -->
				<th>报修内容</th>		
				<th>创建时间</th>			
				<th>更新时间</th>			
				<shiro:hasPermission name="sns:wyRepair:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyRepair">
			<tr>
				<td>${fns:getDictLabel(wyRepair.repairtype, "repair_type", "")}</td>			
				<td>${wyRepair.applyname}</td>			
				<td style="text-align: center;">${wyRepair.phone}</td>			
				<td>${wyRepair.memberName}</td>
				<td>${wyRepair.room}</td>		
				<%-- <td><a class="trace" href='#' pid="${wyRepair.procInsId}" title="点击查看流程图">${wyRepair.procInsId}</a></td>		
				<td>${wyRepair.repairstatus}</td>		 --%>	
				<td>${wyRepair.content}</td>
				<td style="text-align: center;"><fmt:formatDate value="${wyRepair.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>	
				<td style="text-align: center;"><fmt:formatDate value="${wyRepair.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>			
				<shiro:hasPermission name="sns:wyRepair:edit">
				<td style="text-align: center;">
    				<%-- <a href="${ctx}/sns/wyRepair/form?id=${wyRepair.id}">修改</a> --%>
					<a href="${ctx}/sns/wyRepair/delete?id=${wyRepair.id}" onclick="return confirmx('确认要删除【<font color=\'red\'>${wyRepair.content}</font>】吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>	
</body>
</html>