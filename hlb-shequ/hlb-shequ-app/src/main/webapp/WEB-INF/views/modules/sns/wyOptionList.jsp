<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选项表管理</title>
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
        	window.location.href = "${ctx}/sns/wyOption/form";
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyQuestionnaire/list">问卷调查表列表</a></li>
		<li><a href="${ctx}/sns/wyClassification">分类表列表</a></li>
		<li><a href="${ctx}/sns/wySubject/">题目表列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyOption/">选项表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wyOption" action="${ctx}/sns/wyOption/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="btns">
				<input onclick="add()" class="btn btn-primary" type="button" value="添加"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>选项id</th> -->
				<th>排序值</th>
				<th>题目</th>
				<th>选项内容</th>
				<th>权重</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyOption">
			<tr>
				<%-- <td><a>${wyOption.optionid}</a></td> --%>
				<td><a>${wyOption.sortval}</a></td>
				<td style="text-align: center;"><a>${wyOption.title}</a></td>
				<td><a>${wyOption.content}</a></td>
				<td style="text-align: right;"><a>${wyOption.weight}</a></td>
				<td style="text-align: center;"><fmt:formatDate value="${wyOption.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;"><fmt:formatDate value="${wyOption.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;">
    				<a href="${ctx}/sns/wyOption/form?optionid=${wyOption.optionid}&type=edit">修改</a>
					<a href="${ctx}/sns/wyOption/delete?optionid=${wyOption.optionid}" onclick="return confirmx('确认要删除该选项表吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>