<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>宝贝详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/houseDetail.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/houseDetail.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
			var temp_publisherRelation = "${fleaMarket.memberId}";
			var temp_relationId = "${fleaMarket.id}";
		</script>
		<style>
			/*body{margin:0;overflow-x:hidden;padding:0;}*/
			.back{background-image: url(${ctxStatic}/image/back.png);}
		</style>
</head>
<body>
	<!-- <a href="${ctx}/fleaMarket/list">
		<div class="back"></div>
	</a>
	<div class="top">宝贝详情</div> -->
	<div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__ft1">
			</div>
			<div class="weui-cell__bd">
				<p>宝贝详情</p>
			</div>
		</a>
	</div>
	<div class="baseInfo">
		<div class="member">
			<div class="info1">			
				<img src="${fleaMarket.member.avatarurl}" style="height: 100%; -webkit-border-radius: 50%; margin-left: 25%">			
			</div>
			<div class="info2">
				<div class="name">${fleaMarket.member.memberName}</div>
				<div class="time">${fns:pastTimeStr(fleaMarket.createDate)}</div>
			</div>
		</div>
		<div class="price">￥ ${fleaMarket.price}</div>
	</div>
	<div class="content">
		<pre>${fleaMarket.goodsDesc}</pre>
	</div>
	<div class="imgList1">
	<c:forEach items="${fleaMarket.imgList}" var="img" varStatus="stat">
		<c:if test="${stat.index <=3}">
		<img src="${img}" class="imgList11">	
		</c:if>
	</c:forEach>		
	</div>
	<div class="imgList2">
	<c:forEach items="${fleaMarket.imgList}" var="img" varStatus="stat">
	<c:if test="${stat.index >3}">
		<img src="${img}" class="imgList22">
	</c:if>
	</c:forEach>		
	</div>

	<div class="division"></div>
	<!-- <div class="lmtitle">留言 - <span>5</span></div> -->
	<div class="lmcontent">
		<div class="type1comment">
			<c:forEach items="${comments}" var="comments">
			<div class="message">
				<div class="message1" onclick="fillname(this)" id="${comments.id}" name="${comments.publisher}">
					<div class="message11">
						<div class="avatar1"><img src="${comments.publisherAvatar}" class="per1"></div>
						<div class="username">${comments.publisherName}</div>
					</div>
					<div class="content">${comments.comment}</div>
					<div class="time">${comments.createTime}</div>
				</div>
				<div class="type2comment">
					<c:forEach items="${comments.comments}" var="commentss">
					<div class="message2" onclick="fillname(this)" id="${comments.id}" name="${commentss.publisher}">
						<div class="message11">
							<div class="avatar1"><img src="${commentss.publisherAvatar}" class="per1"></div>
							<div class="username color">${commentss.publisherName}</div>
						</div>
						<div class="content">
						<span>回复@${commentss.publisherRelationName}: </span>
						${commentss.comment}
						</div>
						<div class="time">${commentss.createTime}</div>
					</div>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>
	<!-- <c:if test="${fleaMarket.id%2 eq 1}">	
		<div class="message">
			<div class="message1" onclick="fillname(this)" id="">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username">zj523</div>
				</div>
				<div class="content">什么路由板子</div>
				<div class="time">3天前</div>
			</div>
			<div class="message2" onclick="fillname(this)" id="">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username color">xiaomao_113</div>
				</div>
				<div class="content">
				<span>回复@zj523</span>
				Tplink
				</div>
				<div class="time">2小时前</div>
			</div>
		</div>		
	</c:if>

		<c:if test="${fleaMarket.id%2 eq 0}">
		<div class="message">
			<div class="message1" onclick="fillname(this)">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username">zjl51961523</div>
				</div>
				<div class="content">是正品吗？</div>
				<div class="time">3天前</div>
			</div>
			<div class="message2" onclick="fillname(this)">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username color">xiaomao_113</div>
				</div>
				<div class="content">
				<span>回复@tb2820363_00:</span>
				不好可退吗？
				</div>
				<div class="time">1天前</div>
			</div>
		</div>
		</c:if> -->
		<div class="fit"></div>
		<div class="reply1">
			<div class="replycontent">
				<input class="per2" id="comment"></input>
				<input id="bizType" type="hidden" value="flea_market"></input>
				<input id="commentId" type="hidden"></input>
				<input id="relationId" type="hidden"></input>
				<input id="publisherRelation" type="hidden"></input>
			</div>
			<div class="replycontent1">
				<button class="send" onclick="send()">发送</button>
			</div>
		</div>
	</div>
</body>

</html>