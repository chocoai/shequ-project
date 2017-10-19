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
		function closeLayer(){	  		
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	
		function sure(){
			var news=$("#newsForm").find("input[type=checkbox]:checked");
			var newsArr=new Array();
			
			$.each(news,function(index,item){
			
				console.info(item);
				
				var v=$(this).val();
				newsArr.push(v);
			});
			
			console.info(news);
			console.info(newsArr);
			var newStr=newsArr.join(",");
			
			if(!news){
				layer.open({
	                content: "请选择一条素材",
	                skin: 'msg',
	                time: 2
	            });
				return;
			}			
			parent.$("#newsId").val(newStr);
			parent.$("#newsArticleId-show").text(newStr);
			closeLayer();
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="wxNewsArticle" action="${ctx}/weixin/wxNewsArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="确认选择" onclick="sure()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

<form id="newsForm" name="newsForm">
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="border:none;">							
		<thead>
			<th></th>
			<th>缩略图</th>
			<th>标题</th>
			<!-- <th>文章内容</th> -->
		</thead>
		<c:forEach items="${wxNewsArticleList}" var="article" varStatus="status">		
		<tr>
			<td width="30"><input style="width: 30px; height: 30px; margin: 0 30px;" id="news_${status.index}" type="checkbox" value="${article.id}"/></td>
			<td width="100"><label for="news_${status.index}"><img style="width:50px;height:50px;" alt="" src="${article.imgLocalUrl}"></label></td>
			<td>${article.title}</td>
			<!-- <td>
				${fns:unescapeHtml(article.content)}				
			</td>		 -->	
		</tr>		
		</c:forEach>
	</table>
</form>
</body>
</html>