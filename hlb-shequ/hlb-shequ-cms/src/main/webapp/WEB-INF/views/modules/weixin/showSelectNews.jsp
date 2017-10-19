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
			parent.$("#newsArticleId").val(newStr);
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
		<c:forEach items="${newsList}" var="news" varStatus="status">	
		<tr>
			<td><input id="news_${status.index}" type="checkbox" value="${news.mediaId}"/></td>
			<td><label for="news_${status.index}">${news.mediaId}</label></td>
			<td>
				<c:forEach items="${news.content.articles}" var="article">
					缩略图的media_id:${article.thumbMediaId}<br/>
					作者:${article.author}<br/>
					标题:${article.title}<br/>
					“阅读原文”后的页面链接:${article.contentSourceUrl}<br/>
					内容:<pre>${article.content}</pre><br/>
					描述:${article.digest}<br/>
					是否显示封面:${article.showCoverPic}<br/>
					点击图文消息跳转链接：<a target="_blank" style="color:blue;" href="${article.url}">点击查看文章</a><br/>
				</c:forEach>
			</td>			
		</tr>		
		</c:forEach>
	</table>
</form>
</body>
</html>