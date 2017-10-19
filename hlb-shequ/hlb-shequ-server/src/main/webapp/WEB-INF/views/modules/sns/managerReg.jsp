<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>管理员注册</title>
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
        <script src="${ctxStatic}/js/managerReg.js"></script>
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
			管理员注册
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
			<span id="memberName-span">
			<span class="x3"><label>管理员姓名</label></span>
			<span class="x8"><input id="memberName" type="text" class="text-input" name="memberName" placeholder="请输入姓名"></span>
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
		<!-- <div class="line padding border-bottom choose">
			<span id="memberName-span">
			<span class="x4"><label>选择所在的小区</label></span>
			<span class="x1"></span>
			<div class="x6" style="margin-bottom: 0px;"><input id="wymc" type="text" class="text-input" name="wymc" style="background: #fff; color: #06c1ae;" readonly="readonly" value="${WyBuilding.wymc}"></div>
			<input type="hidden" id="buildingId" name="buildingId" value="${WyBuilding.wyid}"></input>
			<img class="x1" style="height: 21px" src="${ctxStatic}/image/前往.png">
		</div>	 -->
		<div class="blank-20"></div>
		<div class="container">
			<button id="regSubmit" type="button" class="button button-block button-big bg-dot agree" disabled="disabled">提交并注册</button>
		</div>			
		<div class="blank-20"></div>
		<!-- <div class="container">
			<input id="ckNew" name="checkbox" type="checkbox" checked="checked"  />
			<a href="#">我同意好邻邦用户使用协议</a>
		</div> -->
		<div class="alertMessageBg">
			<div class="weui-search-bar" id="searchBar" style="margin-top: 50px; height: 44px;position: fixed; width: 100%; z-index: 999;">
				<div class="weui-search-bar__form" style="margin: 0;">
					<div class="weui-search-bar__box">
						<i class="weui-icon-search" style="top: 7px;"></i>
						<input type="search" class="weui-search-bar__input" id="keyword" placeholder="搜索" required="">
						<a href="javascript:" class="weui-icon-clear" id="searchClear" style="margin-top: 7px;"></a>
					</div>
					<label class="weui-search-bar__label" id="searchText">
						<i class="weui-icon-search"></i>
						<span style="line-height: 28px;">搜索</span>
					</label>
				</div>
				<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
			</div>
			<div class="show-building" style="margin-top: 100px; width: 100%">
					

			</div>
		</div>
		<!-- <div class="test1 test2">
			<a >我同意好邻邦用户使用协议testtest..............</a>
		</div> -->
        <!-- 弹窗 -->
  		<div class="mee">
  		</div>
   		<div class="mtt" style="background-image:url(${ctxStatic}/image/loading1.gif)">
   			<!-- <div id="loadbar" style="text-align:center; margin:0 auto">  
            	<span id="bar"  style="width: 10%;"></span>  
        	</div> 
        	<span class="check">验证注册中...</span> -->
        	<!-- <p><img src="${ctxStatic}/image/loading1.gif"><p> -->
   		</div>
	</form>	 
	<script type="text/javascript">
		/*$('.agree').click(function() {
		    $('.mee,.mtt').css('display', 'block');
		    startbar();
		});*/
		/*$('.choose').click(function() {
			$('.alertMessageBg').css("display", "block");
		});
		$("#keyword").on('keypress',function(e) {  
                var keycode = e.keyCode;  
                var searchName = $(this).val();  
                if(keycode=='13') { 
                	if(searchName==null || searchName==""){
                		return;
                	}
					 $.ajax({
		                url: ctx + '/searchBuilding',
		                data: 'searchName=' + searchName,
		                type: 'post',
		                async: false,
		                success: function(data) {
		                	if (data.code == 200){
		                		var array = new Array(); 
		                		array = data.data;
		                		for(var i=0; i<array.length; i++){
		                			var text = building(array[i].buildingId, array[i].wymc);
		                			if(i==0){
		                				var html = "";
		                			}else{
		                				var html = $(".show-building").html();
		                			}
		                			$(".show-building").html(html+text);
		                		}
		                	}
		                	if (data.code == 500){
		                		layer.open({
					                content: "查不到相应小区",
					                skin: 'msg',
					                time: 2
					            });
					            return;
		                	}
		                }
		            }); 
                }  
         });*/
	</script>
	<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
    <script src="${ctxStatic}/js/jquery.min.js"></script>
</body>

</html>