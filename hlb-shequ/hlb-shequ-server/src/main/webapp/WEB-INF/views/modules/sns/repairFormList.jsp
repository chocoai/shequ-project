<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>维修表单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/repairForm.css">
	<link rel="stylesheet" href="${ctxStatic}/css/repairList.css">
	<script src="${ctxStatic}/js/repairForm.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
	</script>
</head>
<body>
	<c:forEach items="${wyRepairBudgetList}" var="wyRepairFormList" varStatus="status">
	<div class="content1" onClick="window.location.href='${ctx}/wuye/showRepairForm?id=${wyRepairFormList.id}'">
		<div class="content2">序号：${status.index + 1}</div>
		<div class="content3">
			<div class="content4">
				<span class="content6">物料名称:</span>
				<span class="content7">${wyRepairFormList.materialName}</span>
			</div>
			<div class="content5">
				<span class="content6">合价:</span>
				<span class="content7">${wyRepairFormList.count}元</span>
			</div>
		</div>
		<div class="content8"></div>
	</div>	
	</c:forEach>
	<div class="content1">
		<span>总物料数量：${totalNum} 个,总金额：${totalMoney} 元</span>
	</div>
	
	<span>当前节点：${claimTask.name}${currTask.name}</span>&nbsp;&nbsp;<span>处理人：${member.memberName}</span><br/>
	
	<c:if test="${not empty claimTask}">
	<div class="content11" onClick="window.location.href='${ctx}/wuye/claim?repairId=${wyRepair.id}&taskid=${taskId}'">
		<button onclick="">认领任务</button>
	</div>
	</c:if>
	<c:if test="${not empty currTask && currTask.taskDefinitionKey eq 'usertask7'}">
	<div class="content11" onClick="window.location.href='${ctx}/wuye/completeTask?repairId=${wyRepair.id}&taskid=${taskId}'">
		<button onclick="">提交审核</button>
	</div>
	</c:if>
	<c:if test="${not empty currTask && currTask.taskDefinitionKey eq 'usertask3'}">
	<div class="content11" onClick="window.location.href='${ctx}/wuye/completeTask?repairId=${wyRepair.id}&taskid=${taskId}&agree=1'">
		<button onclick="">接受</button>
	</div>
	<div class="content11" onClick="window.location.href='${ctx}/wuye/completeTask?repairId=${wyRepair.id}&taskid=${taskId}&agree=0'">
		<button onclick="">不接受</button>
	</div>
	</c:if>
	<c:if test="${not empty currTask && currTask.taskDefinitionKey eq 'usertask5'}">
	<div class="content11" onClick="window.location.href='${ctx}/wuye/completeTask?repairId=${wyRepair.id}&taskid=${taskId}'">
		<button onclick="">完成维修</button>
	</div>
	</c:if>
	
	<c:if test="${not empty currTask && currTask.taskDefinitionKey eq 'usertask6'}">
	<div class="content11" onClick="window.location.href='${ctx}/wuye/completeTask?repairId=${wyRepair.id}&taskid=${taskId}'">
		<span onclick="">付款评价</span>
	</div>
	</c:if>
	
	<c:if test="${currTask.taskDefinitionKey ne 'usertask3'}">
	<div class="content9" onClick="window.location.href='${ctx}/wuye/repairFormDetail?repairId=${wyRepair.id}'"></div>	
	</c:if>	
	<div class="content10" onclick="javascript:history.go(-1)"></div>
	

	<div class="fit"></div>
	<div class="mainMenuWrap">
		<div class="mainMenu">
		    <a class="a1" href="">
		        <div class="thumb"></div>
		        <p>小区</p>
		    </a>
		    <a class="a2" href="">
		        <div class="thumb"></div>
		        <p>通知</p>
		    </a>
		    <a class="a3" href="">
		        <div class="thumb"></div>
		        <p>电话</p>
		    </a>
		    <a class="a4" href="">
		        <div class="thumb"></div>
		        <p>我</p>
		    </a>
		</div>
	</div>
</body>