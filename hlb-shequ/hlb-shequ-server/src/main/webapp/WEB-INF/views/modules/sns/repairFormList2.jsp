<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>维修表单</title>
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
		<div class="weui_panel_hd"><h2>预算清单列表</h2></div>
		<div class="weui_panel_bd">
			<c:forEach items="${wyRepairBudgetMaterielList}" var="wyRepairBudgetMateriel"
				varStatus="status">
				<div class="weui_media_box weui_media_text">
					<h4 class="weui_media_title">物料名称：${wyRepairBudgetMateriel.materialName}</h4>
					<h4 class="weui_media_title">数量：${wyRepairBudgetMateriel.num}</h4>
					<h4 class="weui_media_title">合价：${wyRepairBudgetMateriel.count}</h4>					
					<div style="text-align:center;letter-spacing: 5px;">
						<a href="${ctx}/sns/wyRepairBudgetMateriel/view?id=${wyRepairBudgetMateriel.id}" class="weui_btn weui_btn_mini weui_btn_primary">查看</a>
			            <a href="javascript:;" id="delWyRepairBudget"  onclick="" class="weui_btn weui_btn_mini weui_btn_warn">删除</a>
			            <a href="${ctx}/sns/wyRepairBudgetMateriel/form?id=${wyRepairBudgetMateriel.id}" class="weui_btn weui_btn_mini weui_btn_warn">修改</a>
		            </div>
				</div>
			</c:forEach>
			<a href="${ctx}/sns/wyRepairBudgetMateriel/form?repairId=${wyRepair.id}" class="weui_btn weui_btn_mini weui_btn_warn">添加</a>
				
		</div>
	</div>
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd"><h2>工时费用</h2></div>
		<div class="weui_panel_bd">
			<c:forEach items="${wyRepairBudgetLaborList}" var="wyRepairBudgetLabor"
				varStatus="status">
				<div class="weui_media_box weui_media_text">
					<h4 class="weui_media_title">姓名：${wyRepairBudgetLabor.name}</h4>
					<h4 class="weui_media_title">工时：${wyRepairBudgetLabor.spentHour}</h4>
					<h4 class="weui_media_title">费用：${wyRepairBudgetLabor.count}</h4>					
					<div style="text-align:center;letter-spacing: 5px;">
						<a href="${ctx}/sns/wyRepairBudgetLabor/view?id=${wyRepairBudgetLabor.id}" class="weui_btn weui_btn_mini weui_btn_primary">查看</a>
			            <a href="javascript:;" id="delWyRepairBudgetLabor"  onclick="" class="weui_btn weui_btn_mini weui_btn_warn">删除</a>
			            <a href="${ctx}/sns/wyRepairBudgetLabor/form?id=${wyRepairBudgetLabor.id}" class="weui_btn weui_btn_mini weui_btn_warn">修改</a>
		            </div>
				</div>
			</c:forEach>
			<a href="${ctx}/sns/wyRepairBudgetLabor/form?repairId=${wyRepair.id}" class="weui_btn weui_btn_mini weui_btn_warn">添加</a>
				
		</div>
	</div>
	<div class="weui-panel__hd">总物料数量：${materielNum} 个,总金额：<span id="wyRepairBudget-totalCost">${totalCost}</span> 元</div>			
	
	<span>当前节点：${claimTask.name}${currTask.name}</span>&nbsp;&nbsp;
	<span>处理人：${member.memberName}</span><br/>
	
	<div style="text-align:center;">
	<!-- 认领和完成任务按钮 -->		
		<c:if test="${not empty formHtml}">
			${formHtml}
		</c:if>
		<a href="javascript:history.go(-1);" class="weui_btn weui_btn_mini bg-blue f20">
			<i class="icon icon-71 f20"></i>返回
		</a>		
	</div>		
	
</body>