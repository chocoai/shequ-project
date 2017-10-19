<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
        <html lang="zh-cn">

        <head>
            <title>我的事务</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
            <meta name="keywords" content="Javascript,cancelBubble,stopPropagation"/>
            <link rel="stylesheet" href="${ctxStatic}/css/weui.css">
            <link rel="stylesheet" href="${ctxStatic}/css/weui2.css">
            <link rel="stylesheet" href="${ctxStatic}/css/weui.min.css">
            <link rel="stylesheet" href="${ctxStatic}/jqweui2/css/weui.min.css">
            <link rel="stylesheet" href="${ctxStatic}/jqweui2/css/demos.css">
            <style type="text/css">
            .progress {
                width: 100%;
                float: left;
                color: #09bb07;
            }
            .search {
                width: 11%;
                position: absolute;
                top: 15%;
                left: 84%;
                height: 35px;
            }
            .rowhg {
                width: 100%;
                height: 30px;
                line-height: 30px;
                float: left;
            }
            .bar {
                width: 14%;
                float: right;
            }
            </style>
        </head>

        <body style="background-color: #f8f8f8;">
            <div class="weui_tab " style="height:44px;" id="tab1">
                <!--tab-fixed添加顶部-->
                <div class="weui_navbar" style="height:44px;">
                    <div class="weui_navbar_item ${flag eq '0' ?'tab-green':''}" onclick="selectTab('0')">
                        待办理
                    </div>
                    <div class="weui_navbar_item ${flag eq '1' ?'tab-green':''}" onclick="selectTab('1')">
                        已办理
                    </div>
                    <div class="weui_navbar_item ${flag eq '2' ?'tab-green':''}" onclick="selectTab('2')">
                        我发起
                    </div>
                </div>
            </div>
            <form id="search-form" name="search-form" action="${ctx}/wuye/myAffairs" style="margin-top: -21px; height: 44px;">
                <input type="hidden" name="flag" value="${flag}" />
                <div class="weui-cells">
                    <div class="weui-cell weui-cell_select weui-cell_select-before" style="padding-right:0px">
                        <div class="weui-cell__hd">
                            <select id="select-type" class="weui-select" name="type" style="width:115px;">
                                <option value="">全部分类</option>
                                <c:forEach items="${wyBizDefList}" var="def">
                                    <option value="${def.key}" ${type eq def.key ? 'selected="selected"': ''}>${def.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="weui-search-bar" id="searchBar">
                            <div class="weui-search-bar__form">
                                <div class="weui-search-bar__box" style="height: 28px">  
                                    <i class="weui-icon-search"></i>
                                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="" name="keywords" value="${keywords}">
                                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
                                </div>
                                <label class="weui-search-bar__label" id="searchText">
                                    <i class="weui-icon-search"></i>
                                    <span>搜索</span>
                                </label>
                            </div>
                            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
                        </div>
                       <!--  <div class="weui-cell__bd">
                            <input id="search_input" name="keywords" class="weui-input" type="text" value="${keywords}" placeholder="请输入搜索关键字" style="width:80%;">
                            <img src="${ctxStatic}/image/search.png" onclick="search(this);" class="search" style="width:30px;height:30px;">
                            <button onclick="search(this);" style="opacity: 0;"><i class="weui-icon-search"></i></button> 
                        </div> -->
                    </div>
                </div>
            </form>
            <c:if test="${flag eq '0'}">
                <!-- 开始 朋友圈 -->
                <c:forEach items="${daibanWyApproveList}" var="wyApprove">
                    <div class="weui_cells moments" id="${wyApprove.id}" onclick="show('${wyApprove.bizId}','${wyApprove.procInstId}')">
                        <!-- 普通的post -->
                        <div class="weui_cell moments__post">
                            <div class="weui_cell_hd">
                                <img src="${ctxStatic}/image/avatar.png" />
                            </div>
                            <div class="weui_cell_bd" style="width: 100%">
                                <!-- 人名链接 -->
                                <a class="title" href="javascript:;" style="width: 30%; float: left;">
                                  <span>${wyApprove.name}</span>
                                </a>
                                <div class="progress rowhg">
                                    当前进度：${wyApprove.currTaskName}
                                    <div class="bar"><img src="${ctxStatic}/image/符号-进度条.png" style="width: 100%" onclick="detail('${wyApprove.procInstId}', this)"></div>
                                </div>
                                <!-- post内容 -->
                                <div class="rowhg">
                                    报修内容：
                                    <c:if test="${fn:length(wyApprove.content)>10}">
                                        ${fn:substring(wyApprove.content, 0, 10)}...
                                    </c:if>
                                    <c:if test="${fn:length(wyApprove.content)<=10}">
                                        ${wyApprove.content}
                                    </c:if>
                                </div>
                                <!-- 资料条 -->
                                <div class="toolbar rowhg">
                                    <p>申请时间：
                                        <fmt:formatDate value="${wyApprove.startTime}" pattern="yyyy/MM/dd HH:mm" />
                                    </p>
                                </div>
                            </div>
                            <!-- 结束 post -->
                        </div>
                    </div>
                </c:forEach>
                <!-- 结束 朋友圈 -->
            </c:if>
            <c:if test="${flag eq '1'}">
                <!-- 开始 朋友圈 -->
                <c:forEach items="${doneWyApproveList}" var="wyApprove">
                    <div class="weui_cells moments" id="${wyApprove.id}" onclick="show('${wyApprove.bizId}','${wyApprove.procInstId}')">
                        <!-- 普通的post -->
                        <div class="weui_cell moments__post">
                            <div class="weui_cell_hd">
                                <img src="${ctxStatic}/image/avatar.png" />
                            </div>
                            <div class="weui_cell_bd" style="width: 100%">
                                <!-- 人名链接 -->
                                <a class="title" href="javascript:;" style="width: 30%; float: left;">
                                    <span>${wyApprove.name}</span>
                                </a>
                                <div class="progress rowhg">
                                    当前进度：${wyApprove.currTaskName}
                                    <div class="bar"><img src="${ctxStatic}/image/符号-进度条.png" style="width: 100%" onclick="detail('${wyApprove.procInstId}', this)"></div>
                                </div>
                                <!-- post内容 -->
                                <div class="rowhg">
                                    报修内容：
                                    <c:if test="${fn:length(wyApprove.content)>10}">
                                        ${fn:substring(wyApprove.content, 0, 10)}...
                                    </c:if>
                                    <c:if test="${fn:length(wyApprove.content)<=10}">
                                        ${wyApprove.content}
                                    </c:if>
                                </div>
                                <div class="toolbar rowhg">
                                    <p>申请时间：
                                        <fmt:formatDate value="${wyApprove.startTime}" pattern="yyyy/MM/dd HH:mm" />
                                    </p>
                                </div>
                            </div>
                            <!-- 结束 post -->
                        </div>
                    </div>
                </c:forEach>
                <!-- 结束 朋友圈 -->
            </c:if>
            <c:if test="${flag eq '2'}">
                <!-- 开始 朋友圈 -->
                <c:forEach items="${myWyApproveList}" var="wyApprove">
                    <div class="weui_cells moments" id="${wyApprove.id}" onclick="show('${wyApprove.bizId}','${wyApprove.procInstId}')">
                        <!-- 普通的post -->
                        <div class="weui_cell moments__post">
                            <div class="weui_cell_hd">
                                <img src="${ctxStatic}/image/avatar.png" />
                            </div>
                            <div class="weui_cell_bd" style="width: 100%">
                                <!-- 人名链接 -->
                                <a class="title" href="javascript:;" style="width: 30%; float: left;">
                                    <span>${wyApprove.name}</span>
                                </a>
                                    <div class="progress rowhg">
                                        当前进度：${wyApprove.currTaskName}
                                        <div class="bar"><img src="${ctxStatic}/image/符号-进度条.png" style="width: 100%" onclick="detail('${wyApprove.procInstId}', this)"></div>
                                    </div>
                                    <!-- post内容 -->
                                    <div class="rowhg">
                                        报修内容：
                                        <c:if test="${fn:length(wyApprove.content)>10}">
                                            ${fn:substring(wyApprove.content, 0, 10)}...
                                        </c:if>
                                        <c:if test="${fn:length(wyApprove.content)<=10}">
                                            ${wyApprove.content}
                                        </c:if>
                                    </div>
                                    <div class="toolbar rowhg">
                                        <p>申请时间：
                                            <fmt:formatDate value="${wyApprove.startTime}" pattern="yyyy/MM/dd HH:mm" />
                                        </p>
                                    </div>
                            </div>
                            <!-- 结束 post -->
                        </div>
                    </div>
                </c:forEach>
                <!-- 结束 朋友圈 -->
            </c:if>
            <script src="${ctxStatic}/jqweui2/js/jquery-2.1.4.js"></script>
            <script src="${ctxStatic}/jqweui2/js/fastclick.js"></script>
            <script type="text/javascript">
                //选择tab标签
                function selectTab(flag) {
                    window.location.replace('${ctx}/wuye/myAffairs?flag=' + flag);
                }
                //显示详情
                function show(bizId, procInsId) {
                    window.location.href = '${ctx}/wuye/form2?bizId=' + bizId + "&procInsId=" + procInsId;
                }
                //搜索  
                function search(jq) {
                    var keyword = $(jq).val();
                    if (keyword == undefined || keyword == "") {
                        return;
                    }
                    $("#search-form").submit();
                }

                $("#select-type").change(function() {
                    $("#search-form").submit();
                });
                //选择后进行搜索
                function selectType(jq) {
                    var type = $(jq).val();
                    alert(type);

                }

                function detail(procInstId, event) {
                    stopBubbling(event);
                    window.location.href = "${ctx}/wuye/processDetail?procInstId=" + procInstId;
                }

                function stopBubbling(e) {
                    e = window.event || e;
                    if (e.stopPropagation) {
                        e.stopPropagation(); //阻止事件 冒泡传播
                    } else {
                        e.cancelBubble = true; //ie兼容
                    }
                }
                $(".searchInput").on('keypress',function(e) {  
                    var keycode = e.keyCode;  
                    if(keycode=='13') { 
                        var keyword = $(jq).val();
                        if (keyword == undefined || keyword == "") {
                            return;
                        }
                        $('#search-form').submit();
                    }
                });  
            </script>
            <script src="${ctxStatic}/js/jquery-weui.min.js"></script>
            <script src="${ctxStatic}/js/jquery.min.js"></script>
        </body>

        </html>