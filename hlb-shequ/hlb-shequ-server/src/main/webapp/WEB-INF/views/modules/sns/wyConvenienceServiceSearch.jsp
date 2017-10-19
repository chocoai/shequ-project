<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title></title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${ctxStatic}/css/shequ-server.css">
		<link rel="stylesheet" href="${ctxStatic}/css/weui.min1.css">
		<link rel="stylesheet" href="${ctxStatic}/css/jquery-weui.min.css">
		<script src="${ctxStatic}/js/jquery.js"></script>
		<script src="${ctxStatic}/js/layer.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
			var ctxStatic = "${ctxStatic}";
		</script>

		<style>
			body{margin:0;overflow-x:hidden;padding:0;}
			.other{
				margin-top: 54px !important;
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
		<div class="shequ-popup" style="display: block;">
			<div class="weui-search-bar" id="searchBar" style="height: 44px;position: fixed; width: 100%; z-index: 999;">
				<div class="weui-search-bar__form" style="margin: 0;">
					<div class="weui-search-bar__box">
						<i class="weui-icon-search"></i>
						<input type="search" class="weui-search-bar__input" id="keyword" placeholder="${(type eq '1')?'管理处':'公司或管理处'}搜索" required="">
						<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
					</div>
					<label class="weui-search-bar__label" id="searchText">
						<i class="weui-icon-search"></i>
						<span style="line-height: 28px;">${(type eq '1')?'管理处':'公司或管理处'}搜索</span>
					</label>
				</div>
				<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
			</div>
			<!-- <div style="margin:5px;overflow-x:hidden;padding:0;" class="other">
				${content}
			</div> -->
			<div class="weui-cells other">
				<c:forEach items="${list}" var="list">
		            <a class="weui-cell weui-cell_access" href="${ctx}/wyConvenienceService//view/${list.serviceId}">
		                <div class="weui-cell__hd">
		                </div>
		                <div class="weui-cell__bd">
		                    <p>${list.groupInfo}</p>
		                </div>
		                <div class="weui-cell__ft">
		                </div>
		            </a>
		        </c:forEach>
	        </div>
			<div class="bgtip">
		        <img src="${ctxStatic}/image/zwnr.png" class="bgurl">
		        <p class="bgcontent">暂无内容</p>
		    </div>
		</div>
		<script src="${ctxStatic}/js/jquery-weui.min.js"></script>
    	<script src="${ctxStatic}/js/jquery.min.js"></script>
    	<script type="text/javascript">
    		var type = "${type}";
    		$("#keyword").on('keypress',function(e) {  
				var keycode = e.keyCode;  
				var searchName = $(this).val();  
				if(keycode=='13') { 
					window.location.replace(ctx + '/wyConvenienceService/search?type='+type+'&wymc='+searchName);
				}  
			});
			$(function() {
				var flag = "${flag}";
				if(flag == "false"){
					$(".bgtip").css("display", "block");
				}
			});
    	</script>
	</body>
</html>