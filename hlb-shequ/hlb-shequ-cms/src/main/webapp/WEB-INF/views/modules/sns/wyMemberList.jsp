<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>住户信息管理</title>
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
		<li class="active"><a href="${ctx}/sns/wyMember/">住户信息列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyMember:edit"><li><a href="${ctx}/sns/wyMember/form">住户信息添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyMember" action="${ctx}/sns/wyMember/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label for="memberType">住户类型：</label>
				<form:select path="memberType" class="input-medium">
					<form:option value="" label="请选择"/>
					<c:forEach items="${fns:getDictList('membertype')}" var="dict">
						<option value="${dict.value}" ${(dict.value eq wyMember.memberType)?'selected="selected"':''}>${dict.label}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li>
				<label for="status">住户状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="请选择"/>
					<c:forEach items="${fns:getDictList('effective_invalid')}" var="dict">
						<option value="${dict.value}" ${(dict.value eq wyMember.status)?'selected="selected"':''}>${dict.label}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li>
				<label for="status">住户状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="请选择"/>
					<c:forEach items="${fns:getDictList('effective_invalid')}" var="dict">
						<option value="${dict.value}" ${(dict.value eq wyMember.status)?'selected="selected"':''}>${dict.label}</option>
					</c:forEach>				
				</form:select>
			</li>
			<li><label for="memberName">住户名称：</label>
				<form:input path="memberName" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>住户id</th> -->
				<th>住户名称</th>
				<th>住户类型</th>				
				<th>微信openid</th>
				<th>默认房号</th>
				
				<th>关联人</th>
				<th>手机号码</th>
				<th>管理员</th>
				<!-- <th>同步状态</th>
				<th>同步失败原因</th> -->
				<th>创建时间</th>				
				<th>状态</th>
				<!-- <th>黑名单</th> -->
				<shiro:hasPermission name="sns:wyMember:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyMember">
			<tr>
				<!-- <td><a>${wyMember.memberId}</a></td> -->
				<td><a>${wyMember.memberName}</a></td>
				<td style="text-align:center;">${fns:getDictLabel(wyMember.memberType, "membertype", "")}</td>				
				<td><a>${wyMember.openid}</a></td>
				<td><a><!-- ${wyMember.roomid} -->${wyMember.WYName}${wyMember.LYName}${wyMember.roomNo}</a></td>				
				<td><a><!-- ${wyMember.parentMemberId} -->${wyMember.parentMemberName}</a></td>
				<td style="text-align:center;"><a>${wyMember.mobile}</a></td>
				<c:if test="${wyMember.admintype == 0}">
				<td style="text-align:center;"><a>否</a></td>
				</c:if>
				<c:if test="${wyMember.admintype == 1}">
				<td style="text-align:center;"><a>是</a></td>
				</c:if>
				<!-- <c:if test="${wyMember.syncStatus == 0}">
				<td><a>失败</a></td>
				</c:if>
				<c:if test="${wyMember.syncStatus == 1}">
				<td><a>成功</a></td>
				</c:if>
				<td><a>${wyMember.syncDesc}</a></td> -->
				<td style="text-align:center;"><fmt:formatDate value="${wyMember.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>	
				
				<c:if test="${wyMember.status == 0}">
				<td style="text-align:center;"><a>无效</a></td>
				</c:if>
				<c:if test="${wyMember.status == 1}">
				<td style="text-align:center;"><a>有效</a></td>
				</c:if>
				<!-- <td><a>${wyMember.blacklist}</a></td> -->
				<shiro:hasPermission name="sns:wyMember:edit"><td style="text-align:center;">
    				<a href="${ctx}/sns/wyMember/form?memberId=${wyMember.memberId}&type=edit">修改</a>
					<a href="${ctx}/sns/wyMember/delete?memberId=${wyMember.memberId}" onclick="return confirmx('确认要删除该住户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>