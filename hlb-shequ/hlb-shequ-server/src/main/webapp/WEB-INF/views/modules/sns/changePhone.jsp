<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>好邻邦</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		
		<link rel="stylesheet" href="${ctxStatic}/css/passport.css"/>
		<link rel="stylesheet" href="${ctxStatic}/css/reg.css"/>
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min1.css">
		<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/base.css"/>
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/base.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script src="${ctxStatic}/js/roll.js"></script>
        <script src="${ctxStatic}/js/common.js"></script>
        <script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
		</script>
		<style type="text/css">
		.container{
			position: inherit;
		}
		[class*='icon-']:before {
		    display: inline-block;
		    font-family: inherit;
		    font-weight: normal;
		    font-style: normal;
		    vertical-align: baseline;
		    line-height: 1;
		    -webkit-font-smoothing: antialiased;
		    -moz-osx-font-smoothing: grayscale;
		}
		.hstyle{
			height: 44px;
			margin-bottom: 0px;
		}
		.style1{
			padding: 2px 0 2px 20px;
    		color: #666;
		}
		.style2{
		    margin: 0;
		    -webkit-margin-before: 1em;
		}
		span{
			margin-bottom: 0px;
		}
		</style>
	</head>
	<body>
	<header class="top-fixed bg-yellow bg-inverse">
		<div class="top-title">
			更改手机号
		</div>
	</header>
	
	<form id="reg-form" class="reg-form" target="x-frame" method="post" >
		<input id="isAssociate" type="hidden" name="isAssociate" value="false">
        <div class="line padding border-bottom">
			<span class="x3"><label>手机号码</label></span>
			<span class="x8"><input id="mobile" type="text" class="text-input" name="mobile" placeholder="请输入手机号码" maxlength=11 /></span>
		</div>				
		<div class="line padding border-bottom">
			<span class="x3"><label>验 证 码</label></span>
			<span class="x5"><input id='yzm' type="text" class="text-input" name="smscode" placeholder="请输入短信验证码" maxlength=6 /></span>
			<span class="x3"><button id="m_zcyz" type="button" class="button button-little bg-dot m_zcyz">获取验证码</button></span>
			<span class="x1">
				<img id="yzm-duigou" src="${ctxStatic}/image/duigou.png" style="height:20px;display:none;"/>
				<img id="yzm-cha" src="${ctxStatic}/image/cha.png" style="height:20px;display:none;"/>
			</span>
		</div>
		<div class="line padding border-bottom">
			<span class="x3"><label>图形验证码</label></span>
			<span class="x5"><input id='piccode' type="text" class="text-input" name="piccode" placeholder="请输入图形验证码" maxlength=4></span>
			<span class="x3"><img id="validateCode" src="${ctx}/servlet/validateCodeServlet" style="height:22px;"></span>
			<span class="x1">
				<img id="piccode-duigou" src="${ctxStatic}/image/duigou.png" style="height:20px;display:none;"/>
				<img id="piccode-cha" src="${ctxStatic}/image/cha.png" style="height:20px;display:none;"/>
			</span>
		</div>
		
		<div class="blank-20"></div>
		<div class="container">
			<button id="regSubmit" type="button" class="button button-block button-big bg-dot agree" disabled="disabled">提交</button>
		</div>			
	</form>	 
