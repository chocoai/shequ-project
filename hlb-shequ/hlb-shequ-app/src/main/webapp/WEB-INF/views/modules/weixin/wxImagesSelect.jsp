<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信图文消息管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/layer/layer-v1.8.5/layui.css"  media="all">
	<link rel="stylesheet" href="${ctxStatic}/layer/layer.css" id="layui_layer_skinlayercss">
	<script src="${ctxStatic}/layer/layer-v1.8.5/layer.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>	
	<script src="${ctxStatic}/layer/pc/layui.js" type="text/javascript"></script>
	<style>
		*{margin:0; padding:0;}
		body{font-size:12px;}
		pre{display:none;}
		p{line-height:24px;}
		button{height:30px; line-height:30px; padding:0 10px; margin-right:10px;}
		.border{
			border:5px solid red;
		}
	</style>
	<script type="text/javascript">
		layui.use('upload', function(){
		  layui.upload({
		    url: '${ctx}/weixin/wxNewsArticle/wxImagesUpload'
		    ,success: function(result){	
			    if(result.code==200){
			    	window.location.reload();
			    }else{
			    	alert("上传出错:"+result.msg);
			    }	      
		    }
		  });
		  
		});
		
		function closeLayer(){	  		
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="wxNewsArticle" action="${ctx}/weixin/wxNewsArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns">
				<input type="file" name="file" class="layui-upload-file" lay-title="上传素材"> 
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="确认选择" onclick="sure()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="border:none;">
		<input id="name" type="hidden"> </input>							
		<c:forEach items="${images}" var="img" varStatus="status">		
		<c:if test="${(status.index+1)%5==1}"><tr></c:if>
			<td><img src="${img.url}" id="${img.mediaId}" style="width:200px;height:200px;" onclick="choose(this)"></td>					
		<c:if test="${(status.index+1)%5==0}"></tr></c:if>
		</c:forEach>
	</table>
	<script type="text/javascript">
		function choose(i){
			/*$("img").each(function(){
				$("img").removeClass("border");
			});*/
			$("img").removeClass("border");
			$(i).addClass("border");
		};

		function sure(){
			var photo = $(".border");
			if(photo.length == 0){
				layer.open({
	                content: "请先选择一张图片",
	                skin: 'msg',
	                time: 2
	            });
	            return;
			}else{
				var mediaId = photo.attr("id");
				$("#name").val(mediaId);
				/*parent.window.close();*/
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.$(".shouye").attr("src", photo.attr("src"));
				parent.$("#thumbMediaId").val(mediaId);
				parent.layer.close(index); //再执行关闭   
			}
		}
	</script>
</body>
</html>