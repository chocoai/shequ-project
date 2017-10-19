$(function(){
    var targetUrl=location.href.split("#")[0];
    $.ajax({
        type: "post",
        url: ctx+"/weixin/wxjssdk",
        dataType: "json",
        data:{"url":targetUrl},
        async:false,
        success: function (result) {
            //加载
            wx.config({
                debug: false,
                appId:result.data.appid,
                timestamp: result.data.timestamp,
                nonceStr: result.data.noncestr,
                signature: result.data.signature,
                jsApiList: ['checkJsApi', 'chooseImage','previewImage','uploadImage','downloadImage','openLocation','getLocation']
            });

            wx.ready(function(){
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                wx.checkJsApi({
                    jsApiList: ['chooseImage','previewImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                    success: function(res) {
                         //alert("检测接口:"+res.err_msg);
                    }
                });
            });
        }
    });
});