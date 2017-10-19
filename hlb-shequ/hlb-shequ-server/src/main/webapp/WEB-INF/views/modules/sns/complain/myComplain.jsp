<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>我的投诉</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="keywords" content="Javascript,cancelBubble,stopPropagation" /> 
	<link rel="stylesheet" href="${ctxStatic}/css/myRepair.css">	
	<script src="${ctxStatic}/js/jquery.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
    <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
    <script src="${ctxStatic}/js/zepto.min.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
     /*   var memberType = "${memberType}";
            if(memberType == '0'){
                layer.open({
                    content: "您是游客身份，无权查看",
                    skin: 'msg',
                    time: 2
                });
                window.history.go(-1); 
            }*/
        $(document).ready(function(){
		    $('.avatarUrl').css('height',$('.avatarUrl').css('width'));
		    $('.repairPhoto').css('height',$('.repairPhoto').css('width'));
		     $('#tab1').tab({defaultIndex:"${flag}",activeClass:"tab-green"});
		     $(".moments__post .weui_cell_bd .thumbnails .thumbnail").css('height',$('.moments__post .weui_cell_bd .thumbnails .thumbnail').css('width'));

		});
		
		function changeTab(flag){
			window.location.replace('${ctx}/wyComplain/myComplain?flag=' + flag);
		}
	</script>
	<script src="${ctxStatic}/js/myRepair.js"></script>
</head>
<body ontouchstart style="background-color: #f8f8f8;">
	<div class="weui_tab " style="height:44px;" id="tab1">
           <div class="weui_navbar" style="height:44px;">
               <div class="weui_navbar_item ${flag eq '0' ?'tab-green':''}" onclick="changeTab('0')">
                  	 待处理
               </div>
               <div class="weui_navbar_item" ${flag eq '1' ?'tab-green':''}" onclick="changeTab('1')">
                  	  已处理
               </div>
               <div class="weui_navbar_item ${flag eq '2' ?'tab-green':''}" onclick="changeTab('2')">
					  我申请                             
               </div>
           </div>        
	</div>
	<!--  -->
	<c:if test="${flag eq '0'}">
	<!-- 开始 朋友圈 -->
	<c:forEach items="${wyComplainList}" var="wyComplain">	
      <div class="weui_cells moments" id="${wyComplain.id}" onclick="show('${wyComplain.id}','${wyComplain.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyComplain.applyname}</span>
            </a>            
			<div class="progress">当前进度：${wyComplain.currStepName}</div>
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyComplain.content)>10 }">  
                         ${fn:substring(wyComplain.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyComplain.content)<=10 }">  
                      ${wyComplain.content}  
                </c:if> 
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyComplain.imgList}" var="img">
              <div class="thumbnail" style="width: 32%">
                <img src="${img}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              	<p>申请时间：<fmt:formatDate value="${wyComplain.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
            </div>
          </div>
          <!-- 结束 post -->
        </div>
      </div>      
	</c:forEach>
	<!-- 结束 朋友圈 -->	
	</c:if>
	
	<c:if test="${flag eq '1'}">
	<!-- 开始 朋友圈 -->
	<c:forEach items="${donewyComplainList}" var="wyComplain">	
      <div class="weui_cells moments" id="${wyComplain.id}" onclick="show('${wyComplain.id}','${wyComplain.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyComplain.applyname}</span>
            </a>            
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyComplain.content)>10 }">  
                         ${fn:substring(wyComplain.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyComplain.content)<=10 }">  
                      ${wyComplain.content}  
                </c:if>
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyComplain.imgList}" var="imgList">
              <div class="thumbnail" style="width: 32%">
                <img src="${ctxStatic}/${imgList}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              <p>申请时间：<fmt:formatDate value="${wyComplain.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
            </div>
          </div>
          <!-- 结束 post -->
        </div>
      </div>      
	</c:forEach>
	</c:if>
	
	<c:if test="${flag eq '2'}">
	<c:forEach items="${applyWyComplainList}" var="wyComplain">	
      <div class="weui_cells moments" id="${wyComplain.id}" onclick="show('${wyComplain.id}','${wyComplain.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyComplain.applyname}</span>
            </a>  
            <c:if test="${wyComplain.currStep <2}">
			<div class="edit" onclick="edit('${wyComplain.id}', event)">修改</div>				
			<div class="del" onclick="del('${wyComplain.id}', event)" style="text-decoration: none;">删除</div>
			</c:if>
			<div class="progress">当前进度：${wyComplain.currStepName}</div>          
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyComplain.content)>10 }">  
                         ${fn:substring(wyComplain.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyComplain.content)<=10 }">  
                      ${wyComplain.content}  
                </c:if>
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyComplain.imgList}" var="imgList">
              <div class="thumbnail" style="width: 32%">
                <img src="${ctxStatic}/${imgList}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              <p>申请时间：<fmt:formatDate value="${wyComplain.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
            </div>
          </div>
          <!-- 结束 post -->
        </div>
      </div>      
	</c:forEach>
	<!-- 结束 朋友圈 -->	
	</c:if>	
</body>
</html>