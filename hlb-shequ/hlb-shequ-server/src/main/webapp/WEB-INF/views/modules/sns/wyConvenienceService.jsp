<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/ad_translation.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}"
		</script>
		<style>
			body{margin:5px;overflow-x:hidden;padding:0;}
            /*p{
                text-indent: 2em;
            }*/
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
	<!-- 广告 -->
	<!-- <div class="focus" id="focus">
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
        <div class="bd">
            <div class="tempWrap" style="overflow:hidden; position:relative;">
                <ul style="width: 640px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translate(0px, 0px) translateZ(0px);">
                    <c:forEach items="${ads}" var="ads">
                    <li style="display: table-cell; vertical-align: top; width: 320px;">
                        <a>
                            <img src="${ads.imgSrc}" class="bdimg"/>
                        </a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="acBox">
            <ul style="margin:0px">
                <li style="left:72px;">联系电话：0755-26503033 微信公众号：好邻邦云社区&nbsp;&nbsp;</li>
            </ul>
            <i class="close"></i>
        </div>
    </div> -->

    <!-- 自定义内容 -->
    <div class="other">
    	${content}
    </div>
    <div class="bgtip">
        <img src="${ctxStatic}/image/zwnr.png" class="bgurl">
        <p class="bgcontent">暂无内容</p>
    </div>
    <script type="text/javascript">
    	/*var width = document.body.offsetWidth;
        $(".other").css("margin-top", (width*45/100+10));
        $(".other").css("z-index", "98");*/
        var flag = "${flag}";
        if(flag == "false"){
            $(".other").css("display", "none");
            $(".bgtip").css("display", "block");
        }
    </script>
</body>
</html>