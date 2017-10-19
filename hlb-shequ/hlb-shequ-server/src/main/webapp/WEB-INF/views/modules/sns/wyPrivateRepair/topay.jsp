<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>发起支付</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
	<link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
	<script src="${ctxStatic}/js/zepto.min.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}/";
	//确认删除
	 $(document).on("click", "#delWyRepairBudget", function() {
        $.confirm("您确定要删除吗?", "确认删除?", function() {        	
          	$.toast("删除成功!");
        }, function() {
         	//$.toast("取消删除!");
          //取消操作
        });
      });
      
      
</script>

</head>
<body ontouchstart style="background-color: #f8f8f8;">
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd"><h2>发起支付-结算清单列表</h2></div>
		<div class="weui_panel_bd">
			<table class="weui-table weui-border-tb">
                <thead>
                	<tr>
	                	<th>物料名称</th>
	                	<th>规格型号</th>
	                	<th>数量</th>
	                	<th>合价</th>
                	</tr>
                </thead>
                <tbody>
                <c:forEach items="${wyRepairSettlementMaterielList}" var="wyRepairBudgetMateriel" varStatus="status">
                	<tr>
                		<td>${wyRepairBudgetMateriel.materialName}</td>
                		<td>${wyRepairBudgetMateriel.specificationModel}</td>
                		<td>${wyRepairBudgetMateriel.num}</td>
                		<td>${wyRepairBudgetMateriel.count}</td>                		
                	</tr>                	
                </c:forEach>
                </tbody>
            </table>
				
		</div>
	</div>
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd"><h2>工时费用</h2></div>
		<div class="weui_panel_bd">
			<table class="weui-table weui-border-tb">
                <thead>
                	<tr><th>姓名</th><th>费用</th></tr>
                </thead>
                <tbody>
                <c:forEach items="${wyRepairSettlementLaborList}" var="wyRepairBudgetLabor" varStatus="status">
                	<tr>
                		<td>${wyRepairBudgetLabor.name}</td>                		
                		<td>${wyRepairBudgetLabor.count}</td>                		
                	</tr>                	
                </c:forEach>
                </tbody>
            </table>
		</div>
	</div>
	<div class="weui-panel__hd">总物料数量：${materielNum} 个,总金额：<span id="wyRepairBudget-totalCost">${totalCost}</span> 元</div>			
	
	<span>当前节点：${claimTask.name}${currTask.name}</span>&nbsp;&nbsp;
	<span>处理人：${member.memberName}</span><br/>
	
	<div style="text-align:center;">
	<!-- 认领和完成任务按钮 -->
		<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">
		<a href="${ctx}/wuye/backTask?bizId=${wyRepair.id}&procInsId=${currTask.processInstanceId}" class="weui_btn weui_btn_mini bg-orange f20">
			<i class="icon icon-71 f20"></i>回退
		</a>
		</c:if> 	
		<a href="${sb}" class="weui_btn weui_btn_mini bg-orange f20">
			<i class="icon icon-71 f20"></i>发起支付
		</a>
		<a href="javascript:history.go(-1);" class="weui_btn weui_btn_mini bg-blue f20">
			<i class="icon icon-71 f20"></i>返回
		</a>
		<br/>
		<a href="${ctx}/wyPrivateRepair/evaluate?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}" class="weui_btn weui_btn_mini bg-gray f20">
			直接评论
		</a>		
	</div>
</body>
</html>