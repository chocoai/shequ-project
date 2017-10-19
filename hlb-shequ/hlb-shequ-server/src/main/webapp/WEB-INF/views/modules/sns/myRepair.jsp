<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>我的报修</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta name="keywords" content="Javascript,cancelBubble,stopPropagation" />
	<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
    <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">

    <link rel="stylesheet" href="${ctxStatic}/jqweui2/css/weui.min.css">
    <link rel="stylesheet" href="${ctxStatic}/jqweui2/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctxStatic}/jqweui2/css/demos.css">

    <script src="${ctxStatic}/js/layer.js"></script>
    <script src="${ctxStatic}/jqweui2/js/jquery-2.1.4.js"></script>
	<script src="${ctxStatic}/jqweui2/js/fastclick.js"></script>
	
	<script src="${ctxStatic}/jqweui2/js/jquery-weui.min.js"></script>
	
    
    
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
     
     	
     	
     	function search(jq){
     		var keyword=$(jq).val();
     		if(keyword == undefined || keyword==""){
     			return;
     		}
     		
     		alert(keyword);
     	}
     
	</script>
	<script src="${ctxStatic}/js/myRepair.js"></script>
</head>
<body ontouchstart style="background-color: #f8f8f8;">
	<div class="weui_tab " style="height:44px;" id="tab1"><!--tab-fixed添加顶部-->
           <div class="weui_navbar" style="height:44px;">
               <div class="weui_navbar_item ${flag eq '0' ?'tab-green':''}" onclick="deal('0')">
                  	 待办理
               </div>
               <div class="weui_navbar_item ${flag eq '1' ?'tab-green':''}" onclick="deal('1')">
                  	  已办理
               </div>
               <div class="weui_navbar_item ${flag eq '2' ?'tab-green':''}" onclick="deal('2')">
					  我发起                             
               </div>
           </div>        
	</div>
	
	<div class="weui-cells" style="top:-21px;">
      <div class="weui-cell weui-cell_select weui-cell_select-before">
        <div class="weui-cell__hd">
          <select class="weui-select" name="type-select" style="width:115px;">
          	<option value="">全部分类</option>
          	<%-- <c:forEach items="" var="">
          	<option value="1">+86</option>
          	</c:forEach>  --%>           
          </select>
        </div>        
        <div class="weui-cell__bd">
	        <input id="search_input" onclick="search(this);" class="weui-input" type="text" placeholder="请输入搜索关键字" style="width:80%;">
	        <label for="search_input"><i class="weui-icon-search"></i></label>
        </div>
      </div>
    </div>
	
	<!--  -->
	<c:if test="${flag eq '0'}">
	<!-- 开始 朋友圈 -->
	<c:forEach items="${wyRepairList}" var="wyRepair">	
      <div class="weui_cells moments" id="${wyRepair.id}" onclick="show('${wyRepair.id}','${wyRepair.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyRepair.applyname}</span>
            </a>            
			<div class="progress">当前进度：${wyRepair.currStepName}</div>
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyRepair.content)>10 }">  
                         ${fn:substring(wyRepair.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyRepair.content)<=10 }">  
                      ${wyRepair.content}  
                </c:if> 
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyRepair.imgList}" var="img">
              <div class="thumbnail" style="width: 32%">
                <img src="${img}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              	<p>申请时间：<fmt:formatDate value="${wyRepair.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
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
	<c:forEach items="${doneWyRepairList}" var="wyRepair">	
      <div class="weui_cells moments" id="${wyRepair.id}" onclick="show('${wyRepair.id}','${wyRepair.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyRepair.applyname}</span>
            </a>            
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyRepair.content)>10 }">  
                         ${fn:substring(wyRepair.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyRepair.content)<=10 }">  
                      ${wyRepair.content}  
                </c:if>
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyRepair.imgList}" var="imgList">
              <div class="thumbnail" style="width: 32%">
                <img src="${ctxStatic}/${imgList}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              <p>申请时间：<fmt:formatDate value="${wyRepair.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
            </div>
          </div>
          <!-- 结束 post -->
        </div>
      </div>      
	</c:forEach>
	<!-- 结束 朋友圈 -->	
	</c:if>
	
	<c:if test="${flag eq '2'}">
	<!-- 开始 朋友圈 -->
	<c:forEach items="${applyWyRepairList}" var="wyRepair">	
      <div class="weui_cells moments" id="${wyRepair.id}" onclick="show('${wyRepair.id}','${wyRepair.procInsId}')">
        <!-- 普通的post -->
        <div class="weui_cell moments__post">
          <div class="weui_cell_hd">
            <img src="${ctxStatic}/image/avatar.png" />
          </div>
          <div class="weui_cell_bd" style="width: 100%">
            <!-- 人名链接 -->
            <a class="title" href="javascript:;" style="width: 30%; float: left;">
              <span>${wyRepair.applyname}</span>
            </a>  
            <c:if test="${wyRepair.currStep <2}">
			<div class="edit" onclick="edit('${wyRepair.id}', event)">修改</div>				
			<div class="del" onclick="del('${wyRepair.id}', event)" style="text-decoration: none;">删除</div>
			</c:if>
			<div class="progress">当前进度：${wyRepair.currStepName}</div>          
            <!-- post内容 -->
            <p id="paragraph" class="paragraph" style="width: 100%">
            	报修内容：
            	<c:if test="${fn:length(wyRepair.content)>10 }">  
                         ${fn:substring(wyRepair.content, 0, 10)}...  
                </c:if>  
                <c:if test="${fn:length(wyRepair.content)<=10 }">  
                      ${wyRepair.content}  
                </c:if>
            </p>

            <!-- 伸张链接 -->
            <a id="paragraphExtender" class="paragraphExtender"></a>
            <!-- 相册 -->
            <div class="thumbnails">
            <c:forEach items="${wyRepair.imgList}" var="imgList">
              <div class="thumbnail" style="width: 32%">
                <img src="${ctxStatic}/${imgList}" />
              </div>
            </c:forEach>
            </div>
            <!-- 资料条 -->
            <div class="toolbar">
              <p>申请时间：<fmt:formatDate value="${wyRepair.createtime}" pattern="yyyy/MM/dd HH:mm"/></p>
            </div>
          </div>
          <!-- 结束 post -->
        </div>
      </div>      
	</c:forEach>
	<!-- 结束 朋友圈 -->	
	</c:if>	
</body>