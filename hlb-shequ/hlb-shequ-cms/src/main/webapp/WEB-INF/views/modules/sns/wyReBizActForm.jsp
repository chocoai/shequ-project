<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程引用管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
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
		
		function actChange(dom){
			var category = $(dom).val();
			$("#contentTable-tbody").empty();
			$.ajax({
	            type:"POST",
	            url : "${ctx}/sns/wyReBizAct/getActByType?catagory="+category,
	            datatype : "json",
	            success : function(result){
	           		if(result.code==200){
	           			var data=result.data;
	           			
	           			console.info(data);
	           			
	           			var html="";
	           			if(data){
	           				for(var i=0;i<data.length;i++){
		           				html += "<tr data-procDefId=\""+ data[i].id +"\"><td>"+ data[i].version +"</td><td>"+ data[i].name +"</td><td>"+ data[i].category +"</td><td>"+data[i].key+"</td><td><a href='script:;' onclick='showActPic(\""+ data[i].procDefId +"\",\""+ data[i].name +"\",\""+ data[i].version +"\")'>流程图</a> <input type=\"radio\" name='actId' value='"+ data[i].id +"' onclick=\"selectAct('"+ data[i].id +"')\"></td></tr>";
		           			} 
		           			$("#contentTable-tbody").append(html);
	           			}				           			          			
	           		}      
	            }        
		  	});
		}
		
		function selectAct(id){
			$("#actId").val(id);
		}
		
		function showActPic(id,name,version){
			var url = "${ctx}/act/process/resource/read?resType=image&procDefId="+id;
			$.layer({
			  type: 2,
			  shade: [0.8, '#393D49'],
			  fix: false,
			  title: "查看流程图 <font color='red'>"+name+"（版本V："+ version +"）</font>",
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.8 , document.documentElement.clientHeight * 0.8]
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sns/wyReBizAct/">流程引用列表</a></li>
		<li class="active"><a href="${ctx}/sns/wyReBizAct/form?id=${wyReBizAct.id}">流程引用<shiro:hasPermission name="sns:wyReBizAct:edit">${not empty wyReBizAct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sns:wyReBizAct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wyReBizAct" action="${ctx}/sns/wyReBizAct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">选择业务：</label>
			<div class="controls">
				<form:select path="bizId" class="input-xlarge required">					
					<form:options items="${wyBizDefList}" itemLabel="showName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择工作流程：</label>
			<div class="controls">
				<div style="margin-bottom:5px;">
					<form:hidden path="actId"/>
					<form:select id="act" path="" class="input-xlarge required" onchange="actChange(this)">
						<form:options items="${wyBizDefList}" itemLabel="name" itemValue="key" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>		
				<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:700px;">
					<thead>
						<tr>	
							<td>流程版本</td>
							<td>流程名称</td>
							<td>流程分类</td>
							<td>流程标识</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="contentTable-tbody"></tbody>
				</table>
			</div>			
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sns:wyReBizAct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>