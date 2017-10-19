<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
	    <meta http-equiv='Content-Type' content='text/html;charset=UTF-8'>
	    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'>
	    <meta name='renderer' content='webkit|ie-stand|ie-comp'>
	    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <!-- <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/base.css"/>
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/style.css"/> -->
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/fonts/css/font-awesome.min.css"/>
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/address.css"/>
	    <link rel="stylesheet" href="${ctxStatic}/css/weui.css">
        <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
        <script src="${ctxStatic}/js/zepto.min.js"></script>
	    <script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
	    <script type="text/javascript" src="${ctxStatic}/js/selectRoom.js"></script>
	    <script type="text/javascript">var ctx = "${ctx}";</script>
		<title>合同</title>	
	</head>
	<body>
		<div class="">
			<!--头部-->
			<header class="border-bottom">
				<!-- <div class="header-back">
					<i class=" icon-reply ml10"></i>
				</div> -->
				<div class="text-center header-title">我的合同</div>		
			</header>
			<!--内容-->
			<div class="content">
			<!-- <c:forEach items="${roomList}" var="room">
				<div class="mAddress-item" id="">
					
					<div class="clearfix mt8">
						<div class="select mt5 pull-left select-status" onclick="changeStatus(this, ${room.roomId})">
							<c:if test="${room.roomId == roomId}">
								<i class='icon-ok shopCar-icon'>
									<input class='room' type='hidden' value="${room.roomId}" />
								</i>
							</c:if>
						</div>
						<div class="mAddress-tit">
							<div class="order-address-top clearfix">
								<div class="step order-name">${member.memberName}</div>
								<div class="step1">${member.mobile}</div>
							</div>
							<div class="order-address-con clearfix">
								<div class="order-address-cont">${room.WYName}${room.LYName}${room.roomNo}</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach> -->
				<div class="weui_cells weui_cells_radio">
					<c:forEach items="${roomList}" var="room" varStatus="status">
		            <div onclick="changeStatus(this, ${room.roomId})">
		            	<div class="select-status" style="display: none">
							<c:if test="${room.roomId == roomId}">
								<i class='icon-ok shopCar-icon'>
									<input class='room' type='hidden' value="${room.roomId}" />
								</i>
							</c:if>
						</div>
		            	<label class="weui_cell weui_check_label" for="x1${status.index+1}" >
			                <div class="weui_cell_bd weui_cell_primary">
			                    <p>${room.WYName}${room.LYName}${room.roomNo}</p>
			                </div>
			                <div class="weui_cell_ft">
			                    <c:choose>  
								   <c:when test="${room.roomId == roomId}">    	
								   		<input type="radio" class="weui_check" name="radio1" id="x1${status.index+1}" checked="checked">
								   </c:when>    
								   <c:otherwise>   
								   		<input type="radio" class="weui_check" name="radio1" id="x1${status.index+1}">
								   </c:otherwise>  
								</c:choose> 
			                    <span class="weui_icon_checked"></span>
			                </div>
			            </label>
		            </div>
		         	</c:forEach>
		        </div>
			</div>
			<div class="foot"></div>
		    <div class="text-center">
		    	<div class="pay-btn" onclick="toIndex()">
			    	<a>
			    		<span style="color: #FFF">确定</span>
			    	</a>
			    </div>
		    </div>
		</div>
		<script>
			$(document).ready(function () {
				function historyBack () {
					var refer = $trim(document.referrer);
					if (refer=='') {
						location.href=gWebsite;
					} else{
						history.back();
					}
				}
			});
		</script>
	</body>
</html>