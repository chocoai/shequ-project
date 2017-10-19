<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>组织机构</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
<link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
<script src="${ctxStatic}/js/zepto.min.js"></script>
<script src="${ctxStatic}/js/layer.js"></script>
<script>
	$(function() {
		$('.js-category').click(
				function() {
					$parent = $(this).parent('li');
					if ($parent.hasClass('js-show')) {
						$parent.removeClass('js-show');
						$(this).children('i').removeClass('icon-35').addClass(
								'icon-74');
					} else {
						$parent.siblings().removeClass('js-show');
						$parent.addClass('js-show');
						$(this).children('i').removeClass('icon-74').addClass(
								'icon-35');
						$parent.siblings().find('i').removeClass('icon-35')
								.addClass('icon-74');
					}
				});

	});
</script>
</head>

<body ontouchstart style="background-color: #f8f8f8;">
	<script type="text/javascript">
		function selectmenu(n,pid) {
			var eleMore = document.getElementById("menu_" + n);
			if (eleMore.style.display == "none") {
				eleMore.style.display = 'block';
				$("#cell_" + n).removeClass("icon-74");
				$("#cell_" + n).addClass("icon-35 ");
			} else {
				eleMore.style.display = 'none';
				$("#cell_" + n).removeClass("icon-35");
				$("#cell_" + n).addClass("icon-74");
			}
			
			//从后台获取数据
			$.ajax({
	            url: '${ctx}/sns/wyActCandidate/selectOrgData',
	            data: {pid:pid},
	            type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		//获取公司下面的人员				
						if(n.indexOf("m00")>-1){
							showGlc("menu_" + n,data.data);
						}else if(n.indexOf("m10")>-1){
							showBumen("menu_" + n,data.data);
						}else if(n.indexOf("m20")>-1){
							showStaff("menu_" + n,data.data);
						}
	            	}else{
	            		alert("获取数据失败");
	            	}            
	            }
	        });
			
		}
		
		//显示管理处
		function showGlc(menuId,data){
			var glcNum=$("#"+menuId).find(".glc").length;			
			glcNum +=1;
			var html1='';
			if(data){
				for(var i=0;i<data.length; i++){
					glcNum +=i;
				    html1 +='<div class="glc">';
					html1 +='<div class="weui_panel_bd">';
					html1 +='<div class="weui_media_box weui_media_small_appmsg">';
					html1 +='<div class="weui_cells weui_cells_access">';
					html1 +='<a href="javascript:;" class="weui_cell">';
					//html1 +='<input type="checkbox" class="weui_check" name="candidateIds" value="'+data[i].id+'" id="glc_s_'+glcNum+'">';
		            //html1 +='<i class="weui_icon_checked"></i>';													
					html1 +='<div class="weui_cell_bd weui_cell_primary" style="padding-left: 20px;">';
					//html1 +='<label class="weui_check_label" for="glc_s_'+glcNum+'"><p>'+data[i].name+'</p></label>';
					html1 +='</div>'; 
					html1 +='<span id="cell_m1000'+glcNum+'" class="icon icon-74" onclick="selectmenu(\'m1000'+glcNum+'\',\''+data[i].id+'\');"></span>'; 
					html1 +='</a>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='<div style="display: none;" id="menu_m1000'+glcNum+'">';								
					html1 +='</div>';
					html1 +='</div>';		
				}
				$("#"+menuId).html(html1);			
			}
				
		}
		
		function showBumen(menuId,data){
			var jqMenu="#"+menuId;
			var glcNum=$(jqMenu).find(".bumen").length;			
			glcNum +=1; 			
			var html1='';
			if(data){
				for(var i=0;i<data.length; i++){
					glcNum +=i;
				 	html1 +='<div class="bumen">';
					html1 +='<div class="weui_panel_bd">';
					html1 +='<div class="weui_media_box weui_media_small_appmsg">';
					html1 +='<div class="weui_cells weui_cells_access">';
					html1 +='<a href="javascript:;" class="weui_cell">';
					//html1 +='<input type="checkbox" class="weui_check" name="candidateIds" value="'+data[i].id+'" id="bumen_s_'+glcNum+'">';
		            //html1 +='<i class="weui_icon_checked"></i>';													
					html1 +='<div class="weui_cell_bd weui_cell_primary" style="padding-left: 30px;">';
					//html1 +='<label class="weui_check_label" for="bumen_s_'+glcNum+'"><p>'+data[i].name+'</p></label>';
					html1 +='</div>'; 
					html1 +='<span id="cell_m2000'+glcNum+'" class="icon icon-74" onclick="selectmenu(\'m2000'+glcNum+'\',\''+data[i].id+'\');"></span>'; 
					html1 +='</a>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='<div style="display: none;" id="menu_m2000'+glcNum+'">';								
					html1 +='</div>';
					html1 +='</div>';	
				}			
				$(jqMenu).html(html1);
			}
		}
		
		function showStaff(menuId,data){
			var jqMenu="#"+menuId;
			var glcNum=$(jqMenu).find(".staff").length;			
			glcNum +=1; 			
			var html1='';
			if(data){
				for(var i=0;i<data.length; i++){
					glcNum +=i;
				 	html1 +='<div class="staff">';
					html1 +='<div class="weui_panel_bd">';
					html1 +='<div class="weui_media_box weui_media_small_appmsg">';
					html1 +='<div class="weui_cells weui_cells_access">';
					html1 +='<a href="javascript:;" class="weui_cell">';
					html1 +='<input type="checkbox" class="weui_check" name="candidateIds" value="'+data[i].id+'" id="staff_s_'+glcNum+'">';
		            html1 +='<i class="weui_icon_checked"></i>';													
					html1 +='<div class="weui_cell_bd weui_cell_primary" style="padding-left: 40px;">';
					html1 +='<label class="weui_check_label" for="staff_s_'+glcNum+'"><p>'+data[i].name+'</p></label>';
					html1 +='</div>'; 
					html1 +='</a>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='</div>';
					html1 +='<div style="display: none;" id="menu_m3000'+glcNum+'">';								
					html1 +='</div>';
					html1 +='</div>';	
				}			
				$(jqMenu).html(html1);
			}
		}
		
		
	</script>
