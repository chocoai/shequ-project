<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<html>
<head>
	<title>物业报修</title>
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
		    color: #000;
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
        var status = ${wyRepair.repairStatus};
        if(status == 1){
        	$("#rname").attr("readonly", "true");
        	$("#rphone").attr("readonly", "true");
        	$("#rcontent").attr("readonly", "true");
        	$("#datetime-picker").attr("readonly", "true");
        	$("#rdetail").attr("readonly", "true");
        	$(".myweui_del_icon").css("display", "none");
        	$(".weui-uploader__input-box").css("display", "none");
        }
        alert(${wyRepair.appointmenttime});
	</script>
</head>
<body>
	<!-- <header class="demos-header" style="padding: 15px 0;">
      <h1 class="demos-title">物业报修</h1>
    </header> -->
	<div class="weui-cells weui-title">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__ft1" onclick="javascript:history.go(-1)">
			</div>
			<div class="weui-cell__bd">
				<p>物业报修</p>
			</div>
		</a>
	</div>

	<div class="weui-cells">
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src=""></div>
				<div class="weui-label">
					<p>客户名称</p>
				</div>
			<div class="weui-cell__ft">${member.memberName}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src=""></div>
				<div class="weui-label">
					<p>手机号码</p>
				</div>
			<div class="weui-cell__ft">${member.mobile}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><img src=""></div>
				<div class="weui-label">
					<p>详细地址</p>
				</div>
			<div class="weui-cell__ft">${room.WYName}${room.LYName}${room.roomNo}</div>
		</div>
	</div>

	<!-- <div class="division"></div> -->

	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">报修姓名</label></div>
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
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">预约时间<label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" id='datetime-picker' placeholder="点击选择" value="123"/>
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

	<div class="bt_bg">
		<a onclick="submit()" class="weui-btn weui-btn_primary but_style1">提交</a>
	</div>
	<!-- <a href="javascript:;" class="weui-btn weui-btn_warn but_left">返回</a> -->

	<div class="division1"></div>
	
	<script src="${ctxStatic}/js/jquery.min.js"></script>
	<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
	<script src="${ctxStatic}/js/repairs1.js"></script>
	<script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
	<script src="${ctxStatic}/js/myweixin.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	<script src="${ctxStatic}/js/common.js"></script>
	<script>
		$("#datetime-picker").datetimePicker();
	</script>
</body>
</html>