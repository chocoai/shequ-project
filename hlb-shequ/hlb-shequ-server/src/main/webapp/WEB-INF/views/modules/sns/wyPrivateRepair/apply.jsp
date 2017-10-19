<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>物业报修</title>
	<meta charset="utf-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
	<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
	<link rel="stylesheet" href="${ctxStatic}/css/demos.css">
	<style type="text/css">
		.weui-cell__ft {
		    color: rgba(0, 0, 0, 0.6);
		}
		.weui-btn+.weui-btn {
		    margin-top: 0;
		}
		body{
			background-color: #f8f8f8;
		}
	</style>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var bizKey = "${bizKey}";
        var ctxStatic = "${ctxStatic}/"; 
	</script>
	<script src="${ctxStatic}/js/jquery.min.js"></script>
	<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
</head>
<body>
	<!-- <header class="demos-header" style="padding: 15px 0;">
      <h1 class="demos-title">物业报修</h1>
    </header> -->
	<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__ft1">
			</div>
			<div class="weui-cell__bd">
				<p>物业报修</p>
			</div>
		</a>
	</div>

	<div class="weui-cells usershow">
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/客户名称.png">
			</div>
			<div class="weui-label">
				<p>客户名称</p>
			</div>
			<div class="weui-cell__ft">${member.memberName}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/手机号码.png">
			</div>
			<div class="weui-label">
				<p>手机号码</p>
			</div>
			<div class="weui-cell__ft">${member.mobile}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/详细地址.png">
			</div>
			<div class="weui-label">
				<p>详细地址</p>
			</div>
			<div class="weui-cell__ft">${room.WYName}${room.LYName}${room.roomNo}</div>
		</div>
	</div>

	<div class="weui-cells cscshow" style="display: none">
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/客户名称.png">
			</div>
			<div class="weui-label">
				<p>客户名称</p>
			</div>
			<div class="weui-cell__ft">${wyRepair.member.memberName}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/手机号码.png">
			</div>
			<div class="weui-label">
				<p>手机号码</p>
			</div>
			<div class="weui-cell__ft">${wyRepair.member.mobile}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<img src="${ctxStatic}/image/详细地址.png">
			</div>
			<div class="weui-label">
				<p>详细地址</p>
			</div>
			<div class="weui-cell__ft">${wyRepair.room.WYName}${wyRepair.room.LYName}${wyRepair.room.roomNo}</div>
		</div>
	</div>

	<!-- <div class="division"></div> -->

	<div class="weui-cells weui-cells_form">
		<input type="hidden" id="contentid" name="contentid" value="${wyRepair.id}"/>  
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">报修人</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" placeholder="请输入姓名" id="rname" name="rname" value="${wyRepair.applyname}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">联系电话</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="tel" placeholder="请输入电话" id="rphone" name="rphone" value="${wyRepair.phone}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">报修内容</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" placeholder="请输入内容" id="rcontent" name="rcontent" value="${wyRepair.content}">
			</div>
		</div>
		<div class="weui-cell preTime">
			<div class="weui-cell__hd"><label class="weui-label">预约时间<label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" id='datetime-picker' placeholder="点击选择" value="<fmt:formatDate value='${wyRepair.appointmenttime}' pattern='yyyy-MM-dd HH:mm'/>"/>
			</div>
		</div>
		<div class="weui-cell repairTime" style="display: none">
			<div class="weui-cell__hd"><label class="weui-label">维修时间<label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" id='datetime-picker1' placeholder="点击选择"/>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<textarea class="weui-textarea" placeholder="请输入详细内容" rows="3" name="rdetail" id="rdetail">${wyRepair.contentdetail}</textarea>
			</div>
		</div>
		<div class="weui-cell" style="border-bottom: 1px solid #e5e5e5;">
			<div class="weui-cell__bd">
				<div class="weui-uploader">
					<div class="weui-uploader__bd">
						<ul class="weui-uploader__files" id="uploaderFiles">
							<c:forEach items="${wyRepair.imgList}" var="imgls">
								<div class="myweui_upload_img">
									<div class="myweui_del_icon onclick="delphoto(this)"">
										<img src="${ctxStatic}/image/del.png"></img>
									</div>
									<li class="weui-uploader__file" style="background-image:url('${imgls}')"></li>	
								</div>
							</c:forEach>
						</ul>
						<div class="weui-uploader__input-box" onclick="startCamera()">
						<!-- <div class="weui-uploader__input-box" onclick="test()"> -->
							<input id="uploaderInput" class="weui-uploader__input" readonly="true" accept="image/*" multiple="">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="division"></div>

	<div class="bt_bg" style="display: none">
		<a onclick="submit(1)" class="weui-btn weui-btn_primary but_style1">提交</a>
	</div>

	<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.type eq '3'}"> 
		<div class="weui-cells">
			<a class="weui-cell weui-cell_access" href="${ctx}/sns/wyActCandidate/selectOrg?procInsId=${wyRelationCandidate.procInsId}&procDefId=${wyRelationCandidate.procDefId}&taskId=${wyRelationCandidate.taskId}&source=${wyRelationCandidate.source}">
				<div class="weui-cell__bd"><p>选择下个节点人员:</p></div>
				<div class="weui-cell__ft">选择待办人员</div>
			</a>
			<c:if test="${not empty selectName}">
				<div class="weui-cell">
					<!-- <div class="weui-cell__hd"><img src=""></div> -->
						<!-- <div class="weui-label">
							<p>${selectName}</p>
						</div> -->
					<div class="weui-cell__ft" style="text-align: left;">已选人员：${selectName}</div>
				</div>
			</c:if>
		</div>
	</c:if>
	
	<!-- 委托办理 -->
	<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowDelegateTask}">
	    指定委托办理人:<a href="${ctx}/sns/wyActCandidate/delegateTaskShow?bizId=${wyRepair.id}&procInsId=${wyRelationCandidate.procInsId}&procDefId=${wyRelationCandidate.procDefId}&taskId=${wyRelationCandidate.taskId}&source=${wyRelationCandidate.source}">选择委托办理人</a>
	</c:if>
	
	<div class="weui-cells serverinfo" style="margin-top: 0px; display: none">
		<div class="weui-cell">
			<!-- <div class="weui-cell__hd"><img src=""></div> -->
			<div class="weui-label">
				<p>当前节点：</p>
			</div>
			<div class="weui-cell__ft">${currTask.name}${claimTask.name}</div>
		</div>
		<div class="weui-cell">
			<!-- <div class="weui-cell__hd"><img src=""></div> -->
			<div class="weui-label">
				<p>处理人：</p>
			</div>
			<div class="weui-cell__ft">${member.memberName}</div>
		</div>
		<div class="weui-cell chooseStatus_csc" style="display: none">
			<!-- <div class="button_3">
	           <a onclick="completeTask(true)" href="#" class="weui_btn weui_btn_mini weui_btn_primary">有效</a>
	       </div>
		   	<div class="button_3">
		           <a onclick="completeTask(false)" href="#" class="weui_btn weui_btn_mini weui_btn_default">无效</a>
		       </div>
		   	<div class="button_3">
		           <a onclick="javascript:history.go(-1)" class="weui_btn weui_btn_mini weui_btn_warn">返回</a>
		    </div> -->
		    <a onclick="changestatus(true, 'repair')" class="weui-btn weui-btn_primary but_style1">有效</a>
		    <a onclick="changestatus(false, 'repair')" class="weui-btn weui-btn_default but_style1">无效</a>
		    <a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
		<div class="weui-cell chooseStatus_grabOrder" style="display: none; padding-bottom: 90px;">
			<!-- <div class="button_2_left">
		        <a href="${ctx}/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}" class="weui_btn weui_btn_mini bg-blue f20">抢单</a>     
		    </div>
		   	<div class="button_2_right">
		        <a onclick="javascript:history.go(-1)" href="#" class="weui_btn weui_btn_mini bg-green f20">返回</a>
		    </div> -->
		    <a href="${ctx}/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}" class="weui-btn weui-btn_primary but_style1">抢单</a>
		    <a onclick="javascript:history.go(-1)" href="#" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
		<div class="weui-cell chooseStatus_startRepair" style="display: none; padding-bottom: 90px;">
			<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">
			    <a href="${ctx}/wuye/backTask?bizId=${wyRepair.id}&procInsId=${wyRepair.procInsId}" class="weui-btn weui-btn_warn but_style1">回退</a>
		    </c:if>
		   		<a onclick="startRepair();" href="#" class="weui-btn weui-btn_primary but_style1">开始维修</a>
		    	<a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
	</div>

	<!-- <a href="javascript:;" class="weui-btn weui-btn_warn but_left">返回</a> -->

	<div class="division1"></div>

	<script type="text/javascript">
		var status = ${wyRepair.repairstatus}+"";
        if(status == "1"){
        	$("#rname").attr("readonly", "true");
        	$("#rphone").attr("readonly", "true");
        	$("#rcontent").attr("readonly", "true");
        	$("#datetime-picker").attr("readonly", "true");
        	$("#rdetail").attr("readonly", "true");
        	$(".myweui_del_icon").css("display", "none");
        	$(".weui-uploader__input-box").css("display", "none");
        }

        if("${wyRepair.member.memberName}" != ""){
        	$(".myweui_del_icon").css("display", "none");
        	$(".weui-uploader__input-box").css("display", "none");
        	$(".usershow").css("display", "none");
        	$(".cscshow").css("display", "block");
        	$(".serverinfo").css("display", "block");
        }
        var node = "${node}";//每个调用该页面的方法均会传入一个node节点，根据node觉得报修内容下方显示信息
        switch(node){
        	case "start"://开始节点
        		$(".bt_bg").css("display", "block");
        		break;
        	case "csc"://客服中心受理
        		$(".chooseStatus_csc").css("display", "block");
        		break;
        	case "grabOrder":
        		$(".chooseStatus_grabOrder").css("display", "block");
        		break;
        	case "startRepair":
        		$(".preTime").css("display", "none");
        		$(".repairTime").css("display", "flex");
        		$("#datetime-picker1").datetimePicker();
        		$(".chooseStatus_startRepair").css("display", "block");
        		break;
        	case "show":
        		$(".serverinfo").css("display", "none");
        		break;
        }
	</script>
	<script src="${ctxStatic}/js/repairs1.js"></script>
	<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
	<script src="${ctxStatic}/js/myweixin.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	<script src="${ctxStatic}/js/common.js"></script>
	<script>
		$("#datetime-picker").datetimePicker();
		function completeTask(isvalid){
			var vars;
			if(isvalid){
				vars='[{"key":"commonFlow","value":"true"},{"key":"condition2","value":"22"},{"key":"condition3","value": "33"}]';
			}else{
				vars='[{"key":"commonFlow","value":"false"}]';
			}
			vars=encodeURIComponent(vars);		
			window.location.href='${ctx}/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}&vars='+vars;
		}

		function changestatus(isvalid, type){
			//是否需要选择处理人			
			<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.type eq '3'}">
				var flag=false;
				var formdata={
					procDefId : "${wyRelationCandidate.procDefId}",
					taskId : "${wyRelationCandidate.taskId}",
					source : "${wyRelationCandidate.source}",
					procInsId:"${wyRelationCandidate.procInsId}"
				};
				$.ajax({
		            url: '${ctx}/sns/wyActCandidate/checkSelectOrg',
		            data: formdata,
		            type: 'post',
		            async: false,
		            success: function(data) {            	       
		            	if(data.code==200){
		            		
		            	}else{
		            		flag=true;
		            		return false;
		            	}            
		            }
		        });
		        if(flag){
					alert("请先选择下一节点处理人");
					return;
				}
			</c:if>

			$.ajax({
				url: "${ctx}/wuye/changestatus",
				data: {"type":type, "procInsId":"${wyRepair.procInsId}"},
				type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		completeTask(isvalid);
	            	}        
	            }
			});
		}

		function startRepair(){
			//更新开始维修预约时间
			var beginTime=$("#datetime-picker1").val();
			
			if(beginTime==undefined||beginTime==""||beginTime==null){
				layer.open({
					content : "请选择维修时间",
					skin : 'msg',
					time : 2
				});
				return;
			}
			var param={
				bizId : "${wyRepair.id}",
				appointmentTime:beginTime
			};
			$.ajax({
		        type: "post",
		        url: ctx+"/wyPrivateRepair/updateAppointmentTime",
		        dataType: "json",
		        data:param,
		        async:false,
		        success: function (result) {	            
					if(result.code ==200){
						window.location.href=ctx+"/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}";
					}else{
						layer.open({
							content : "更新维修时间失败!",
							skin : 'msg',
							time : 2
						});
					}
		        }
		    });
		}
	</script>
</body>
</html>