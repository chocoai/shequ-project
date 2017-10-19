<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>从文小区</title>
<link rel="stylesheet" href="${mobileStatic}/css/weui.css">
<link rel="stylesheet" type="text/css"
	href="${mobileStatic}/css/region.css">

<!--组件依赖js begin-->
<script type="text/javascript" src="${mobileStatic}/js/zepto.js "></script>
<script type="text/javascript" src="${mobileStatic}/js/zepto.extend.js "></script>
<style>
.gmu-media-detect {
	-webkit-transition: width 0.001ms;
	width: 0;
	position: relative;
	bottom: -999999px;
}

@media screen and (width: 1920px) {
	#gmu-media-detect0 {
		width: 100px;
	}
}
</style>
<script type="text/javascript" src="${mobileStatic}/js/zepto.ui.js "></script>
<script type="text/javascript" src="${mobileStatic}/js/zepto.fix.js "></script>
<!--刷新-->
<script type="text/javascript" src="${mobileStatic}/js/refresh.js "></script>

<style type="text/css">
.tab_title {
	padding: 0px;
	width: 100%;
	display: -moz-box;
	display: -webkit-box;
}

.tab_title a {
	display: block;
	-webkit-box-flex: 1;
	-moz-box-flex: 1;
	text-align: center;
	height: 40px;
	line-height: 40px;
	border-right: 1px solid #e4e4e4;
	border-bottom: 1px solid #e4e4e4;
	font-size: 14px;
}

.tab_title a:last-child {
	border-right: none;
}

.tab_title a.active {
	color: #EB233F;
	font-size: 16px;
	background: url(${mobileStatic}/image/hongjiao.png) 0px 0px no-repeat
		#fff;
	background-size: 16px;
}

#btn_div {
	position: fixed;
	height: 50px;
	z-index: 990;
	right: 0px;
	bottom: 120px;
}

.btn_img {
	background: url(${mobileStatic}/image/menu_btn.png) no-repeat;
	width: 30px;
	height: 30px;
	background-size: 30px;
	display: inline-block;
	margin: 10px;
	-webkit-transition: all 0.2s ease-out;
	-moz-transition: all 0.2s ease-out;
}

#btn_dj {
	height: 50px;
	width: 50px;
	background: #48b54e;
	position: absolute;
	right: 0px;
	top: 0px;
	z-index: 100;
}

.menu_btn {
	height: 50px;
	padding: 5px 0px 5px 5px;
	font-size: 14px;
	background: #48b54e;
	color: #fff;
	position: absolute;
	right: 50px;
	width: 247px;
	-webkit-transition: all 0.5s ease-out;
	-moz-transition: all 0.5s ease-out;
	z-index: 80;
}

.menu_btn a {
	display: inline-block;
	color: #fff;
	border-right: 1px solid #fff;
	text-align: center;
	width: 80px;
	height: 100%;
	line-height: 40px;
}

.hide_b {
	-moz-transform: rotate(0deg);
	-webkit-transform: rotate(0deg);
}

.show_b {
	-moz-transform: rotate(-45deg);
	-webkit-transform: rotate(-45deg);
}

.hide_m {
	-moz-transform: translateX(297px);
	-webkit-transform: translateX(297px);
	opacity: 0;
}

.show_m {
	-moz-transform: translateX(0px);
	-webkit-transform: translateX(0px);
	opacity: 1;
}
/*加载*/
.ui-refresh-down {
	height: 42px;
	border-top: 1px solid #e4e4e4;
	border-bottom: 1px solid #e4e4e4;
	line-height: 42px;
	background: #f0f0f0;
	text-align: center;
}

.ui-refresh-icon {
	display: inline-block;
	content: '';
	background: url(${mobileStatic}/image/arrow-down.png) no-repeat;
	width: 12px;
	background-size: 12px;
	height: 7px;
	margin-right: 5px;
}

.ui-refresh-icon {
	display: inline-block;
	content: '';
	background: url(${mobileStatic}/image/arrow-down.png) no-repeat;
	width: 12px;
	background-size: 12px;
	height: 7px;
	margin-right: 5px;
}

.ui-loading {
	display: inline-block;
	content: '';
	background: url(${mobileStatic}/image/loading.gif) no-repeat;
	width: 16px;
	background-size: 16px;
	height: 16px;
	margin-right: 5px;
}

.rank {
	text-align: center;
	color: white;
	background-color: #48b54e;
	width: 50px;
	font-size: 12px;
	height: 20px;
	line-height: 20px;
	border-radius: 2px;
}

