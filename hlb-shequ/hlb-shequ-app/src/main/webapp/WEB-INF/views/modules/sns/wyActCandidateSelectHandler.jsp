<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<style type="text/css">
		.division{
			border-top: 1px solid #e5e5e5;
		}
	</style>
</head>
<body> 
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="tabbable" id="tabs-59696">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-856083" data-toggle="tab">指定待办人</a>
					</li>										
				</ul>
			<form id="wyActCandidateSelectHandler" name="wyActCandidateSelectHandler" class="form-horizontal" action="">
				<div class="tab-content">
					<div class="tab-pane active" id="panel-856083">
				  	   	<input type="hidden" name="taskId" value="${wyActCandidate.taskId}"/>
				  	   	<input type="hidden" name="procDefId" value="${wyActCandidate.procDefId}"/>
				  	   	<input type="hidden" name="source" value="${wyActCandidate.source}"/>				  	   
						<div>
							<input id="type_0" name="type" class="required" type="radio" value="0" ${(wyActCandidate.type eq '0')?'checked':''} onclick="selectCandidate()">
							<label for="type_0" id="label-selectCandidate"><span style="font-size: 13px">处理人</span></label>
							<!-- <input class="btn" type="button" value="选择处理人" onclick="selectCandidate()" > -->
						</div>
						<div>
							<input id="type_2" name="type" class="required" type="radio" value="2" ${(wyActCandidate.type eq '2')?'checked':''}>
							<label for="type_2"><span style="font-size: 13px">发起人</span></label>
						</div>
						<c:if test="${fn:length(actList)>0}">
						<div>
							<input id="type_5" name="type" class="required" type="radio" value="5" ${(wyActCandidate.type eq '5')?'checked':''}>
							<label for="type_5"><span style="font-size: 13px">上一任务处理人</span></label>
						</div>						
						<div>
							<input id="type_3" name="type" class="required" type="radio" value="3" ${(wyActCandidate.type eq '3')?'checked':''}>
							<label for="type_3"><span style="font-size: 13px">由上一任务指定</span></label>
						</div>						
						<div>
							<input id="type_1" name="type" class="required" type="radio" value="1" ${(wyActCandidate.type eq '1')?'checked':''} onclick="selectHistoryTaskHandlePerson()">
							<label for="type_1" id="label-selectHistoryTaskHandlePerson"><span style="font-size: 13px">历史任务处理人</span></label>
							<!-- <input class="btn" type="button" value="选择历史任务处理人" onclick="selectHistoryTaskHandlePerson()"> -->
						</div>	
						</c:if>
						<br/>
						<div class="division"></div>
						<br/>	
						<div>
							<input id="allowDelegateTask_1" name="allowDelegateTask" class="required" type="checkbox" value="true" ${wyActCandidate.allowDelegateTask?'checked':''}>
							<label for="allowDelegateTask_1"><span style="font-size: 13px">允许委托</span></label>
						</div>
						<c:if test="${fn:length(actList)>0}">
						<div>
							<input id="allowReturnBack_1" name="allowBack" class="required" type="checkbox" value="true" ${wyActCandidate.allowBack?'checked':''}>
							<label for="allowReturnBack_1"><span style="font-size: 13px">允许回退</span></label>
						</div>
						</c:if>			
					</div>				
				</div>
			</form> 
			</div>
		</div>
	</div>
</div>

<div class="form-actions">
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="saveCandidateSelectHandler();">&nbsp;
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeLayer()">
</div>       
      
<script type="text/javascript">
	$("#label-selectCandidate").hover(function(){
	<c:if test="${wyActCandidate.type eq '0'}">
		var tipstr="已选择部门：${orgNameList}<br/> <br/>已选择人员：${candidateNameList}<br/> ";
		layer.tips(tipstr, this, {
		    style: ['background-color:#0FA6D8; color:#fff', '#0FA6D8'],
		    maxWidth:150,
		    time:1
		  });
	</c:if>
	});
	
	$("#label-selectHistoryTaskHandlePerson").hover(function(){
		<c:if test="${wyActCandidate.type eq '1'}">
		var tipstr="历史节点为：<br/>【<font color='red'>${sameTaskName}</font>】";
		layer.tips(tipstr, this, {
		    style: ['background-color:#0FA6D8; color:#fff', '#0FA6D8'],
		    maxWidth:150,
		    time:1
		  });		
		</c:if>		  
	});



	//选择处理人
	function selectCandidate(){
		$("#type_0").attr("checked", true);
		var url = "${ctx}/sns/wyActCandidate/selectOrgUser?procDefId=${wyActCandidate.procDefId}&taskId=${wyActCandidate.taskId}&source=${wyActCandidate.source}";
				
		$.layer({
		  type: 2,
		  shade: [0.9, '#393D49'],
		  fix: false,
		  title: "选择处理人",
		  maxmin: true,
		  iframe: {src : url},
		  area: [document.documentElement.clientWidth * 0.9 , document.documentElement.clientHeight * 0.9],
		  close: function(index){
		   	// layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});
	}
	
	//选择历史任务处理人
	function selectHistoryTaskHandlePerson(){
		$("#type_1").attr("checked", true);
		var url = "${ctx}/sns/wyActCandidate/selectHistoryTaskHandlePerson?procDefId=${wyActCandidate.procDefId}&taskId=${wyActCandidate.taskId}&source=${wyActCandidate.source}";
				
		$.layer({
		  type: 2,
		  shade: [0.9, '#393D49'],
		  fix: false,
		  title: "选择历史节点",
		  maxmin: true,
		  iframe: {src : url},
		  area: [document.documentElement.clientWidth * 0.9 , document.documentElement.clientHeight * 0.9],
		  close: function(index){
		   	// layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
		  }
		});		
	}

	function saveCandidateSelectHandler(){
		//获取form表单数据
		var formdata = $("#wyActCandidateSelectHandler").serialize();	
		$.ajax({
            url: '${ctx}/sns/wyActCandidate/saveWyActCandidate',
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
		<c:if test="${not empty more}">
		 window.history.go(-1);
		</c:if>
		<c:if test="${empty more}">
		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		 parent.layer.close(index);
		</c:if>
	}	
</script>       
       
          
</body>
</html>