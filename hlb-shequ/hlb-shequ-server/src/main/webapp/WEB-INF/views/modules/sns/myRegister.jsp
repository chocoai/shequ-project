<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>我的注册信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/index.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/ad_translation.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}"
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;width: 100%}
			.title{
				height: 30px;
			    line-height: 30px;
			    text-align: center;
			    font-size: 24px;
			    color: #09bb07;
			    margin: 12px 0;
			    font-weight: 600;
			}
			.ma{
			    height: 80px;
			    line-height: 80px;   
			}
			.ma1{
				width: 20%;
			    height: 80px;
			    float: left;
			    margin-left: 6%;
			}
			.myimg{
				width: 66px;
			    height: 66px;
			    margin: 7px 0;
			    -webkit-border-radius: 50%;
			}
			.myavatar{
				/*width: 100%;*/
    			height: 80px;
    			border-top: 0.5px solid #edebeb;
    			/*border-bottom: 0.5px solid #edebeb;*/
    			
			}
			.imgfile{
			    width: 66px;
			    height: 66px;
			    position: absolute;
			    margin: 7px 0;
			    opacity: 0;
			}
			.word{
				width: 30%;
			    float: left;
			    margin-left: 15px;
			}
			.div1{
				height: 50px;
			    line-height: 50px;
			    /*width: 100%;*/
			    border-bottom: 0.5px solid #edebeb;
			    margin: 0 15px;
			}
			.div1 input, select{
				/*height: 30px;
			    margin: 10px 0;*/
			    height: 25px;
			    width: 33%;
			    border: none;
			    float: left;
			    font-size: 16px;
			    /*margin-right: 20%;*/
			}
			.save{
				/*color: #FFF;
			    background-color: #06c1ae;
			    width: 30%;
			    margin: 10px 35% 50px 35%;
			    height: 40px;
			    line-height: 40px;
			    text-align: center;
			    border-radius: 10px;
			    font-size: 16px;*/
			}
			.go{
				width: 5%;
			    height: 14px;
			    margin: 17px 15px;
			    float: right;
			}
			.reg-left{
				width: 20%;
			    float: left;
			    height: 80px;
			    margin-left: 13%;
			    text-align: center;
			    position: absolute;
			    left: -10%;
			    top: 0px;
			}
			.reg-right{
				width: 20%;
			    float: right;
			    height: 80px;
			    margin-right: 13%;
			    text-align: center;
			    position: absolute;
			    right: -10%;
			    top: 0px;
			}
			.reg-left img, .reg-right img{
			    width: 50px;
			    height: 50px;
			    margin-top: 5px;
			}
			.reg-left div, .reg-right div{
				font-size: 12px;
			}
			.t1{
				width: 100%;
    			height: 70px
			}
			.t2{
				width: 15%;
			    float: left;
			    font-size: 12px;
			    margin-left: 3%;
			}
			.t4{
				width: 15%;
			    float: left;
			    font-size: 12px;
			}
			.t3{
				float: left;
			    width: 64%;
			    height: 46px;
			    line-height: 46px;
			    text-align: center;
			    font-size: 24px;
			    color: #FF9900;
			    margin: 12px 0;
			    font-weight: 600;
			}
			.t1 img{
				width: 100%
			}
		</style>
