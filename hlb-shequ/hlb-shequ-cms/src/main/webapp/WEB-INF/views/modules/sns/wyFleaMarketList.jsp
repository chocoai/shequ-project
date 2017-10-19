<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跳蚤市场管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyFleaMarket/">跳蚤市场列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyFleaMarket:edit"><li><a href="${ctx}/sns/wyFleaMarket/form">跳蚤市场添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyFleaMarket" action="${ctx}/sns/wyFleaMarket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>物品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>物品名称</th>
				<th>发布人</th>
				<th>物品描述</th>
				<th>价格</th>
				<th>是否爱心赠送</th>
				<th>评论数</th>
				<th>点赞数</th>
				<!-- <th>经度</th>
				<th>维度</th> -->
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyFleaMarket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyFleaMarket">
			<tr>
				<td><%-- <a href="${ctx}/sns/wyFleaMarket/form?id=${wyFleaMarket.id}"> --%>${wyFleaMarket.goodsName}<!-- </a> --></td>
				<td>${wyFleaMarket.member.memberName}</td>
				<td>${wyFleaMarket.goodsDesc}</td>
				<td>${wyFleaMarket.price}</td>
				<td>${fns:getDictLabel(wyFleaMarket.giftGiving, "yes_no", "")}</td>
				<td>${wyFleaMarket.commentNum}</td>
				<td>${wyFleaMarket.dianzanNum}</td>
				<%-- <td>${wyFleaMarket.longitude}</td>
				<td>${wyFleaMarket.latitude}</td> --%>
				<td>
					<fmt:formatDate value="${wyFleaMarket.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyFleaMarket:edit"><td>
					<a href="${ctx}/sns/wyFleaMarket/audit?id=${wyFleaMarket.id}" onclick="return confirmx('确认要审核【<font color=\'red\'>${wyFleaMarket.goodsName}</font>】吗？', this.href)">审核</a>
    				<%-- <a href="${ctx}/sns/wyFleaMarket/form?id=${wyFleaMarket.id}">修改</a> --%>
					<a href="${ctx}/sns/wyFleaMarket/delete?id=${wyFleaMarket.id}" onclick="return confirmx('确认要删除该跳蚤市场吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>