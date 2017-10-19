<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发消息记录表管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp"%>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxMassMsg/tplSelectAccounut">选择公众号</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/tplSelectSendObj">选择发送对象</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/sendMsgSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="msgId"/>
		<form:hidden path="accountId"/>
		<form:hidden path="source"/>
		<input id="memberIds" name="memberIds" type="hidden"/>
		<input id="wyIds" name="wyIds" type="hidden"/>		
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">发送类型：</label>
			<div class="controls">
				<select id="send-type" name="type" class="input-xlarge" onchange="selectType(this);">					
					<option value="1" ${wxMassMsg.type eq '1' ?'selected="selected"':''}>指定物业</option>
					<option value="2" ${wxMassMsg.type eq '2' ?'selected="selected"':''}>指定用户</option>
					<option value="3" ${wxMassMsg.type eq '3' ?'selected="selected"':''}>所有用户</option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<!-- 选择物业公司 -->
		<div id="selectWY" class="control-group">
			<label class="control-label">选择物业：</label>
			<div class="controls">
				<div id="wyTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
		
		<!-- 选择指定的用户 -->
		<div id="selectUser" class="control-group" style="display:none;">
			<label class="control-label">选择会员：</label>
			<div class="controls">
				<div id="memberTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMassMsg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发 送" onclick="return getSelected();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		var wyTree,memberTree;
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});	
			
			//人员选择
			var setting = {
				check:{enable:true,nocheckInherit:true},
				view:{selectedMulti:false},
				data:{simpleData:{enable:true}},
				callback:{
					beforeClick:function(id, node){
						memberTree.checkNode(node, !node.checked, true, true);
						return false;
					},
					beforeExpand: function(treeId, treeNode){
						//定义在展开前获取动态节点数据
						
					},
					onCheck: function (event, treeId, treeNode){						
						var changedNodes = memberTree.getCheckedNodes(true);
						for (var i=0; i < changedNodes.length; i++){  
							var node = changedNodes[i];  					    	
							var halfCheck = node.getCheckStatus();
							if(halfCheck.half){
								memberTree.checkNode(node, false, false);							
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
			var zNodes=[
					<c:forEach items="${memberTreeList}" var="dto">{id:"${dto.id}", pId:"${dto.pid}", name:"${dto.name}",chkDisabled:"${dto.chkDisabled}"},
		            </c:forEach>];
			// 初始化树结构
			memberTree = $.fn.zTree.init($("#memberTree"), setting, zNodes);
			// 不选择父节点
			memberTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
			// 默认选择节点
			var ids = [
					<c:forEach items="${candidateList}" var="detail">{id:"${detail.candidate}"},
		            </c:forEach>];
			console.info(ids);
			
			for(var i=0; i<ids.length; i++) {				
				var node = memberTree.getNodeByParam("id", ids[i].id);
				var pnode = memberTree.getNodeByParam("id", node.pId);				
				try{
					memberTree.checkNode(node, true, false);
					memberTree.expandNode(pnode, true, true,true,false);
				}catch(e){}
			}
			
				
			//物业选择
			var setting2 = {
				check:{enable:true,nocheckInherit:true},
				view:{selectedMulti:false},
				data:{simpleData:{enable:true}},
				callback:{
					beforeClick:function(id, node){
						wyTree.checkNode(node, !node.checked, true, true);
						return false;
					},
					beforeExpand: function(treeId, treeNode){
						//定义在展开前获取动态节点数据
						
					},
					onCheck: function (event, treeId, treeNode){						
						var changedNodes = wyTree.getCheckedNodes(true);
						for ( var i=0; i < changedNodes.length; i++){  
							var node = changedNodes[i];  					    	
							var halfCheck = node.getCheckStatus();
							if(halfCheck.half){
								wyTree.checkNode(node, false, false);							
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
					<c:forEach items="${orgTreeList}" var="dto">{id:"${dto.id}", pId:"${dto.pid}", name:"${dto.name}",chkDisabled:"${dto.chkDisabled}"},
		            </c:forEach>];
			// 初始化树结构
			wyTree = $.fn.zTree.init($("#wyTree"), setting2, zNodes2);
			// 不选择父节点
			wyTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
			// 默认选择节点
			var ids2 = [
					<c:forEach items="${candidateList}" var="detail">{id:"${detail.candidate}"},
		            </c:forEach>];
			console.info(ids2);
			
			for(var i=0; i<ids2.length; i++) {				
				var node = wyTree.getNodeByParam("id", ids2[i].id);
				var pnode = wyTree.getNodeByParam("id", node.pId);				
				try{
					wyTree.checkNode(node, true, false);
					wyTree.expandNode(pnode, true, true,true,false);
				}catch(e){}
			}
				
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
				return childNodes;
			}	
					
		});
		//类型变更	
		function selectType(dom){
			var v = $(dom).val();
			if(v==2){
				$("#selectWY").hide();
				$("#selectUser").show();
				
			}else if(v==3){
				$("#selectWY").hide();
				$("#selectUser").hide();
				
			}else{
				$("#selectWY").show();
				$("#selectUser").hide();
				
			}
		}
		//获取树形结构勾选值
		function getSelected(){
			var type = $("#send-type").val();
			if(type == '1'){
				//组装物业id信息
				var wyIds = [], orgNodes = wyTree.getCheckedNodes(true);
				for(var i=0; i<orgNodes.length; i++) {
					var halfCheck = orgNodes[i].getCheckStatus();	
					var id=orgNodes[i].id;				
					//全选的则添加进去
					if(!halfCheck.half && id.indexOf("WY_") ==0){
						wyIds.push(id);
					}
				}			
				var ids = wyIds.join(",");
				$("#wyIds").val(ids);
			}else if(type == '2'){
				//组装会员信息id
				var memberIds = [], memberNodes = memberTree.getCheckedNodes(true);
				for(var i=0; i < memberNodes.length; i++) {
					var halfCheck = memberNodes[i].getCheckStatus();	
					var id = memberNodes[i].id;				
					//全选的则添加进去
					if(!halfCheck.half && id.indexOf("M_") ==0){
						memberIds.push(id);
					}
				}			
				var ids2 = memberIds.join(",");
				$("#memberIds").val(ids2);				
			}
			
			var formdata=$("#inputForm").serialize();
			//ajax提交
			 $.ajax({
	            type:"POST",
	            url : "${ctx}/weixin/wxMassMsg/sendMsgSave",
	            data : formdata,
	            datatype : "json",
	            success : function(result){
	           		if(result.code==200){
	           			alert("正在发送中...");
	           		}else{
	           			alert("发送出错");
	           		}       
	            }        
		  	});			
			return false;
		}
		
	</script>
</body>
</html>