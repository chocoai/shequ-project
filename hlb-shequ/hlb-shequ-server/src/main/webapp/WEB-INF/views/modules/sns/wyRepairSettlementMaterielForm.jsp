<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
	<title>表单详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		
	<link rel="stylesheet" href="${ctxStatic}/css/weui.css">
	<link rel="stylesheet" href="${ctxStatic}/css/weui2.css">	
	<script src="${ctxStatic}/js/zepto.min.js"></script>	
	<script type="text/javascript">
		var ctx = "${ctx}";
        var ctxStatic = "${ctxStatic}/";
        
         function upDownOperation(element)
		 {
		        var _input = element.parent().find('input'),
		            _value = _input.val(),
		            _step = _input.attr('data-step') || 1;
		        //检测当前操作的元素是否有disabled，有则去除
		        element.hasClass('disabled') && element.removeClass('disabled');
		        //检测当前操作的元素是否是操作的添加按钮（.input-num-up）‘是’ 则为加操作，‘否’ 则为减操作
		        if ( element.hasClass('weui-number-plus') )
		        {
		            var _new_value = parseInt( parseFloat(_value) + parseFloat(_step) ),
		                _max = _input.attr('data-max') || false,
		                _down = element.parent().find('.weui-number-sub');
		            
		            //若执行‘加’操作且‘减’按钮存在class='disabled'的话，则移除‘减’操作按钮的class 'disabled'
		            _down.hasClass('disabled') && _down.removeClass('disabled');
		            if (_max && _new_value >= _max) {
		                _new_value = _max;
		                element.addClass('disabled');
		            }
		        } else {
		            var _new_value = parseInt( parseFloat(_value) - parseFloat(_step) ),
		                _min = _input.attr('data-min') || false,
		                _up = element.parent().find('.weui-number-plus');
		            //若执行‘减’操作且‘加’按钮存在class='disabled'的话，则移除‘加’操作按钮的class 'disabled'
		            _up.hasClass('disabled') && _up.removeClass('disabled');
		            if (_min && _new_value <= _min) {
		                _new_value = _min;
		                element.addClass('disabled');
		            }
		        }
		        _input.val( _new_value );
		    }
			  
			     
		  $(function(){
		  		function calc(){
		  			var price = $("#weui-number-input-price").val();    
				    var num = $("#weui-number-input-num").val();			       	
			        var total = num * price;			        
			        	total = total.toFixed(2);
			        $("#total-price").text(total);		  		
		  		}
		  		
				$('.weui-number-plus').click(function(){
			        upDownOperation( $(this) );
			        calc();
			    });
			    $('.weui-number-sub').click(function(){
			        upDownOperation( $(this) );
				    calc();
			    });
			    //绑定
			     $('#weui-number-input-price,#weui-number-input-num').keyup(function(){			        
				    calc();
			    });					
		 });  
		 
        
	</script>
	<script src="${ctxStatic}/js/repairForm.js"></script>
</head>
<body style="background-color: #f8f8f8;">
<form id="myform" name="myform" action="${ctx}/sns/wyRepairSettlementMateriel/save" method="post">
	<div class="weui_cells_title"><h3>添加物料详情</h3></div>	
	<div class="weui_cells weui_cells_form">	
		<div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">物料编号</label></div>
            <div class="weui_cell_bd weui_cell_primary">
            	<input type="hidden" name="id" value="${wyRepairSettlementMateriel.id}"/>
            	<input id="repairId" type="hidden" name="repairId" value="${empty repairId ? wyRepairSettlementMateriel.repairId : repairId}"/>
                <input name="materialNo" value="${wyRepairSettlementMateriel.materialNo}" class="weui_input" type="text" placeholder="请输入物料编号" emptyTips="请输入物料编号" maxlength="15" required/>                
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">物料名称</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input name="materialName"  value="${wyRepairSettlementMateriel.materialName}" class="weui_input" placeholder="请输入物料名称"/>
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">规格型号</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input name="specificationModel" value="${wyRepairSettlementMateriel.specificationModel}" class="weui_input" placeholder="请输入规格型号"/>
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">价格</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input name="price" value="${wyRepairSettlementMateriel.price}" id="weui-number-input-price" class="weui_input" type="number" placeholder="请输入价格"/>
            </div>
        </div>
        <div class="weui_cell">
 			<div class="weui_cell_bd weui_cell_primary"> <p>数量</p> </div>
  			<div style="font-size: 0px;" class="weui_cell_ft">
   				<a class="weui-number weui-number-sub needsclick">-</a>
   				<input name="num" value="${empty wyRepairSettlementMateriel.num ? 0 : wyRepairSettlementMateriel.num}" id="weui-number-input-num" pattern="[0-9]*" type="number" class="weui-number-input" style="width: 50px;height:28px;" value='0'  data-min="0" data-max="100" data-step="1">
    			<a class="weui-number weui-number-plus needsclick">+</a> 
    		</div>    		
    	</div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">合计</label></div>
            <div class="weui_cell_bd weui_cell_primary">
            	<span id="total-price">${empty wyRepairSettlementMateriel.count ? 0.00 : wyRepairSettlementMateriel.count}</span> 元
            </div>
        </div>        
        <div class="weui_btn_area" style="text-align:center">
        	<a href="javascript:;" class="weui_btn bg-red weui_btn_inline" onclick="$('#myform').submit()">保存</a>
        	<a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_plain_primary f20" onclick="javascript:history.go(-1)">返回</a>
	    </div>
	</div>
</form>	
</body>
</html>