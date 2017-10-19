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
			<li><label>物业小区：</label>
				<form:select path="wymc" class="input-medium">
					<option value="">全部小区</option>
					<c:forEach items="${list}" var="list">
						<option value="${list.source}_${list.groupId}" ${((list.source eq source)&&(list.groupId eq wyid))?'selected="selected"':''}>${list.wymc}</option>
					</c:forEach>
				</form:select>
			</li>
			<li><label>业务类型：</label>
				<!-- <form:select path="bizType" class="input-medium">
					<form:option value="" label="所有类型"/>
					<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select> -->
				<form:select path="bizType" class="input-medium">
					<form:option value="" label="请选择"/>
					<c:forEach items="${fns:getDictList('comment_type')}" var="dict">
						<option value="${dict.value}" ${(dict.value eq wyComment.bizType)?'selected="selected"':''}>${dict.label}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li>
				<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;--&nbsp;<input id="endDate" name="endDate" type="text" readonly="readonly" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>发表人：</label>
				<form:input path="publisherMember.memberName" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>发表人</th>
				<!-- <th>回复谁</th> -->
				<!-- <th>回复</th> -->
				<th>评论内容</th>
				<th>有效</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sns:wyComment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyComment">
			<tr>
				<td>
					${wyComment.title1}${wyComment.title2}${wyComment.title3}${wyComment.title4}
				</td>
				<td>
					${wyComment.publisherMember.memberName}
				</td>
				<!-- <td>
					${wyComment.relationMember.memberName}
				</td>
				<td style="text-align: center;">
					${wyComment.beReply?"是":"否"}
				</td> -->
				<td>
					${wyComment.comment}
				</td>
				<c:if test="${wyComment.audit == true}">
					<td style="text-align: center;color: green">是</td>
				</c:if>
				<c:if test="${wyComment.audit == false}">
					<td style="text-align: center;color: red">否</td>
				</c:if>
				<td style="text-align: center;">
					<fmt:formatDate value="${wyComment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyComment:edit"><td style="text-align: center;">
					<c:if test="${wyComment.audit}">
						<a style="color: red" href="${ctx}/sns/wyComment/audit?id=${wyComment.id}&bizType=${wyComment.bizType}" onclick="return confirmx('确认禁言该评论【<font color=\'red\'>${wyComment.comment}</font>】？', this.href)">禁言</a>
					</c:if>
					<c:if test="${wyComment.audit == false}">
						<a style="color: green" href="${ctx}/sns/wyComment/audit?id=${wyComment.id}&bizType=${wyComment.bizType}" onclick="return confirmx('确认恢复该评论【<font color=\'red\'>${wyComment.comment}</font>】？', this.href)">恢复</a>
					</c:if>
    				<%-- <a href="${ctx}/sns/wyComment/form?id=${wyComment.id}">修改</a> --%>
					<a href="${ctx}/sns/wyComment/delete?id=${wyComment.id}" onclick="return confirmx('确认要删除该评论吗？', this.href)">删除</a>
					<a href="${ctx}/sns/wyComment/detail?id=${wyComment.id}&relationName=${wyComment.publisherMember.memberName}&title=${wyComment.title1}${wyComment.title2}${wyComment.title3}${wyComment.title4}&bizType=${wyComment.bizType}&wymc=${source}_${wyid}">查看详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>