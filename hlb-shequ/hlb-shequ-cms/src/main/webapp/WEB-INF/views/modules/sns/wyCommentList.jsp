<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评论和回复表管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyComment/">评论列表</a></li>
<%-- 		<shiro:hasPermission name="sns:wyComment:edit"><li><a href="${ctx}/sns/wyComment/form">评论和回复表添加</a></li></shiro:hasPermission>
 --%>	</ul>
	<form:form id="searchForm" modelAttribute="wyComment" action="${ctx}/sns/wyComment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务类型：</label>
				<form:select path="bizType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发表人：</label>
				<form:input path="publisherMember.memberName" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务类型</th>
				<th>发表人</th>
				<th>回复谁</th>
				<th>是否为回复</th>
				<th>评论内容</th>
				<th>是否已审核</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyComment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyComment">
			<tr>
				<td>
					<%-- <a href="${ctx}/sns/wyComment/form?id=${wyComment.id}"> --%>
						${wyComment.bizType}
					<!-- </a> -->
				</td>
				<td>
					${wyComment.publisherMember.memberName}
				</td>
				<td>
					${wyComment.relationMember.memberName}
				</td>
				<td>
					${wyComment.beReply?"是":"否"}
				</td>
				<td>
					${wyComment.comment}
				</td>
				<td>${wyComment.audit?"是":"否"}</td>
				<td>
					<fmt:formatDate value="${wyComment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyComment:edit"><td>
					<a href="${ctx}/sns/wyComment/audit?id=${wyComment.id}" onclick="return confirmx('确认审核通过【<font color=\'red\'>${wyComment.comment}</font>】？', this.href)">审核</a>
    				<%-- <a href="${ctx}/sns/wyComment/form?id=${wyComment.id}">修改</a> --%>
					<a href="${ctx}/sns/wyComment/delete?id=${wyComment.id}" onclick="return confirmx('确认要删除该评论和回复表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>