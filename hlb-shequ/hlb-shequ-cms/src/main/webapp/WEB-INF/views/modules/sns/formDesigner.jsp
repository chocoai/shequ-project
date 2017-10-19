<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程表单设计</title>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>	
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.js"> </script>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/leipi.formdesign.v4.js"></script>
	
</head>
<body>
<div class="container">
<form method="post" id="saveform" name="saveform" action="/demo/formdesign.html" method="post">
<input type="hidden" name="form_id" value="1632">
<div class="row">
    <div class="well well-small">
<span class="pull-right">
    <a href="javascript:void(0);" class="btn btn-primary btn-small" onclick="leipiFormDesign.fnReview();">预览效果</a>
    <a href="javascript:void(0);" class="btn btn-success btn-small" id="button_save" onclick="leipiFormDesign.fnCheckForm('save');">确定保存</a>
</span>
<p>
        <button type="button" onclick="leipiFormDesign.exec('text');" class="btn btn-info">文本框</button>
        <button type="button" onclick="leipiFormDesign.exec('textarea');" class="btn btn-info">多行文本</button>
        <button type="button" onclick="leipiFormDesign.exec('select');" class="btn btn-info">下拉菜单</button>
        <button type="button" onclick="leipiFormDesign.exec('radios');" class="btn btn-info">单选框</button>
        <button type="button" onclick="leipiFormDesign.exec('checkboxs');" class="btn btn-info">复选框</button>
        <button type="button" onclick="leipiFormDesign.exec('macros');" class="btn btn-info">宏控件</button>
        <button type="button" onclick="leipiFormDesign.exec('progressbar');" class="btn btn-info">进度条</button>
        <button type="button" onclick="leipiFormDesign.exec('qrcode');" class="btn btn-info">二维码</button>
        <button type="button" onclick="leipiFormDesign.exec('listctrl');" class="btn btn-info">列表控件</button>
        <button type="button" onclick="leipiFormDesign.exec('more');" class="btn btn-primary">一起参与...</button>
</p>
     </div>
    <div class="alert">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>提醒：</strong>单选框和复选框，如：<code>{|-</code>选项<code>-|}</code>两边边界是防止误删除控件，程序会把它们替换为空，请不要手动删除！
    </div>

</div><!--end row-->   
<div class="row">
    <script id="myFormDesign" type="text/plain" style="width:100%;"></script>
</div><!--end row-->
</form>
</div><!--end container-->

<!-- script start-->  
<script type="text/javascript">

var leipiEditor = UE.getEditor('myFormDesign',{
            toolleipi:true,//是否显示，设计器的 toolbars
            textarea: 'design_content',   
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
           toolbars:[[
            'fullscreen', 'source', '|', 'undo', 'redo', '|','bold', 'italic', 'underline', 'fontborder', 'strikethrough',  'removeformat', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|', 'fontfamily', 'fontsize', '|', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',  'link', 'unlink',  '|','simpleupload',  'horizontal',  'spechars',  'wordimage', '|', 'inserttable', 'deletetable',  'mergecells',  'splittocells', '|','template' ]],
            //focus时自动清空初始化时的内容
            //autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:300
            //更多其他参数，请参考ueditor.config.js中的配置项
        });

 var leipiFormDesign = {
    exec : function (method) {
        leipiEditor.execCommand(method);
    },
    
    fnCheckForm : function ( type ) {
        if(leipiEditor.queryCommandState( 'source' ))
            leipiEditor.execCommand('source');//切换到编辑模式才提交，否则有bug
            
       
        if(leipiEditor.hasContents()){
            leipiEditor.sync();       //同步内容
            
            var type_value,formid,formeditor;
            if( typeof type!=='undefined' ){
                type_value = type;
            }
            formeditor=leipiEditor.getContent();
            
			$("#button_save").text("submit...");
               //异步提交数据
             $.ajax({
                type: 'POST',
                url : '/demo/formdesign.html',
                dataType : 'json',
                data : {"form_id":1632,"design_content":formeditor},
                success : function(data){
					$("#button_save").text("确定保存");
                  if(data.status==1){
                      alert('保存成功');
                      location.reload();
                  }else{
                      alert(data.info);
                  }
                }
            });
        } else {
            alert('表单内容不能为空！')
            $('#submitbtn').button('reset');
            return false;
        }
    } ,
    
    fnReview : function (){
        if(leipiEditor.queryCommandState( 'source' ))
            leipiEditor.execCommand('source');//切换到编辑模式才提交，否则有bug
            
        if(leipiEditor.hasContents()){
            leipiEditor.sync();       //同步内容
            
            document.saveform.target="mywin";
            window.open('','mywin',"menubar=0,toolbar=0,status=0,resizable=1,left=0,top=0,scrollbars=1,width=" +(screen.availWidth-10) + ",height=" + (screen.availHeight-50) + "\"");
            document.saveform.action="/demo/temp_preview.html";
            document.saveform.submit(); //提交表单
        } else {
            alert('表单内容不能为空！');
            return false;
        }
    }
};
</script>
<!-- script end -->
</body>
</html>