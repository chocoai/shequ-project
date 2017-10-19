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
	<link rel="stylesheet" href="${ctxStatic}/css/weui3.css">
	<script src="${ctxStatic}/js/zepto.min.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}/";
	function delWyRepairBudgetMateriel(id){
	      	$.confirm("您确定要删除吗?", "确认删除?", function() {
	        	$.ajax({
		            url: '${ctx}/sns/wyRepairSettlementMateriel/deleteById',
		            data: {id:id},
		            type: 'post',
		            async: false,
		            success: function(data) {            	       
		            	if(data.code==200){
		            		$.toast("删除成功!");
		            		window.location.reload();
		            	}else{
		            		$.toast("删除失败!");
		            	}            
		            }
		        });        	
	          	$.toast("删除成功!");
	        }, function() {
	         	//$.toast("取消删除!");
	          //取消操作
	        });
      }
      
       function delWyRepairBudgetLabor(id){
	      	$.confirm("您确定要删除吗?", "确认删除?", function() {
	        	$.ajax({
		            url: '${ctx}/sns/wyRepairSettlementLabor/deleteById',
		            data: {id:id},
		            type: 'post',
		            async: false,
		            success: function(data) {            	       
		            	if(data.code==200){
		            		$.toast("删除成功!");
		            		window.location.reload();
		            	}else{
		            		$.toast("删除失败!");
		            	}            
		            }
		        });        	
	          	$.toast("删除成功!");
	        }, function() {
	         	//$.toast("取消删除!");
	          //取消操作
	        });
      }
      
      
</script>

</head>
<body ontouchstart style="background-color: #f8f8f8;">
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd"><h2>结算清单列表</h2></div>
		<div class="weui_panel_bd">
			<table class="weui2-table weui2-border-tb">
                <thead>
                	<tr>
	                	<th>物料名称</th>
	                	<th>规格型号</th>
	                	<!-- <th>数量</th> -->
	                	<th>合价</th>
	                	<th>操作</th>
                	</tr>
                </thead>
                <tbody>
                <c:forEach items="${wyRepairSettlementMaterielList}" var="materiel" varStatus="status">
                	<tr>
                		<td>${materiel.materialName}</td>
                		<td>${materiel.specificationModel}</td>
                		<%-- <td>${wyRepairBudgetMateriel.num}</td> --%>
                		<td>${materiel.count}</td>
                		<td>
                			<a href="${ctx}/sns/wyRepairSettlementMateriel/view?id=${materiel.id}" >查看</a>
			            	<a href="javascript:;" id="delWyRepairBudget"><span onclick="delWyRepairBudgetMateriel('${materiel.id}')">删除</span></a>
			            	<a href="${ctx}/sns/wyRepairSettlementMateriel/form?id=${materiel.id}">修改</a>
			            </td>
                	</tr>                	
                </c:forEach>
                </tbody>
            </table>
            <a href="${ctx}/sns/wyRepairSettlementMateriel/form?repairId=${wyRepair.id}">添加</a>				
		</div>
	</div>
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd"><h2>工时费用</h2></div>
		<div class="weui_panel_bd">
			<table class="weui2-table weui2-border-tb">
                <thead>
                	<tr><th>姓名</th><th>费用</th><th>操作</th></tr>
                </thead>
                <tbody>
                <c:forEach items="${wyRepairSettlementLaborList}" var="labor" varStatus="status">
                	<tr>
                		<td>${labor.name}</td>                		
                		<td>${labor.count}</td>
                		<td>
                			<a href="${ctx}/sns/wyRepairSettlementLabor/view?id=${labor.id}">查看</a>
			            	<a href="javascript:;" id="delWyRepairBudgetLabor"  onclick="delWyRepairBudgetLabor('${labor.id}')">删除</a>
			            	<a href="${ctx}/sns/wyRepairSettlementLabor/form?id=${labor.id}">修改</a>
			            </td>
                	</tr>                	
                </c:forEach>
                </tbody>
            </table>
            <a href="${ctx}/sns/wyRepairSettlementLabor/form?repairId=${wyRepair.id}">添加</a>				
		</div>
	</div>
	<div class="weui-panel__hd">总物料数量：${materielNum} 个,总金额：<span id="wyRepairBudget-totalCost">${totalCost}</span> 元</div>			
	
	<span>当前节点：${claimTask.name}${currTask.name}</span>&nbsp;&nbsp;
	<span>处理人：${member.memberName}</span><br/>
	
	<div style="text-align:center;">
	<!-- 认领和完成任务按钮 -->
		<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">
		<a href="${ctx}/wuye/backTask?bizId=${wyRepair.id}&procInsId=${wyRepair.procInsId}" class="weui_btn weui_btn_mini bg-orange f20">
			<i class="icon icon-71 f20"></i>回退
		</a>
		</c:if>	
		<a href="${ctx}/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}" class="weui_btn weui_btn_mini bg-orange f20">
			<i class="icon icon-71 f20"></i>完成维修
		</a>
		<a href="javascript:history.go(-1);" class="weui_btn weui_btn_mini bg-blue f20">
			<i class="icon icon-71 f20"></i>返回
		</a>		
	</div>
</body>
</html>