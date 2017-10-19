<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">

		<style type="text/css">
			body{margin: 0 auto;}
			.top{
				width: 100%;
			    height: 40px;
			    background-color: #09bb07;
			    text-align: center;
			    line-height: 40px;
			    font-size: 20px;
			    color: #FFF;
			    z-index: 99;
			}
			.back{
				width: 10%;
				height: 40px;
				z-index: 100;
				position: absolute;
				background-image: url(${ctxStatic}/image/back2.png);
				background-repeat: no-repeat;
				background-size: 100% 100%;
			}
		</style>
	</head>

	<body>
		<div class="back"></div>
		<div class="top">问卷调查</div>
	</body>