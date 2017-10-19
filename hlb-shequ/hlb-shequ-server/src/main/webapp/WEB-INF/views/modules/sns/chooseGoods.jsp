<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <title>查费缴费</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/chooseGoods.css">
        <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/fonts/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/example.css">
        <link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<!-- <script src="${ctxStatic}/js/chooseGoods.js"></script> -->
        <script src="${ctxStatic}/js/layer.js"></script>
        <script type="text/javascript">
            var ctx = "${ctx}"; 
            var WYID = "${WYID}";
            var HTID = "${HTID}";
            var JFYF = "${JFYF}";
            var SOURCE = "${SOURCE}";
/*          var memberType = "${memberType}";
            if(memberType == '0'){
                layer.open({
                    content: "您是游客身份，无权查看",
                    skin: 'msg',
                    time: 2
                });
                window.history.go(-1); 
            }*/
        </script>
</head>

<body>
    <div class="wrap">
        <!--头部-->
        <!-- <header class="border-bottom">
            <div class="header-back">
                <a href="javascript:history.back();"> <i class=" icon-reply ml10"></i>
                </a>
            </div>
            <div class="text-center header-title">商品选择</div>
        </header> -->
        <div class="weui-cells weui-title weui-title-green" onclick="javascript:history.go(-1)">
            <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__ft1">
                </div>
                <div class="weui-cell__bd">
                    <p>查费缴费</p>
                </div>
            </a>
        </div>
        <!--内容-->
        <div class="weui-cells">
            <a class="weui-cell weui-cell_access" onclick="topay(0)">
                <div class="weui-cell__hd">
                    <img style="height:25px;width:25px;margin-right:10px;display:block" src="${ctxStatic}/image/wysf.png">
                </div>
                <div class="weui-cell__bd">
                    <p>物业收费</p>
                </div>
                <div class="weui-cell__ft">
                </div>
            </a>
            <a class="weui-cell weui-cell_access" onclick="topay(1)">
                <div class="weui-cell__hd">
                    <img style="height:25px;width:25px;margin-right:10px;display:block" src="${ctxStatic}/image/lsfy.png">
                </div>
                <div class="weui-cell__bd">
                    <p>临时费用</p>
                </div>
                <div class="weui-cell__ft">
                </div>
            </a>
            <a class="weui-cell weui-cell_access" onclick="topay(2)">
                <div class="weui-cell__hd">
                    <img style="height:25px;width:25px;margin-right:10px;display:block" src="${ctxStatic}/image/lxfy.png">
                </div>
                <div class="weui-cell__bd">
                    <p>零星费用</p>
                </div>
                <div class="weui-cell__ft">
                </div>
            </a>
        </div>

        <div class="foot"></div>
    </div>
    <script type="text/javascript">
        function topay(feetype){
            var param = {
                "feetype":feetype,
                "WYID" : WYID,
                "HTID" : HTID,
                "JFYF" : JFYF,
                "SOURCE" : SOURCE
            }
            var sign;
            var urlName;
                
            $.ajax({
                url : ctx+"/getSign",
                type : "post",
                data : param,
                async : false,
                dataType : "json",
                success:function(result){
                    console.log(result);
                    sign = result.data;
                },
                error:function(jqXHR, textStatus, errorThrown){
                    alert("加密失败");
                }
            });

            if(feetype == "0"){
                urlName = "${fns:getConfig('server.project.wy-client')}/index.html?";
            }else{
                urlName = "${fns:getConfig('server.project.wy-client')}/index"+feetype+".html?";
            }

            var url = urlName+ 
                "x0="+feetype+"&x1="+param.WYID+"&x2="+param.HTID+"&x3="+param.JFYF+"&x4="+param.SOURCE+"&sign="+sign;

            window.location.href = url;   
        }
    </script>
</body>
</html>

