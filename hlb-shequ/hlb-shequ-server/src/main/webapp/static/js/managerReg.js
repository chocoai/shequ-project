var url = "";
    //用户手机号码校验
var mobile_timeout;
var mobile_count = 100;
var mobile_lock = 0;
var selectType = 1;
var text = "";


$(function() {
    $("#m_zcyz").click(function() {
        //  debugger;
        var mobile = $("#mobile").val();
        if (!mobile || mobile == "" || mobile == "请输入手机号码") {
            //提示
            layer.open({
                content: '<font>手机号码不能为空</font>',
                skin: 'msg',
                time: 2 //2秒后自动关闭
            });
            $("#mobile").focus();
            return;
        }
        //校验手机号码
        if (!isMobile(mobile)) {
            layer.open({
                content: '请输入正确的手机号码',
                skin: 'msg',
                time: 2
            });
            return;
        }
        if (mobile_lock == 0) {
            mobile_lock = 1;
            $.ajax({
                url: ctx + '/sendsms',
                data: 'mobile=' + mobile,
                type: 'post',
                async: false,
                success: function(data) {
                    console.info(data);
                    if (data && data.code == 200) {
                        mobile_count = 100;
                        $(".m_zcyz").addClass("on");
                        $('#m_zcyz').attr("disabled", "disabled");
                        BtnCount();
                    } else {
                        mobile_lock = 0;
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                }
            });
        }
    });
});

BtnCount = function() {
    if (mobile_count == 0) {
        $(".m_zcyz").removeClass("on");
        $('#m_zcyz').removeAttr("disabled");
        $('#m_zcyz').html("重新发送");
        mobile_lock = 0;
        clearTimeout(mobile_timeout);
    } else {
        mobile_count--;
        $('#m_zcyz').html("验证(" + mobile_count.toString() + ")秒");
        mobile_timeout = setTimeout(BtnCount, 1000);
    }
};


//主会员手机号码验证
var main_mobile_timeout;
var main_mobile_count = 100;
var main_mobile_lock = 0;
$(function() {
    $("#m_mainSmscode").click(function() {
        var mobile = $("#mainMemberPhone").val();
        if (!mobile || mobile == "" || mobile == "请输入业主手机号码") {
            $("#mainMemberPhone").focus();
            layer.open({
                content: '业主手机号码不能为空',
                skin: 'msg',
                time: 2
            });
            return;
        }
        //  debugger;
        if (main_mobile_lock == 0) {
            var flag = true;
            $.ajax({ //获取验证码之前先判断主会员是否存在系统中
                url: ctx + '/checkParentMember',
                data: 'mobile=' + mobile,
                type: 'post',
                async: false,
                success: function(data) {
                    // debugger;                 	
                    if (data.code == 200) {
                        $("#parentMemberId").val(data);
                    } else {
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                        flag = false;
                    }
                }
            });

            if(!flag){
                return ;
            }

            main_mobile_lock = 1;

            $.ajax({
                url: ctx + '/sendsms',
                data: 'mobile=' + mobile,
                type: 'post',
                async: false,
                success: function(data) {
                    // debugger;                 	
                    if (data.code == 200) {
                        main_mobile_count = 100;
                        $("#m_mainSmscode").addClass("on");
                        $('#m_mainSmscode').attr("disabled", "disabled");
                        mainBtnCount();
                    } else {
                        main_mobile_lock = 0;
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                }
            });
        }
    });
});
mainBtnCount = function() {
    if (main_mobile_count == 0) {
        $("#m_mainSmscode").removeClass("on");
        $('#m_mainSmscode').removeAttr("disabled");
        $('#m_mainSmscode').html("重新发送");
        main_mobile_lock = 0;
        clearTimeout(main_mobile_timeout);
    } else {
        main_mobile_count--;
        $('#m_mainSmscode').html("获取(" + main_mobile_count.toString() + ")秒");
        main_mobile_timeout = setTimeout(mainBtnCount, 1000);
    }
};

$(function() {
    //绑定用户输入短信验证码,如果已输入6个数字就进行验证
    $("#yzm").keyup(function() {
        $("#yzm-duigou").hide();
        $("#yzm-cha").hide();
        //限制六位数字输入
        var smscode = $(this).val();
        smscode = smscode.replace(/[^\.\d]/g, '');
        $(this).val(smscode);

        if (smscode && smscode.length == 6) {
           $.ajax({
                url: ctx + '/checkSmsCode',
                data: { smscode: smscode },
                type: 'post',
                async: false,
                success: function(data) {
                    if (data.code == 200) {
                        //保存验证码校验结果
                        $("#yzm").data("isValidate", true);
                        $("#yzm-duigou").show();
                        $("#yzm-cha").hide();
                    } else {
                        $("#yzm").data("isValidate", false);
                        $("#yzm-duigou").hide();
                        $("#yzm-cha").show();
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                    showreg();
                }
            });
        }
    });
    //点击刷新验证码
    $("#validateCode").click(function() {
        $('#validateCode').attr('src', ctx + '/servlet/validateCodeServlet?' + new Date().getTime());
    });

    //关联会员注册
    $("#associateReg").click(function() {
        window.location.href = ctx + "/reg?isAssociate=true";
    });
    //图形验证码校验
    $("#piccode").keyup(function() {
        $("#piccode-duigou").hide();
        $("#piccode-cha").hide();
        var piccode = $(this).val();
        piccode = piccode.replace(/[^\.\d\w]/g, '');
        $(this).val(piccode);

        if (piccode && piccode.length == 4) {
            $.ajax({
                url: ctx + '/checkPiccode',
                data: { piccode: piccode },
                type: 'post',
                async: false,
                success: function(data) {
                    if (data.code == 200) {
                        $("#piccode").data("isValidate", true);
                        $("#piccode-duigou").show();
                        $("#piccode-cha").hide();
                    } else {
                        $("#piccode").data("isValidate", false);
                        $("#piccode-duigou").hide();
                        $("#piccode-cha").show();
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                    showreg();
                }
            });
        }
    });


    //关联会员验证主会员手机号码
    $("#mainSmscode").keyup(function() {
        $("#mainSmscode-duigou").hide();
        $("#mainSmscode-cha").hide();
        //限制六位数字输入
        var smscode = $(this).val();
        smscode = smscode.replace(/[^\.\d]/g, '');
        $(this).val(smscode);

        if (smscode && smscode.length == 6) {
           $.ajax({
                url: ctx + '/checkSmsCode',
                data: { smscode: smscode },
                type: 'post',
                async: false,
                success: function(data) {
                    if (data.code == 200) {
                        //保存验证码校验结果
                        $("#mainSmscode").data("isValidate", true);
                        $("#mainSmscode-duigou").show();
                        $("#mainSmscode-cha").hide();
                    } else {
                        $("#mainSmscode").data("isValidate", false);
                        $("#mainSmscode-duigou").hide();
                        $("#mainSmscode-cha").show();
                        layer.open({
                            content: data.msg,
                            skin: 'msg',
                            time: 2
                        });
                    }
                    showreg();
                }
            });
        }
    });

    //注册提交
    $("#regSubmit").click(function() {
        //验证码校验		
        if (!$("#yzm").data("isValidate")) {
            $("#yzm").focus();
            layer.open({
                content: "请先通过手机验证码",
                skin: 'msg',
                time: 2
            });
            return;
        }

        if($("#isAssociate").val() === "false" && ($("#memberName").val()==null || $("#memberName").val()=="")){
            layer.open({
                content: "姓名不能为空",
                skin: 'msg',
                time: 2
            });
            return;
        }


        //图像验证码
        if (!$("#piccode").data("isValidate")) {
            $("#piccode").focus();
            layer.open({
                content: "请先通过图形验证码",
                skin: 'msg',
                time: 2
            });
            return;
        }

        /*//判断是否有勾选协议
        if (document.getElementById("ckNew").checked == false) {
            layer.open({
                content: "请先同意好邻邦用户使用协议",
                skin: 'msg',
                time: 2
            });
            return;
        }*/

        $.ajax({
            type: "POST",
            url: ctx + "/toManagerReg",
            data: $('#reg-form').serialize(), // 你的formid
            async: true, //开启异步，使得进度条可以随时读取
            error: function(request) {
                layer.open({
                    content: "服务器异常",
                    skin: 'msg',
                    time: 2
                });
                window.location.href = ctx + "/index";
            },
            success: function(data) {
                //console.log(data);
            	//$('.mee,.mtt').css('display', 'none');
                if (data.data == "success") {
                    //window.location.href = ctx + "/selectRoom";
                    window.location.href = ctx + "/selectSource";
                } else if (data.data == "fail") {
                    layer.open({
                        content: "系统出错",
                        skin: 'msg',
                        time: 2
                    });
                    return;
                } else if (data.data == "nomember") {
                    /*layer.open({
                        content: "查询不到相关房屋记录",
                        skin: 'msg',
                        time: 2
                    });*/
                    window.location.href = ctx + "/index";
                } else if (data.data == "exits") {
                    layer.open({
                        content: "该手机已经注册过了",
                        skin: 'msg',
                        time: 2
                    });
                } else if (data.data == "noexits") {
                    layer.open({
                        content: "系统中业主手机不存在或身份不为业主",
                        skin: 'msg',
                        time: 2
                    });
                }else {//关联会员无需选房，直接去到首页
                    window.location.href = ctx + "/index?roomId="+data.data;
                }
            }
        });
    });


});

var i = 0;

function startbar() {
    var showbar = setInterval("setbar()", 1000);
}

function setbar() {
    console.log("setbar");
    //读取当前
    i += 5;
    if (i >= 100) {
        clearInterval(showbar);
    }
    document.getElementById("bar").style.width = i + "%";
    document.getElementById("bar").innerHTML = i + "%";
}

function showreg(){
    if($("#yzm").data("isValidate") && $("#piccode").data("isValidate")) {
        $("#regSubmit").attr("disabled", false);
    } else if(selectType==2 && $("#yzm").data("isValidate")) {
        $("#m_mainSmscode").attr("disabled", false);
        if($("#mainSmscode").data("isValidate") && $("#piccode").data("isValidate")){
            $("#regSubmit").attr("disabled", false);
        }else{
            $("#regSubmit").attr("disabled", "disabled");
        }
    }else{
        $("#regSubmit").attr("disabled", "disabled");
    }
}

function building(id, wymc){
    var text1 = "<div class='weui-cell weui-cell_access hstyle' id='"+id+"' onclick='fillBuilding("+id+",\""+wymc+"\")'>";
    var text2 = "<div class='weui-cell__bd weui-cell_primary style1'>";
    var text3 = "<p class='style2'>"+wymc+"</p>";
    var text4 = "</div>";
    var text5 = "</div>";
    return text1+text2+text3+text4+text5;
}

function fillBuilding(id, wymc){
    $(".alertMessageBg").css("display", "none");
    $("#wymc").val(wymc);
    $("#buildingId").val(id);
}