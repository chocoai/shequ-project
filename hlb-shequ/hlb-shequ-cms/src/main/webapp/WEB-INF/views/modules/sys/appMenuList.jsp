<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 2}).show();
			
			$("#sysType").change(function(){
				var type=$("#sysType").val();
				
				$("#searchForm").submit();
			});
			
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/appMenu/updateSort");
	    	$("#listForm").submit();
    	}
    	
    	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/appMenu?sysType=${appMenu.sysType}">菜单列表</a></li>
		<shiro:hasPermission name="sys:appMenu:edit"><li><a href="${ctx}/sys/appMenu/form?sysType=${appMenu.sysType}">菜单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appMenu" action="${ctx}/sys/appMenu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>子系统名称：</label>
				<form:select path="sysType" class="input-medium">
					<form:options items="${fns:getDictList('sys_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead>
				<tr>
					<th>名称</th>
					<th>屏蔽码</th>
					<th>链接</th>
					<th style="text-align:center;">排序</th>
					<th>可见</th>
					<th>权限标识</th>
					<shiro:hasPermission name="sys:appMenu:edit">
					<th>操作</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}" controller="false">
					<td nowrap>
						<i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i>
						<c:choose>
						<c:when test="${not empty menu.permission}">
						<a href="${ctx}/sys/appMenu/permissionForm?id=${menu.id}&sysType=${appMenu.sysType}">${menu.name}</a>
						</c:when>
						<c:otherwise>
						<a href="${ctx}/sys/appMenu/form?id=${menu.id}&sysType=${appMenu.sysType}">${menu.name}</a>
						</c:otherwise>
						</c:choose>						
					</td>
					<th>${menu.screenCode}</th>
					<td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="sys:appMenu:edit">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission>
						<shiro:lacksPermission name="sys:appMenu:edit">
							${menu.sort}
						</shiro:lacksPermission>
					</td>
					<td style="text-align: center;">${menu.isShow eq '1'?'显示':'隐藏'}</td>
					<td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
					<shiro:hasPermission name="sys:appMenu:edit">
					<td nowrap style="text-align: center;">
						<c:choose>
						<c:when test="${not empty menu.permission}">						
						<a href="${ctx}/sys/appMenu/permissionForm?id=${menu.id}&sysType=${appMenu.sysType}">修改</a>
						</c:when>
						<c:otherwise>
						<a href="${ctx}/sys/appMenu/form?id=${menu.id}&sysType=${appMenu.sysType}">修改</a>
						</c:otherwise>
						</c:choose>
						<a href="${ctx}/sys/appMenu/delete?id=${menu.id}&sysType=${appMenu.sysType}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<c:if test="${empty menu.permission}">	
						<c:choose>	
						<c:when test="${empty menu.href}">			
						<a href="${ctx}/sys/appMenu/form?parent.id=${menu.id}&sysType=${appMenu.sysType}">添加下级菜单</a>
						</c:when>
						<c:otherwise>
						<a href="${ctx}/sys/appMenu/permissionForm?parent.id=${menu.id}&sysType=${appMenu.sysType}" title="添加权限按钮">添加权限</a> 
						</c:otherwise>
						</c:choose>	
						</c:if>
					</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<shiro:hasPermission name="sys:appMenu:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
</body>
</html>