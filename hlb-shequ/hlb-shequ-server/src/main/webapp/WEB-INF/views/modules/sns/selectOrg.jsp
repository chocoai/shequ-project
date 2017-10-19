<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<meta name="decorator" content="default" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=1">
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
<link rel="stylesheet" href="${ctxStatic}/mui/css/app.css">
<script src="${ctxStatic}/mui/js/mui.min.js" type="text/javascript"></script>	
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
				},
				fontCss : {"font-family": "微软雅黑", "font-size": "28px"}
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
			
			
			for(var i=0; i<ids2.length; i++) {				
				var node = candidateTree.getNodeByParam("id", ids2[i].id);
				var pnode = candidateTree.getNodeByParam("id", node.pId);				
				try{
					candidateTree.checkNode(node, true, false);
					candidateTree.expandNode(pnode, true, true,true,false);
				}catch(e){}
			}
		
		});
		
		function selectStaff(){
			
		}
		
		function selectTab(type,jq){
			return;
			console.info(jq);
			if(type==1){
				$("#orgTree-div").hide();
				$("#candidateTree-div").show();
				$(jq).attr("class","tab-green");
			}else if(type==2){
				$("#candidateTree-div").hide();
				$("#orgTree-div").show();
				$(jq).attr("class","tab-green");
			}
		}
		
</script>
<style type="text/css">
.ztree li span{
 	font-size: 18px;
}
</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="wyRelationCandidate"
		action="" method="post" class="form-horizontal">		
		<div class="mui-content">
			<div class="mui-card">
				<div style="padding: 10px 10px;">
					<div id="segmentedControl" class="mui-segmented-control">
						<a class="mui-control-item mui-active" href="#item1">组织结构</a>
						<a class="mui-control-item" href="#item2">人员</a>					
					</div>
				</div>
				<div>
					<div id="item1" class="mui-control-content mui-active">					
						<div id="orgTree-div" class="controls">
							<div id="orgTree" class="ztree"	style="margin-top:3px;float:left;font-size:25px;"></div>
							<form:hidden path="orgIds" />
						</div>					
					</div>
					<div id="item2" class="mui-control-content">
						<div id="candidateTree-div" class="controls">
							<div id="candidateTree" class="ztree" style="margin-top:3px;float:left;"></div>
							<form:hidden path="candidateIds" />
						</div> 
					</div>	
				</div>
			</div>
			<div class="mui-card">
				<div class="mui-button-row">
					<button type="button" class="mui-btn mui-btn-danger" onclick="saveCandidateSelectHandler()">确 认</button>
					<button type="button" class="mui-btn" onclick="window.history.go(-1);">返 回</button>
				</div>			
			</div>	
		</div>		
	</form:form>
	
	<script type="text/javascript">
		function saveCandidateSelectHandler(){
			//-------部门选择
			var orgIds = [],orgNames = [], orgNodes = orgTree.getCheckedNodes(true);
			
			for(var i=0; i<orgNodes.length; i++) {
				var halfCheck = orgNodes[i].getCheckStatus();	
				var id=orgNodes[i].id;				
				//全选的则添加进去
				if(!halfCheck.half && id.indexOf("G_") ==0){
					orgIds.push(id);
					orgNames.push(orgNodes[i].name);					
				}
			}
			
			//-------用户选择
			var candidateIds = [],candidateNames = [], candidateNodes = candidateTree.getCheckedNodes(true);
			for(var i=0; i<candidateNodes.length; i++) {
				var halfCheck = candidateNodes[i].getCheckStatus();
				var id = candidateNodes[i].id;	
				//全选的则添加进去
				if(!halfCheck.half && id.indexOf("M_") ==0){
					candidateIds.push(id);
					candidateNames.push(candidateNodes[i].name);
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
			
			//保存候选人信息<input type="hidden" name="procInsId" value="${wyRelationCandidate.procInsId}"/>
			var formdata={
				orgIds:orgIds,
				candidateIds:candidateIds,
				procDefId:"${wyRelationCandidate.procDefId}",
				taskId:"${wyRelationCandidate.taskId}",
				source:"${wyRelationCandidate.source}",
				procInsId:"${wyRelationCandidate.procInsId}"
			};
			
			$.ajax({
	            url: '${ctx}/sns/wyActCandidate/selectOrgSave',
	            data: formdata,
	            type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		var selectName = "";
	            		if(orgNames && orgNames.length>0){
	            			selectName += "部门--" + orgNames.join(","); 
	            		}
	            		if(candidateNames && candidateNames.length>0){
	            			selectName += "人员--" + candidateNames.join(",");
	            		}    	
	            		var url2 = document.referrer + "&selectName=" + selectName;	 
	            		window.location.href = url2;
	            		
	            	}else{
	            		alert("保存失败");
	            	}            
	            }
	        });			
		}

	</script>
</body>
</html>