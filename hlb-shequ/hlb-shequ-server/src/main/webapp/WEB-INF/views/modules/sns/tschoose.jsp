<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	</head>

	<style type="text/css">
		body{margin: 0 auto;}
		.top{
			width: 100%;
		    height: 50px;
		    background-color: #09bb07;
		    text-align: center;
		    line-height: 50px;
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
		.pri{
			width: 80%;
		    background-color: #349634;
		    height: 40px;
		    border-radius: 5px;
		    text-align: center;
		    margin: 0 auto;
		    line-height: 40px;
		    color: #FFF;
		    font-size: 20px;
		    margin-top: 50%;
		}
		.pub{
			width: 80%;
		    background-color: #349634;
		    height: 40px;
		    border-radius: 5px;
		    text-align: center;
		    margin: 0 auto;
		    line-height: 40px;
		    color: #FFF;
		    font-size: 20px;
		    margin-top: 10%;
		}
	</style>

	<body>
		<div class="back" onClick="window.location.href='javascript:history.back()'"></div>
		<div class="top">物业投诉</div>
		<div class="pri" onclick="jump(1)">私人区域</div>
		<div class="pub" onclick="jump(2)">公共区域</div>
	</body>

	<script type="text/javascript">
		function jump(flag){
			window.location.href = "${ctx}/wuye/complain?flag="+flag;
		}
	</script>
</html>