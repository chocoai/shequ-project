function actForm(options) {
    var _defaults = {
        srcEle: this,
        pid: $(this).attr('pid')
    };
    var opts = $.extend(true, _defaults, options);

    // 处理使用js跟踪当前节点坐标错乱问题
    $('#changeImg').live('click', function() {
        $('#workflowTraceDialog').dialog('close');
        if ($('#imgDialog').length > 0) {
            $('#imgDialog').remove();
        }
        $('<div/>', {
            'id': 'imgDialog',
            title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点',
            html: "<img src='" + ctx + '/workflow/process/trace/auto/' + opts.pid + "' />"
        }).appendTo('body').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });
    });

    // 获取图片资源
    var imageUrl = ctx + "/workflow/resource/process-instance2?pid=" + opts.pid + "&type=image";
    $.getJSON(ctx + '/workflow/process/trace2?pid=' + opts.pid, function(infos) {
        var positionHtml = "";

        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                title: '查看流程（按ESC键可以关闭）',
                html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
                    "<div id='processImageBorder'>" +
                    positionHtml +
                    "</div>" +
                    "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });

        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            open: function() {
                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button>');
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

                // 此处用于显示每个节点的信息，如果不需要可以删除
                $('.activity-attr').qtip({
                    content: function() {
                        var vars = $(this).data('vars');
                        var tipContent = "<table class='need-border'>";
                        $.each(vars, function(varKey, varValue) {
                            if (varValue && varKey != "activitiId" && varKey != "procDefinitionId") {
                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                            }
                        });
                        tipContent += "</table>";
                        return tipContent;
                    },
                    position: {
                        at: 'bottom left',
                        adjust: {
                            x: 3
                        }
                    }
                });
                // end qtip

                //点击属性，弹出选择代办人员选择
                $('.activity-attr').click(function() {
                    var vars = $(this).data('vars');
                    //任务节点名称
                    var taskName = vars["任务名称"];
                    //任务节点类型
                    var taskType = vars["任务类型"];
                    var activitiId = vars["activitiId"];
                    var procDefinitionId = vars["procDefinitionId"];
                    
                    console.info("------vars---start----");
                    console.info(vars);
                    console.info("------vars---end----");
                    
                    //选择处理人,用户任务才进行处理
                    if (taskType == "用户任务"||taskType == "开始节点"||taskType == "结束节点") {
                        $('#selectHandler').dialog({
                            modal: true,
                            resizable: false,
                            dragable: false,
                            open: function() {
                                $('#selectHandler').empty();
                                $('#selectHandler').dialog('option', 'title', '任务节点【<font color="red">' + taskName + '</font>】配置表单url地址');
                                
                                var url="/sns/wyActForm/showForm";
                                if(taskType == "开始节点" || taskType == "结束节点"){
                                	url="/sns/wyActForm/showForm2";
                                }
                                //获取选择页面
                                var src = ctx + url + '?activitiId=' + activitiId + '&procDefinitionId=' + procDefinitionId+"&activitiType=" + taskType;
                              
                                var htmlstr = '<iframe id="selectHandlerFrame" name="selectHandlerFrame" src="' + src + '" width="100%" height="100%" style="overflow:hidden;" scrolling="no" frameborder="no"></iframe>';
                                $('#selectHandler').append(htmlstr);
                            },
                            close: function() {
                               
                            },
                            width: document.documentElement.clientWidth * 0.5,
                            height: document.documentElement.clientHeight * 0.5
                        });
                    }
                });
            },
            close: function() {
                $('#workflowTraceDialog').remove();
            },
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });

    });
}