.del {
	width: 50px;
	height: 20px;
	line-height: 20px;
	font-size: 12px;
	margin-right: 2px;
	background-color: #dfdfdf;
	border-radius: 2px;
	text-align: center;
}
</style>
</head>

<body>
	<header id="header">
		<div class="header_div">
			<div id="left_div"
				onclick="alert('123');"></div>
			<div id="middle_div">
				<h1 id="title">我的报修</h1>
			</div>
			<div id="right_div">
				<div class="right_bottom"></div>
				<div class="right_img"></div>
			</div>
		</div>
	</header>
	<div class="cont">
		<div class="tab_title">
			<a href="#"	class="active">全部报修</a> 
			<a href="#">已处理</a>
			<a href="">未处理</a>
		</div>		
		<div class="ui-refresh" id="repair">
			<div class="weui_cells weui_cells_access" id="data-list">
				<a class="weui_cell"
					href="#">
					<div class="weui_cell_bd weui_cell_primary">
						<p></p>
						<p style="background-color:#eb223f;color:white;width:50px;height:20px; border-radius: 5px;font-size:12px;text-align:center;line-height:20px;float:left">未处理</p>
						<p style="font-size:12px;line-height:20px;">&nbsp;&nbsp;请选择报修类型</p>
						<p></p>
					</div>
					<div class="weui_cell_ft"></div> 
				</a>				
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p style="font-size:12px;color: #a9a9a9;clear:both;margin-top:5px;"'="">报修日期：2017-03-28 16:53</p>
					</div>
					<div class="weui_cell_bd del" onclick="del(47)">删除</div>
				</div>
				
				<a style="height:20px;width:100%;background-color: #efeef4;display:block"></a>
			</div>
			<div class="ui-refresh-down">
				<span class="ui-refresh-label">没有更多内容了</span>
			</div>
		</div>
		
		
	</div>

	<style type="text/css">
.mainMenuWrap {
	width: 100%;
	position: fixed;
	background-color: #f8f8f8;
	border-top: 1px solid #ddd;
	height: 58px;
	left: 0;
	bottom: 0;
	z-index: 2100;
}

.mainMenu {
	width: 100%;
	text-align: center;
	height: 58px;
}

.mainMenu a {
	width: 25%;
	line-height: 1.2em;
	display: block;
	float: left;
	text-align: center;
	font-size: 12px;
	color: #82878b;
}

.mainMenu a .cur,.mainMenu .a1 a,.a2 a,.a4 a:hover {
	color: #48b54e;
}

.mainMenu a .thumb {
	-webkit-transform-origin: center top;
	-webkit-transform: scale(0.62);
	background-image:
		url(${mobileStatic}/image/bottom.png);
	background-repeat: no-repeat;
	background-size: 130px auto;
	margin: -1px auto -19px;
	width: 60px;
	height: 60px;
	/*margin-left:10px;*/
}

.mainMenu .a1 .thumb {
	background-position: -8px 10px;
}

.mainMenu .a1 .cur .thumb,.mainMenu .a1:hover .thumb {
	background-position: -67px 10px;
}

.mainMenu .a2 .thumb {
	background-position: -5px -50px;
}

.mainMenu .a2 .cur .thumb,.mainMenu .a2:hover .thumb {
	background-position: -67px -50px;
}

.mainMenu .a3 .thumb {
	background-position: -2px -110px;
}

/*.mainMenu .a3 .cur .thumb,.mainMenu .a3:hover .thumb{ background-position:-50px -90px;}*/
.mainMenu .a4 .thumb {
	background-position: -5px -175px;
}

.mainMenu .a4 .cur .thumb,.mainMenu .a4:hover .thumb {
	background-position: -67px -175px;
}

.noread_num {
	position: absolute;
	background: #ff3e3e;
	padding: 0px 5px;
	border-radius: 10px;
	color: #fff;
	top: 5px;
	left: 39%;
	font-size: 12px;
}
</style>
	<script>
function a1() {
    window.location.href = "./index.php?i=3&c=entry&do=home&m=xfeng_community";
}

function a2() {
    window.location.href = "./index.php?i=3&c=entry&op=list&do=announcement&m=xfeng_community";
}

function a4() {
    window.location.href = "./index.php?i=3&c=entry&do=member&m=xfeng_community";
}

