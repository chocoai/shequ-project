<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>维修表单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="${ctxStatic}/css/evaluate.css">
	<script src="${ctxStatic}/js/evaluate.js"></script>
	<script src="${ctxStatic}/js/jquery.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
	</script>
</head>
<body>
<form id="evaluateFrom" name="evaluateFrom" action="${ctx}/wuye/evaluateSave" method="post">
	<div class="title">
		<div class="back"></div>
		<div class="titlecontent">发表评价</div>
	</div>
	<input type="hidden" name="relationId" value="${wyRepair.id}"/>
	<input type="hidden" name="title" value="${wyRepair.content}"/>
	<input id="star1" type="hidden" name="star1"/>
	<input id="star2" type="hidden" name="star2"/>
	<input id="star3" type="hidden" name="star3"/>
	<div class="info">${wyRepair.content}</div>
	<div class="content">		
    	<textarea rows="50" cols="3" class="rdetail" placeholder="请输入您的意见..."></textarea>
	</div>
</form>
	<div class="classification">
		<div class="service">
			<div class="describe">服务态度</div>
			<div class="score score1">
				<div class="star star11 empty" onclick="star(1, 1)"></div>
				<div class="star star12 empty" onclick="star(1, 2)"></div>
				<div class="star star13 empty" onclick="star(1, 3)"></div>
				<div class="star star14 empty" onclick="star(1, 4)"></div>
				<div class="star star15 empty" onclick="star(1, 5)"></div>
			</div>
		</div>
		<div class="service">
			<div class="describe">服务态度</div>
			<div class="score score2">
				<div class="star star21 empty" onclick="star(2, 1)"></div>
				<div class="star star22 empty" onclick="star(2, 2)"></div>
				<div class="star star23 empty" onclick="star(2, 3)"></div>
				<div class="star star24 empty" onclick="star(2, 4)"></div>
				<div class="star star25 empty" onclick="star(2, 5)"></div>
			</div>
		</div>
		<div class="service">
			<div class="describe">服务态度</div>
			<div class="score score3">
				<div class="star star31 empty" onclick="star(3, 1)"></div>
				<div class="star star32 empty" onclick="star(3, 2)"></div>
				<div class="star star33 empty" onclick="star(3, 3)"></div>
				<div class="star star34 empty" onclick="star(3, 4)"></div>
				<div class="star star35 empty" onclick="star(3, 5)"></div>
			</div>
		</div>
	</div>
	<div class="submit" onclick="submit()">发表评价</div>
	<c:if test="${ispaied ne '1' }">
	<div>
		<a href="${sb}">发起支付</a>
	</div>
	</c:if>
</body>