<form id="orgForm" name="orgForm">
	<input type="hidden" name="procDefId" value="${wyRelationCandidate.procDefId}"/>
	<input type="hidden" name="taskId" value="${wyRelationCandidate.taskId}"/>
	<input type="hidden" name="source" value="${wyRelationCandidate.source}"/>
	<input type="hidden" name="procInsId" value="${wyRelationCandidate.procInsId}"/>
	<div class="weui_cells_title">请选择组织机构</div>
	<div class="weui_panel weui_cells_checkbox">
		<div class="weui_panel_bd">
			<div class="weui_media_box weui_media_small_appmsg">
				<div class="weui_cells weui_cells_access">
					<a href="javascript:;" class="weui_cell">					
						<input type="checkbox" class="weui_check" name="candidateIds" value="${company.id}" id="s1">
                    	<i class="weui_icon_checked"></i>                    							
						<div class="weui_cell_bd weui_cell_primary" style="padding-left: 10px;">
							<label class="weui_check_label" for="s1"><p>${company.name}</p></label>
						</div>					 	
						<span id="cell_m0000" class="icon icon-74" onclick="selectmenu('m0000','G_1');"></span> 
					</a>
				</div>
			</div>
		</div>
		<div style="display: none;" id="menu_m0000"></div>
	</div>
</form>
	<div class="weui_btn_area weui_btn_area_inline">
        <a href="javascript:;" class="weui_btn weui_btn_warn" onclick="submitOrg()">确 认</a>
        <a href="javascript:;" class="weui_btn bg-orange weui_btn_inline" onclick="window.history.go(-1);">返 回</a>
    </div>
    <script type="text/javascript">
    	function submitOrg(){
    		var formdata=$("#orgForm").serialize();
    		//保存信息
    		console.info(formdata);
    		$.ajax({
	            url: '${ctx}/sns/wyActCandidate/selectOrgSave',
	            data: formdata,
	            type: 'post',
	            async: false,
	            success: function(data) {            	       
	            	if(data.code==200){
	            		//获取公司下面的人员				
						
						window.history.go(-1);
	            	}else{
	            		alert("获取数据失败");
	            	}            
	            }
	        });
    	}
    </script>
	

</body>
</html>