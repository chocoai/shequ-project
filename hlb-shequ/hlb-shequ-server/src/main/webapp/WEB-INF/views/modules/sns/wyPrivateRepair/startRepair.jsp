<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>物业报修</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/base.css">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/layer.css" id="layui_layer_skinlayercss">
		<link rel="stylesheet" href="${ctxStatic}/css/repairs.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
		<script src="${ctxStatic}/js/zepto.min.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/iscroll-probe.js"></script>
        <script src="${ctxStatic}/js/api.js"></script>
        <script src="${ctxStatic}/js/getscript.js"></script>
		<script src="${ctxStatic}/js/convertor.js"></script>
		<script src="${ctxStatic}/js/swiper.min.js"></script>
		<script src="${ctxStatic}/js/common.js"></script>
        <script src="${ctxStatic}/js/jweixin-1.0.0.js"></script>
        <script src="${ctxStatic}/js/select.js"></script>
        <script src="${ctxStatic}/js/picker.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
            var ctxStatic = "${ctxStatic}/";
            $(function(){
            	$("#beginTime").datetimePicker({title:"选择日期时间",min:'2015-12-10',max:'2050-10-01'});                            
                if('${imgnum}'==1){
                    $(".picture1").attr("src", "${wyRepair.imgList[0]}");
                    $(".picture1").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $("#picture1").val("${wyRepair.imgList[0]}");
                }
                if('${imgnum}'==2){
                    $(".picture1").attr("src", "${wyRepair.imgList[0]}");
                    $(".picture2").attr("src", "${wyRepair.imgList[1]}");
                    $(".picture1").css('display','block'); 
                    $(".picture2").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $(".del2").css('display','block'); 
                    $("#picture1").val("${wyRepair.imgList[0]}");
                    $("#picture2").val("${wyRepair.imgList[1]}");
                }
                if('${imgnum}'==3){
                    $(".picture1").attr("src", "${wyRepair.imgList[0]}");
                    $(".picture2").attr("src", "${wyRepair.imgList[1]}");
                    $(".picture3").attr("src", "${wyRepair.imgList[2]}");
                    $(".picture1").css('display','block'); 
                    $(".picture2").css('display','block'); 
                    $(".picture3").css('display','block'); 
                    $(".del1").css('display','block'); 
                    $(".del2").css('display','block'); 
                    $(".del3").css('display','block'); 
                    $("#picture1").val("${wyRepair.imgList[0]}");
                    $("#picture2").val("${wyRepair.imgList[1]}");
                    $("#picture3").val("${wyRepair.imgList[2]}");
                }
            });
		</script>
		<style>
			/*body{margin:0;overflow-x:hidden;padding:0;}*/
            body{
                margin:0;
                overflow-x:
                hidden;padding:0;
                background-color: rgba(248, 253, 169, 0.45);
                font-size: 17px;
            }
		</style>
