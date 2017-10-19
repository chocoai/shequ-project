<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>	
	<script src="${ctxStatic}/js/jquery.freezeheader.js"></script>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<style type="text/css">
		th {
			padding: 4px !important;
		}
		td {
			padding: 3.5px !important;
		}
	</style>
	<script type="text/javascript">
		var orgTree;
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
			$("#treeTable").freezeHeader({ 'height': '600px' });			
			
			//组织机构选择			
			var setting = {
				check:{enable:true,nocheckInherit:true},
				view:{selectedMulti:false},
				data:{simpleData:{enable:true}},
				checkable:true,
				callback:{
					beforeClick:function(id, node){
						orgTree.checkNode(node, !node.checked, true, true);
						return false;
					},
					beforeExpand: function(treeId, treeNode){
						//定义在展开前获取动态节点数据
						
					},
					onCheck: function (event, treeId, treeNode){						
						var changedNodes = orgTree.getCheckedNodes(true);
						for ( var i=0; i < changedNodes.length; i++){  
							var node = changedNodes[i];  					    	
							var halfCheck = node.getCheckStatus();
							if(halfCheck.half){
								console.info(node);
								orgTree.checkNode(node, false, false);								
							}
					    }  					
					}
				},
				async: {
					enable: true,
					url:"${ctx}/sns/wyActCandidate/getGroupStaff",
					autoParam:["id", "pId", "name"],
					otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				}
			};
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
				return childNodes;
			}
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${orgTreeList}" var="dto">{id:"${dto.id}", pId:"${dto.pid}", name:"${dto.name}",nocheck:"${dto.nocheck}"},
		            </c:forEach>];
			// 初始化树结构
			orgTree = $.fn.zTree.init($("#groupTree"), setting, zNodes);
			// 不选择父节点
			orgTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
			// 默认选择节点
			var ids = [
					<c:forEach items="${appUserScopeList}" var="userScope">{id:"${userScope.groupId}"},
		            </c:forEach>];
			for(var i=0; i<ids.length; i++) {				
				var node = orgTree.getNodeByParam("id", ids[i].id);
				try{
					orgTree.checkNode(node, true, false);
					orgTree.expandNode(node, true, true,true,false);
				}catch(e){}
			}
			
			
		});
		 function toSelect(jq, rc){
        	var cas = $(jq).attr("class");
        	if($(jq).attr("checked")){
        		var clas = new Array();
        		$("."+cas).each(function(){
        			$(this).attr("checked", true);
        			clas = $(this).attr("class").split(" ");
        			$(".column1."+clas[0]).attr("checked", true);
        		});
        	}else{
        		$("."+cas).attr("checked", false);
        		if(cas == "column1"){
        			 $("table :checkbox").attr("checked",false);
        		}
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
		
		function roleDetail(id){
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
		
		//提交验证
        function submitCheck(){
        	//-------部门选择
			var orgIds = [], orgNodes = orgTree.getCheckedNodes(true);
			for(var i=0; i<orgNodes.length; i++) {
				var halfCheck = orgNodes[i].getCheckStatus();	
				var id=orgNodes[i].id;				
				//全选的则添加进去
				if(!halfCheck.half && (id.indexOf("G_")  >-1 || id.indexOf("WY_") >-1)){
					if(id.indexOf("WY_") >-1){
						id = id + "_" + orgNodes[i].pId;
					}
					orgIds.push(id);
				}
			}
			console.info(orgIds);
			$("#groupIds").val(orgIds);
			
			return true;
        }
	</script>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/appUser/auth?uid=${appMenuVo.uid}&gid=${appMenuVo.gid}">授权<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appMenuVo" action="${ctx}/sys/appUser/authSave" method="post" class="form-horizontal">
		<form:hidden path="uid"/>
		<form:hidden path="gid"/>
		<sys:message content="${message}"/>
		<div class="container-fluid" style="width: 1500px;">
			<div class="row-fluid">
				<div class="span8">
					<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
						<thead>
							<tr>
								<th>菜单名称</th>
								<c:forEach items="${permissionSet}" var="perm">
								<th>${perm}</th>
								</c:forEach>
								<th>查看<input class="column1" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>新增<input class="column2" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>修改<input class="column3" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>删除<input class="column4" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>执行<input class="column5" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>审核<input class="column6" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>打印<input class="column7" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>导出<input class="column8" type="checkbox" onclick="toSelect(this, 1)"/></th>
								<th>导入<input class="column9" type="checkbox" onclick="toSelect(this, 1)"/></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${volist}" var="menu" varStatus="status">
							<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
								<td nowrap>${menu.name} <c:if test="${not empty menu.href}"><input class="row${status.index}" type="checkbox" onclick="toSelect(this, 2)"/></c:if></td>								
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.view}"><input class="row${status.index} column1" onclick="cancel('row${status.index}')" name="permission" type="checkbox" value="${menu.view}" ${menu.viewChecked?'checked="checked"':''} /></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.add}"><input class="row${status.index} column2" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.add}" ${menu.addChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.edit}"><input class="row${status.index} column3" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.edit}" ${menu.editChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.del}"><input class="row${status.index} column4" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.del}" ${menu.delChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.exec}"><input class="row${status.index} column5" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.exec}" ${menu.execChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.audit}"><input class="row${status.index} column6" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.audit}" ${menu.auditChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.print}"><input class="row${status.index} column7" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.print}" ${menu.printChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.export}"><input class="row${status.index} column8" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.export}" ${menu.exportChecked?'checked="checked"':''}/></c:if></td>
								<td style="text-align:center;"><c:if test="${not empty menu.href && not empty menu.daoru}"><input class="row${status.index} column9" onclick="check('row${status.index}')" name="permission" type="checkbox" value="${menu.daoru}" ${menu.daoruChecked?'checked="checked"':''}/></c:if></td>
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
								<td><input name="role" type="checkbox" value="${role.id}" ${role.permission eq role.id ?'checked="checked"':''}/></td>
								<td>${role.name}</td>
								<td>${role.enname}</td>		
								<td><a href="javascript:;" onclick="roleDetail('${role.id}')">详情</a></td>					
							</tr>	
						</c:forEach>					
						</tbody>
					</table>	
					<div class="control-group" style="margin-left: -50px;border-bottom:0;">
						<label class="control-label">数据范围：</label>						
						<div id="groupTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
						<form:hidden path="groupIds"/>		
					</div>
								
				</div>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return submitCheck();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>