<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
		var orgTree;
		var candidateTree; 
		$(document).ready(function(){
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
			orgTree = $.fn.zTree.init($("#orgTree"), setting, zNodes);
			// 不选择父节点
			orgTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
			// 默认选择节点
			var ids = [
					<c:forEach items="${orgList}" var="detail">{id:"${detail.candidate}"},
		            </c:forEach>];
			console.info(ids);
			for(var i=0; i<ids.length; i++) {				
				var node = orgTree.getNodeByParam("id", ids[i].id);
				try{
					orgTree.checkNode(node, true, false);
					orgTree.expandNode(node, true, true,true,false);
				}catch(e){}
			}			
			
			//人员选择
			var setting2 = {
				check:{enable:true,nocheckInherit:true},
				view:{selectedMulti:false},
				data:{simpleData:{enable:true}},
				callback:{
					beforeClick:function(id, node){
						candidateTree.checkNode(node, !node.checked, true, true);
						return false;
					},
					beforeExpand: function(treeId, treeNode){
						//定义在展开前获取动态节点数据
						
					},
					onCheck: function (event, treeId, treeNode){						
						var changedNodes = candidateTree.getCheckedNodes(true);
						for ( var i=0; i < changedNodes.length; i++){  
							var node = changedNodes[i];  					    	
							var halfCheck = node.getCheckStatus();
							if(halfCheck.half){
								console.info(node);
								candidateTree.checkNode(node, false, false);							
							}
					    }  					
					}
				},
				async: {
					enable: true,
					url:"${ctx}/sns/wyActCandidate/getGroupStaff",
					autoParam:["id", "name=n", "level=lv"],
					otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				}
			};
			
			// 用户-菜单
			var zNodes2=[
					<c:forEach items="${candidateTreeList}" var="dto">{id:"${dto.id}", pId:"${dto.pid}", name:"${dto.name}",chkDisabled:"${dto.chkDisabled}"},
		            </c:forEach>];
			// 初始化树结构
			candidateTree = $.fn.zTree.init($("#candidateTree"), setting2, zNodes2);
			// 不选择父节点
			candidateTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
			// 默认选择节点
			var ids2 = [
					<c:forEach items="${candidateList}" var="detail">{id:"${detail.candidate}"},
		            </c:forEach>];
			console.info(ids2);
			
			for(var i=0; i<ids2.length; i++) {				
				var node = candidateTree.getNodeByParam("id", ids2[i].id);
				var pnode = candidateTree.getNodeByParam("id", node.pId);				
				try{
					candidateTree.checkNode(node, true, false);
					candidateTree.expandNode(pnode, true, true,true,false);
				}catch(e){}
			}
		
		});
		
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="wyRelationCandidate"
		action="" method="post" class="form-horizontal">
		<form:hidden path="id" />

		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="tabbable" id="tabs-270499">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#panel-573269" data-toggle="tab">选择部门</a>
							</li>
							<li><a href="#panel-387327" data-toggle="tab">选择人员</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="panel-573269">
								<div class="control-group">
									<label class="control-label">组织机构:</label>
									<div class="controls">
										<div id="orgTree" class="ztree"
											style="margin-top:3px;float:left;"></div>
										<form:hidden path="orgIds" />
									</div>
								</div>
							</div>
							<div class="tab-pane" id="panel-387327">
								<div class="control-group">
									<label class="control-label">人员组织结构:</label>
									<div class="controls">
										<div id="candidateTree" class="ztree"
											style="margin-top:3px;float:left;"></div>
										<form:hidden path="candidateIds" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</form:form>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit"
			value="保 存" onclick="saveCandidateSelectHandler();">&nbsp; <input
			id="btnCancel" class="btn" type="button" value="返 回"
			onclick="closeLayer()">
	</div>
	<script type="text/javascript">
		function saveCandidateSelectHandler(){
			//-------部门选择
			var orgIds = [], orgNodes = orgTree.getCheckedNodes(true);
			for(var i=0; i<orgNodes.length; i++) {
				var halfCheck = orgNodes[i].getCheckStatus();	
				var id=orgNodes[i].id;				
				//全选的则添加进去
				if(!halfCheck.half && id.indexOf("G_") ==0){
					orgIds.push(id);
				}
			}
			console.info(orgIds);			
				
			
			//-------用户选择
			var candidateIds = [], candidateNodes = candidateTree.getCheckedNodes(true);
			for(var i=0; i<candidateNodes.length; i++) {
				var halfCheck = candidateNodes[i].getCheckStatus();
				var id = candidateNodes[i].id;	
				//全选的则添加进去
				if(!halfCheck.half && id.indexOf("M_") ==0){
					candidateIds.push(id);
				}
			}		
				
			//当两者都没有选择的时候提示
			if(!candidateIds && !orgIds){
				//检查是否有选中用户
				if(!candidateIds || candidateIds.length==0){
					layer.alert('请选择用户!', 8);
					return;
				}
				//检查是否有选中用户
				if(!orgIds || orgIds.length==0){
					layer.alert('请选择部门!', 8);
					return;
				}
			}			
			var orgIds = orgIds.join(",");	
			var candidateIds = candidateIds.join(",");
			
			//保存候选人信息
			var formdata={
				orgIds:orgIds,
				candidateIds:candidateIds,
				procDefId:"${wyRelationCandidate.procDefId}",
				taskId:"${wyRelationCandidate.taskId}",
				source:"${wyRelationCandidate.source}"
			};			
			
			console.info(formdata);
			
			$.ajax({
	            url: '${ctx}/sns/wyActCandidate/selectOrgUserSave',
	            data: formdata,
	            type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		closeLayer();
	            	}else{
	            		alert("保存失败");
	            	}            
	            }
	        });			
		}
		
		function closeLayer(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>