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
</head>
<body style="background-color: #f8f8f8;">
<form id="myform" name="myform" action="${ctx}/sns/wyRepairSettlementLabor/save" method="post">
	<div class="weui_cells_title"><h3>添加工时费用</h3></div>	
	<div class="weui_cells weui_cells_form">	
		<div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
            <div class="weui_cell_bd weui_cell_primary">
            	<input type="hidden" name="id" value="${wyRepairSettlementLabor.id}"/>
            	<input id="repairId" type="hidden" name="repairId" value="${empty repairId ? wyRepairSettlementLabor.repairId : repairId}"/>
                <input name="name" value="${wyRepairSettlementLabor.name}" class="weui_input" type="text" placeholder="请输入姓名" emptyTips="请输入姓名" maxlength="15" required/>                
            </div>
        </div>
        <%-- <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">每小时单价</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input name="price"  value="${wyRepairSettlementLabor.price}" class="weui_input" placeholder="请输入每小时单价"/>
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">花费小时数</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input name="spentHour" value="${wyRepairSettlementLabor.spentHour}" class="weui_input" placeholder="请输入花费小时数"/>
            </div>
        </div> --%>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">工时费</label></div>
            <div class="weui_cell_bd weui_cell_primary">
            	<input name="count" value="${wyRepairSettlementLabor.count}" class="weui_input" placeholder="请输入工时费"/>
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