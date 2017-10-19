<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctxStatic}/layer/pc/css/layui.css"  media="all">
	<script src="${ctxStatic}/layer/pc/layui.js" type="text/javascript"></script>	
	

</head>
<style>
body{padding: 50px 100px;}
</style>
</head>
<body>

<input type="file" name="file" class="layui-upload-file">
<br><br>
<i>保留原格式：</i><input type="file" name="file" id="test">
<br><br>
<input type="file" name="file1" lay-type="file" lay-ext="zip|rar" class="layui-upload-file">
<br><br>
<input type="file" name="file1" lay-type="audio" class="layui-upload-file">
<br><br>
<input type="file" name="file2" lay-type="video" class="layui-upload-file">

<script>
layui.use('upload', function(){

  layui.upload({
    url: '${ctx}/weixin/wxNewsArticle/wxImagesUpload'
    ,success: function(src){
      console.log(src);
      alert(src);
    }
  });
  
  layui.upload({
    url: '${ctx}/weixin/wxNewsArticle/wxImagesUpload'
    ,elem: '#test'
    ,unwrap: true
    ,success: function(src){
      console.log(src);
        alert(src);
    }
  });
  
});
</script>
</body>
</html>