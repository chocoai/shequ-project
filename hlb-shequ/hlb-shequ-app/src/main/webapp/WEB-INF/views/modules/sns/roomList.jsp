<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房屋信息管理</title>
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
		<li class="active"><a href="${ctx}/sns/room/">房屋信息列表</a></li>
		<shiro:hasPermission name="sns:room:edit"><li><a href="${ctx}/sns/room/form">房屋信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="room" action="${ctx}/sns/room/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label for="roomno">房号：</label>
				<form:input path="roomno" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li><label for="roomno">楼盘名称：</label>
				<form:input path="lyname" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			<li><label for="roomno">物业名称：</label>
				<form:input path="wyname" htmlEscape="false" maxlength="100" class="input-medium "/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>房屋id</td>
				<td>公司代码</td>
				<td>物业id</td>
				<td>合同id</td>
				<td>客户id</td>
				<td>物业编号</td>
				<td>物业名称</td>
				<td>楼盘id</td>
				<td>楼盘编号</td>
				<td>楼盘名称</td>
				<td>房号</td>
				<td>合同终止状态</td>
				<td>合同终止日期</td>
				<!-- <td>会员id</td> -->
				<td>会员名称</td>
				<td>创建时间</td>
				<td>更新时间</td>
				<shiro:hasPermission name="sns:room:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="room">
			<tr>
				<td><a>${room.roomid}</a></td>
				<td><a>${room.source}</a></td>
				<td><a>${room.wyid}</a></td>
				<td><a>${room.htid}</a></td>
				<td><a>${room.khid}</a></td>
				<td><a>${room.wyno}</a></td>
				<td><a>${room.wyname}</a></td>
				<td><a>${room.lyid}</a></td>
				<td><a>${room.lyno}</a></td>
				<td><a>${room.lyname}</a></td>
				<td><a>${room.roomno}</a></td>
				<%-- <td><a>${room.terminationstatus}</a></td> --%>
				<c:if test="${room.terminationstatus == 1}">
				<td><a>已终止</a></td>
				</c:if>
				<c:if test="${room.terminationstatus == 0}">
				<td><a>正常</a></td>
				</c:if>
				<td><fmt:formatDate value="${room.terminationdate}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<%-- <td><a>${room.memberid}</a></td> --%>
				<td><a>${room.membername}</a></td>
				<td><fmt:formatDate value="${room.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td><fmt:formatDate value="${room.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<shiro:hasPermission name="sns:room:edit"><td>
    				<a href="${ctx}/sns/room/form?roomid=${room.roomid}&type=edit">修改</a>
					<a href="${ctx}/sns/room/delete?roomid=${room.roomid}" onclick="return confirmx('确认要删除该房屋信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>