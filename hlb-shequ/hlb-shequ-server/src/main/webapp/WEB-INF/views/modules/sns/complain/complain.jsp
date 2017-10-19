<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<html>
<head>
	<title>物业报事</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
				<p>物业报事</p>
			</div>
		</a>
	</div>

	<div class="weui-cells usershow" style="display: none">
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src="${ctxStatic}/image/客户名称.png"></div>
				<div class="weui-label">
					<p>住户名称</p>
				</div>
			<div class="weui-cell__ft">${member.memberName}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src="${ctxStatic}/image/手机号码.png"></div>
				<div class="weui-label">
					<p>手机号码</p>
				</div>
			<div class="weui-cell__ft">${member.mobile}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src="${ctxStatic}/image/详细地址.png"></div>
				<div class="weui-label">
					<p>详细地址</p>
				</div>
			<div class="weui-cell__ft">${room.WYName}${room.LYName}${room.roomNo}</div>
		</div>
	</div>

	<div class="weui-cells cscshow" style="display: none">
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src=${ctxStatic}/image/客户名称.png""></div>
				<div class="weui-label">
					<p>住户名称</p>
				</div>
			<div class="weui-cell__ft">${complainMember.memberName}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src="${ctxStatic}/image/手机号码.png"></div>
				<div class="weui-label">
					<p>手机号码</p>
				</div>
			<div class="weui-cell__ft">${complainMember.mobile}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src="${ctxStatic}/image/详细地址.png"></div>
				<div class="weui-label">
					<p>详细地址</p>
				</div>
			<div class="weui-cell__ft">${room.WYName}${room.LYName}${room.roomNo}</div>
		</div>
	</div>

	<!-- <div class="division"></div> -->

	<div class="weui-cells weui-cells_form">
		<input type="hidden" id="contentid" name="contentid" value="${wyComplain.id}"/>  
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">报事人</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" placeholder="请输入姓名" id="rname" name="rname" value="${wyComplain.applyname}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">联系电话</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="tel" placeholder="请输入电话" id="rphone" name="rphone" value="${wyComplain.phone}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">投诉内容</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" placeholder="请输入内容" id="rcontent" name="rcontent" value="${wyComplain.content}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<textarea class="weui-textarea" placeholder="请输入详细内容" rows="3" name="rdetail" id="rdetail">${wyComplain.contentdetail}</textarea>
			</div>
		</div>
		<div class="weui-cell" style="border-bottom: 1px solid #e5e5e5;">
			<div class="weui-cell__bd">
				<div class="weui-uploader">
					<div class="weui-uploader__bd">
						<ul class="weui-uploader__files" id="uploaderFiles">
							<c:forEach items="${wyComplain.imgList}" var="imgls">
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

	<!-- 投诉页面 -->
	<div class="bt_bg start" style="display: none">
		<a onclick="submit(2)" class="weui-btn weui-btn_primary but_style1">提交</a>
	</div>
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
		<!-- 客服中心受理 -->
		<div class="weui-cell accept" style="display: none">
			<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">	
				<a href="${ctx}/wuye/backTask?bizId=${wyComplain.id}&procInsId=${wyComplain.procInsId}" class="weui-btn weui-btn_warn but_style1">回退</a>
			</c:if>
		    <a onclick="changestatus(true, 'complain')" class="weui-btn weui-btn_primary but_style1">接受</a>
		    <a onclick="changestatus(false, 'complain')" class="weui-btn weui-btn_default but_style1">不接受</a>
		    <a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
		<!-- 投诉处理页面 -->
		<div class="weui-cell deal" style="display: none">
			<c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">	
				<a href="${ctx}/wuye/backTask?bizId=${wyComplain.id}&procInsId=${wyComplain.procInsId}" class="weui-btn weui-btn_warn but_style1">回退</a>
			</c:if>
		    <a href="${ctx}/wuye/fulfilTask?procInsId=${wyComplain.procInsId}&bizId=${wyComplain.id}" class="weui-btn weui-btn_primary but_style1">已处理</a>
		    <a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
		<!-- 投诉验收 -->
		<div class="weui-cell check" style="display: none">
		    <a onclick="completeTask(true)" class="weui-btn weui-btn_primary but_style1">合格</a>
		    <a onclick="completeTask(false)" class="weui-btn weui-btn_default but_style1">不合格</a>
		    <a onclick="javascript:history.go(-1)" class="weui-btn weui-btn_warn but_style1">返回</a>
		</div>
	</div>
	<!-- 查看历史 -->
	<div class="bt_bg show" style="display: none">
		<a href="${ctx}/wuye/processDetail?procInstId=${wyComplain.procInsId}" class="weui-btn weui-btn_primary but_style1">查看处理流程</a>
	</div>
	
	<div class="division1"></div>
	<script type="text/javascript">
		function disabledList(){
			$("#rname").attr("readonly", "true");
			$("#rphone").attr("readonly", "true");
			$("#rcontent").attr("readonly", "true");
			$("#rdetail").attr("readonly", "true");
			$(".myweui_del_icon").css("display", "none");
			$(".weui-uploader__input-box").css("display", "none");
		}
		var status = ${wyComplain.status}+"";
		if(status == "1"){
			disabledList();
		}
		var node = "${node}";//每个调用该页面的方法均会传入一个node节点，根据node觉得报修内容下方显示信息
		switch(node){
			case "start"://开始节点
				$(".usershow").css("display", "block");
        		$(".start").css("display", "block");
        		break;
        	case "accept"://客服中心受理
        		$(".cscshow").css("display", "block");
        		$(".serverinfo").css("display", "block");
        		$(".accept").css("display", "block");
        		disabledList();
        		break;
        	case "deal"://投诉处理页面
        		$(".cscshow").css("display", "block");
        		$(".serverinfo").css("display", "block");
        		$(".deal").css("display", "block");
        		break;
        	case "check"://投诉验收
        		$(".cscshow").css("display", "block");
        		$(".serverinfo").css("display", "block");
        		$(".check").css("display", "block");
        		break;
        	case "show"://开始节点
				$(".usershow").css("display", "block");
        		$(".show").css("display", "block");
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
			window.location.href='${ctx}/wuye/fulfilTask?procInsId=${wyComplain.procInsId}&bizId=${wyComplain.id}&vars='+vars;
			
			
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
				data: {"type":type, "procInsId":"${wyComplain.procInsId}"},
				type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		completeTask(isvalid);
	            	}        
	            }
			});
		}
	</script>
</body>
</html>