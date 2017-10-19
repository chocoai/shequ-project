<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题目表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var subjectid = "${subjectid}";
			$("#"+subjectid+" td").each(function () {                
                $(this).css("background-color", "#ffe1e1");
            });
             $("tr td").not("tr td:last-child").bind('click',function(){
            	$("#"+subjectid+" td").each(function () {             
	                $(this).css("background-color", "#FFF");
	            });
            	/* $(this).parent().css("background-color", "#ffe1e1"); */
                subjectid = $(this).parent().attr("id"); 
                $("#"+subjectid+" td").each(function () {             
	                $(this).css("background-color", "#ffe1e1");
	            });
	            $.ajax({
					url : ctx+"/sns/wySubject/changeid",
					type : 'post',
					data : {"subjectid":subjectid},
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
        	window.location.href = "${ctx}/sns/wySubject/form";
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyQuestionnaire/list">问卷调查表列表</a></li>
		<li><a href="${ctx}/sns/wyClassification">分类表列表</a></li>
		<li class="active"><a href="${ctx}/sns/wySubject/">题目表列表</a></li>
		<li><a href="${ctx}/sns/wyOption/">选项表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wySubject" action="${ctx}/sns/wySubject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>title：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> --%>
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
				<!-- <th>题目表id</th> -->
				<th>排序值</th>
				<th>分类名称</th>
				<th>题目名称</th>
				<th>题目类型</th>
				<th>必填字段</th>
				<th>状态值</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wySubject">
			<tr id="${wySubject.subjectid}">
				<%-- <td><a>${wySubject.subjectid}</a></td> --%>
				<td><a>${wySubject.sortval}</a></td>
				<td style="text-align: center;"><a>${wySubject.classificationname}</a></td>
				<td style="text-align: center;"><a>${wySubject.title}</a></td>
				<c:if test="${wySubject.type == 0}">
				<td style="text-align: center;"><a>单选题</a></td>
				</c:if>
				<c:if test="${wySubject.type == 1}">
				<td style="text-align: center;"><a>多选题</a></td>
				</c:if>	
				<c:if test="${wySubject.type == 2}">
				<td style="text-align: center;"><a>填空题</a></td>
				</c:if>	
				<c:if test="${wySubject.notnull == 1}">
				<td style="text-align: center;"><a>是</a></td>
				</c:if>
				<c:if test="${wySubject.notnull == 0}">
				<td style="text-align: center;"><a>否</a></td>
				</c:if>
				<c:if test="${wySubject.status == 1}">
				<td style="text-align: center;"><a>有效</a></td>
				</c:if>
				<c:if test="${wySubject.status == 0}">
				<td style="text-align: center;"><a>无效</a></td>
				</c:if>
				<td style="text-align: center;"><fmt:formatDate value="${wySubject.createtime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;"><fmt:formatDate value="${wySubject.updatetime}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
				<td style="text-align: center;">
    				<a href="${ctx}/sns/wySubject/form?subjectid=${wySubject.subjectid}&type1=edit">修改</a>
					<a href="${ctx}/sns/wySubject/delete?subjectid=${wySubject.subjectid}" onclick="return confirmx('确认要删除该题目表吗？', this.href)">删除</a>
					<%-- <c:if test="${wySubject.type != 2}">
						<a href="${ctx}/sns/wyOption?subjectid=${wySubject.subjectid}&title=${wySubject.title}&flag=savesession" >查看题目列表</a>
					</c:if>	 --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>