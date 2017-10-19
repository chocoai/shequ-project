<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>跳蚤市场</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<!-- <link rel="stylesheet" href="${ctxStatic}/carousel/css/bootstrap.min.css">
		<link rel="stylesheet" href="${ctxStatic}/carousel/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="${ctxStatic}/carousel/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/carousel/css/zzsc.css">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/carousel/dist/jquery.vm-carousel.css"> -->
		<link rel="stylesheet" href="${ctxStatic}/swiper/swiper.min.css">
		<link rel="stylesheet" href="${ctxStatic}/css/fleaMarketBrowse.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
        <link rel="stylesheet" href="${ctxStatic}/css/myweui.css">
		<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/swiper/swiper.min.js"></script>
		<script src="${ctxStatic}/js/houseRent.js"></script>
		<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
		<!-- <script src="${ctxStatic}/carousel/js/modernizr.js"></script>
		<style type="text/css">
			h2 {
				 margin-bottom: 40px;
				 margin-top: 40px;
			  }
			  .vmc-centered{ padding: 10px 0; }
			  .vmc-centered img { transition: all 0.3s ease; }
			  .vmc-centered .vmc_active img { transform: scale(1.2); }
		</style> -->


		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
			var ignore = "${ignore}";
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;background-color: #f8f8f8;}
		</style>
</head>
<body>
	<!-- <div class="top">
		<div class="topleft" onclick="window.location.href='${ctx}/index'"><img class="topimg" src="${ctxStatic}/image/首页.png"></div>
	    <div class="heading">跳蚤市场-浏览</div>
	    <div class="topright" onclick="window.location.href='${ctx}/fleaMarket/publish'"><img class="topimg" src="${ctxStatic}/image/添加.png"></div>
	</div> -->
	<div class="weui-cells weui-title weui-title-green">
        <a class="weui-cell weui-cell_access" href="javascript:;">
            <div class="weui-cell__ft1" onclick="javascript:history.go(-1)">
            </div>
            <div class="weui-cell__bd">
                <p>跳蚤市场-浏览</p>
            </div>
            <div class="weui-cell__hd" onclick="window.location.href='${ctx}/fleaMarket/publish'"><img src="${ctxStatic}/image/添加4.png"></div>
            <div class="weui-cell__ft" onclick="window.location.href='${ctx}/fleaMarket/publish'">
            </div>
        </a>
    </div>
    <form id="search-form" action="${ctx}/fleaMarket/list?dirType=${ignore}" method="post" style="padding-bottom: 10px">
	    <div class="weui-search-bar" id="searchBar" style="height: 44px; width: 100%; z-index: 999;">
			<div class="weui-search-bar__form" style="margin: 0;">
				<div class="weui-search-bar__box">
					<i class="weui-icon-search"></i>
					<input type="search" class="weui-search-bar__input" id="keyword" placeholder="搜索" name="goodsName">
					<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
				</div>
				<label class="weui-search-bar__label" id="searchText">
					<i class="weui-icon-search"></i>
					<span style="line-height: 28px;">搜索</span>
				</label>
			</div>
			<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
		</div>
	   <!--  <div class="searchheight" style="display: none">
	    	<div class="searchContent searchheight">
	    		<input class="per" placeholder="手机" name="goodsName" value="${wyFleaMarket.goodsName}"/>
	    	</div>
	    	<div class="searchLogo">
	    		<span onclick="$('#search-form').submit();"><img src="${ctxStatic}/image/search.png" class="per" style="width:25px;height:25px;margin-left:3px;"></span>    		
	    	</div>
	    </div> -->
    </form>

<c:forEach items="${wyFleaMarketList}" var="fleaMarket">
    <div class="content">
    	<div class="baseInfo">
    		<div class="member">
    			<div class="info1">
    				<img src="${fleaMarket.member.avatarurl}" style="height: 100%;-webkit-border-radius: 50%;">
    			</div>
    			<div class="info2">
    				<div class="name">${fleaMarket.member.memberName}</div>
    				<div class="time">${fns:pastTimeStr(fleaMarket.createDate)}</div>
    			</div>
    		</div>
    		<div class="price">￥ ${fleaMarket.price}</div>
    	</div>
    	<div class="container photo">
    		<div class="swiper-container swiper">
			    <div class="swiper-wrapper">
			    <c:forEach items="${fleaMarket.imgList}" var="img">
			        <div class="swiper-slide">
			        	<a onclick="showImg('${img}')">
			        		<img src="${img}" class="imgtype">
			        	</a>
			        </div>
			    </c:forEach>			       
			    </div>
			    <!-- 如果需要分页器 -->
			    <div class="swiper-pagination"></div>
			</div>
    	</div>
    	<div class="detail" onclick="toShowDetail1('${fleaMarket.id}')">    	
    		${fleaMarket.goodsDesc}     		
    	</div>
    	<div class="count">
    		<c:if test="${fleaMarket.dianzhanStatus == 2}">
    			<img src="${ctxStatic}/image/点赞.png" class="per1 dz1" onclick="dianzan1('${fleaMarket.id}',this, 'dz2', 'dz1');">
    			<img src="${ctxStatic}/image/点赞红.png" class="per1 dz2" onclick="dianzan1('${fleaMarket.id}',this, 'dz1', 'dz2');" style="display: none">
    		</c:if>
    		<c:if test="${fleaMarket.dianzhanStatus == 1}">
    			<img src="${ctxStatic}/image/点赞.png" class="per1 dz1" onclick="dianzan1('${fleaMarket.id}',this, 'dz2', 'dz1');" style="display: none">
    			<img src="${ctxStatic}/image/点赞红.png" class="per1 dz2" onclick="dianzan1('${fleaMarket.id}',this, 'dz1', 'dz2');">
    		</c:if>
    		<span>${fleaMarket.dianzanNum==null?0:fleaMarket.dianzanNum}</span>
    		<img onclick="toShowDetail1('${fleaMarket.id}')" src="${ctxStatic}/image/评论.png" class="per1">${fleaMarket.commentNum ==null?0:fleaMarket.commentNum}
    	</div>
    </div>
</c:forEach>

	<div class="weui-gallery" style="display: none">
		<span class="weui-gallery-img"></span>
		<div class="weui-gallery-opr">
			<a href="javascript:" class="weui-gallery-del" onclick="$('.weui-gallery').fadeOut();" style="color: #FFF">
				返回
			</a>
		</div>
	</div>
    <div class="bottom"></div>
	<script>  
		var swiper = $(".swiper");  
		swiper.css("height", swiper.width()/4);
		var imgtype = $(".imgtype");
		imgtype.css("height", imgtype.width()/4);

		var mySwiper = new Swiper ('.swiper-container', {
			/*direction: 'horizontal',*/
			loop: false,
			/*effect : 'coverflow',*/
			slidesPerView: 4,
			/*centeredSlides: true,*/

			 /*// 如果需要分页器
			pagination: '.swiper-pagination',*/

			// 如果需要前进后退按钮
			nextButton: '.swiper-button-next',
			prevButton: '.swiper-button-prev',
		})  
		
		$("#keyword").on('keypress',function(e) {  
			var keycode = e.keyCode;  
			if(keycode=='13') { 
				$('#search-form').submit();
			}  
		});	      
  </script>
</body>
</html>