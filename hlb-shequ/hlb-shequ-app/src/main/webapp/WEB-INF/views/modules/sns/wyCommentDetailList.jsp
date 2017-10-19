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
		<li><a href="${ctx}/sns/wyComment?bizType=${wyComment.bizType}&wymc=${wyComment.wymc}">评论列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyComment?bizType=${wyComment.bizType}">评论回复详情</a></li>
<%-- 		<shiro:hasPermission name="sns:wyComment:edit"><li><a href="${ctx}/sns/wyComment/form">评论和回复表添加</a></li></shiro:hasPermission>
 --%>	</ul>
	<!-- <form:form id="searchForm" modelAttribute="wyComment" action="${ctx}/sns/wyComment/detail?id=${wyComment.id}&relationName=${wyComment.relationName}&title=${wyComment.title}&bizType=${wyComment.bizType}&wymc=${wyComment.wymc}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发表人：</label>
				<form:input path="publisherMember.memberName" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form> -->
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>发表人</th>
				<th>回复谁</th>
				<!-- <th>回复</th> -->
				<th>评论内容</th>
				<th>有效</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyComments">
			<tr>
				<td>
					${wyComment.title}
				</td>
				<td>
					${wyComments.publisherMember.memberName}
				</td>
				<td>
					${wyComment.relationName}
				</td>
				<!-- <td style="text-align: center;">
					${wyComment.beReply?"是":"否"}
				</td>  -->
				<td>
					${wyComments.comment}
				</td>
				<c:if test="${wyComments.audit == true}">
					<td style="text-align: center;color: green">是</td>
				</c:if>
				<c:if test="${wyComments.audit == false}">
					<td style="text-align: center;color: red">否</td>
				</c:if>
				<td style="text-align: center;">
					<fmt:formatDate value="${wyComments.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<c:if test="${wyComment.audit}">
						<a style="color: red" href="${ctx}/sns/wyComment/audit?id=${wyComments.id}&bizType=${wyComments.bizType}&did=${wyComment.id}&drelationName=${wyComment.relationName}&dtitle=${wyComment.title}&dbizType=${wyComment.bizType}&dwymc=${wyComment.wymc}" onclick="return confirmx('确认禁言该评论【<font color=\'red\'>${wyComment.comment}</font>】？', this.href)">禁言</a>
					</c:if>
					<c:if test="${wyComment.audit == false}">
						<a style="color: green" href="${ctx}/sns/wyComment/audit?id=${wyComments.id}&bizType=${wyComments.bizType}&did=${wyComment.id}&drelationName=${wyComment.relationName}&dtitle=${wyComment.title}&dbizType=${wyComment.bizType}&dwymc=${wyComment.wymc}" onclick="return confirmx('确认恢复该评论【<font color=\'red\'>${wyComment.comment}</font>】？', this.href)">恢复</a>
					</c:if>
					<a href="${ctx}/sns/wyComment/delete?id=${wyComments.id}&did=${wyComment.id}&drelationName=${wyComment.relationName}&dtitle=${wyComment.title}&dbizType=${wyComment.bizType}&dwymc=${wyComment.wymc}" onclick="return confirmx('确认要删除该评论吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>