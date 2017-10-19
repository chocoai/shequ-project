<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>跳蚤市场</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<!--标准mui.css-->
		<link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">

	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">cardview（卡片视图）</h1>
		</header>
		<div class="mui-content">
			<div class="mui-card">
				<div class="mui-card-header mui-card-media" style="">
					<img src="${ctxStatic}/mui/images/logo.png" />
					<div class="mui-media-body">
						小M
						<p>2天前</p>						
					</div>
				</div>
				<div class="mui-card-content" >
					<img src="${ctxStatic}/mui/images/yuantiao.jpg" alt="" width="100%"/>
				</div>
				<div class="mui-card-footer">
					<a class="mui-card-link">Like</a>
					<a class="mui-card-link">Comment</a>
					<a class="mui-card-link">Read more</a>
				</div>
			</div>
		</div>
		<script src="../js/mui.min.js"></script>
	</body>

</html>