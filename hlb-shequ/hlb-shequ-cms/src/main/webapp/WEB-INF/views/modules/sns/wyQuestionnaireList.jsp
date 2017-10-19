<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问卷调查表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var questionnaireid = "${questionnaireid}";
			$("#"+questionnaireid+" td").each(function () {                
                $(this).css("background-color", "#ffe1e1");
            });
            $("tr td").not("tr td:last-child").bind('click',function(){
            	$("#"+questionnaireid+" td").each(function () {             
	                $(this).css("background-color", "#FFF");
	            });
            	/* $(this).parent().css("background-color", "#ffe1e1"); */
                questionnaireid = $(this).parent().attr("id"); 
                $("#"+questionnaireid+" td").each(function () {             
	                $(this).css("background-color", "#ffe1e1");
	            });
	            $.ajax({
					url : ctx+"/sns/wyQuestionnaire/changeid",
					type : 'post',
					data : {"questionnaireid":questionnaireid},
					async : false,
					success : function(data) {
						// debugger;
						if (data.data == "success") {
							return;
						}
					}
				});
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function add(){
        	window.location.href = "${ctx}/sns/wyQuestionnaire/form";
        }
        function copy(){
        	var href = "${ctx}/sns/wyQuestionnaire/copy";
        	return confirmx('确认要复制该问卷调查表吗？', href); 	
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyQuestionnaire/">问卷调查表列表</a></li>
		<li><a href="${ctx}/sns/wyClassification">分类表列表</a></li>
		<li><a href="${ctx}/sns/wySubject/">题目表列表</a></li>
		<li><a href="${ctx}/sns/wyOption/">选项表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wyQuestionnaire" action="${ctx}/sns/wyQuestionnaire/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>title：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> --%>
			<li class="btns">
				<shiro:hasPermission name="sns:wyQuestionnaire:edit">
					<input onclick="add()" class="btn btn-primary" type="button" value="添加"/>
					<input onclick="copy()" class="btn btn-primary" type="button" value="复制"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>问卷id</th> -->
				<th>排序值</th>
				<th>问卷名称</th>
				<th>问卷说明</th>
				<th>状态值</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyQuestionnaire:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyQuestionnaire">
			<tr id="${wyQuestionnaire.questionnaireid}">
				<%-- <td><a>${wyQuestionnaire.questionnaireid}</a></td> --%>
				<td><a>${wyQuestionnaire.sortval}</a></td>
				<td><a>${wyQuestionnaire.title}</a></td>
				<td><a>${wyQuestionnaire.explain}</a></td>
				<c:if test="${wyQuestionnaire.status == 1}">
				<td><a>有效</a></td>
				</c:if>
				<c:if test="${wyQuestionnaire.status == 0}">
				<td><a>无效</a></td>
				</c:if>
				<td><fmt:formatDate value="${wyQuestionnaire.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td><fmt:formatDate value="${wyQuestionnaire.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<shiro:hasPermission name="sns:wyQuestionnaire:edit"><td>
    				<a href="${ctx}/sns/wyQuestionnaire/form?questionnaireid=${wyQuestionnaire.questionnaireid}&type=edit">修改</a>
					<a href="${ctx}/sns/wyQuestionnaire/delete?questionnaireid=${wyQuestionnaire.questionnaireid}" onclick="return confirmx('确认要删除该问卷调查表吗？', this.href)">删除</a>
					<%-- <a href="${ctx}/sns/wyClassification?questionnaireid=${wyQuestionnaire.questionnaireid}&title=${wyQuestionnaire.title}&type=savesession">查看分类列表</a> --%>
					<a href="${ctx}/sns/wyQuestionnaire/release?questionnaireid=${wyQuestionnaire.questionnaireid}" onclick="return confirmx('确认要发布该问卷调查表吗？', this.href)">发布</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>