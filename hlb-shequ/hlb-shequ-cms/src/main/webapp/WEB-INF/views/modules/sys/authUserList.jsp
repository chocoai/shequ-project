<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/appUser/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			$("#sysType").change(function(){
				var type=$("#sysType").val();
				
				$("#searchForm").submit();
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/appUser/authList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/appUser/authList">应用平台用户列表</a></li>
	</ul>		
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>姓名</th>
				<th>员工编号</th>
				<th>性别</th>
				<th>归属公司</th>
				<th>归属部门</th>
				<th>授权状态</th>
				<shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.no}</td>
				<td style="text-align: center;">${user.sex}</td>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td style="text-align:center;">${user.authStatus?'已授权':'<font color="red">未授权</font>'}</td>
				<shiro:hasPermission name="sys:user:edit">
					<td style="text-align:center;">
	    				<a href="${ctx}/sys/appUser/auth?uid=${user.id}&gid=${gid}">授权</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>