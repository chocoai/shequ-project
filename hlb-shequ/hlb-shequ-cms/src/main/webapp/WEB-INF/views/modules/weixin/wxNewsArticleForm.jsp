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
	<script type="text/javascript">
		var ctx="${ctx}";
		var wxUploadImgUrl="${ctx}/weixin/wxNewsArticle/wxNewsImagesUpload";
		var src,srcOriginal;
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			//调用微信接口预览发送的图文消息
			$("#preView").click(function(){
				alert("预览");			
			});
			
		});
		
		//打开选择
		function thumbMediaIdFinderOpen(){
			var url="${ctx}/weixin/wxNewsArticle/wxImagesSelect";	
			var title="选择微信图片";	
			$.layer({
			  type: 2,
			  shade: [0.9, '#393D49'],
			  fix: false,
			  title: title,
			  maxmin: true,
			  iframe: {src : url},
			  area: [document.documentElement.clientWidth * 0.6 , document.documentElement.clientHeight * 0.6],
			  close: function(index){
			   	layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
			  }
			});
		}	
		
		function thumbMediaIdDelAll(){
			$("#thumbMediaId").val("");
			var images = $("#thumbMediaIdPreview").find("img");
			$.each(images,function(index,item){
				$(item).attr("src","");
			});
		}	
		
		function thumbMediaIdDel(jq){
			$(jq).prev().attr("src","");
			$("#thumbMediaId").val("");
		}
		
		layui.use('upload', function(){
		  layui.upload({
		    url: ctx+'/weixin/wxNewsArticle/wxImagesUpload'
		    ,success: function(result){	
			    if(result.code==200){
			    	console.info(result.data);
			    	$('#file-img-show').attr("src",result.data.thumbMediaId);
			    	$('#thumbMediaId').val(result.data.mediaId);
			    	$('#imgLocalUrl').val(result.data.thumbMediaId);
			    }else{
			    	alert("上传出错:"+result.msg);
			    }
		    }
		  });
		  
		});
		
		
	function showImage(){		
		var data = $("#wx_fileUpload").contents().find('body').find("pre").html();		
        //检查返回是否为json格式
        if(isJSON(data)){
        	data = eval('(' + data + ')');
        }
        $("#_wx_file_form_show_img").attr("src",data.data.url);
	}
	
	
	
	//检查是否为json格式
	function isJSON (str, pass_object) {
		  if (pass_object && isObject(str)) return true;
		  if (!isString(str)) return false;
		  str = str.replace(/\s/g, '').replace(/\n|\r/, '');
		  if (/^\{(.*?)\}$/.test(str))
		    return /"(.*?)":(.*?)/g.test(str);
		  if (/^\[(.*?)\]$/.test(str)) {
		    return str.replace(/^\[/, '')
		      .replace(/\]$/, '')
		      .replace(/},{/g, '}\n{')
		      .split(/\n/)
		      .map(function (s) { return isJSON(s); })
		      .reduce(function (prev, curr) { return !!curr; });
		  }
		  return false;
	}
	
	 function isString(str){
        if(typeof str=="string"){
            return true;
        }else{
            return false;
        }
    }		
	</script>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxNewsArticle/">微信图文消息列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxNewsArticle/form?id=${wxNewsArticle.id}">微信图文消息<shiro:hasPermission name="weixin:wxNewsArticle:edit">${not empty wxNewsArticle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxNewsArticle:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxNewsArticle" action="${ctx}/weixin/wxNewsArticle/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">封面图：</label>
			<div class="controls">
				<img id="file-img-show" class="shouye" src="${wxNewsArticle.imgLocalUrl}" style="max-width:100px;max-height:100px;_height:100px;border:0;padding:3px;">&nbsp;&nbsp;
				<input type="file" name="file" class="layui-upload-file" lay-title="上传素材"> 
				<input id="thumbMediaId" name="thumbMediaId" maxlength="100" class="input-xlarge" type="hidden" value="${wxNewsArticle.thumbMediaId}">
				<input id="imgLocalUrl" name="imgLocalUrl" maxlength="100" class="input-xlarge" type="hidden" value="${wxNewsArticle.imgLocalUrl}">				
				<span class="help-inline"><font color="red">(图片小于2M)*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否显示封面：</label>
			<div class="controls">
				<form:radiobuttons path="showCoverPic" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>			
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
				<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xxlarge measure-input required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者：</label>
			<div class="controls">
				<form:input path="author" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">&ldquo;阅读原文&rdquo;链接：</label>
			<div class="controls">
				<form:input path="contentSourceUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<span class="help-inline"><font color="gray">在图文消息页面点击“阅读原文”后的页面链接</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图文摘要：</label>
			<div class="controls">
				<form:textarea id="digest" htmlEscape="false" path="digest" rows="4" maxlength="255" class="input-xxlarge required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">图文内容：</label>
			<div class="controls">			
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="1000" class="input-xxlarge required"/>
				<sys:ckeditor replace="content" uploadPath="/weixin/massImage" />
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxNewsArticle:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="preView" class="btn" type="button" value="预 览"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>