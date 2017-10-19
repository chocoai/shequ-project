<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>我的报修</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="keywords" content="Javascript,cancelBubble,stopPropagation" /> 
	<link rel="stylesheet" href="${ctxStatic}/css/myRepair.css">	
	<script src="${ctxStatic}/js/jquery.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
        $(document).ready(function(){
		    $('.avatarUrl').css('height',$('.avatarUrl').css('width'));
		    $('.repairPhoto').css('height',$('.repairPhoto').css('width'));
		});
	</script>
	<script src="${ctxStatic}/js/myRepair.js"></script>
</head>
<body>
	<div class="mytitle">
		<div class="home" onclick="window.location.href='${ctx}/index'"></div>
		<span class="titleName">我的报修</span>
		<div class="process">
		<c:forEach items="${flowNames}" var="name" varStatus="status">
			<span class="process_content">${status.index +1}.${name}<c:if test="${status.index+1 != fn:length(flowNames)}">>></c:if></span>
		</c:forEach>
			
			<!-- <div class="process">1.申请>2.申请>3.申请>4.申请>5.申请>6.申请>7.申请>8.申请>9.申请</div> -->
		</div>
	</div>
	<div class="fix"></div>
	<c:forEach items="${wyRepairList}" var="wyRepairList">
	<div class="preContent" id="${wyRepairList.id}" onclick="show('${wyRepairList.id}')">
		<div class="avatar">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent">
			<div class="other">
				<div class="name">${wyRepairList.applyname}</div>
				<c:if test="${wyRepairList.currStep<2}">
				<div class="edit" onclick="edit('${wyRepairList.id}', event)">修改</div>				
				<div class="del" onclick="del('${wyRepairList.id}', event)">删除</div>
				</c:if>
				<div class="progress">进度：${wyRepairList.currStep}/${fn:length(flowNames)}</div>
			</div>
			<div class="repairTime"><fmt:formatDate value="${wyRepairList.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			<div class="content">报修内容：${wyRepairList.content}</div>
			<div class="photo">
				<c:forEach items="${wyRepairList.imgList}" var="imgList">
				<img class="repairPhoto" src="${ctxStatic}/${imgList}"/>
				</c:forEach>
			</div>
		</div>
	</div>
	</c:forEach>
</body>