function select(s) {
    if (s == 'a1') {
        //$(".a1 .thumb").addClass("cur");
        $(".a1 .thumb").css("background-position", "-67px 10px");
        $(".a1 p").css("color", "#48b54e")
    } else if (s == 'a2') {
        // $(".a2").addClass("cur");
        $(".a2 .thumb").css("background-position", "-67px -50px");
        $(".a2 p").css("color", "#48b54e")
    } else if (s == 'a3') {

    } else {
        // $(".a4").addClass("cur");
        $(".a4 .thumb").css("background-position", "-67px -175px");
        $(".a4 p").css("color", "#48b54e")
    }

}
// $(function(){
//           $(".a3").click(function(event) {
//               $(".a3 .thumb").css("background-position", "-65px -175px");
//                 $(".a3 p").css("color", "#48b54e")
//           });      
// });
</script>
	<div class="mainMenuWrap">
		<div class="mainMenu">
			<a class="a1" href="javascript:a1();">
				<div class="thumb" style="background-position: -67px 10px;"></div>
				<p style="color: rgb(72, 181, 78);">小区</p> </a> <a class="a2"
				href="javascript:a2();">
				<div class="thumb"></div>
				<p>通知</p> </a> <a class="a3" href="tel:13800138000">
				<div class="thumb"></div>
				<p>电话</p> </a> <a class="a4" href="javascript:a4();">
				<div class="thumb"></div>
				<p>我</p> </a>
		</div>
	</div>

	<script>
    $(function() {
        $("#btn_div").fix({
            right: 0,
            bottom: 120
        })
        $("#btn_dj").click(function() {
            var input_btn = $("#btn_input")
            if (input_btn.attr("class") == "btn_img hide_b") {
                input_btn.removeClass();
                input_btn.addClass("btn_img show_b");
            } else {
                input_btn.removeClass();
                input_btn.addClass("btn_img hide_b");
            }
            var menu_b = $("#menu_b")
            if (menu_b.attr("class") == "menu_btn hide_m") {
                menu_b.removeClass();
                menu_b.addClass("menu_btn show_m");
            } else {
                menu_b.removeClass();
                menu_b.addClass("menu_btn hide_m");
            }
        })
       
    })
    function del(id){
        var id = id;
                $.post("./index.php?i=3&c=entry&do=repair&m=xfeng_community", {"op":"delete","id":id}, function(msg){
                    if (msg.status == 1) {
                                setTimeout(function(){
                                    window.location.reload();
                                 },100);
                            };
                },'json');
    }
    </script>
	<script>
    /*ajax请求函数,判断共几页，呈现不同效果*/
    (function() {

        xqAjax();

        function xqAjax() {
            var url = "./index.php?i=3&c=entry&op=my&do=repair&m=xfeng_community";
            var status = "";
            $.ajax({
                url: url,
                async: false,
                type: 'post',
                dataType: 'json',
                data: {
                    status: status
                },
                beforeSend: function() {
                    //$("#data-list").html("<li class='seach_loading'><img src='http://wq.pt800.com/addons/xfeng_community/template/mobile/style/style1/static/image/loading.gif'/>为您搜索中……</li>")
                },
                success: function(data) {
                    var $list = $('.data-list'),
                        html = (function(data) { //数据渲染
                            var liArr = [];
                            $.each(data.allhtml, function() {
                                liArr.push(this.html);
                            });
                            return liArr.join('');
                        })(data);

                    $('.weui_cells_access').html(html)
                    if (data.page_count < 2) {
                        $(".ui-refresh-down").html("<span class='ui-refresh-label'>没有更多内容了</span>");
                    } else {
                        refreshFun();
                    }
                }
                // error: function() {
                //     alert('请求不到数据，请重新搜索！');
                // }

            })
        }

        /*组件初始化refreshFun函数*/

        function refreshFun() {
            var index = 1;
            $('.ui-refresh').refresh({
                ready: function(dir, type) {
                    var me = this;
                    var status = "";
                    index++;
                    $.getJSON("./index.php?i=3&c=entry&op=my&do=repair&m=xfeng_community", {
                        page: index,
                        status: status
                    }, function(data) {
                        var $list = $('.weui_cells_access'),
                            html = (function(data) { //数据渲染
                                var liArr = [];
                                $.each(data.allhtml, function() {
                                    liArr.push(this.html);
                                });
                                return liArr.join('');
                            })(data);
                        $list[dir == 'up' ? 'prepend' : 'append'](html);
                        if (index < data.page_count) {
                            me.afterDataLoading();
                        } else {
                            me.disable(dir, false);
                        }
                    });
                }
            });

        }
        /*组件初始化js end*/
    })();
    </script>
	<script>
    $(function() {
        select('a1');
    });
    </script>

	<div class="gmu-media-detect" id="gmu-media-detect0"></div>
	<div style="position:fixed;top:10px;"></div>

</body>
</html>