</head>
<body>
	<!-- 广告 -->
	<div class="focus" id="focus">
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
        <!--下面的limit="0,2"是幻灯的个数，2代表2张图，以此类推，site_id=57是你广告位的ID-->
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
        <!-- 系统公告 -->
        <div class="acBox">
            <ul style="margin:0px">
                <li style="left:72px;">联系电话：0755-26503033 微信公众号：好邻邦云社区&nbsp;&nbsp;</li>
            </ul>
            <i class="close"></i>
        </div>
        <!-- <div class="reg-left" onclick="window.location.href='${ctx}/reg'">
			<img src="${ctxStatic}/image/重新注册.png">
			<div>重新注册</div>
		</div>
		<div class="reg-right" onclick="window.location.href='${ctx}/addRoom'">
			<img src="${ctxStatic}/image/重新注册.png">
			<div>更新房号</div>
		</div> -->
    </div>

     <!-- 其他内容 -->
    <div>
    	<!-- <div class="t2"><img src="${ctxStatic}/image/重新注册.png"><div>重新注册</div></div> -->
    	<div class="title">我的注册信息</div>
    	<!-- <div class="t4"><img src="${ctxStatic}/image/重新注册.png"><div>更新房号</div></div> -->
    </div>
    <div class="myavatar">
    	<div class="ma word">我的头像</div>
    	<div class="ma1">
	    	<input id="imgfile" class="imgfile" type="file" accept="image/*" name="file"/>
	    	<c:if test="${member.avatarurl == null}">
	    		<img src="${ctxStatic}/image/avatar.png" class="myimg">
	    	</c:if>
	    	<c:if test="${member.avatarurl != null}">
	    		<img src="${member.avatarurl}" class="myimg">
	    	</c:if>
    	</div>
    </div>
   	<div class="weui-cells" style="margin-top: 0px;" id="info">
		<a class="weui-cell" onclick="changePhone()">
			<div class="weui-label">
				<p>手机号码</p>
			</div>
			<div class="weui-cell__ft">${member.mobile}</div>
			<div class="weui-cell_access" style="flex: auto;">
				<div class="weui-cell__ft"></div>
			</div>
		</a>
		<div class="weui-cell">
			<div class="weui-cell__hd"><!-- <img src="${ctxStatic}/image/昵称.png"> -->
				<div class="weui-label"><p>我的昵称</p></div>
			</div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="nickname" value="${member.nickname}">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><!-- <img src="${ctxStatic}/image/性别.png"> --></div>
			<div class="weui-label"><p>我的性别</p></div>
			<c:if test="${member.sex == 0}">
		    	<select class="sexchoose" name="sex"> 
					<option selected="selected" value="0">男</option>
					<option value="1">女</option>
				</select>
			</c:if>
			<c:if test="${member.sex == 1}">
		    	<select class="sexchoose" name="sex"> 
					<option value="0">男</option>
					<option selected="selected" value="1">女</option>
				</select>
			</c:if>
		</div>
	   <!--  <a class="weui-cell weui-cell_access" onclick="window.location.href='${ctx}/personcenter/myaddress'">
			<div class="weui-cell__bd">
				<p>我的收货地址</p>
			</div>
			<div class="weui-cell__ft">
			</div>
		</a> -->
		<a class="weui-cell weui-cell_access" onclick="window.location.href='${ctx}/reg'">
			<div class="weui-cell__bd">
				<p>重新注册</p>
			</div>
			<div class="weui-cell__ft">
			</div>
		</a>
		<a class="weui-cell weui-cell_access" onclick="window.location.href='${ctx}/addRoom'">
			<div class="weui-cell__bd">
				<p>更新房号</p>
			</div>
			<div class="weui-cell__ft">
			</div>
		</a>
   	</div>
    <!-- <div class="div1" onclick="changePhone()">
    	<div class="word">手机号码</div>
    	<input placeholder="${member.mobile}" readonly="readonly"></input>
    	<img class="go" src="${ctxStatic}/image/往前.png">
    </div>
    <form id="info" style="margin-bottom: 0; width: 100%">
    	<div class="div1">
	    	<div class="word">我的昵称</div>
	    	<input placeholder="${member.nickname}" name="nickname"></input>
	    </div>
	    <div class="div1">
	    	<div class="word">我的性别</div>
	    	<c:if test="${member.sex == 0}">
	    	<select class="sexchoose" name="sex"> 
				<option selected="selected" value="0">男</option>
				<option value="1">女</option>
			</select>
			</c:if>
			<c:if test="${member.sex == 1}">
	    	<select class="sexchoose" name="sex"> 
				<option value="0">男</option>
				<option selected="selected" value="1">女</option>
			</select>
			</c:if>
	    </div>
    </form>
    <div class="div1">
    	<div class="word">我的收货地址</div>
    	<img class="go" src="${ctxStatic}/image/往前.png" onclick="window.location.href='${ctx}/personcenter/myaddress'">
    </div>
    <div class="div1" onclick="window.location.href='${ctx}/reg'">
    	<div class="word">重新注册</div>
    	<img class="go" src="${ctxStatic}/image/往前.png">
    </div>
    <div class="div1" onclick="window.location.href='${ctx}/addRoom'">
    	<div class="word">更新房号</div>
    	<img class="go" src="${ctxStatic}/image/往前.png">
    </div> -->
    <!-- <div class="save" type="button">保存</div> -->

    <div class="weui-cell save">
	    <a class="weui-btn weui-btn_primary but_style1">保存</a>
	</div>


</body>

<script type="text/javascript">
	var width = document.body.offsetWidth;
    $(".title").css("margin-top", (width*45/100+10));
    $(".title").css("z-index", "98");
    
    $("#imgfile").change(function(){
		var formdata = new FormData(); 
		var v_this = $(this);
		if($("#imgfile").val() == ""){
			return false;
		}
		var fileObj = v_this.get(0).files; 
		formdata.append("file", fileObj[0]); 
		formdata.append("foldername", "uploadfile");
		$.ajax({ 
			url : "${ctx}/personcenter/uploadfile",
			type : 'post', 
			data : formdata, 
			cache : false, 
			contentType : false, 
			processData : false, 
			dataType : "json", 
			success : function(data) { 
				$(".myimg").attr("src", data.data); 
			} 
		}); 
		return false; 
	}); 

	$(".save").click(function(){
		console.log($('#info').serialize());
		$.ajax({
			type: "POST",
			url: ctx + "/personcenter/saveInfo",
			data: {"nickname":$('input[name="nickname"]').val(), "sex":$('select[name="sex"]').val()},
			async: false,
            success: function(data) {
				layer.open({
				    content: "保存成功",
				    skin: 'msg',
				    time: 2
				});
            	window.location.href = ctx + "/personcenter/index";
            }
		});
	});

	function changePhone(){
		window.location.href = ctx + "/changePhone";
	}
</script>
</html>