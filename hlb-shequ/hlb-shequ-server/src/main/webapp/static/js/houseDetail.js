var temps;

$(function(){
	$("#comment").attr("placeholder", "回复主人");
	/*$(".per1").css("height", $(".per1").width());*/
	//$("#publisherRelation").val(temp_publisherRelation);
});

function fillname(temp){
	temps = temp;
	var username = $(temp).find(".username").html();
	username = "回复：" + username;
	var commentId = $(temp).attr("id");
	var publisherRelation = $(temp).attr("name");
	$("#comment").attr("placeholder", username);
	$("#commentId").val(commentId);
	$("#publisherRelation").val(publisherRelation);
}

function send(){
	//alert("1:"+$("#publisherRelation").val()+"  2:"+$("#commentId").val());
	var comment = $("#comment").val();
	if(comment!=null && comment!=''){
		$.ajax({
			url : ctx+"/houseRent/submitComment",
			data : {"bizType":$("#bizType").val(),// 业务类型
					"relationId":temp_relationId,// 对应的业务表id
					//"publisher":,// 发表言论的人
					"publisherRelation":$("#publisherRelation").val(),// 回复谁
					"comment":$("#comment").val(), // 评论内容
					"commentId":$("#commentId").val(), // 评论id,当用户发表回复时,该字段必须有值
			},
			type : 'post',
			async : false,
			success : function(data) {
				if(data.code==200){
					if(data.data.type == 1){
						var type1comment = $(".type1comment").html();
						$(".type1comment").html(type1comment+data.data.data);
					}else if(data.data.type == 2){
						var jq;
						if($(temps).attr("class") == "message1"){
							jq = $(temps).parent().children(".type2comment");
						}else{
							jq = $(temps).parent().parent().children(".type2comment");
						}
						
						var type2comment = jq.html();
						jq.html(type2comment+data.data.data);
  						var oPos = jq.offsetTop;
  						return window.scrollTo(0, oPos-36);
					}
					$("#comment").attr("placeholder", "回复主人");
					$("#comment").val("");
					$("#commentId").val("");
					$("#publisherRelation").val("");
				}else if(data.code==500){
					var url=ctx+"/index";
					window.location.href = url;
				}
			}
		});
	}
	$("#comment").attr("placeholder", "回复主人");
	$("#comment").val("");
	$("#commentId").val("");
	$("#publisherRelation").val("");
}