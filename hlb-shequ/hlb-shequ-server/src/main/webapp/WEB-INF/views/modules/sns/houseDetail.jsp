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
		<script src="${ctxStatic}/js/jquery.js"></script>

		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
		</script>
		<style>
			/*body{margin:0;overflow-x:hidden;padding:0;}*/
			.back{background-image: url(${ctxStatic}/image/back.png);}
		</style>
</head>
<body>
	<div class="back"></div>
	<div class="top">宝贝详情</div>
	<div class="baseInfo">
		<div class="member">
			<div class="info1">
				<img src="${ctxStatic}/image/avatar.png" style="width: 100%;height: 100%;">
			</div>
			<div class="info2">
				<div class="name">Marry</div>
				<div class="time">2天前</div>
			</div>
		</div>
		<div class="price">￥5200.00</div>
	</div>
	<div class="content">
		<pre>iPhone6s Plus 128G 国行 98成新 iPhone6s Plus 128G 国行 98成新. 
因有其他苹果，转让本机，国行，全网通。功能全部完整，支持沟通的现场验货测试。</pre>
	</div>
	<div class="imgList1">
		<img src="${ctxStatic}/image/temp/02.jpg" class="imgList11">
		<img src="${ctxStatic}/image/temp/03.jpg" class="imgList11">
		<img src="${ctxStatic}/image/temp/04.jpg" class="imgList11">
		<img src="${ctxStatic}/image/temp/05.jpg" class="imgList11">
	</div>
	<div class="imgList2">
		<img src="${ctxStatic}/image/temp/08.jpg" class="imgList22">
		<img src="${ctxStatic}/image/temp/09.jpg" class="imgList22">
		<img src="${ctxStatic}/image/temp/12.jpg" class="imgList22">
		<img src="${ctxStatic}/image/temp/08.jpg" class="imgList22">
	</div>

	<div class="division"></div>
	<div class="lmtitle">留言 - <span>5</span></div>
	<div class="lmcontent">
		<div class="message">
			<div class="message1" onclick="fillname(this)">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username">zjl51961523</div>
				</div>
				<div class="content">是正品吗？第一个颜色有没货？</div>
				<div class="time">3天前</div>
			</div>
			<div class="message2" onclick="fillname(this)">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username color">xiaomao_113</div>
				</div>
				<div class="content">
				<span>回复@tb2820363_00</span>
				不好可退吗？
				</div>
				<div class="time">6天前</div>
			</div>
		</div>

		<div class="message">
			<div class="message1" onclick="fillname(this)">
				<div class="message11">
					<div class="avatar1"><img src="${ctxStatic}/image/avatar.png" class="per1"></div>
					<div class="username">zjl51961523</div>
				</div>
				<div class="content">是正品吗？第一个颜色有没货？</div>
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
				<div class="time">6天前</div>
			</div>
		</div>
		<div class="fit"></div>
		<!-- <div type="button" class="reply" id="reply">
			回复
		</div> -->
		<div class="reply1">
			<div class="replycontent">
				<input class="per2" id="replycontent"></input>
			</div>
			<div class="replycontent1">
				<button class="send">发送</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/*document.getElementById("reply").addEventListener('click', function(){
			$(".reply1").css("display", "block");
			$(".reply").css("display", "none");
			$("#replycontent").focus();
		});*/
		$(function(){
			$("#replycontent").attr("placeholder", "回复主人");
		});
		function fillname(temp){
			var username = $(temp).find(".username").html();
			username = "回复：" + username;
			$("#replycontent").attr("placeholder", username);
		}
	</script>
</body>

</html>