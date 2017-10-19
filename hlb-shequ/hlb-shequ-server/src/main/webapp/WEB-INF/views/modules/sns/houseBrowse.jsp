<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>房屋租售-浏览</title>
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
		<link rel="stylesheet" href="${ctxStatic}/css/houseBrowse.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/swiper/swiper.min.js"></script>
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
			var ctxStatic = "${ctxStatic}"
		</script>
		<style>
			body{margin:0;overflow-x:hidden;padding:0;background-color: #FFFFCC;}
		</style>
</head>
<body>
	<div class="top">
		<div class="topleft"><img class="topimg" src="${ctxStatic}/image/首页.png"></div>
	    <div class="heading">房屋租售-发布</div>
	    <div class="topright"><img class="topimg" src="${ctxStatic}/image/添加.png"></div>
	</div>
    <div class="searchheight">
    	<div class="searchType searchheight">
			<select class="per">
				<option value="0">出租</option>
				<option value="1">出售</option>
			</select>
    	</div>
    	<div class="searchContent searchheight">
    		<input class="per" placeholder="单身公寓"></input>
    	</div>
    	<div class="searchLogo">
    		<img src="${ctxStatic}/image/search.png" class="per">
    	</div>
    </div>

    <div class="content">
    	<div class="baseInfo">
    		<div class="member">
    			<div class="info1">
    				<img src="${ctxStatic}/image/avatar.png" style="width: 100%;height: 100%;">
    			</div>
    			<div class="info2">
    				<div class="name">Marry</div>
    				<div class="time">2天前</div>
    			</div>
    		</div>
    		<div class="price">￥5200.00</div>
    	</div>
    	<div class="container photo">
    		<div class="swiper-container swiper">
			    <div class="swiper-wrapper">
			        <div class="swiper-slide">
			        	<a onclick="showImg('${ctxStatic}/carousel/images/1.jpg')">
			        		<img src="${ctxStatic}/carousel/images/1.jpg" class="imgtype">
			        	</a>
			        </div>
			       <!--  <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/2.jpg">
			        		<img src="${ctxStatic}/carousel/images/2.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/3.jpg">
			        		<img src="${ctxStatic}/carousel/images/3.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/4.jpg">
			        		<img src="${ctxStatic}/carousel/images/4.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/5.jpg">
			        		<img src="${ctxStatic}/carousel/images/5.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/6.jpg">
			        		<img src="${ctxStatic}/carousel/images/6.jpg" class="imgtype">
			        	</a>
			        </div>
			    </div> -->
			    
			</div>
    	</div>
    	<div class="detail">
    		业主自住一手房，2房1厅1卫，56平米，家电家私齐全
    	</div>
    	<div class="count">
    		<img src="${ctxStatic}/image/点赞.png" class="per1">5
    		<img src="${ctxStatic}/image/评论.png" class="per1">10
    	</div>
    </div>

    <div class="content">
    	<div class="baseInfo">
    		<div class="member">
    			<div class="info1">
    				<img src="${ctxStatic}/image/avatar.png" style="width: 100%;height: 100%;">
    			</div>
    			<div class="info2">
    				<div class="name">Marry</div>
    				<div class="time">2天前</div>
    			</div>
    		</div>
    		<div class="price">￥5200.00</div>
    	</div>
    	<div class="container photo">
    		<div class="swiper-container swiper">
			    <div class="swiper-wrapper">
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/1.jpg">
			        		<img src="${ctxStatic}/carousel/images/1.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/2.jpg">
			        		<img src="${ctxStatic}/carousel/images/2.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/3.jpg">
			        		<img src="${ctxStatic}/carousel/images/3.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/4.jpg">
			        		<img src="${ctxStatic}/carousel/images/4.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/5.jpg">
			        		<img src="${ctxStatic}/carousel/images/5.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/6.jpg">
			        		<img src="${ctxStatic}/carousel/images/6.jpg" class="imgtype">
			        	</a>
			        </div>
			    </div>
			
			</div>
    	</div>
    	<div class="detail">
    		业主自住一手房，2房1厅1卫，56平米，家电家私齐全
    	</div>
    	<div class="count">
    		<img src="${ctxStatic}/image/点赞.png" class="per1">5
    		<img src="${ctxStatic}/image/评论.png" class="per1">10
    	</div>
    </div>

    <div class="content">
    	<div class="baseInfo">
    		<div class="member">
    			<div class="info1">
    				<img src="${ctxStatic}/image/avatar.png" style="width: 100%;height: 100%;">
    			</div>
    			<div class="info2">
    				<div class="name">Marry</div>
    				<div class="time">2天前</div>
    			</div>
    		</div>
    		<div class="price">￥5200.00</div>
    	</div>
    	<div class="container photo">
    		<div class="swiper-container swiper">
			    <div class="swiper-wrapper">
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/1.jpg">
			        		<img src="${ctxStatic}/carousel/images/1.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/2.jpg">
			        		<img src="${ctxStatic}/carousel/images/2.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/3.jpg">
			        		<img src="${ctxStatic}/carousel/images/3.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/4.jpg">
			        		<img src="${ctxStatic}/carousel/images/4.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/5.jpg">
			        		<img src="${ctxStatic}/carousel/images/5.jpg" class="imgtype">
			        	</a>
			        </div>
			        <div class="swiper-slide">
			        	<a href="${ctxStatic}/carousel/images/6.jpg">
			        		<img src="${ctxStatic}/carousel/images/6.jpg" class="imgtype">
			        	</a>
			        </div>
			    </div>
			    <!-- 如果需要分页器 -->
			    <div class="swiper-pagination"></div>
			</div>
    	</div>
    	<div class="detail">
    		业主自住一手房，2房1厅1卫，56平米，家电家私齐全
    	</div>
    	<div class="count">
    		<img src="${ctxStatic}/image/点赞.png" class="per1">5
    		<img src="${ctxStatic}/image/评论.png" class="per1">10
    	</div>
    </div>

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
			/*nextButton: '.swiper-button-next',
			prevButton: '.swiper-button-prev',*/
		})   

		function showImg(url){
			$(".weui-gallery-img").css("background-image", "url("+url+")");
			$(".weui-gallery").fadeIn();
		}     
  </script>
</body>
</html>