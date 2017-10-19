<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群发消息记录表管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/layer/layer-v1.8.5/layui.css"  media="all">
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxMassMsg/news">群发列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMassMsg/form?id=${wxMassMsg.id}">群发<shiro:hasPermission name="weixin:wxMassMsg:edit">${not empty wxMassMsg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMassMsg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMassMsg" action="${ctx}/weixin/wxMassMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="newsId" htmlEscape="false" maxlength="255" class="input-media"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">群发名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公众号：</label>
			<div class="controls">
				<form:select path="accountId" class="input-xlarge required" onchange="accountChange()">
					<option value="">请选择</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>				
		<div class="control-group">
			<label class="control-label">选择图文消息：</label>			
			<div class="controls">				
				<div style="margin-bottom:5px;">
					<label>图文标题：</label>
					<input id="title" name="title" class="input-medium" type="text" value="" maxlength="20">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchTitle();"/>
				</div>				
				<table id="selectTable" class="table table-striped table-bordered table-condensed" style="width:500px;">
					<thead>
						<tr>
							<th>选择</th>
							<th>缩略图</th>
							<th>图文标题</th>
							<th>文章内容</th>
						</tr>
					</thead>
					<tbody id="selectTable-tbody"></tbody>
				</table>
				已选择图文消息：
				<table id="selectedNewsTable" class="table table-striped table-bordered table-condensed" style="width:500px;">
					<thead>
						<tr>
							<th>选择</th>
							<th>缩略图</th>
							<th>图文标题</th>
							<th>文章内容</th>
						</tr>
					</thead>
					<tbody id="selectedNewsTable-tbody"></tbody>
				</table>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMassMsg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
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
		
		//弹出选择用户框
		function showSelectUsers(){
			var url="${ctx}/sns/wyActCandidate/selectOrgUser2";
			var title="选择用户";
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: title,
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.6 , document.documentElement.clientHeight * 0.6],
			  close: function(index){
			   	 layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});			
		}
		
		function selectNews(){
			var accountId = $("#accountId").val();
			if(!accountId || accountId ==''){
				layer.alert('请先选择公众号');
				return ;
			}
			var url="${ctx}/weixin/wxMassMsg/showSelectNews2?accountId=" + accountId;
			var title="选择图文消息";
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: title,
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.6 , document.documentElement.clientHeight * 0.6]			  
			});
		}
		
		function accountChange(title){
			var accountId = $("#accountId").val();
			if(!accountId || accountId ==''){
				layer.alert('请先选择公众号');
				return ;
			}
			title = title ? title:"";
			
			$("#selectTable-tbody").empty();
			$("#selectedNewsTable").empty();
			$("#newsId").val("");
			$.ajax({
	            type:"POST",
	            url : "${ctx}/weixin/wxMassMsg/showSelectNewsList?accountId="+accountId+"&name="+title,
	            datatype : "json",
	            success : function(result){
	           		if(result.code==200){
	           			var data=result.data;
	           			console.info(data);
	           			var html="";
	           			if(data){
	           				for(var i=0;i<data.length;i++){
	           					var v = data[i];
	           					html +="<tr>";
	           					html +="<td><input type='checkbox' name='mgsIds' value='"+ v.id +"' onclick='selectToNews(\""+v.id+"\",this)'/></td>";
	           					html +="<td><img src='"+ v.imgLocalUrl +"' style='width:50px;height:50px;'/></td>";
	           					html +="<td>"+ v.title +"</td>";
	           					var digest = v.digest;
	           					/*if(digest.length>20){
	           						digest = digest.substring(0,30);
	           					}	 */          					
	           					html +="<td>"+ digest +"</td>";
	           					html +="</tr>";
		           			} 
		           			$("#selectTable-tbody").append(html);
	           			}				           			          			
	           		}      
	            }        
		  	});
			
		}
		
		function selectToNews(id,dom){
			//组装id值
			var newsId=$("#newsId").val();
			if(newsId && newsId !=''){
				var newsIdArr = newsId.split(",");
				//判断是否已经选择
				if(newsIdArr.contains(id)){
					$(dom).attr("disabled","disabled");
					alert("会员"+id+"已经添加");
					return;
				}							
				newsIdArr.push(id);
				$("#newsId").val(newsIdArr.join(","));				
			}else{
				$("#newsId").val(id);	
			}
			//移动tr
			var input = $(dom);
			var tr = input.parent().parent();			
			input.attr("onclick","removeToNews('"+id+"',this)");
			$("#selectedNewsTable").append(tr);			
		}
		
		function removeToNews(id,dom){
			var newsId=$("#newsId").val();
			if(newsId && newsId !=''){
				var newsIdArr = newsId.split(",");
				newsIdArr.remove(id);
				$("#newsId").val(newsIdArr.join(","));						
			}
		
			var input = $(dom);
			var tr = input.parent().parent();			
			input.attr("onclick","selectToNews('"+id+"',this)");
			$("#selectTable-tbody").append(tr);
		}
		
		function searchTitle(){
			var title=$("#title").val();
			accountChange(title);
		}
		
	</script>
	
	
</body>
</html>