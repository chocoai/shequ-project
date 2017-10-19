<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户权限</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>	
	<script src="${ctxStatic}/js/jquery.freezeheader.js"></script>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
			
			$("#sysType").change(function(){
				var sysType=$(this).val();
				window.location.href="${ctx}/sys/appUser/auth?uid=${appMenuVo.uid}&gid=${appMenuVo.gid}&sysType="+sysType;
			});
		});
		
		function roleDetail(id){
			var sysType = $("#sysType").val();
			var src = "${ctx}/sys/role/form?id="+id+"&isView=true";
			$.layer({
			  type: 2,
			  shade: [0.8, '#393D49'],
			  fix: false,
			  title: "查看角色详情",
			  maxmin: true,
			  iframe: {src : src},
			  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8],
			  close: function(index){
			   // layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});
		}
	</script>
	<style type="text/css">
		.fix1{
			position: fixed;
		    width: 100%;
		    background-color: #FFF;
		}
		.distance_fix1{
			margin-top: 40px;
		}
		.thead{

		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs fix1" >
		<li class="active"><a href="${ctx}/sys/appUser/authInfo">我的权限</a></li>
	</ul><br/> 
	<form:form id="inputForm" modelAttribute="appMenuVo" method="post" class="form-horizontal distance_fix1">
		<form:hidden path="uid"/>
		<form:hidden path="gid"/>
		<sys:message content="${message}"/>		
		<div class="container-fluid">			
			<div class="row-fluid">				
				<div class="span8">										
					<table id="treeTable" data-toggle="table1" data-height="668" class="table table-striped table-bordered table-hover">
						<thead >
							<tr>
								<th><div>菜单名称</div></th>								
								<th><div>查看</div></th>
								<th><div>新增</div></th>
								<th><div>修改</div></th>
								<th><div>删除</div></th>
								<th><div>执行</div></th>
								<th><div>审核</div></th>
								<th><div>打印</div></th>
								<th><div>导出</div></th>
								<th><div>导入</div></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${volist}" var="menu" varStatus="status">
							<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
								<td nowrap>${menu.name} <c:if test="${not empty menu.href}"></c:if></td>		
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.view}"><input class="row${status.index} column1" onclick="cancel('row${status.index}')" name="permission" type="checkbox" value="${menu.view}" ${menu.viewChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.add}"><input class="row${status.index} column2" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.add}" ${menu.addChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.edit}"><input class="row${status.index} column3" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.edit}" ${menu.editChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.del}"><input class="row${status.index} column4" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.del}" ${menu.delChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.exec}"><input class="row${status.index} column5" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.exec}" ${menu.execChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.audit}"><input class="row${status.index} column6" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.audit}" ${menu.auditChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.print}"><input class="row${status.index} column7" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.print}" ${menu.printChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.export}"><input class="row${status.index} column8" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.export}" ${menu.exportChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.daoru}"><input class="row${status.index} column9" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.daoru}" ${menu.daoruChecked?'checked="checked"':''} disabled="disabled"/></c:if></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>				
				</div>
				<div class="span4">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>选择</th>
								<th>角色名称</th>
								<th>英文名称</th>
								<th>操作</th>							
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${appRoleList}" var="role">
							<tr>
								<td><input name="role" type="checkbox" value="${role.id}" ${role.permission eq role.id ?'checked="checked"':''} disabled="disabled"/></td>
								<td>${role.name}</td>
								<td>${role.enname}</td>		
								<td><a href="javascript:;" onclick="roleDetail('${role.id}')">详情</a></td>					
							</tr>	
						</c:forEach>					
						</tbody>
					</table>				
				</div>
			</div>
		</div>		
		
	</form:form>
	<script type="text/javascript">
		$(document).ready(function () {
            $("#treeTable").freezeHeader({ 'height': '600px' });
        });
        function toSelect(jq){
        	var cas = $(jq).attr("class");
        	if($(jq).attr("checked")){
        		$("."+cas).attr("checked", true);
        		$(".column1").attr("checked", true);
        	}else{
        		$("."+cas).attr("checked", false);
        	}
        }
        function check(row){
        	$("."+row+".column1").attr("checked", true);
        }
        function cancel(row){
        	if(!$("."+row+".column1").attr("checked")){
        		$("."+row).attr("checked", false);
        	}
        }
	</script>
</body>
</html>