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
		<li><a href="${ctx}/weixin/wxMassMsg/tplIndex">消息列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/tplSendMsg?msgId=${wxMassMsg.msgId}">发送消息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/sendMsgSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="msgId"/>
		<input id="memberIds" name="memberIds" type="hidden"/>
		<input id="wyIds" name="wyIds" type="hidden"/>	
		<input id="ldids" name="ldids" type="hidden"/>	
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">选择公众号：</label>
			<div class="controls">
				<select id="accountId" name="accountId" class="input-xlarge" onchange="selectAccount(this);">	
					<c:forEach items="${accountList}" var="account">
					<option value="${account.id}" ${account.id eq wxAccount.id ?'selected="selected"':''}>${account.wxname}</option>
					</c:forEach>					
				</select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">发送类型：</label>
			<div class="controls">
				<select id="send-type" name="type" class="input-xlarge" onchange="selectType(this);">					
					<option value="1" ${wxMassMsg.type eq '1' ?'selected="selected"':''}>指定物业</option>
					<option value="5" ${wxMassMsg.type eq '5' ?'selected="selected"':''}>指定楼栋</option>
					<option value="2" ${wxMassMsg.type eq '2' ?'selected="selected"':''}>指定用户</option>
					<option value="3" ${wxMassMsg.type eq '3' ?'selected="selected"':''}>所有用户</option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- 选择楼栋	 -->	
		<div id="selectLouYu" style="display:none;">
			<div class="control-group">
				<label class="control-label">选择物业：</label>
				<div class="controls">
					<select id="selectLouYu-WY" class="input-xlarge" onchange="changeLouYuWY()">	
						<option value="">请选择</option>					
					</select>
					<span class="help-inline"><font color="red">*</font></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">选择楼栋：</label>
				<div class="controls">
					<select id="selectLouYu-LD" class="input-xlarge" onchange="changeLD()">	
						<option value="">请选择</option>					
					</select>
					<span class="help-inline"><font color="red">*</font></span>
				</div>
			</div>		
		</div>	
		<!-- 选择物业公司 -->
		<div id="selectWY" class="control-group" style="${(wxMassMsg.type eq '1'|| empty wxMassMsg.type) ?'display:block;':'display:none;'}">
			<label class="control-label">选择物业：</label>
			<div class="controls">
				<div id="wyTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
		
		<div id="selectUser" style="${wxMassMsg.type eq '2' ?'display:block;':'display:none;'}">	
		<div class="control-group">
			<label class="control-label">选择物业：</label>
			<div class="controls">
				<select id="selectedWY2" name="selectedWY2" class="input-xlarge" onchange="changeWY()">	
					<option value="">请选择</option>					
				</select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择楼盘：</label>
			<div class="controls">
				<select id="selectedLP2" name="selectedLP2" class="input-xlarge" onclick="selectedLP()">	
					<option value="">请选择</option>					
				</select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>	
		<!-- 选择指定的用户 -->
		<div class="control-group">
			<label class="control-label">住户：</label>
			<div class="controls">
				<div style="margin-bottom:5px;">
					<label>住户姓名：</label>
					<form:input path="memberName" htmlEscape="false" maxlength="20" class="input-medium"/>	
					<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchMember();"/>
				</div>							
				<table id="selectTable" class="table table-striped table-bordered table-condensed" style="width:700px;">
					<thead>
						<tr>
							<th>住户id</th>
							<th>住户姓名</th>
							<th>住户类型</th>				
							<th>默认房号</th>				
							<th>手机号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="selectTable-tbody"></tbody>
				</table>
				已选择住户：
				<table id="selectedMemberTable" class="table table-striped table-bordered table-condensed" style="width:700px;">
					<thead>
						<tr>
							<th>住户id</th>
							<th>住户姓名</th>
							<th>住户类型</th>				
							<th>默认房号</th>				
							<th>手机号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="selectedMemberTable-tbody"></tbody>
				</table>
				<script type="text/javascript">						
					//选择某一行			
					function selectToMember(mid,dom){
						//将选中的id添加到memberid后面组装起来
						var mids=$("#memberIds").val();
						if(mids && mids !=''){
							var midArr=mids.split(",");
							//判断是否已经选择
							if(midArr.contains(mid)){
								alert("会员"+mid+"已经添加");
								return;
							}							
							midArr.push(mid);
							$("#memberIds").val(midArr.join(","));						
						}else{
							$("#memberIds").val(mid);	
						}					
						var td=$(dom);
						var tr=td.parent();
						td.html("<a>移除</a>");
						td.attr("onclick","removeMember('"+ mid +"',this)");
						$("#selectedMemberTable-tbody").append(tr);						
					}
					//删除一行
					function removeMember(mid,dom){
						var mids=$("#memberIds").val();
						if(mids && mids !=''){
							var midArr=mids.split(",");
							midArr.remove(mid);
							$("#memberIds").val(midArr.join(","));						
						}
						
						var td=$(dom);
						var tr=td.parent();
						td.html("<input type=\"checkbox\">");
						td.attr("onclick","selectToMember('"+ mid +"',this)");
						
						var allTr=$("#selectTable-tbody").find("tr");						
						var mids = new Array();
						$.each(allTr,function(){							
							mids.push($(this).attr("data-memberId"));
						});
						
						if(mids.contains(mid)){
							tr.remove();
							return;
						}
						$("#selectTable-tbody").append(tr);
					}
					//触发楼盘
					function selectedLP(memberName){
						var lyid = $("#selectedLP2").val();
						var source="${wxMassMsg.source}";
						$("#selectTable-tbody").empty();
						memberName = memberName==undefined ?"":memberName;
						//获取用户列表
						$.ajax({
				            type:"POST",
				            url : "${ctx}/sys/office/getLyMemberList?source="+source+"&lyid="+lyid+"&memberName="+memberName,
				            datatype : "json",
				            success : function(result){
				           		if(result.code==200){
				           			var data=result.data;
				           			var html="";
				           			if(data){
				           				for(var i=0;i<data.length;i++){
					           				html += "<tr data-memberId=\""+ data[i].memberId +"\"><td>"+data[i].memberId+"</td><td>"+data[i].memberName+"</td><td></td><td>"+data[i].roomid+"</td><td>"+data[i].mobile+"</td><td onclick=\"selectToMember('"+data[i].memberId+"',this)\"><input type=\"checkbox\"></td></tr>";
					           			} 
					           			$("#selectTable-tbody").append(html);
				           			}				           			          			
				           		}      
				            }        
					  	});						
					}
				</script>
			</div>
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
		});
		
		//类型变更	
		function selectAccount(dom){
			//刷新物业属性结构
			refreshTree("${wxMassMsg.source}",$("#accountId").val());
		}
		
		//类型变更	
		function selectType(dom){
			var v = $(dom).val();
			if(v==2){
				$("#selectWY").hide();
				$("#selectLouYu").hide();
				$("#selectUser").show();				
				changeSelectedWy("#selectedWY2");
			}else if(v==3){
				$("#selectWY").hide();
				$("#selectUser").hide();
				
			}else if(v==5){
				$("#selectWY").hide();
				$("#selectUser").hide();
				$("#selectLouYu").show();
				changeSelectedWy("#selectLouYu-WY");
			}else{
				$("#selectWY").show();
				$("#selectUser").hide();
				$("#selectLouYu").hide();
			}
		}
		
		function changeSelectedWy(jq){
			$(jq).empty();			
			$(jq).append('<option value="">请选择</option>');
			
			$(jq).find("option:first").prop("selected", 'selected')
            $(jq).trigger('change.select2');
            
			var source="${wxMassMsg.source}";
			var accountId=$("#accountId").val();
			$.ajax({
	            type:"POST",
	            url : "${ctx}/sys/office/getWYBuildingList?source="+source+"&accountId="+accountId,
	            datatype : "json",
	            success : function(result){
	           		if(result.code==200){
	           			var data=result.data;
	           			var html="";
	           			for(var i=0;i<data.length;i++){	           				
	           				$(jq).append('<option value="'+ data[i].wyid +'">'+ data[i].wyid + " " + data[i].wymc +'</option>');
	           			}           			
	           		}      
	            }        
		  	});			
		}
		//更改物业触发
		function changeWY2(jq,wyid){
			$(jq).empty();
			$(jq).append('<option value="">请选择</option>');
			/* $(jq).val("");
			$(jq).find("option[text='请选择']").attr("selected",true);
			$(jq).get(0).selectedIndex=-1; */
			
			$(jq).find("option:first").prop("selected", 'selected')
            $(jq).trigger('change.select2');
            
			//获取楼栋
			
			if(!wyid || wyid ==''){				
				return;
			}
			$.ajax({
	            type:"POST",
	            url : "${ctx}/sys/office/getLouYuList?source=${wxMassMsg.source}&wyid="+wyid,
	            datatype : "json",
	            success : function(result){
	           		if(result.code==200){
	           			var data=result.data;
	           			var html="";
	           			if(data && data!=""){
	           				for(var i=0;i<data.length;i++){
		           				$(jq).append('<option value="'+ data[i].lyid +'">'+ data[i].lyno + " " + data[i].lyname +'</option>');
		           			}
	           			}
	           		}      
	            }        
		  	});						
		}
		
		function changeWY(){
			var wyid=$("#selectedWY2").val();
			changeWY2("#selectedLP2",wyid);
		}
		
		function changeLouYuWY(){
			var wyid=$("#selectLouYu-WY").val();
			changeWY2("#selectLouYu-LD",wyid);
		}
		
		function changeLD(){
			var ldid=$("#selectLouYu-LD").val();
			$("#ldids").val(ldid);
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
								
			}
			
			//发送校验逻辑
			if(type == '1' && (!$("#wyIds").val() || $("#wyIds").val()=='')){
				alert("请选择物业再发送");
				return false;
			}else if(type == '5'){
				if($("#accountId").val()==undefined || $("#accountId").val()==''){
					alert("请选择公众号");
					return false;
				}
				if($("#send-type").val()==undefined || $("#send-type").val()==''){
					alert("请选择发送类型");
					return false;
				}
				if($("#selectLouYu-WY").val()==undefined || $("#selectLouYu-WY").val()==''){
					alert("请选择物业");
					return false;
				}
				if($("#selectLouYu-LD").val()==undefined || $("#selectLouYu-LD").val()==''){
					alert("请选择楼栋");
					return false;
				}
			}else if(type == '2'){
				if($("#accountId").val()==undefined || $("#accountId").val()==''){
					alert("请选择公众号");
					return false;
				}
				if($("#send-type").val()==undefined || $("#send-type").val()==''){
					alert("请选择发送类型");
					return false;
				}
				if($("#selectedWY2").val() == undefined || $("#selectedWY2").val() == ''){
					alert("请选择物业");
					return false;
				}
				if($("#selectedLP2").val() == undefined || $("#selectedLP2").val() == ''){
					alert("请选择楼栋");
					return false;
				}
				if($("#memberIds").val() == undefined || $("#memberIds").val() == ''){
					alert("请查询住户信息");
					return false;
				}
			
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
	           			alert("正在发送中...，具体发送情况,请查看发送详情");
	           		}else{
	           			alert("发送出错");
	           		}       
	            }        
		  	});			
			return false;
		}
		
		//搜索会员
		function searchMember(){
			var memberName=$("#memberName").val();
			selectedLP(memberName);
		}
		
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
			}
		};
		
		function refreshTree(source,accountId){				
			$.getJSON("${ctx}/sys/office/getWYTree?source="+source+"&accountId="+accountId,function(data){
				wyTree = $.fn.zTree.init($("#wyTree"), setting2, data);	
				//wyTree.setting2.check.chkboxType = { "Y" : "ps", "N" : "ps" };			
			});
		}		
		refreshTree("${wxMassMsg.source}",$("#accountId").val());
			
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		
		
		
		
	</script>
</body>
</html>