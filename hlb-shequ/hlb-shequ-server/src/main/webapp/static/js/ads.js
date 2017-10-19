//轮播图js
$(function() {
	var width = document.body.offsetWidth;
    $(".bdimg").css("height",(width/2));//轮播图比例2:1
    $("#search-btn").click(function() {
        if ($(".top-search").css("display") == 'block') {
            $(".top-search").hide();
            $(".top-title").show(200);
        } else {
            $(".top-search").show();
            $(".top-title").hide(200);
        }
    });
    $("#search-bar li").each(function(e) {
        $(this).click(function() {
            if ($(this).hasClass("on")) {
                $(this).parent().find("li").removeClass("on");
                $(this).removeClass("on");
                $(".serch-bar-mask").hide();
            } else {
                $(this).parent().find("li").removeClass("on");
                $(this).addClass("on");
                $(".serch-bar-mask").show();
            }
            $(".serch-bar-mask .serch-bar-mask-list").each(function(i) {
                if (e == i) {
                    $(this).parent().find(".serch-bar-mask-list").hide();
                    $(this).show();
                } else {
                    $(this).hide();
                }
                $(this).find("li").click(function() {
                    $(this).parent().find("li").removeClass("on");
                    $(this).addClass("on");
                });
            });
        });
    });
});

TouchSlide({
    slideCell: "#focus",
    titCell: ".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
    mainCell: ".bd ul",
    effect: "left",
    autoPlay: true, //自动播放
    autoPage: true, //自动分页
    switchLoad: "_src", //切换加载，真实图片路径为"_src", 
});