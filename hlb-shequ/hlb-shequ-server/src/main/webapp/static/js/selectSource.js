$(document).ready(function(){
	$(".add").click(function(){
		$(".shequ-popup").css("display", "block");
	});
	$(".submit").click(function(){
		if($('.building-info').serialize()==""){
			layer.open({
                content: "请添加房屋",
                skin: 'msg',
                time: 2
            });
			return;
		}
		 $.ajax({
            type: "POST",
            url: ctx + "/searchRoomNew",
            data: $('.building-info').serialize(), // 你的formid
            success: function(data) {
            	if (data.data == "success") {
                    window.location.href = ctx + "/selectRoom";
                }
            },
            error: function(request) {
                layer.open({
                    content: "服务器异常",
                    skin: 'msg',
                    time: 2
                });
                window.location.href = ctx + "/index";
            }
        });
	});
	$("#keyword").on('keypress',function(e) {  
		var keycode = e.keyCode;  
		var searchName = $(this).val();  
		if(keycode=='13') { 
			if(searchName==null || searchName==""){
				return;
			}
			$.ajax({
				url: ctx + '/searchSource',
				data: 'searchName=' + searchName,
				type: 'post',
				async: false,
				success: function(data) {
					if (data.code == 200){
						var array = new Array(); 
						array = data.data;
						for(var i=0; i<array.length; i++){
							var text = building(array[i].id, array[i].urlname);
							if(i==0){
								var html = "";
							}else{
								var html = $(".show-building").html();
							}
							$(".show-building").html(html+text);
						}
					}
					if (data.code == 500){
						layer.open({
							content: "查不到相应物业",
							skin: 'msg',
							time: 2
						});
						return;
					}
				}
			}); 
		}  
	});
});

function building(id, wymc){
    var text1 = "<div class='weui-cell weui-cell_access hstyle' id='"+id+"' onclick='fillBuilding1("+id+",\""+wymc+"\")'>";
    var text2 = "<div class='weui-cell__bd weui-cell_primary style1'>";
    var text3 = "<p class='style2'>"+wymc+"</p>";
    var text4 = "</div>";
    var text5 = "</div>";
    return text1+text2+text3+text4+text5;
}

function fillBuilding(id, wymc){
    $(".shequ-popup").css("display", "none");
    var text1 = "<div class='shequ-content_list'>";
    var text2 = "<div class='shequ-content_list_decorate'></div>";
    var text3 = "<input type='hidden' value='"+id+"' name='buildingId'></input>"
    var text4 = wymc;
    var text5 = "<img src='"+ctxStatic+"/image/房屋.png'>";
    var text6 = "</div>";
    var text = $(".shequ-content").html();
    $(".shequ-content").html(text+text1+text2+text3+text4+text5+text6);
}

function fillBuilding1(id, wymc){
    //$(".shequ-popup").css("display", "none");
    /*var text1 = "<div class='shequ-content_list'>";
    var text2 = "<div class='shequ-content_list_decorate'></div>";
    var text3 = "<input type='hidden' value='"+id+"' name='id'></input>"
    var text4 = wymc;
    var text5 = "<img src='"+ctxStatic+"/image/房屋.png'>";
    var text6 = "</div>";
    var text = $(".shequ-content").html();
    $(".shequ-content").html(text+text1+text2+text3+text4+text5+text6);*/
     $.ajax({
        type: "POST",
        url: ctx + "/searchManagerID",
        data: {"id":id}, // 你的formid
        success: function(data) {
        	if (data.data == "success") {
                window.location.href = ctx + "/index";
            }
        },
        error: function(request) {
            layer.open({
                content: "服务器异常",
                skin: 'msg',
                time: 2
            });
            //window.location.href = ctx + "/index";
        }
    });
}