</head>
<body>
	<!-- 广告 -->
	<div class="focus" id="focus">
        <div class="hd">
            <ul>
                <li class="on">
                    1
                </li>
                <li class="">
                    2
                </li>
            </ul>
        </div>
        <!--下面的limit="0,2"是幻灯的个数，2代表2张图，以此类推，site_id=57是你广告位的ID-->
        <div class="bd">
            <div class="tempWrap" style="overflow:hidden; position:relative;">
                <ul style="width: 640px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translate(0px, 0px) translateZ(0px);">
                    <c:forEach items="${ads}" var="ads">
                    <li style="display: table-cell; vertical-align: top; width: 320px;">
                        <a>
                            <img src="${ads.imgSrc}" />
                        </a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

    <!-- 用户信息 -->
    <div class="info">
    	<div class="title">
    		<span class="titlename">物业报修</span>
    	</div>
    	<div class="username">
    		<span class="infolist">客户名称 : ${wyRepair.member.memberName}</span>
    	</div>
    	<div class="mobile">
    		<span class="infolist">手机号码 : ${wyRepair.member.mobile}</span>
    	</div>
    	<div class="address">
    		<span class="infolist">详细地址 : ${wyRepair.room.WYName}${wyRepair.room.LYName}${wyRepair.room.roomNo}</span>
    	</div>
    </div>

    <div class="border"></div>

    <!-- 报修内容 -->
    <div class="content1">
    	<form id="repairs-form" method="post">
    		<div class="area">
                <input type="hidden" id="contentid" name="contentid" value="${wyRepair.id}"></input>
    			<input class="private" type="radio" name="repairtype" value="1" disabled="true" ${(wyRepair.repairtype eq '1')?'checked="checked"':''} />
    			<span>私人区域</span>
    			<input class="public" type="radio" name="repairtype" value="0" disabled="true" ${(wyRepair.repairtype eq '0')?'checked="checked"':''}/>
    			<span>公共区域</span>
    		</div>
    		<div class="repairsname">
    			<div >报修姓名</div>
    			<input id="rname" name="rname" value="${wyRepair.applyname}" disabled="true"/>
    		</div>
    		<div class="phone">
    			<div>联系电话</div>
    			<input id="rphone" name="rphone" value="${wyRepair.phone}" disabled="true"/>
    		</div>
    		<div class="repairscontent">
    			<div>报修内容</div>
    			<input id="rcontent" name="rcontent" value="${wyRepair.content}" disabled="true"/>
    		</div>
    		<div class="detail">
    			<div>详细描述</div>
    			<textarea rows="50" cols="3" name="rdetail" id="rdetail" readonly="readonly">${wyRepair.contentdetail}</textarea>
    		</div>
    		<div class="photo">
    			<div class="camera">
    				<image class="img img0" src="${ctxStatic}/image/timg.jpg"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture1"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture2"></image>
    			</div>
    			<div class="picture">
    				<image class="img picture3"></image>
    			</div>
    		</div>
    		<div class="ordertime">
    			<div class="pretime">维修时间</div>
    			<input id="beginTime" type="text" name="beginTime" />
    		</div>
    	</form>
    </div>
    <div class="border"></div>
    
<c:if test="${not empty selectHandlerformHtml}"> 
    ${selectHandlerformHtml}
</c:if>

<c:if test="${not empty delegateHandlerFormHtml}">
    ${delegateHandlerFormHtml}
</c:if>
	
<!-- 提交按钮 -->
<div class="submit">
    <span>当前节点：${currTask.name}${claimTask.name}</span>&nbsp;&nbsp;<span>处理人：${member.memberName}</span><br/>


   <!-- 操作按钮 -->
   <c:if test="${not empty wyRelationCandidate && wyRelationCandidate.allowBack}">
    <div class="submit1">
        <a href="${ctx}/wuye/backTask?bizId=${wyRepair.id}&procInsId=${wyRepair.procInsId}" class="weui_btn weui_btn_mini bg-blue f20" style="width: 150%;">回退</a>
    </div>
    </c:if>
   	<div class="submit1">
        <a onclick="startRepair();" href="#" class="weui_btn weui_btn_mini bg-blue f20" style="width: 150%;">开始维修</a>
    </div>
    <div class="submit2">
        <a onclick="javascript:history.go(-1)" class="weui_btn weui_btn_mini weui_btn_plain_primary f20" style="width: 150%;">返回</a>
    </div>
	
</div>
</body>
<script type="text/javascript">	
	function startRepair(){
		//更新开始维修预约时间
		var beginTime=$("#beginTime").val();
		
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
	        url: "${ctx}/wyPrivateRepair/updateAppointmentTime",
	        dataType: "json",
	        data:param,
	        async:false,
	        success: function (result) {	            
				if(result.code ==200){
					window.location.href="${ctx}/wuye/fulfilTask?procInsId=${wyRepair.procInsId}&bizId=${wyRepair.id}";
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

</html>