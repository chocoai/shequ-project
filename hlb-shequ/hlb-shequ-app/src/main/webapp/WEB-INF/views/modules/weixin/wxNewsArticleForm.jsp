<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信图文消息管理</title>
	<meta name="decorator" content="default"/>
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
    

    
    function setAccountInfo(data){    	
    	if(data){
    		$("#thumbMediaId").val(data.mediaId);    		
    	}
    	console.info($("#thumbMediaId").val());
    	console.info("----setAccountInfo---end--");
    }
    
    function getAccountInfo(){
    	return "?accountId=" + $("#accountId").val();
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
			<label class="control-label">公众号：</label>
			<div class="controls">
				<form:select path="accountId" class="input-xlarge required">
					<option value="">请选择</option>
					<form:options items="${fns:getWxAccountList()}" itemLabel="wxname" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">封面图：</label>			
			<div class="controls">
				<input id="thumbMediaId" name="thumbMediaId" maxlength="100" class="input-xlarge" type="hidden" value="${wxNewsArticle.thumbMediaId}">				
				<input id="imgLocalUrl" name="imgLocalUrl" maxlength="100" class="input-xlarge" type="hidden" value="${wxNewsArticle.imgLocalUrl}"> 
				<sys:fileupload input="imgLocalUrl" type="images" uploadPath="/images" url="${ctx}/weixin/wxNewsArticle/wxImagesUpload" afterCallback="setAccountInfo" beforeCallback="getAccountInfo"></sys:fileupload>						
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
			<!-- <input id="preView" class="btn" type="button" value="预 览"/> -->
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>