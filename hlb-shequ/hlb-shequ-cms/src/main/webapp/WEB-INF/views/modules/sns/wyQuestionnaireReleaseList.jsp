<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问卷发布表管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyQuestionnaireRelease/">问卷发布表列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyQuestionnaireRelease:edit"><li><a href="${ctx}/sns/wyQuestionnaireRelease/form">问卷发布表添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyQuestionnaireRelease" action="${ctx}/sns/wyQuestionnaireRelease/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>调查问卷</th>
				<th>参与人数</th>
				<th>运行状态</th>
				<th>创建时间</th>
				<th>结束时间</th>
				<shiro:hasPermission name="sns:wyQuestionnaireRelease:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyQuestionnaireRelease">
			<tr>
				<td><a>${wyQuestionnaireRelease.title}</a></td>
				<td><a>${wyQuestionnaireRelease.num}</a></td>
				<c:if test="${wyQuestionnaireRelease.runstatus == 0}">
				<td><a style="color:red">停止中</a></td>
				</c:if>
				<c:if test="${wyQuestionnaireRelease.runstatus == 1}">
				<td><a>运行中</a></td>
				</c:if>
				<td><fmt:formatDate value="${wyQuestionnaireRelease.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td><fmt:formatDate value="${wyQuestionnaireRelease.endtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<shiro:hasPermission name="sns:wyQuestionnaireRelease:edit"><td>
    				<a href="${ctx}/sns/wyQuestionnaireRelease/form?releaseId=${wyQuestionnaireRelease.releaseId}&type=edit">修改</a>
					<a href="${ctx}/sns/wyQuestionnaireRelease/delete?releaseId=${wyQuestionnaireRelease.releaseId}" onclick="return confirmx('确认要删除该问卷发布表吗？', this.href)">删除</a>
					<c:if test="${wyQuestionnaireRelease.runstatus == 0}"><a href="${ctx}/sns/wyQuestionnaireRelease?runstatus=1&releaseId=${wyQuestionnaireRelease.releaseId}" onclick="return confirmx('确认要启动该问卷发布表吗？', this.href)">启动</a></c:if>
					<c:if test="${wyQuestionnaireRelease.runstatus == 1}"><a href="${ctx}/sns/wyQuestionnaireRelease?runstatus=0&releaseId=${wyQuestionnaireRelease.releaseId}" onclick="return confirmx('确认要停止该问卷发布表吗？', this.href)">停止</a></c:if>
					<a href="${ctx}/sns/wyQuestionnaireRelease/showResult?releaseId=${wyQuestionnaireRelease.releaseId}">查看结果</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>