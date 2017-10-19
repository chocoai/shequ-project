<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
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
			.content{
				width: 100%;
			    height: 40px;
			    line-height: 40px;
			    border-bottom: 1px solid #e2e2e2;
			    text-indent: 20px;
			}
			.bgtip{
				display: none;
				width: 80%;
			    margin: 0 10%;
			    margin-top: 30%;
			}
			.bgurl{
				width: 100%;
			}
			.bgcontent{
				text-align: center;
			    font-size: 27px;
			    margin: 0px;
			    color: #A9A9A9;
			}
		</style>
	</head>

	<body>
		<div class="back" onClick="window.location.href='javascript:history.back()'"></div>
		<div class="top">调查结果</div>
		<c:forEach items="${wyQuestionnaireReleasesList}" var="wyQuestionnaireReleasesList">
		<div class="content" onclick="detail(${wyQuestionnaireReleasesList.releaseId})">${wyQuestionnaireReleasesList.title}</div>
		</c:forEach>

		<div class="bgtip">
			<img src="${ctxStatic}/image/zwnr.png" class="bgurl">
			<p class="bgcontent">暂无内容</p>
		</div>
	</body>

	<script type="text/javascript">
		var flag = ${flag};
		if(flag == 0){
			$(".bgtip").css("display","block");
		}
		function detail(releaseId){
			window.location.href = "/hlb-shequ-cms/admin/sns/wyQuestionnaireRelease/showResultDetail?releaseId="+releaseId;
		}
	</script>
</html>