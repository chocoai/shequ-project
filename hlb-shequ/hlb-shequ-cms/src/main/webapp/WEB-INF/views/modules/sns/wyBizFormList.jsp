<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程表单管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        //设计表单
        function designForm(id){
        	var src="${ctx}/sns/wyBizForm/formDesigner";
        	//打开弹出界面
        	$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: "表单设计",
			  maxmin: true,
			  iframe: {src : src},
			  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
			  close: function(index){
			   	// layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyBizForm/">流程表单列表</a></li>
		<shiro:hasPermission name="sns:wyBizForm:edit"><li><a href="${ctx}/sns/wyBizForm/form">流程表单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wyBizForm" action="${ctx}/sns/wyBizForm/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label for="category">业务类型：</label>
				<form:select path="formType" class="input-medium">
					<form:option value="" label="所有分类"/>
					<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<c:set var="user" value="${fns:getUser()}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编码</th>			
				<th>表单名称</th>
				<th>业务类型</th>
				<th>URL地址</th>
				<c:if test="${user.isSuperAdmin}">
				<th>自定义表单ID</th>
				</c:if>
				<shiro:hasPermission name="sns:wyBizForm:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyBizForm">
			<tr>
				<td>${wyBizForm.code}</td>			
				<td>
					<a href="${ctx}/sns/wyBizForm/form?id=${wyBizForm.id}">
						${wyBizForm.formName}
					</a>
				</td>
				<td>
					<c:set var="formType2" value="无分类"/>				
					<c:forEach items="${wyBizDefList}" var="def" varStatus="status">
						<c:if test="${def.key eq wyBizForm.formType}">
							<c:set var="formType2" value="${def.name}"/>
						</c:if>						
					</c:forEach>
					${formType2}
				</td>
				<td>
					${wyBizForm.formUrl}
				</td>
				<c:if test="${user.isSuperAdmin}">
				<td>${wyBizForm.formId}</td>
				</c:if>				
				<shiro:hasPermission name="sns:wyBizForm:edit"><td style="text-align: center;">
    				<a href="${ctx}/sns/wyBizForm/form?id=${wyBizForm.id}">修改</a>
					<a href="${ctx}/sns/wyBizForm/delete?id=${wyBizForm.id}" onclick="return confirmx('确认要删除该流程表单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>