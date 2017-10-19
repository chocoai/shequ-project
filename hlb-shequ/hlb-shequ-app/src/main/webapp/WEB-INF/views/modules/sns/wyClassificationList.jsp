<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var classificationid = "${classificationid}";
			$("#"+classificationid+" td").each(function () {                
                $(this).css("background-color", "#ffe1e1");
            });
             $("tr td").not("tr td:last-child").bind('click',function(){
            	$("#"+classificationid+" td").each(function () {             
	                $(this).css("background-color", "#FFF");
	            });
            	/* $(this).parent().css("background-color", "#ffe1e1"); */
                classificationid = $(this).parent().attr("id"); 
                $("#"+classificationid+" td").each(function () {             
	                $(this).css("background-color", "#ffe1e1");
	            });
	            $.ajax({
					url : ctx+"/sns/wyClassification/changeid",
					type : 'post',
					data : {"classificationid":classificationid},
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
        	window.location.href = "${ctx}/sns/wyClassification/form";
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyQuestionnaire/list">问卷调查表列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyClassification">分类表列表</a></li>
		<li><a href="${ctx}/sns/wySubject/">题目表列表</a></li>
		<li><a href="${ctx}/sns/wyOption/">选项表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wyClassification" action="${ctx}/sns/wyClassification/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="btns">
				<input onclick="add()" class="btn btn-primary" type="button" value="添加"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>分类id</th> -->
				<th>排序值</th>
				<th>分类名称</th>
				<th>问卷名称</th>
				<th>权值</th>
				<th>状态值</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyClassification">
			<tr id="${wyClassification.classificationid}">
				<%-- <td><a>${wyClassification.classificationid}</a></td> --%>
				<td><a>${wyClassification.sortval}</a></td>
				<td><a>${wyClassification.classificationname}</a></td>
				<td style="text-align: center;"><a>${wyClassification.title}</a></td>
				<td style="text-align: right;"><a>${wyClassification.weight}</a></td>
				<c:if test="${wyClassification.status == 1}">
				<td style="text-align: center;"><a>有效</a></td>
				</c:if>
				<c:if test="${wyClassification.status == 0}">
				<td style="text-align: center;"><a>无效</a></td>
				</c:if>
				<td style="text-align: center;"><fmt:formatDate value="${wyClassification.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;"><fmt:formatDate value="${wyClassification.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;">
    				<a href="${ctx}/sns/wyClassification/form?classificationid=${wyClassification.classificationid}&type=edit">修改</a>
					<a href="${ctx}/sns/wyClassification/delete?classificationid=${wyClassification.classificationid}" onclick="return confirmx('确认要删除该分类表吗？', this.href)">删除</a>
					<%-- <a href="${ctx}/sns/wySubject?classificationid=${wyClassification.classificationid}&classificationname=${wyClassification.classificationname}&flag=savesession" >查看题目列表</a> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>