$(function(){
// 系统公告滚动显示
    var $acBox = $('.acBox');
    var $acBox_ul = $acBox.find('ul');
    var acBox_ul_w = $acBox_ul.width();
    var $acBox_li = $acBox.find('li');
    var acBox_li_w = $acBox_li.width();
    var achtml = $acBox_li.html();
    // $acBox_li.html(achtml + achtml);
    var timeAc = null;
    var acBoxLeft = 0;
    function acBoxScroll(){
        if(acBoxLeft < acBox_li_w){
            acBoxLeft += 1;
        }else{
            acBoxLeft = -acBox_ul_w;
        }
        $acBox_li.css('left',-acBoxLeft);
        timeAc = setTimeout(acBoxScroll,20);
    }
     timeAc = setTimeout(acBoxScroll,200);

    // 关闭公告
    $acBox.find('.close').click(function(e){
        e.stopPropagation()
        clearTimeout(timeAc);
        $acBox.remove();
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

    /*weui1.0版本*/
    /*$(function(){
        $('.weui-tabbar__item').on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
        });
    });*/
    $(function(){
        /*TagNav('#tagnav',{
            type: 'scrollToFirst',
        });*/
        var width = document.body.offsetWidth;
        $(".bdimg").css("height",(width*45/100));
        $(".acBox").css("top", (width*45/100-24));
        $(".acBox").css("z-index", "100");
        $(".focus .bd").css("z-index", "99");
        $(".focus .bd").css("position", "fixed");
        $(".focus .bd").css("top", "0px");
    });

    $("input").blur(function(){
        var height = $(".bdimg").height();
        $(".bdimg").css("width", height*100/45);
        var width = $(".bdimg").width();
    });

    var sw;
    var sh;
    function orient() {
        if (window.orientation == 0 || window.orientation == 180) {
            $(".bdimg").width(sw);
        }
        else if (window.orientation == 90 || window.orientation == -90) {
            $(".bdimg").width(sh);
        }
    }

    $(function(){
        sw = screen.width;
        sh = screen.height;
    });


    $(window).bind( 'orientationchange', function(e){
        orient();
    });
});