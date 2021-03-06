<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跳蚤市场管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
		<div id="innerdiv" style="position:absolute;">
			<img id="bigimg" style="border:5px solid #fff;" src="" />
		</div>
	</div>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sns/wyFleaMarket/">跳蚤市场列表</a></li>
		<%-- <shiro:hasPermission name="sns:wyFleaMarket:edit"><li><a href="${ctx}/sns/wyFleaMarket/form">跳蚤市场添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wyFleaMarket" action="${ctx}/sns/wyFleaMarket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>物业小区：</label>
				<form:select path="wymc" class="input-medium">
					<option value="">所有小区</option>
					<c:forEach items="${list}" var="list">
						<option value="${list.source}_${list.groupId}" ${((list.source eq source)&&(list.groupId eq wyid))?'selected="selected"':''}>${list.wymc}</option>
					</c:forEach>
				</form:select>
			</li>
			<li><label>物品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>物品名称</th>
				<th>发布人</th>
				<th>物品描述</th>
				<th>价格</th>
				<th>是否爱心赠送</th>
				<th>评论数</th>
				<th>点赞数</th>
				<!-- <th>经度</th>
				<th>维度</th> -->
				<th>是否已审核</th>
				<th>图片</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sns:wyFleaMarket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wyFleaMarket">
			<tr>
				<td style="width: 180px;"><%-- <a href="${ctx}/sns/wyFleaMarket/form?id=${wyFleaMarket.id}"> --%>${wyFleaMarket.goodsName}<!-- </a> --></td>
				<td>${wyFleaMarket.member.memberName}</td>
				<td>${wyFleaMarket.goodsDesc}</td>
				<td style="text-align: right;">${wyFleaMarket.price}</td>
				<td style="text-align: center;">
					${wyFleaMarket.giftGiving?"<font color='red'>是<font>":"否"}
				</td>
				<td style="text-align: right;">${wyFleaMarket.commentNum}</td>
				<td style="text-align: right;">${wyFleaMarket.dianzanNum}</td>
				<%-- <td>${wyFleaMarket.longitude}</td>
				<td>${wyFleaMarket.latitude}</td> --%>
				<td style="text-align: center;">${wyFleaMarket.audit?"<font color='red'>是<font>":"否"}</td>
				<td style="text-align: center; width: 200px">
					<c:forEach items="${wyFleaMarket.imgList}" var="img">
						<img src="${img}" style="height: 27px;width: 27px" onclick="imgShow('#outerdiv', '#innerdiv', '#bigimg', this)">
			    	</c:forEach>
				</td>		
				<td style="text-align: center;width: 125px">
					<fmt:formatDate value="${wyFleaMarket.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sns:wyFleaMarket:edit"><td style="text-align: center;width: 100px">
					<a href="${ctx}/sns/wyFleaMarket/audit?id=${wyFleaMarket.id}" onclick="return confirmx('确认要审核【<font color=\'red\'>${wyFleaMarket.goodsName}</font>】吗？', this.href)">审核</a>
    				<%-- <a href="${ctx}/sns/wyFleaMarket/form?id=${wyFleaMarket.id}">修改</a> --%>
					<a href="${ctx}/sns/wyFleaMarket/delete?id=${wyFleaMarket.id}" onclick="return confirmx('确认要删除【<font color=\'red\'>${wyFleaMarket.goodsName}</font>】吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function imgShow(outerdiv, innerdiv, bigimg, jq){  
		    var _this = $(jq);
		    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性  
		    $(bigimg).attr("src", src);//设置#bigimg元素的src属性  
		  
		        /*获取当前点击图片的真实大小，并显示弹出层及大图*/  
		    $("<img/>").attr("src", src).load(function(){  
		        var windowW = $(window).width();//获取当前窗口宽度  
		        var windowH = $(window).height();//获取当前窗口高度  
		        var realWidth = this.width;//获取图片真实宽度  
		        var realHeight = this.height;//获取图片真实高度  
		        var imgWidth, imgHeight;  
		        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放  
		          
		        if(realHeight>windowH*scale) {//判断图片高度  
		            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放  
		            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度  
		            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度  
		                imgWidth = windowW*scale;//再对宽度进行缩放  
		            }  
		        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度  
		            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放  
		                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度  
		        } else {//如果图片真实高度和宽度都符合要求，高宽不变  
		            imgWidth = realWidth;  
		            imgHeight = realHeight;  
		        }  
		                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放  
		          
		        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距  
		        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距  
		        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性  
		        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg  
		    });  
		      
		    $(outerdiv).click(function(){//再次点击淡出消失弹出层  
		        $(this).fadeOut("fast");  
		    });  
		}  
	</script>
</body>
</html>