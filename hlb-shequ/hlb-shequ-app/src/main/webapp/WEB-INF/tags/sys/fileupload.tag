<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="上传url地址"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、flash、thumb"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="beforeCallback" type="java.lang.String" required="false" description="执行上传前成调用函数"%>
<%@ attribute name="afterCallback" type="java.lang.String" required="false" description="执行完上传成功后的回调函数"%>
<ol id="${input}Preview"></ol>
<c:if test="${!readonly}">
<a href="javascript:" onclick="${input}FinderOpen();" class="btn">${selectMultiple?'添加':'选择'}</a>&nbsp;
<a href="javascript:" onclick="${input}DelAll();" class="btn">清除</a>
<div id="${input}-fileupload-div" style="display:none;"></div>
</c:if>
<script type="text/javascript">
	function ${input}FinderOpen(){
		//为了防止onchange时间失灵，每次上传都创建一个input，上传完就清除该input
		var inputDom = '<input id="${input}-fileupload" type="file" onchange="${input}FileUpload()"/>';	
		$("#${input}-fileupload-div").append(inputDom);	
		$("#${input}-fileupload").click();		 
	}
	
	//文件上传,触发
	function ${input}FileUpload(){
		var formData = new FormData();
        formData.append("file", document.getElementById("${input}-fileupload").files[0]);  
        formData.append("uploadPath", "${uploadPath}"); 
        $("#${input}-fileupload-div").empty();
        
        var url = "${url}";
        //获取url地址的参数
        if(typeof ${beforeCallback} == "function"){
        	//执行回调获取参数
        	 var relationId = ${beforeCallback}();
        	 url += relationId;  
        }
        //关联业务id
             
        $.ajax({
            url: url,
            type: "POST",
            data: formData,           
            contentType: false,           
            processData: false,
            async:false,
            success: function (result) {
                if (result.code == 200) {                	
                	var data = result.data;
                	$("#${input}").val(data.url);
                    ${input}Preview();                    
                    
                    if(typeof ${afterCallback} == "function"){
                    	//执行回调函数
                    	${afterCallback}(data);
                    }                    
                }
                if (result.code == 500) {
                    alert(result.msg);
                }               
            },
            error: function () {
                alert("上传失败，请重试！");               
            }
        });
	}
	
	function ${input}SelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){//<c:if test="${type eq 'thumb'}">
			url += files[i].getThumbnailUrl();//</c:if><c:if test="${type ne 'thumb'}">
			url += files[i].getUrl();//</c:if>
			if (i<files.length-1) url+="|";
		}//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);//</c:if>
		${input}Preview();
		//top.$.jBox.close();
	}
	function ${input}ThumbSelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){
			url += files[i].getThumbnailUrl();
			if (i<files.length-1) url+="|";
		}//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);//</c:if>
		${input}Preview();
		//top.$.jBox.close();
	}
	function ${input}Callback(api){
		ckfinderAPI = api;
	}
	function ${input}Del(obj){
		var url = $(obj).prev().attr("url");
		$("#${input}").val($("#${input}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
		${input}Preview();
	}
	function ${input}DelAll(){
		$("#${input}").val("");
		${input}Preview();
	}
	function ${input}Preview(){
		var inputVal = $("#${input}").val();
		inputVal = (inputVal==undefined) ? '|':inputVal;
		
		console.info("---inputVal--start--");
		console.info(inputVal);
		console.info("---inputVal--start--");
		
		var li, urls = inputVal.split("|");
		$("#${input}Preview").children().remove();
		for (var i=0; i<urls.length; i++){
			if (urls[i]!=""){//<c:if test="${type eq 'thumb' || type eq 'images'}">
				li = "<li><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">";//</c:if><c:if test="${type ne 'thumb' && type ne 'images'}">
				li = "<li><a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//</c:if>
				li += "&nbsp;&nbsp;<c:if test="${!readonly}"><a href=\"javascript:\" onclick=\"${input}Del(this);\">×</a></c:if></li>";
				$("#${input}Preview").append(li);
			}
		}
		if ($("#${input}Preview").text() == ""){
			$("#${input}Preview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
		}
	}
	${input}Preview();
</script>