</body>
<script type="text/javascript">
	var mobile_lock = 0;
	$(function() {
	    $("#m_zcyz").click(function() {
	        //  debugger;
	        var mobile = $("#mobile").val();
	        if (!mobile || mobile == "" || mobile == "请输入手机号码") {
	            //提示
	            layer.open({
	                content: '<font>手机号码不能为空</font>',
	                skin: 'msg',
	                time: 2 //2秒后自动关闭
	            });
	            $("#mobile").focus();
	            return;
	        }
	        //校验手机号码
	        if (!isMobile(mobile)) {
	            layer.open({
	                content: '请输入正确的手机号码',
	                skin: 'msg',
	                time: 2
	            });
	            return;
	        }
	        if (mobile_lock == 0) {
	            mobile_lock = 1;
	            $.ajax({
	                url: ctx + '/sendsms',
	                data: 'mobile=' + mobile,
	                type: 'post',
	                async: false,
	                success: function(data) {
	                    console.info(data);
	                    if (data && data.code == 200) {
	                        mobile_count = 100;
	                        $(".m_zcyz").addClass("on");
	                        $('#m_zcyz').attr("disabled", "disabled");
	                        BtnCount();
	                    } else {
	                        mobile_lock = 0;
	                        layer.open({
	                            content: data.msg,
	                            skin: 'msg',
	                            time: 2
	                        });
	                    }
	                }
	            });
	        }
	    });
	});

	BtnCount = function() {
	    if (mobile_count == 0) {
	        $(".m_zcyz").removeClass("on");
	        $('#m_zcyz').removeAttr("disabled");
	        $('#m_zcyz').html("重新发送");
	        mobile_lock = 0;
	        clearTimeout(mobile_timeout);
	    } else {
	        mobile_count--;
	        $('#m_zcyz').html("验证(" + mobile_count.toString() + ")秒");
	        mobile_timeout = setTimeout(BtnCount, 1000);
	    }
	};

	 $("#yzm").keyup(function() {
        $("#yzm-duigou").hide();
        $("#yzm-cha").hide();
        //限制六位数字输入
        var smscode = $(this).val();
        smscode = smscode.replace(/[^\.\d]/g, '');
        $(this).val(smscode);

        if (smscode && smscode.length == 6) {
        	$.ajax({
                url: ctx + '/checkSmsCode',
                data: { smscode: smscode },
                type: 'post',
                async: false,
                success: function(data) {
                    if (data.code == 200) {
                        //保存验证码校验结果
                        $("#yzm").data("isValidate", true);
                        $("#yzm-duigou").show();
                        $("#yzm-cha").hide();
                    } else {
                        $("#yzm").data("isValidate", false);
                        $("#yzm-duigou").hide();
                        $("#yzm-cha").show();
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                    showreg();
                }
            });
            $("#yzm").data("isValidate", true);
            $("#yzm-duigou").show();
            $("#yzm-cha").hide();
        }
    });

    //点击刷新验证码
    $("#validateCode").click(function() {
        $('#validateCode').attr('src', ctx + '/servlet/validateCodeServlet?' + new Date().getTime());
    });

    //图形验证码校验
    $("#piccode").keyup(function() {
        $("#piccode-duigou").hide();
        $("#piccode-cha").hide();
        var piccode = $(this).val();
        piccode = piccode.replace(/[^\.\d\w]/g, '');
        $(this).val(piccode);

        if (piccode && piccode.length == 4) {
            $.ajax({
                url: ctx + '/checkPiccode',
                data: { piccode: piccode },
                type: 'post',
                async: false,
                success: function(data) {
                    if (data.code == 200) {
                        $("#piccode").data("isValidate", true);
                        $("#piccode-duigou").show();
                        $("#piccode-cha").hide();
                    } else {
                        $("#piccode").data("isValidate", false);
                        $("#piccode-duigou").hide();
                        $("#piccode-cha").show();
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                    showreg();
                }
            });
        }
    });

    function showreg(){
	    if($("#yzm").data("isValidate") && $("#piccode").data("isValidate")) {
	        $("#regSubmit").attr("disabled", false);
	    } else if($("#yzm").data("isValidate")) {
	        $("#m_mainSmscode").attr("disabled", false);
	        if($("#mainSmscode").data("isValidate") && $("#piccode").data("isValidate")){
	            $("#regSubmit").attr("disabled", false);
	        }else{
	            $("#regSubmit").attr("disabled", "disabled");
	        }
	    }else{
	        $("#regSubmit").attr("disabled", "disabled");
	    }
	}

	$("#regSubmit").click(function(){
		 //验证码校验		
        if (!$("#yzm").data("isValidate")) {
            $("#yzm").focus();
            layer.open({
                content: "请先通过手机验证码",
                skin: 'msg',
                time: 2
            });
            return;
        }

        //图像验证码
        if (!$("#piccode").data("isValidate")) {
            $("#piccode").focus();
            layer.open({
                content: "请先通过图形验证码",
                skin: 'msg',
                time: 2
            });
            return;
        }

        $.ajax({
            url: ctx + '/changePhoneDetail',
            data: 'mobile=' + mobile,
            type: 'post',
            async: false,
            success: function(data) {
                // debugger;                 	
                if (data.code == 200) {
                	layer.open({
                        content: data.data,
                        skin: 'msg',
                        time: 2
                    });
                	window.location.href =  ctx + "/personcenter/myRegister"
                }else if(data.code == 500){
                	layer.open({
                        content: data.data,
                        skin: 'msg',
                        time: 2
                    });
                }
            }
        });
	});
</script>
</html>