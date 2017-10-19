CKEDITOR.dialog.add('date', function(editor) {
	var escape = function(value) {
		return value;
	};
	var	html='<iframe id="wx_fileUpload" name="fileUpload" onload="showImage()" style="display:none;"></iframe><img id="_wx_file_form_show_img"/><form id="_wx_file_form_" method="post"  accept="image/png,image/jpeg,image/jpg" action="'+ wxUploadImgUrl +'" enctype="multipart/form-data" name="fileForm" target="fileUpload"><input type="file" class="fileInput" name="file"><input class="btn btn-warning" type="submit" value="提 交" onchange="uploadFile()"/></form>';

	return {
		title : '上传图片到微信服务器',
		resizable : CKEDITOR.DIALOG_RESIZE_BOTH,
		minWidth : 300,
		minHeight : 80,
		contents : [ {
			id : 'cb',
			name : 'cb',
			label : 'cb',
			title : 'cb',
			elements : [ {
				type : 'html',
				html : html
			} ]
		} ],
		onOk : function() {	
			var src = $("#_wx_file_form_show_img").attr("src");			
			editor.insertHtml("<img src='" + src + "'/>");
		},
		onLoad : function() {
			$("#_wx_file_form_show_img").attr("src","");	
		}
	};
});