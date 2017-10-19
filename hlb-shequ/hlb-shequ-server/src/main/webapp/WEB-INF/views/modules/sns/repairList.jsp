<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>报修列表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/repairList.css">
	<link rel="stylesheet" href="${ctxStatic}/css/myRepair.css">
	<script src="${ctxStatic}/js/jquery.js"></script>
	<script src="${ctxStatic}/js/repairList.js"></script>
	<script src="${ctxStatic}/js/myRepair.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
        $(document).ready(function(){
        	var type = "${type}";
		    $('.avatarUrl').css('height',$('.avatarUrl').css('width'));
		    $("#"+type).addClass("active");
		});
    </script>
</head>
<body style="margin: 0">
    <header id="header">
        <div class="header_div">
            <div id="left_div" onclick="javascript:history.go(-1)"></div>
            <div id="middle_div">
                <h1 id="title">报修列表</h1>
            </div>
        </div>
    </header>
    <div class="tab_title">
    	<a onClick="window.location.href='${ctx}/wuye/repairList?type=weichuli'" id="weichuli">未处理</a>
    	<a onClick="window.location.href='${ctx}/wuye/repairList?type=yichuli'" id="yichuli">已处理</a>
        <a onClick="window.location.href='${ctx}/wuye/repairList?type=qiangdan'" id="qiangdan">未签收</a>        
    </div>
    
    <c:if test="${type eq 'weichuli'}">
    <c:forEach items="${daibanTasks}" var="wyRepairList">
	<div class="preContent" id="${wyRepairList.id}" onclick="show('${wyRepairList.id}')">
		<div class="avatar">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent">
			<div class="other">
				<div class="name">${wyRepairList.applyname}</div>
				<div class="edit" onclick="edit('${wyRepairList.id}', event)">修改</div>
				<div class="del" onclick="del('${wyRepairList.id}', event)">删除</div>
				<div class="progress">进度：${wyRepairList.currStepName}</div>
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
	</c:if>
	
	<c:if test="${type eq 'yichuli'}">
    <c:forEach items="${yichuli}" var="wyRepairList">
	<div class="preContent" id="${wyRepairList.id}" onclick="show('${wyRepairList.id}')">
		<div class="avatar">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent">
			<div class="other">
				<div class="name">${wyRepairList.applyname}</div>
				<div class="edit" onclick="edit('${wyRepairList.id}', event)">修改</div>
				<div class="del" onclick="del('${wyRepairList.id}', event)">删除</div>
				<div class="progress">进度：${wyRepairList.currStepName}</div>
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
	</c:if>
	
	<c:if test="${type eq 'qiangdan'}">
    <c:forEach items="${grabtasks}" var="wyRepairList">
	<div class="preContent" id="${wyRepairList.id}" onclick="show('${wyRepairList.id}')">
		<div class="avatar">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent">
			<div class="other">
				<div class="name">${wyRepairList.applyname}</div>
				<div class="edit" onclick="edit('${wyRepairList.id}', event)">修改</div>
				<div class="del" onclick="del('${wyRepairList.id}', event)">删除</div>
				<div class="progress">进度：${wyRepairList.currStepName}</div>
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
	</c:if>
<%-- 
	<c:if test="${type eq 'weichuli'}">
	<c:forEach items="${daibanTasks}" var="repair">
    <div class="preContent" id="${repair.id}" style="height: 90px;">
		<div class="avatar" style="height: 90px;">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent" style="height: 90px;">
			<div class="other">
				<div class="name" style="width: 95%">姓名：${repair.applyname}</div>
			</div>
			<div class="repairTime"><fmt:formatDate value="${repair.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			<div class="content">${repair.contentdetail}</div>
		</div>
	</div>
	</c:forEach>
	</c:if>
	
	<c:if test="${type eq 'yichuli'}">
	<c:forEach items="${daibanTasks}" var="repair">
    <div class="preContent" id="${repair.id}" style="height: 90px;">
		<div class="avatar" style="height: 90px;">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent" style="height: 90px;">
			<div class="other">
				<div class="name" style="width: 95%">姓名：${repair.applyname}</div>
			</div>
			<div class="repairTime"><fmt:formatDate value="${repair.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			<div class="content">${repair.contentdetail}</div>
		</div>
	</div>
	</c:forEach>
	</c:if>
	
	<c:if test="${type eq 'qiangdan'}">
	<c:forEach items="${grabtasks}" var="repair">
    <div class="preContent" id="${repair.id}" style="height: 90px;">
		<div class="avatar" style="height: 90px;">
			<img class="avatarUrl" src="${ctxStatic}/image/avatar.png" />
		</div>
		<div class="repairContent" style="height: 90px;">
			<div class="other">
				<div class="name" style="width: 95%">姓名：${repair.applyname}</div>
			</div>
			<div class="repairTime"><fmt:formatDate value="${repair.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			<div class="content">${repair.contentdetail}</div>
		</div>
	</div>
	</c:forEach>
	</c:if>
 --%>
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